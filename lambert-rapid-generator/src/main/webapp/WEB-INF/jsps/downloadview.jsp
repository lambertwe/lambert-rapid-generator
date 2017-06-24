<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>代码生成器</title>
</head>

<body>
	<div align="center">
	<h2>${data.xml}配置文件详细</h2>

	<table border="1" cellpadding="10">
		<tr>
			<td>用户名：</td>
			<td>${data.username}</td>
		</tr>
		<tr>
			<td>密码：</td>
			<td>${data.password}</td>
		</tr>
		<tr>
			<td>连接url：</td>
			<td>${data.url}</td>
		</tr>
		<tr>
			<td>数据库连接url：</td>
			<td>${data.driver}</td>
		</tr>
		<tr>
			<td>数据连接schema：</td>
			<td>${data.schema}</td>
		</tr>
		<tr>
			<td>选择表：</td>
			<td><select name="select" id="xml">
					<c:forEach items="${data.tables}" var="p">
						<option value="${p.sqlName}">${p.sqlName}</option>
					</c:forEach>
			</select> 
				<a href="#" onclick="genTable()">生成</a>
			</td>
		</tr>
		<tr>
			<td><a
				href="${pageContext.request.contextPath}/download/tables?xml=${data.xml}">生成全部</a>
			</td>
		</tr>

		<script type="text/javascript">
			function dd() {
				var sel = document.getElementsByName("select")[0];
				var selvalue = sel.options[sel.options.selectedIndex].value//你要的值
				window.location.href ="${pageContext.request.contextPath}/download/tables?xml=${data.xml}&table="+selvalue;
			}
			function genTable(){
				dd();
			}
		</script>
	</table>
	<a href="JavaScript:;" onClick="javascript:history.back(-1);">返回上一页</a>
	</div>
</body>
</html>