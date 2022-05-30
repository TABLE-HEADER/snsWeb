<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList,util.ImageConvert"%>

<%

// セッションからユーザー情報を取得
User user_prof = (User)session.getAttribute("user");
// セッション切れか確認
if(user_prof == null){
	//セッション切れならerror.jspへフォワード
	request.setAttribute("error","セッションが切れています。");
	request.setAttribute("cmd","signout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}

String[] birthday = {"", "", ""};
if(user_prof.getBirthday() != null){
	birthday = user_prof.getBirthday().split("-");
}

String[] phone = {"", "", ""};
if(user_prof.getPhone() != null){
	phone = user_prof.getPhone().split("-");
}

String profile	= user_prof.getProfile() != null ? user_prof.getProfile() : "";
String address	= user_prof.getAddress() != null ? user_prof.getAddress() : "";

String name		= request.getParameter("name") != null ? request.getParameter("name") : user_prof.getName();
String userid_prof	= request.getParameter("userid") != null ? request.getParameter("userid") : user_prof.getUserid();
String year		= request.getParameter("year") != null ? request.getParameter("year") : birthday[0];
String month	= request.getParameter("month") != null ? request.getParameter("month") : birthday[1];
String day		= request.getParameter("day") != null ? request.getParameter("day") : birthday[2];
String email	= request.getParameter("email") != null ? request.getParameter("email") : user_prof.getEmail();
String phone1	= request.getParameter("phone1") != null ? request.getParameter("phone1") : phone[0];
String phone2	= request.getParameter("phone2") != null ? request.getParameter("phone2") : phone[1];
String phone3	= request.getParameter("phone3") != null ? request.getParameter("phone3") : phone[2];
profile			= request.getParameter("profile") != null ? request.getParameter("profile") : profile;
address			= request.getParameter("address") != null ? request.getParameter("address") : address;
String pass1	= request.getParameter("pass1") != null ? request.getParameter("pass1") : user_prof.getPassword();
String pass2	= request.getParameter("pass2") != null ? request.getParameter("pass2") : user_prof.getPassword();
boolean privacy	= request.getParameter("privacy") != null ? Boolean.parseBoolean(request.getParameter("privacy")) : user_prof.getPrivacy();
boolean allow_dm	= request.getParameter("allow_dm") != null ? Boolean.parseBoolean(request.getParameter("allow_dm")) : user_prof.getAllow_dm();
 %>
<html>
	<head>
		<title>KanDan</title>
		<link rel="stylesheet" href="<%=request.getContextPath() %>/view/style.css">
	</head>
	<body>

		<div class="flex_field">

			<div class = "header">
				<%@include file= "/common/header.jsp" %>
			</div>

			<!--

			<div class="main">

				<div class="sidebar" style="width:100px">

				</div>

				<div class="content">

			-->
					<%
					ArrayList<String> formMessage = (ArrayList<String>)request.getAttribute("formMessage");
					if(formMessage != null && formMessage.size() >= 1) {
						%>
						<p align="center">
							<font size="5" color="red">
								** 入力に誤りがあります。以下のご確認をお願いします。 **<br>
							</font>
							<font size="4" color="red">
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

					<h1 align="center">ユーザー情報変更</h1>

					<form action="<%=request.getContextPath()%>/updateProfile" method="post">

						<table style="border-spacing:10px;" align="center">
								<tr>
									<th width="250" align="right">名前<font color="red">（必須）</font>：</th>
									<td><input type=text size="30" id="name" name="name" value="<%=name %>"></td>
									<td rowspan="0" valign="top">&emsp;<a href="<%=request.getContextPath()%>/userpage?usr=<%=user_prof.getUserid()%>"><img id="thumbnail" src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(user_prof.getIcon()), request, response) %>" width="128" height="128" align=top style="padding:0 0 8px"></a><br>
									<input type="file" id="uploadImg" accept=".png, .jpg, .jpeg, .bmp"><br>
									<font color="grey" size="2">画像は128*128に縮小表示されます。</font>
									<input type="hidden" id="icon" name="icon" value="">
									</td>
								</tr>
								<tr>
									<th width="250" align="right">ユーザーID<font color="red">（必須）</font>：</th>
									<td>@<input type=text size="27" name="userid" placeholder="半角英数字を入力" value="<%=userid_prof %>"></td>
								</tr>
								<tr>
									<th width="250" align="right">生年月日<font color="red">（必須）</font>：</th>
									<td>
									<input type=text size="5" name="year" placeholder="年" value="<%=year %>">/
									<input type=text size="5" name="month"  placeholder="月" value="<%=month %>">/
									<input type=text size="5" name="day"  placeholder="日" value="<%=day %>">
									</td>
								</tr>
								<tr>
									<th width="250" align="right">メールアドレス<font color="red">（必須）</font>：</th>
									<td><input type=text size="30" name="email" value="<%=email %>"></td>
								</tr>
								<tr>
									<th width="250" align="right">電話番号：</th>
									<td>
									<input type=text size="5" name="phone1" value="<%=phone1 %>">-
									<input type=text size="5" name="phone2" value="<%=phone2 %>">-
									<input type=text size="5" name="phone3" value="<%=phone3 %>">
									</td>
								</tr>
								<tr>
									<th width="250" align="right">プロフィール：</th>
									<td><input type=text size="30" name="profile" value="<%=profile %>"></td>
								</tr>
								<tr>
									<th width="250" align="right">住所：</th>
									<td><input type=text size="30" name="address" value="<%=address %>"></td>
								</tr>
								<tr>
									<th width="250" align="right">パスワード<font color="red">（必須）</font>：</th>
									<td><input type=text size="30" name="pass1" placeholder="半角英数字8文字以上" value="<%=pass1 %>"></td>
								</tr>
								<tr>
									<th width="250" align="right">パスワード（確認用）<font color="red">（必須）</font>：</th>
									<td><input type=text size="30" name="pass2" value="<%=pass2 %>"></td>
								</tr>
						</table>

						<table align="center">
							<tr>
								<td><input type="checkbox" name="privacy" value="1" <%=privacy ? "checked" : "" %>></td>
								<td>アカウントを非公開にする</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="allow_dm" value="1" <%=allow_dm ? "checked" : "" %>></td>
								<td>フォロワー以外からのDMを許可しない</td>
							</tr>
						</table>

						<p align="center" style="margin: 20px"><input type="submit" value="変更を保存"></p>

					</form>

				</div>

			<!--

			</div>

		</div> -->

	</body>
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="<%=request.getContextPath() %>/common/uploadImg.js" type="text/javascript"></script>
</html>