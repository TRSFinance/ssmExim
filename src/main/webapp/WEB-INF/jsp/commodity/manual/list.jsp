<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
   <%@include file="../../common/taglib.jsp" %>
<html>  
  <head>  
    <title>自定义分类商品列表页</title>  
  </head>  
    
    
  <body>  
 
  <table border="1" bordercolor="#a0c6e5" style="border-collapse:collapse">
  	<!-- 表头信息开始 -->
  	<thead>
  		<tr>
			<td>序号</td>
			<td>商品编码</td>
			<td>商品名称</td>
			<td>商品编码集合</td>
			<td>操作</td>					
		</tr>
	</thead>  
	<!-- 表头信息结束 -->
	<tbody>
	    <!--  任务循环开始  -->
		<c:if test="${!empty data}"> 
		    <c:forEach items="${data}" var="CommodityManualDict" varStatus="status">
			  	<tr>											 
				   	<td>${ status.index  + 1} </td>
					<td>${ CommodityManualDict.comManualCode}</td>
					<td width="50%">${ CommodityManualDict.comManualName}</td>
					<td>${ CommodityManualDict.comCodeList}</td>
					<td>
						<a href="<%=ctx%>/commManual/view?key=${CommodityManualDict.comManualCode}">
							<i></i>查看
						</a>
						<a href="<%=ctx%>/commManual/v_edit?key=${CommodityManualDict.comManualCode}">
							<i></i>修改
						</a>
						<a href="<%=ctx%>/commManual/del?key=${CommodityManualDict.comManualCode}">
<%-- 						<a onclick="dels(${CommodityManualDict.comManualCode})"> --%>
							<i></i>删除
						</a>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<!--  任务循环结束  -->
	</tbody>	
	</table>
  </body>  
</html>  