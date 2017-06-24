<%@ page language="java" pageEncoding="UTF-8"%>
<html>
  <head>
    <title>代码生成器</title>
  </head>
  
  <body> 
  <div align="center">
<form action="${pageContext.request.contextPath}/person/saveOrUpdate.action" method="post" enctype="multipart/form-data">
     <h2>新增用户</h2>
<table>
<tr>
    <td>id:</td>
    <td><input type="text" name="id"/></td>
</tr>
<tr>
    <td>name:</td>
    <td><input type="text" name="name"/></td>
</tr>
<tr>
    <td>age:</td>
    <td><input type="text" name="age"/></td>
</tr>
<tr>
    <td>photo:</td>
    <td><input type="file" name="photoPathxx"/></td>
</tr>
<tr>
    <td colspan="2">
        <input type="submit"/>
    </td>
</tr>
</table>
<a href="JavaScript:;" onClick="javascript:history.back(-1);">返回上一页</a>
    </div>
</form>    
  </body>
</html>