package com.energicube.eno.pattern.model;
// Generated 2013-8-21 16:23:12 by Hibernate Tools 3.4.0.CR1


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * UcLogicGroup generated by hbm2java
 */
@Entity
@Table(name = "UC_logicGroup"
        
)
public class UcLogicGroup implements java.io.Serializable {


    private String id;
    private String groupName;
    private String systemId;
    private String parentId;
    private String groupCode;
    private String siteId;
    private String orgId;
    private String groupType;
    private Set<UcGroupDevice> ucGroupDevices = new HashSet<UcGroupDevice>(0);

    public UcLogicGroup() {
    }


    public UcLogicGroup(String id) {
        this.id = id;
    }

    public UcLogicGroup(String id, String groupName, String systemId, String parentId, Set<UcGroupDevice> ucGroupDevices) {
        this.id = id;
        this.groupName = groupName;
        this.systemId = systemId;
        this.parentId = parentId;
        this.ucGroupDevices = ucGroupDevices;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Column(name = "groupName", length = 60)
    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    @Column(name = "systemId", length = 60)
    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }


    @Column(name = "parentId", length = 36)
    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ucLogicGroup")
    public Set<UcGroupDevice> getUcGroupDevices() {
        return this.ucGroupDevices;
    }

    public void setUcGroupDevices(Set<UcGroupDevice> ucGroupDevices) {
        this.ucGroupDevices = ucGroupDevices;
    }

    @Column(name = "siteId", length = 36)
    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @Column(name = "orgId", length = 36)
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Column(name = "groupCode", length = 100)
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Column(name = "groupType", length = 4)
    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
}


