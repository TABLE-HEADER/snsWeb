<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="bean.User,util.ImageConvert,dao.UserDAO"%>

<%
	// セッションからユーザー情報を取得
	User user_side = (User)session.getAttribute("user");

%>

<%if(user_side == null) { %>
	<h2 style="margin:5px"><font color="blue">Sign In and Customize Your KanDan!</font></h2>
<%}
else {

	// フォロー・フォロワーのカウント
	UserDAO objDao = new UserDAO();
	int[] count = objDao.followCount(user_side.getUserid());
	%>

	<div align="center">
		<a href="<%=request.getContextPath()%>/userpage?usr=<%=user_side.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(user_side.getIcon()), request, response) %>" width="128" height="128" align=top style="padding:0 0 8px"></a><br>
		<%=user_side.getName() %>
		<%=user_side.getPrivacy() ? "🔑" : "" %><br>
		@<%=user_side.getUserid() %><br>
		<p><%=user_side.getProfile() == null ? "プロフィール未設定" : user_side.getProfile() %></p>
		<span class="followedCount_<%=user_side.getUserid() %>"><%=count[0] %></span> フォロー<br>
		<%=count[1] %> フォロワー<br>
		<br>
		<a href="<%=request.getContextPath() %>/view/profile.jsp">＜プロフィール＞</a><br>
		＜メッセージ＞<br>
		＜トピック＞<br>
		＜設定＞<br>
		<br>
		<br>
		＜通知＞<hr>
		<br>
	</div>
<%} %>