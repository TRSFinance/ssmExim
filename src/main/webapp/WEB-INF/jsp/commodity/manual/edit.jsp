<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
   <%@include file="../../common/taglib.jsp" %>
<html>  
  <head>  
    <title>自定义分类商品编辑页</title>  
  </head>  
    
<body>  
 <form action="<%=ctx%>/commManual/edit" method="post">
	<table border="1" bordercolor="#a0c6e5" style="border-collapse:collapse">
  		<!-- 表头信息开始 -->
		<thead>
	  		<tr>
				<td>参数</td>
				<td>值</td>					
			</tr>
		</thead>  
		<!-- 表头信息结束 -->
		<tbody> 
		  	<tr>											 
			   	<td>商品编码 </td>
				<td><input name="key" type="text" value="${ commManual.comManualCode}" readOnly="true" style="border:0"></td>
			</tr>
			<tr>											 
			   	<td>商品名称</td>
				<td><input name="comManualName" type="text" value="${ commManual.comManualName}"></td>
			</tr>
			<tr>											 
			   	<td>商品编码集合</td>
				<td><input name="comCodeList" type="text" value="${ commManual.comCodeList}"></td>
			</tr>
			<tr>
				<td colspan="2" class="td-center">				
				<input type="button"
					class="btn btn-primary " value="返回" onclick="winback()">									

				<input type="submit"
					class="btn btn-primary " value="保存">
				</td>
			</tr>	
		</tbody>	
	</table>
</form>
</body>  
</html>  