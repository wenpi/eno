<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 计量仪对话框 -->
<div id="dlgMeter" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<div class="dialog-close"  data-dismiss="modal" aria-hidden="true">×</div>
		<p class="dialog-title" id="myModalLabel">计量器</p>
	</div>
	<div class="modal-body">		
	
	<iframe id="winMeter" link="${locsystemsUrl}" scrolling="auto"
		marginheight="0" marginwidth="0" frameborder="0" src=""
		style="width: 100%; min-height:600px;"></iframe>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	</div>
</div>