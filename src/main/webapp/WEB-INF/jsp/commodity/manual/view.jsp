<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html>  
  <head>  
    <title>自定义分类商品详情页</title>  
  </head>  
    
  <body>  
 
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
				<td>${ commManual.comManualCode}</td>
			</tr>
			<tr>											 
			   	<td>商品名称</td>
				<td>${ commManual.comManualName}</td>
			</tr>
			<tr>											 
			   	<td>商品编码集合</td>
				<td>${ commManual.comCodeList}</td>
			</tr>
			
	</tbody>	
	</table>
  </body>  
</html>  