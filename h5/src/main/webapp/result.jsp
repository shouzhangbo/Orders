<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ tagliburi="http://java.sun.com/jsp/jstl/core"prefix="c"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>HTML5的标题</title>
	</head>
<body>
	<h1>webapp</h1>
	<input type="hidden" id="flag" value="${list}" />
	<div>
		<form id="form1" action="queryOrder.json" method="post">
		<div>
			手机号：<input name="mobile" />
			用户id：<input name="userId" />
			<input type="submit" value="查询" />
		</div>
		<table style="border:1;bordercolor:#000000;">
			<tr>
				<td>
					id
				</td>
				<td>
					手机号
				</td>
				<td>
					金额
				</td>
				<td>
					状态
				</td>
				<td>
					时间
				</td>
			</tr>
			 <c:forEach var="order" items="${list}">
			 	<tr>
				<td>
					<c:out value="${order.orderId}" />
				</td>
				<td>
					<c:out value="${order.mobile}" />
				</td>
				<td>
					<c:out value="${order.amount}" />
				</td>
				<td>
					<c:out value="${order.statusName}" />
				</td>
				<td>
					<c:out value="${order.createdAt}" />
				</td>
			</tr>
			</c:forEach>
		</table>
		<div>
			<input type="hidden" id="page" name="page" value="${page }" />
			<input type="hidden" id="pageSize" name="pageSize" value="${pageSize }" />
			<input type="button" id="upPage" value="上一页" />/
			<input type="button" id="downPage" value="下一页" />
		</div>
		</form>
	</div>
	<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
	<script>
		$(function(){
			var flag = $('#flag').val();
			if(null==flag||''==flag||'null'==flag){
				$('#page').val('1');
				$('#pageSize').val('10');
				$('#form1').submit();
			}
			console.log($('#page').val());
			$('#upPage').click(function(){
				$('#page').val(parseInt($('#page').val())-1);
				$('#form1').submit();
			});
			$('#downPage').click(function(){
				$('#page').val(parseInt($('#page').val())+1);
				$('#form1').submit();
			});
		})
	</script>
</body>
</html>
