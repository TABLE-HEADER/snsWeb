<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="bean.User"%>

<%
	//セッションからユーザー情報を取得
	User user = (User)session.getAttribute("user");
%>
<table align="center">
	<tr>
		<td style="width:40vw">
			<div align="left"><a href="<%=request.getContextPath() %>/mainpage">
			<img src="<%=request.getContextPath() %>/file/kandan.png" alt="kandan" width="298px" height="52px" style="margin: 0 0 0 10px"></a></div>
		</td>
<%
	//セッション情報によって表示する内容を変更
	if(user != null){
%>
		<td align="right" style="width:55vw">
			<br><br>
			<a href="<%=request.getContextPath() %>/signout">サインアウト</a><br>
		</td>
<%
	}
	else{
%>
		<td align="center" style="width:35vw">
			<%if(request.getAttribute("login_failure") != null){ %>
			<font color="red">ログインに失敗しました。</font>
			<%} %>
			<br>
			<%
				String userid = "", password = "";

				//クッキーの取得
				Cookie[] userCookie = request.getCookies();

				//クッキーがあるか判定
				if(userCookie != null){
					for(int i = 0 ; i < userCookie.length; i++){
						//クッキーからユーザー情報の取得
						if(userCookie[i].getName().equals("userid")){
							userid = userCookie[i].getValue();
						}
						//クッキーからパスワード情報の取得
						if(userCookie[i].getName().equals("password")){
							password = userCookie[i].getValue();
						}
					}
			}
			%>
			<form name="signin" action="<%=request.getContextPath()%>/signin" method="post">
				<input type="text" name="userid" placeholder="ユーザーID/メールアドレス" value="@<%=userid %>"><br>
				<input type="text" name="password" placeholder="パスワード" value="<%=password %>"><br>
			</form>
		</td>
		<td align="right" style="width:20vw">
			<br>
			<button onclick="location.href='<%=request.getContextPath() %>/view/signup.jsp'" style="color:white; background-color:green;">サインアップ</button><br>
			<button onclick="document.signin.submit();return false;" style="color:white; background-color:blue;">サインイン</button><br>
		</td>
<%
	}
%>
		<td style="width:2vw"></td>
	</tr>
</table>

<hr align="center" size="5" color="blue" style="width:98vw"></hr>