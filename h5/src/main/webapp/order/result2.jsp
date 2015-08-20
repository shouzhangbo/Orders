<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" />
<html>
	<head>
		<meta charset="UTF-8">
		<title>HTML5的标题</title>
	</head>
<body>
	<h1>充值记录</h1>
	<c:forEach var="order" items="${list}">
		<div>
			<em>手机号：</em><em><c:out value="${order.mobile }" /></em>
			<em>金额：</em><em><c:out value="${order.amount }" /></em>
			<em>用户名：</em><em><c:out value="${order.userId }" /></em>
		</div>
	</c:forEach>
	<input type="button" id="" class="" onclick="javascript:history.go(-1)" value="返回" />
</body>
</html>
