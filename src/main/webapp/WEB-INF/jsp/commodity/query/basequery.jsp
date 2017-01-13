<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="../../common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本查询</title>
<%@include file="../../common/jscss.jsp" %>
<script type="text/javascript">
	function initMenu() {
  $('.EIU-menu ul').hide();
  $('.EIU-menu ul:first').show();
  $('.EIU-menu li a').click(
    function() {
      var checkElement = $(this).next();
      if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
         checkElement.slideUp('normal');
		 $(this).css('background','url(<%=request.getContextPath() %>/style/img/EIU-li2.png) no-repeat left center');
        }
    if((checkElement.is('ul')) && (!checkElement.is(':visible'))){
        checkElement.slideDown('normal');
		$(this).css('background','url(<%=request.getContextPath() %>/style/img/EIU-li.png) no-repeat left center');
        return false;
        }
      }
    );
  }
$(document).ready(function() {
	initMenu();
	$('.EIU-cal').children('option').hide();
	$('.EIU-cal').click(function(){
		   $('.EIU-sel').show();		   
		});
	$('.EIU-sel h2 span').click(function(){		       	   
		   $('.EIU-sel').hide();		   
		});	
	
	 $("#login").hover(function(){
		 $(".login-dis").show();
   });
$("#login").mouseleave(function(){
	  $(".login-dis").hover(function(){	     
		  $(this).show();
      });
	  $(".login-dis").mouseleave(function(){
		$(this).hide();
		});

   });
$("#rd").hover(function(){
		 $(".rdlj").show();
   });
$("#rd").mouseleave(function(){
	  $(".rdlj").hover(function(){	     
		  $(this).show();
      });
	  $(".rdlj").mouseleave(function(){
		$(this).hide();
		});

   });
	
	});
</script>

</head>

<body>
<div class="top">
  <div class="top-m">
    <div class="top-l">
      <p id="rd">热点链接<span></span></p>
      <a href="#">设为首页</a> 
    </div>
    
    <div class="top-r">
      <p class="login" id="login">登陆</p>
      <a href="#">用户注册<span></span></a> <img src="<%=request.getContextPath() %>/style/img/top-l.png" />
      <p style="margin-right:0;">搜索：</p>
      <div class="top-sel">
        <input  class="sel-ipt"/>
        <input type="button"  class="sel-btn"/>
      </div>
      
    </div>
  </div>
</div>
<div style="width:1200px; margin:0 auto;">
<div class="rdlj">
       <ul>
          <li>
            <a href="#">
            <img  src="<%=request.getContextPath() %>/style/img/xw.png" width="22" height="22"/>
            <p>新闻</p></a>
          </li>
          <li>
            <a href="#">
            <img  src="<%=request.getContextPath() %>/style/img/xw.png" width="22" height="22"/>
            <p>音乐</p></a>
          </li>
          <li>
            <a href="#">
            <img  src="<%=request.getContextPath() %>/style/img/xw.png" width="22" height="22"/>
            <p>公开课</p></a>
          </li>
          <li>
            <a href="#">
            <img  src="<%=request.getContextPath() %>/style/img/xw.png" width="22" height="22"/>
            <p>彩票</p></a>
          </li>
       </ul>
    </div>
<form class="login-dis">
         <input style=" margin-top:28px;" value="请输入用户名" onfocus="javascript:if(this.value=='请输入用户名')this.value='';"/>
         <input value="请输入密码" onfocus="javascript:if(this.value=='请输入密码')this.value='';"/>
         <a href="#">忘记密码？</a>
         <input type="button" value="登陆"  class="login-btn" />
      </form>
      </div>
<div class="main">
<div style="width:1200px; margin:0 auto;">
  <img src="<%=request.getContextPath() %>/style/img/logo.gif"  class="logo"/>
  <div class="menu"> 
    <ul>
      <li class="menu-on"><a href="#"><span class="li-l"></span>
        <p>首 页</p>
        <span class="li-r"></span></a></li>
      <li><a href="#"><span class="li-l"></span>
        <p>宏观经济</p>
        <span class="li-r"></span></a></li>
      <li><a href="#"><span class="li-l"></span>
        <p>金融同业</p>
        <span class="li-r"></span></a></li>
      <li><a href="#"><span class="li-l"></span>
        <p>行业</p>
        <span class="li-r"></span></a></li>
      <li><a href="#"><span class="li-l"></span>
        <p>企业</p>
        <span class="li-r"></span></a></li>
      <li><a href="#"><span class="li-l"></span>
        <p>专栏</p>
        <span class="li-r"></span></a></li>
      <li><a href="#"><span class="li-l"></span>
        <p>定制信息</p>
        <span class="li-r"></span></a></li>
    </ul>
    <a href="#" class="menu-a">部门共享</a> </div>
</div>
</div>

<!--头部结束-->
<div class="gnhg-title">
   <h1>贸易商品</h1>
   <p>基本数据查询</p>
</div>
<div class="gnhg-con search">
      <div class="gnhg-menu gl-ser search2 fgwjg">
      
      <table cellpadding="0" cellspacing="0">
      <tr>
      <td><span  style="margin-left: 10px;">年份:</span></td>  
      <td>
      <select class="EIU-ipt">
            <option>2012</option>
            <option>2013</option>
            <option>2014</option>
            <option>2015</option>
            <option>2016</option>      
      </select>
      </td>
      <td><span style="margin-left: 10px;">贸易方向:</span></td>  
      <td> <select class="EIU-ipt">
            <option>进口</option>
            <option>出口</option>
            <option>再出口</option>
     </select></td>
      <td><select class="EIU-ipt EIU-cal"><option>报告国</option></select> </td>
     
      <td><select class="EIU-ipt EIU-cal"><option>对象国</option></select></td>  
     
      </tr>
      <tr>
      
      <td></td>
      <td><input  type="button"  class="gl-btn2" style="float:left; margin-left:20px;"/>
      </td>
      </tr>
      </table>
      </div>	

      <div class="hy-con EIU-sel">
          <h2> <span></span>国别选择</h2>
          <ul class="EIU-menu">
          <li class="on">
			<a href="#">非洲</a>
			<ul>
				<li><a href="#"><input type="checkbox"/>乌干达</a></li>
				<li><a href="#"><input type="checkbox"/>埃塞俄比亚</a></li>
                <li><a href="#"><input type="checkbox"/>圣多美及普林西比</a></li>
			</ul>
		</li>
		<li>
			<a href="#">欧州</a>
			<ul>
				<li><a href="#"><input type="checkbox"/>乌干达</a></li>
				<li><a href="#"><input type="checkbox"/>埃塞俄比亚</a></li>
                <li><a href="#"><input type="checkbox"/>圣多美及普林西比</a></li>
			</ul>
		</li>
		<li>
			<a href="#">亚州</a>
			<ul>
				<li><a href="#"><input type="checkbox"/>乌干达</a></li>
				<li><a href="#"><input type="checkbox"/>埃塞俄比亚</a></li>
                <li><a href="#"><input type="checkbox"/>圣多美及普林西比</a></li>
			</ul>
		</li>
        <li>
			<a href="#">亚州</a>
			<ul>
				<li><a href="#"><input type="checkbox"/>乌干达</a></li>
				<li><a href="#"><input type="checkbox"/>埃塞俄比亚</a></li>
                <li><a href="#"><input type="checkbox"/>圣多美及普林西比</a></li>
			</ul>
		</li>
	</ul>
      </div>
       <table cellpadding="0" cellspacing="0" class="fgw-tab EIU-tab">
          <tr class="title">
             <td><span>|</span>报告类型</td>
             <td><span>|</span>国别</td>
             <td><span>|</span>报告名称</td>
             <td><span>|</span>报告名称</td>
             <td><span>|</span>报告名称</td>
             
             <td>采集日期</td>
          </tr>
          <tr>
             <td class="tc-on">国别风险报告</td>
               <td >圣多美及普林西比</td> <td >圣多美及普林西比</td><td >圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
         
          <tr class="on">
              <td class="tc-on">国别风险报告</td>
                                <td >圣多美及普林西比</td> <td >圣多美及普林西比</td><td >圣多美及普林西比</td>
             
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
          <tr>
             <td class="tc-on">国别风险报告</td>
             <td>圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
         
          <tr class="on">
              <td class="tc-on">国别风险报告</td>
                                <td >圣多美及普林西比</td> <td >圣多美及普林西比</td><td >圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
           <tr>
             <td class="tc-on">国别风险报告</td>
             <td>圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
         
          <tr class="on">
              <td class="tc-on">国别风险报告</td>
                                <td >圣多美及普林西比</td> <td >圣多美及普林西比</td><td >圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
           <tr>
             <td class="tc-on">国别风险报告</td>
                                <td >圣多美及普林西比</td> <td >圣多美及普林西比</td><td >圣多美及普林西比</td>
             <td>Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
         
          <tr class="on">
              <td class="tc-on">国别风险报告</td>
                              <td >圣多美及普林西比</td> <td >圣多美及普林西比</td><td >圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
           <tr>
             <td class="tc-on">国别风险报告</td>
             <td>圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
         
          <tr class="on">
              <td class="tc-on">国别风险报告</td>
                              <td >圣多美及普林西比</td> <td >圣多美及普林西比</td><td >圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
           <tr>
             <td class="tc-on">国别风险报告</td>
             <td>圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
         
          <tr class="on">
              <td class="tc-on">国别风险报告</td>
                              <td >圣多美及普林西比</td> <td >圣多美及普林西比</td><td >圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
          <tr>
             <td class="tc-on">国别风险报告</td>
             <td>圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
         
          <tr class="on">
              <td class="tc-on">国别风险报告</td>
                              <td >圣多美及普林西比</td> <td >圣多美及普林西比</td><td >圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
          <tr>
             <td class="tc-on">国别风险报告</td>
             <td>圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
         
          <tr class="on">
              <td class="tc-on">国别风险报告</td>
                              <td >圣多美及普林西比</td> <td >圣多美及普林西比</td><td >圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
          <tr>
             <td class="tc-on">国别风险报告</td>
             <td>圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
         
          <tr class="on">
              <td class="tc-on">国别风险报告</td>
                              <td >圣多美及普林西比</td> <td >圣多美及普林西比</td><td >圣多美及普林西比</td>
             <td >Country Risk Service-Updater:November 13th 2014</td>
             <td>2014-01-12</td>
          </tr>
       </table>
      <h2 class="page-p" style="float:left; margin-left:220px;margin-top:30px;margin-right:40px; "><p>共有<span>1194</span>条记录  页码:1/120页  每页显示20条</p></h2>
      <div class="gl-page" style="margin-top:40px; margin-left:10px;">
          
         <a href="#">首页</a>  <a href="#" class="gl-page-on">1</a>  <a href="#">2</a>  <a href="#">3</a>  <a href="#">下一页</a>  <a href="#">尾页</a>
      </div>
</div>
<!--内容结束-->

<div class="foot">
   <p>中国进出口银行©2014 版权所有</p>
</div>
</body>
</html>
