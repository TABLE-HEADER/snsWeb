<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList"%>
<html>
	<head>
		<title>エラー</title>
	</head>
	<body>

		<%@include file= "/common/header.jsp" %>

		<%
		String error = (String)request.getAttribute("error");
		String cmd = (String)request.getAttribute("cmd");
		%>

		<p align="center">
		●●エラー●●<br>
		<%=error%>
		</p>

		<br>

		<p align="center">
		<%
		if(cmd.equals("mainpage")) {
		%>
			<a href="<%=request.getContextPath() %>/mainpage">＜トップへ戻る＞</a>
		<%
		}
		else if(cmd.equals("list")) {
		%>
			<a href="<%=request.getContextPath() %>/list">＜お問い合わせ一覧に戻る＞</a>
		<%
		}
		else {
		%>
			<a href="<%=request.getContextPath() %>/signout">＜サインイン画面へ＞</a>
		<%
		}
		%>
		</p>

	</body>
</html>