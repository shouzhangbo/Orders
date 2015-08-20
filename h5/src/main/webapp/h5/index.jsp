<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>HTML5的标题</title>
	</head>
<body>
<form action="createOrder.json" method="post">
	请输入要冲值的手机号：<input type="text" class="" name="mobile" id="mobile" /><br/>
	请选择充值金额：
	<select name="amount" id="amount" class="">
		<option value='1000' selected>10元</option>
		<option value='2000'>20元</option>
		<option value='5000'>50元</option>
		<option value='10000'>100元</option>
		<option value='20000'>200元</option>
	</select><br/>
	用户名：<input type="text" name="userId" value=""  />
	<input type="submit" class="" id="recharge" value="充值" /><br/>
</form>
	<form action="query.json" method="post">
	<input type="text" name="mobile"  id=""  /><input type="submit" value="查询历史订单" />
	</form>
</body>
</html>
