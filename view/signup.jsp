<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList"%>

<%
String name		= request.getParameter("name") != null ? request.getParameter("name") : "";
String userid	= request.getParameter("userid") != null ? request.getParameter("userid") : "";
String year		= request.getParameter("year") != null ? request.getParameter("year") : "";
String month	= request.getParameter("month") != null ? request.getParameter("month") : "";
String day		= request.getParameter("day") != null ? request.getParameter("day") : "";
String email	= request.getParameter("email") != null ? request.getParameter("email") : "";
String pass1	= request.getParameter("pass1") != null ? request.getParameter("pass1") : "";
String pass2	= request.getParameter("pass2") != null ? request.getParameter("pass2") : "";

 %>

<html>
	<head>
		<title>ログイン</title>
		<link rel="stylesheet" href="<%=request.getContextPath() %>/view/style.css">
	</head>
	<body id="bg">

		<div align="center"><a href="<%=request.getContextPath() %>/mainpage">
		<img src="<%=request.getContextPath() %>/file/kandan_b.png"  alt="kandan" width="461px" height="83px" style="margin: 50px 0 0">
		</a></div>

		<h1 style="margin:20px" align="center">新規登録</h1>

		<%
		ArrayList<String> formMessage = (ArrayList<String>)request.getAttribute("formMessage");
		if(formMessage != null && formMessage.size() >= 1) {
			%>
			<p align="center">
				<font size="5" color="yellow">
					** 入力に誤りがあります。以下のご確認をお願いします。 **<br>
				</font>
				<font size="4" color="yellow">
					<%
					for(String message : formMessage){
						%>
						<%= message %><br>
						<%
					}
					%>
				</font>
			</p>
			<%
		}
		%>

		<form action="<%=request.getContextPath()%>/signup" method="post">

			<table style="border-spacing:10px;" align="center" >
					<tr>
						<th width="200" align="right">名前：</th>
						<td><input type=text size="30" name="name" value="<%=name %>"></td>
					</tr>
					<tr>
						<th width="200" align="right">ユーザーID：</th>
						<td>@<input type=text size="27" name="userid" placeholder="半角英数字を入力" value="<%=userid %>"></td>
					</tr>
					<tr>
						<th width="200" align="right">生年月日：</th>
						<td>
						<input type=text size="5" name="year" placeholder="年" value="<%=year %>">/
						<input type=text size="5" name="month"  placeholder="月" value="<%=month %>">/
						<input type=text size="5" name="day"  placeholder="日" value="<%=day %>">
						</td>
					</tr>
					<tr>
						<th width="200" align="right">メールアドレス：</th>
						<td><input type=text size="30" name="email" value="<%=email %>"></td>
					</tr>
					<tr>
						<th width="200" align="right">パスワード：</th>
						<td><input type=text size="30" name="pass1" placeholder="半角英数字8文字以上" value="<%=pass1 %>"></td>
					</tr>
					<tr>
						<th width="200" align="right">パスワード（確認用）：</th>
						<td><input type=text size="30" name="pass2" value="<%=pass2 %>"></td>
					</tr>
			</table>

			<p align="center" style="margin: 20px"><input type="checkbox" name="privacy" value="1">&nbsp;アカウントを非公開にする</p>

			<p align="center" style="margin: 20px"><input type="submit" value="登録"></p>

		</form>

	</body>
</html>