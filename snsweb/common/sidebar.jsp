<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="bean.User,util.ImageConvert,dao.UserDAO,bean.Inform,dao.InformDAO,java.util.*" %>

<%
	// セッションからユーザー情報を取得
	User user_side = (User)session.getAttribute("user");
	ArrayList<Inform> info_list = null;
	int new_info = 0;
	int info_row = 5;

	// ログイン状態の場合、informinfoを取得/既読フラグ書き換え。
	if(user_side != null) {

		// 通知の取得
		InformDAO infoDao = new InformDAO();
		info_list = infoDao.selectByUserid(user_side.getUserid(), info_row);
		new_info = infoDao.updateFlag(info_list);

	}

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
		<p><%=user_side.getProfile() == null ? "&nbsp;" : user_side.getProfile().replace("\n", "<br>") %></p>
		<span class="followedCount_<%=user_side.getUserid() %>"><%=count[0] %></span> フォロー<br>
		<%=count[1] %> フォロワー<br>
		<br>
		<a href="<%=request.getContextPath() %>/view/profile.jsp">＜プロフィール＞</a><br>
		＜メッセージ＞<br>
		＜設定＞<br>
		<br>
		＜通知＞<%=new_info > 0 ? "<br><font color=\"red\">新着：" + new_info + "件</font>" : "" %><hr>

			<div class="inf">
				<%if(info_list == null || info_list.size() == 0){ %>
					通知はありません。<br>
				<%}else {%>
					<%for(Inform i : info_list) {%>
						<%=i.getType().equals("fav") ? "💗" : (i.getType().equals("follow") ? "🤝" : "💬") %>
						&nbsp;<a href="<%=request.getContextPath()%>/userpage?usr=<%=i.getAgentid()%>">@<%=i.getAgentid() %></a>さんが<%=i.getText().replaceFirst("あなたのコメント\\(([0-9]+)\\)","<a href=" + request.getContextPath() + "/mainpage?id=$1>あなたのコメント</a>") %>
						<hr align="center" size="1" color="blue" style="width:95%"></hr>
					<%} %>
				<%} %>
			</div>

		<br>
	</div>
<%} %>