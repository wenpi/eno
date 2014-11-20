package com.energicube.eno.message.redis;

import com.energicube.eno.monitor.model.AssetMeasurement;
import com.energicube.eno.monitor.model.OkcLogs;
import com.energicube.eno.monitor.service.AssetLogsService;
import com.energicube.eno.monitor.service.OkcLogsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Redis操作服务
 * <p/>
 * <pre>
 * 主要包括以下功能
 * 1、向指定的频道发送值
 * 2、向chan:set_rtdb频道发送更新信息
 * 3、hGetAllByKeys 获取指定健集合的域值
 * 4、hGetAllValueByKey 获取指定健的域值
 * 5、hGetAllByKey 获取指定key的所有域
 * 6、mGetByKeys 获取values by keys
 * </pre>
 */
@Service
public class RedisOpsService {

    private static final Log logger = LogFactory.getLog(RedisOpsService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private OkcLogsService okcLogsService;

    @Autowired
    private AssetLogsService assetLogsService;
    /*private final SimpMessageSendingOperations messagingTemplate;
	
	@Autowired
	public RedisOpsService(SimpMessageSendingOperations messagingTemplate){
		this.messagingTemplate = messagingTemplate;
	}
	*/

    /**
     * 通过指定的通道更新对象
     *
     * @param channel 通道名称
     * @param message 消息对象
     */
    public void sendMessage(String channel, Serializable message) {
        redisTemplate.convertAndSend(channel, message);
    }

    /**
     * 发送测点更新信息
     *
     * @param taginfo 测量点信息
     *                <p/>
     *                <pre>
     *                for example： {a:"setval",id:123,t:12122.33,p:2,v:22.2}
     *                </pre>
     */
    public void sendTagInfo(TagInfo taginfo) {

//		logger.debug("send taginfo:" + taginfo.toSendString());

		/*if("".equals(taginfo.toSendString())) {
			String payload = "Rejected taginfo " + taginfo;
			this.messagingTemplate.convertAndSendToUser("guest","/queue/errors", payload);
			return;
		}*/
        sendMessage("chan:from_all_to_uckernal:value", taginfo.toSendString());
        // 记录日志信息  [ ChengKang 2014-08-02 ]
        OkcLogs Logs = new OkcLogs();
        String AssetTagid = taginfo.getId().toString();
        String TagValues = taginfo.getV().toString();

        // 根据TagId查找资产数据库，获得一些资产的信息，写成一个String [liyinhao 2014-08-14]
        AssetMeasurement AssetMByTag = null;
        AssetMeasurement AssetMByTagId = null;
        try {
            AssetMByTag = (AssetMeasurement) assetLogsService.findByTag(AssetTagid, TagValues);
            AssetMByTagId = (AssetMeasurement) assetLogsService.findByTagId(AssetTagid);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String locations = AssetMByTag.getLocationName();
        String assetattribute = AssetMByTag.getAssetName();
        String tagvalue = AssetMByTag.getTagvalue();

        String tagidStr = AssetMByTagId.getTagId();
        String tagnamestrStr = AssetMByTagId.getTagname();

        String strlogs = locations + "    " + tagidStr + "    " + tagnamestrStr + "    " + assetattribute + "    " + tagvalue;
        Logs.WriteLogMsg(this.getClass().getName(), OkcLogs.LOG_TAGINFO, strlogs);
        try {
            okcLogsService.save(Logs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * redis命令发送
     *
     * @param commandInfo
     */
    public void sendCommand(CommandInfo commandInfo) {
//		logger.debug("send taginfo:" + commandInfo.toSendString());
        // 记录日志信息  [ ChengKang 2014-08-02 ]
        OkcLogs Logs = new OkcLogs();
        String Message = "Update TagInfo : [ " + commandInfo.toSendString() + " ]";
        Logs.WriteLogMsg(this.getClass().getName(), OkcLogs.LOG_COMMAND, Message);
        try {
            okcLogsService.save(Logs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendMessage("chan:from_all_to_uckernal:command", commandInfo.toSendString());
    }

    /**
     * redis面板发送客流命令
     *
     * @param commandInfo
     */
    public void sendPfeCommand(PassengerCmdInfo pfeInfo) {
        logger.debug("send pfeInfo:" + pfeInfo.toSendString());

        // 记录日志信息  [ ChengKang 2014-08-02 ]
        OkcLogs Logs = new OkcLogs();
        String Message = "Update TagInfo : [ " + pfeInfo.toSendString() + " ]";
        Logs.WriteLogMsg(this.getClass().getName(), OkcLogs.LOG_PREINFO, Message);
        try {
            okcLogsService.save(Logs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendMessage("chan:tomcat_2_l2_4_passenger", pfeInfo.toSendString());
    }

    /**
     * redis面板发送表达式语句
     *
     * @param commandInfo
     */
    public void sendExpression(Map<String, String> map) {
        if (map.size() > 0) {
            //起始
            sendMessage("chan:from_all_to_uckernal:command", "{\"cmd\":\"BEGIN_SPY_EXPGROUP\",\"p1\":\"chan:uckernal_2_tomcat:expvalue\"}");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                try {
                    String str = String.format("{\"cmd\":\"%s\",\"p1\":\"%s\",\"p2\":\"%s\",\"p3\":0,\"p4\":\"%s\"}", "ADD_SPY_EXPGROUP", "chan:uckernal_2_tomcat:expvalue", entry.getValue(), entry.getKey());
                    sendMessage("chan:from_all_to_uckernal:command", str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //结束
            sendMessage("chan:from_all_to_uckernal:command", "{\"cmd\":\"END_SPY_EXPGROUP\",\"p1\":\"chan:uckernal_2_tomcat:expvalue\"}");
        }
    }


    /**
     * 获取指定键集合的域值
     *
     * @param keys 键集合
     * @return 值列表
     */
    public List<String> hGetAllByKeys(Collection<String> keys) {
        List<String> result = new ArrayList<String>();
        for (String key : keys) {
            result.add(hGetAllValueByKey(key));
        }
        ;
        return result;
    }

    /**
     * 获取指定键的域值
     * <p/>
     * <pre>command line:hgetall tags:51</pre>
     *
     * @param key 键名称
     * @return 值对象
     */
    public String hGetAllValueByKey(String key) {
        Map<byte[], byte[]> map = redisTemplate.getConnectionFactory().getConnection().hGetAll(key.getBytes());
        if (map.size() > 0 && map.containsKey("Value".getBytes())) {
            byte[] val = map.get("Value".getBytes());
            return new String(val, 0, val.length);
        }
        return null;
    }

    /**
     * 获取指定键的全部域
     *
     * @param key 键名称
     * @return Map of fields
     */
    public Map<byte[], byte[]> hGetAllByKey(String key) {
        return redisTemplate.getConnectionFactory().getConnection().hGetAll(key.getBytes());
    }


    /**
     * 获取指定的域的值
     *
     * @param fields 域数组
     * @return list of {@link TagInfo}
     */
    public List<TagInfo> mGetByKeys(String[] fields) {
        List<TagInfo> result = new ArrayList<TagInfo>();


        byte[][] args = new byte[fields.length][];
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] != null)
                args[i] = fields[i].getBytes();
        }

        //Redis mget command line
        List<byte[]> list = redisTemplate.getConnectionFactory().getConnection().mGet(args);

        //解析结果
        for (byte[] data : list) {
            String content = new String(data, 0, data.length);
            if (StringUtils.hasLength(content)) {
                TagInfo taginfo = deserialize(content);
                result.add(taginfo);
            }
        }

        return result;
    }

    private final ObjectMapper mapper = new ObjectMapper();
    //private JsonNode node;

    /**
     * 反序列化字符内容
     *
     * @param content JSON Content
     * @return {@link TagInfo}
     */
    public TagInfo deserialize(String content) {
        //判断首尾字符是否为引号，如果是则移除
        String strJson = "";
        if (content.substring(0, 1).equals("\"")) {
            strJson = content.substring(1, content.length());
        }
        if (content.substring(content.length() - 1, content.length()).equals("\"")) {
            strJson = content.substring(1, content.length() - 1);
        }

        TagInfo taginfo = null;
        try {
            taginfo = mapper.readValue(strJson, TagInfo.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return taginfo;
    }
}
