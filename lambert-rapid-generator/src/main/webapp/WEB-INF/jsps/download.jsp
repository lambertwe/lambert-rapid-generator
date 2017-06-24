<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <title>代码生成器</title>
  </head>
  
  <body>
  	<div align="center">
  		<form action="${pageContext.request.contextPath}/download/addXml" method="post" enctype="multipart/form-data">
		     <h2>新增配置文件</h2>
			<table>
			<tr>
			    <td>新增XML:</td>
			    <td><input type="file" name="xml"/></td>
			</tr>
			<tr>
			    <td colspan="2">
			        <input type="submit"/>
			    </td>
			</tr>
			</table>
		    
		</form>  
		<h2>文件列表</h2>
		<table border="1" cellpadding="10">
			<tr>
			    <td>文件名</td>
			    <td>操作</td>
			</tr>
			<c:forEach items="${result}" var="p">
			<tr>
			    <td>${p}</td>
			    <td>
			        <a href="${pageContext.request.contextPath}/download/showXml?id=${p}">查看</a>
			        <a href="${pageContext.request.contextPath}/download/downXml?id=${p}">下载</a>
			        <a href="${pageContext.request.contextPath}/download/deleteXml?delId=${p}">删除</a>
			    </td>
			</tr>
			</c:forEach>
		</table>
		<div align="center">
  			<h4>说明：</h4>
  			<h5>
  				1.本代码生成器只需上传generator-*。xml的配置文件<br>
  				2.如要修改配置文件请将配置文件下载后修改上传即可覆盖<br>
  				原来的文件，也可以删除原来的文件，在上传！<br>
  				3.配置好之后点击查看后进入需要下载的列表，根据需求选择下载表；<br>
  				谢谢使用！<br>
  			</h5>
  		</div>
  	</div>
  </body>
</html>