<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="bean.User,bean.Comment,java.util.*,util.ImageConvert,java.time.LocalDateTime,java.time.format.DateTimeFormatter,util.CompareDate,dao.UserDAO"%>

<%
	// セッションからユーザー情報を取得
	User user_cont = (User)session.getAttribute("user");

	// リクエストスコープから表示形式を取得
	User user_page = (User)request.getAttribute("user_page");

	// textとtypeを送ったかどうか確認
	String text = request.getParameter("text");
	String type = request.getParameter("type");

	// idを送ったかどうか確認
	String id_s;
	Integer id = null;
	try {
		id_s = (String)request.getParameter("id");
		if(id_s != null){
			id = Integer.parseInt(id_s);
		}
	}
	catch(NumberFormatException e) {
		request.setAttribute("error","不正なフォームアクセスです。");
		request.setAttribute("cmd","mainpage");
		request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		return;
	}

	// タイムラインの取得
	ArrayList<Comment> timeline_list = (ArrayList<Comment>)request.getAttribute("timeline_list");
	ArrayList<Comment> timeline_self_list = (ArrayList<Comment>)request.getAttribute("timeline_self_list");
	ArrayList<Comment> timeline_fav_list = (ArrayList<Comment>)request.getAttribute("timeline_fav_list");
	ArrayList<Comment> timeline_img_list = (ArrayList<Comment>)request.getAttribute("timeline_img_list");
	ArrayList<Comment> timeline_rep_list = (ArrayList<Comment>)request.getAttribute("timeline_rep_list");

	ArrayList<User> user_list = (ArrayList<User>)request.getAttribute("user_list");
	ArrayList<User> followed_list = (ArrayList<User>)request.getAttribute("followed_list");
	ArrayList<User> follower_list = (ArrayList<User>)request.getAttribute("follower_list");

	boolean visible = Boolean.parseBoolean(request.getParameter("visible"));

	// searchの実行
	if(text != null && type != null){

		if(type.equals("username")) {%>

			<div class= "add-control">

				&emsp;&emsp;ユーザー検索 : "<%=text %>" の検索結果
				<%if(user_list != null){ %>
					（<%=user_list.size() %>件）
				<%} %>

				<div class="c2">

					<input type="radio" id="tab1" class="radio" name="tab" checked="checked" ><label class="tabtitle title1" for="tab1" style="display:none">タイムライン</label>
					<div class="tabbody">
						<div class="body1" style="height: calc(98vh - 260px)">
							<%if(user_list == null || user_list.size() == 0){ %>
								条件に該当するユーザーは見つかりません。<br>
							<%}else {%>
								<%for(User u : user_list) {%>
								<table align="left" style="width:calc(98vw - 300px)">
									<tr>
										<td align="left" valign="top" width="72px">
											<a href="<%=request.getContextPath()%>/userpage?usr=<%=u.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(u.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
										</td>
										<td align="left">
											<%=u.getName() %><font size="2" color="grey">@<%=u.getUserid() %></font><%=u.getPrivacy() ? "🔑" : "" %>
											<p><%=u.getProfile().replace("\n", "<br>") %></p>
										</td>
									</tr>
								</table>
								<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
								<%} %>
							<%} %>
						</div>
					</div>

				</div>
			</div>

		<%}
		else {%>

		<div class= "add-control">

				&emsp;&emsp;<%=type.equals("all") ? "全文検索" : "フォロワー内検索"%> : "<%=text %>" の検索結果
				<%if(timeline_list != null){ %>
					（<%=timeline_list.size() %>件）
				<%} %>

				<div class="c2">

					<input type="radio" id="tab1" class="radio" name="tab" checked="checked" ><label class="tabtitle title1" for="tab1" style="display:none">タイムライン</label>
					<div class="tabbody">
						<div class="body1" style="height: calc(98vh - 260px)">
							<%if(timeline_list == null || timeline_list.size() == 0){ %>
								条件に該当するコメントはありません。<br>
							<%}else {%>
								<%for(Comment com : timeline_list) {

									String dif = CompareDate.compareDate(com.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));

								%>
								<table align="left" style="width:calc(98vw - 300px)">
									<tr>
										<td align="left" valign="top" width="72px" rowspan="2">
											<a href="<%=request.getContextPath()%>/userpage?usr=<%=com.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
										</td>
										<td align="left" rowspan="2">
											<%=com.getName() %><font size="2" color="grey">@<%=com.getUserid() %> <%=dif %></font>
											<%=com.getParentid() != null ? "<font color=\"grey\" size=\"2\"><a href=\"" + request.getContextPath() + "/mainpage?id=" + com.getParentid() + "\">←&nbsp;返信</a></font>" : "" %>
											<p>
												<%=com.getText().replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replace("\n", "<br>") %>
											</p>
											<%if(com.getPhoto() != null) {%>
													<img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getPhoto()), request, response) %>" width="256" height="auto" style="padding:0 0 8px">
											<%} %>
										</td>
										<td align="right" valign="top" width="32px">
											<div class="dropdown" align="right">
												<button class="dropdown_btn" id="dropdown_btn_1_<%=com.getId() %>" onclick="dropDown(1,<%=com.getId() %>)"">
													<svg viewBox="0 0 512 512"><circle cx="256" cy="256" r="64" fill="grey"/><circle cx="256" cy="448" r="64" fill="grey"/><circle cx="256" cy="64" r="64" fill="grey"/></svg>
												</button>
												<div class="dropdown_body" align="left">
													<ul class="dropdown_list">
														<%if(user_cont != null && (user_cont.getUserid().equals(com.getUserid()) || user_cont.getAuthority())) { %>
														<li class="dropdown_item"><a href="<%=request.getContextPath() %>/deleteComment?id=<%=com.getId() %>" class="dropdown_item-link">削除</a></li>
														<%} %>
														<li class="dropdown_item"><a href="<%=request.getContextPath() %>/mainpage" class="dropdown_item-link">コメントを報告</a></li>
													</ul>
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<%if(user_cont == null) {%>
											<td align="right" valign="bottom" width="96px">
												♡<%=com.getFavCount() %>
												<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
												<span class="comCount"><%=com.getComCount() %></span>
											</td>
										<%}
										else {%>
											<td align="right" valign="bottom" width="96px">
												<span class="fav fav_<%=com.getId()%>" onclick="changeFav(<%=com.getId()%>,<%=com.getFav()%>,<%=com.getFavCount() %>,'<%=user_cont.getUserid() %>');"><%=com.getFav() == true ? "❤" : "♡" %></span>
												<span class="favCount_<%=com.getId()%>"><%=com.getFavCount() %></span>
												<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
												<span class="comCount"><%=com.getComCount() %></span>
											</td>
										<%} %>
									</tr>
								</table>
								<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
								<%} %>
							<%} %>
						</div>
					</div>

				</div>
			</div>

	<% 	}
	}
	else {

		// リプライページレイアウト
		if(id != null) {

			Comment com = (Comment)request.getAttribute("Comment");
			%>

			<div class= "add-control">

				&emsp;&emsp;＜コメント詳細＞

				<div class="c2">

					<input type="radio" id="tab1" class="radio" name="tab" checked="checked" ><label class="tabtitle title1" for="tab1" style="display:none">タイムライン</label>
					<div class="tabbody">
						<div class="body1" style="height: calc(98vh - 260px)">

							<%if(com == null || com.getName() == null) {%>

								<p align="center">このコメントは存在しません。</p>

							<%}
							else {%>

								<table align="left" style="width:calc(98vw - 300px)">
									<tr>
										<td align="left" valign="top" width="72px" rowspan="2">
											<a href="<%=request.getContextPath()%>/userpage?usr=<%=com.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
										</td>
										<td align="left" rowspan="2">
											<%=com.getName() %><font size="2" color="grey">@<%=com.getUserid() %></font>
											<%=com.getParentid() != null ? "<font color=\"grey\" size=\"2\"><a href=\"" + request.getContextPath() + "/mainpage?id=" + com.getParentid() + "\">←&nbsp;返信</a></font>" : "" %>
											<p>
												<%=com.getText().replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replace("\n", "<br>") %>
											</p>
											<%if(com.getPhoto() != null) {%>
												<img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getPhoto()), request, response) %>" width="256" height="auto" style="padding:0 0 8px">
											<%} %>
											<br>
											<font size="2" color="grey"><%=com.getDate() %></font>
										</td>
										<td align="right" valign="top" width="32px">
											<div class="dropdown" align="right">
												<button class="dropdown_btn" id="dropdown_btn_1_<%=com.getId() %>" onclick="dropDown(1,<%=com.getId() %>)"">
													<svg viewBox="0 0 512 512"><circle cx="256" cy="256" r="64" fill="grey"/><circle cx="256" cy="448" r="64" fill="grey"/><circle cx="256" cy="64" r="64" fill="grey"/></svg>
												</button>
												<div class="dropdown_body" align="left">
													<ul class="dropdown_list">
														<%if(user_cont != null && (user_cont.getUserid().equals(com.getUserid()) || user_cont.getAuthority())) { %>
														<li class="dropdown_item"><a href="<%=request.getContextPath() %>/deleteComment?id=<%=com.getId() %>" class="dropdown_item-link">削除</a></li>
														<%} %>
														<li class="dropdown_item"><a href="<%=request.getContextPath() %>/mainpage" class="dropdown_item-link">コメントを報告</a></li>
													</ul>
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<%if(user_cont == null) {%>
											<td align="right" valign="bottom" width="96px">
												♡<%=com.getFavCount() %>
												<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
												<span class="comCount"><%=com.getComCount() %></span>
											</td>
										<%}
										else {%>
											<td align="right" valign="bottom" width="96px">
												<span class="fav fav_<%=com.getId()%>" onclick="changeFav(<%=com.getId()%>,<%=com.getFav()%>,<%=com.getFavCount() %>,'<%=user_cont.getUserid() %>');"><%=com.getFav() == true ? "❤" : "♡" %></span>
												<span class="favCount_<%=com.getId()%>"><%=com.getFavCount() %></span>
												<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
												<span class="comCount"><%=com.getComCount() %></span>
											</td>
										<%} %>
									</tr>
									<%if(user_cont != null) { %>
										<tr>
											<td align="center" colspan="3">
												<form method="post" action="<%=request.getContextPath() %>/postComment">
													<div align="center">
														<textarea name="text" placeholder="上のコメントに返信" style="resize: none; width:95%; height:72px"></textarea>
														<input type="hidden" name="reply_id" value="<%=com.getId() %>">
													</div>
													<div align="right" style="padding-right:4vw">
														<input type="file" id="uploadImg" accept=".png, .jpg, .jpeg, .bmp">
														<input type="hidden" id="icon" name="icon" value="">
														<input type="submit" value="送信">
													</div><br>
												</form>
											</td>
										</tr>
									<%} %>
								</table>

								<%if(com.getComCount() != 0) { %>

									＜返信：<%=com.getComCount() %>件＞

									<%for(Comment rep : timeline_list) {

										String dif = CompareDate.compareDate(rep.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));%>

										<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
										<table align="left" style="width:calc(98vw - 300px)">
											<tr>
												<td align="left" valign="top" width="72px" rowspan="2">
													<a href="<%=request.getContextPath()%>/userpage?usr=<%=rep.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(rep.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
												</td>
												<td align="left" rowspan="2">
													<%=rep.getName() %><font size="2" color="grey">@<%=rep.getUserid() %> <%=dif %></font>
													<%=rep.getParentid() != null ? "<font color=\"grey\" size=\"2\"><a href=\"" + request.getContextPath() + "/mainpage?id=" + rep.getParentid() + "\">←&nbsp;返信</a></font>" : "" %>
													<p>
														<%=rep.getText().replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replace("\n", "<br>") %>
													</p>
													<%if(rep.getPhoto() != null) {%>
															<img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(rep.getPhoto()), request, response) %>" width="256" height="auto" style="padding:0 0 8px">
													<%} %>
												</td>
												<td align="right" valign="top" width="32px">
													<div class="dropdown" align="right">
														<button class="dropdown_btn" id="dropdown_btn_1_<%=rep.getId() %>" onclick="dropDown(1,<%=rep.getId() %>)"">
															<svg viewBox="0 0 512 512"><circle cx="256" cy="256" r="64" fill="grey"/><circle cx="256" cy="448" r="64" fill="grey"/><circle cx="256" cy="64" r="64" fill="grey"/></svg>
														</button>
														<div class="dropdown_body" align="left">
															<ul class="dropdown_list">
																<%if(user_cont != null && (user_cont.getUserid().equals(rep.getUserid()) || user_cont.getAuthority())) { %>
																<li class="dropdown_item"><a href="<%=request.getContextPath() %>/deleteComment?id=<%=rep.getId() %>" class="dropdown_item-link">削除</a></li>
																<%} %>
																<li class="dropdown_item"><a href="<%=request.getContextPath() %>/mainpage" class="dropdown_item-link">コメントを報告</a></li>
															</ul>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<%if(user_cont == null) {%>
													<td align="right" valign="bottom" width="96px">
														♡<%=rep.getFavCount() %>
														<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=rep.getId() %>'">💬</span>
														<span class="comCount"><%=rep.getComCount() %></span>
													</td>
												<%}
												else {%>
													<td align="right" valign="bottom" width="96px">
														<span class="fav fav_<%=rep.getId()%>" onclick="changeFav(<%=rep.getId()%>,<%=rep.getFav()%>,<%=rep.getFavCount() %>,'<%=user_cont.getUserid() %>');"><%=rep.getFav() == true ? "❤" : "♡" %></span>
														<span class="favCount_<%=rep.getId()%>"><%=rep.getFavCount() %></span>
														<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=rep.getId() %>'">💬</span>
														<span class="comCount"><%=rep.getComCount() %></span>
													</td>
												<%} %>
											</tr>
										</table>
									<%}

								}

							}%>

						</div>
					</div>

				</div>
			</div>



		<%}

		else{

			// ユーザーページレイアウト
			if(user_page != null){

				// フォロー・フォロワーのカウント
				UserDAO objDao = new UserDAO();
				int[] count = objDao.followCount(user_page.getUserid());
			%>

				<div class="c1">
					<table align="left">
						<tr>
							<td valign="top" >
								<a href="<%=request.getContextPath()%>/userpage?usr=<%=user_page.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(user_page.getIcon()), request, response) %>" width="128" height="128" style="padding:0 0 8px"></a>
							</td>
							<td style="padding-left:20px">
								<font size="5" ><b>
									<%=user_page.getName() %><font color="grey">@<%=user_page.getUserid() %></font>
								</b></font>
									<%if(user_cont != null && !user_cont.getUserid().equals(user_page.getUserid())) {%>
									&emsp;<a class="btn btn_follow_<%=user_page.getUserid() %> btn_follow_<%=user_page.getFollowed()%>" onclick="changeFollow('<%=user_page.getUserid()%>',<%=user_page.getFollowed()%>,'<%=user_cont.getUserid() %>');"><span class="follow follow_<%=user_page.getUserid()%>" ><%=user_page.getFollowed() == true ? "フォロー済み" : "フォロー" %></span></a>
									<%} %>
									<%=user_page.getPrivacy() ? "🔑" : "" %>
								<br>
								<p><%=user_page.getProfile() == null ? "プロフィール未設定" : user_page.getProfile() %></p>
								<%=count[0] %> フォロー&emsp;&emsp;<span class="followCount_<%=user_page.getUserid()%>"><%=count[1] %></span> フォロワー
							</td>
						</tr>
					</table>
				</div>

				<%if(visible || !user_page.getPrivacy()) {%>

				<div class= "add-control">
					<div class="c2">

						<input type="radio" id="tab1" class="radio" name="tab" checked="checked" ><label class="tabtitle title1" for="tab1" >セルフ</label>
						<input type="radio" id="tab2" class="radio" name="tab" ><label class="tabtitle title2" for="tab2">リプライ</label>
						<input type="radio" id="tab3" class="radio" name="tab" ><label class="tabtitle title3" for="tab3">画像</label>
						<input type="radio" id="tab4" class="radio" name="tab" ><label class="tabtitle title4" for="tab4">お気に入り</label>
						<input type="radio" id="tab6" class="radio" name="tab" ><label class="tabtitle title6" for="tab6">フォロー</label>
						<input type="radio" id="tab7" class="radio" name="tab" ><label class="tabtitle title7" for="tab7">フォロワー</label>
						<div class="tabbody">
							<div class="body1" style="height: calc(98vh - 430px)">
								<%if(timeline_self_list == null || timeline_self_list.size() == 0){ %>
									コメントはありません。<br>
								<%}else {%>
									<%for(Comment com : timeline_self_list) {

										String dif = CompareDate.compareDate(com.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));

									%>
									<table align="left" style="width:calc(98vw - 300px)">
										<tr>
											<td align="left" valign="top" width="72px" rowspan="2">
												<a href="<%=request.getContextPath()%>/userpage?usr=<%=com.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
											</td>
											<td align="left" rowspan="2">
												<%=com.getName() %><font size="2" color="grey">@<%=com.getUserid() %> <%=dif %></font>
												<%=com.getParentid() != null ? "<font color=\"grey\" size=\"2\"><a href=\"" + request.getContextPath() + "/mainpage?id=" + com.getParentid() + "\">←&nbsp;返信</a></font>" : "" %>
												<p><%=com.getText().replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replace("\n", "<br>") %></p>
												<%if(com.getPhoto() != null) {%>
														<img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getPhoto()), request, response) %>" width="256" height="auto" style="padding:0 0 8px">
												<%} %>
											</td>
											<td align="right" valign="top" width="32px">
												<div class="dropdown" align="right">
													<button class="dropdown_btn" id="dropdown_btn_1_<%=com.getId() %>" onclick="dropDown(1, <%=com.getId() %>)">
														<svg viewBox="0 0 512 512"><circle cx="256" cy="256" r="64" fill="grey"/><circle cx="256" cy="448" r="64" fill="grey"/><circle cx="256" cy="64" r="64" fill="grey"/></svg>
													</button>
													<div class="dropdown_body" align="left">
														<ul class="dropdown_list">
															<%if(user_cont != null && (user_cont.getUserid().equals(com.getUserid()) || user_cont.getAuthority())) { %>
															<li class="dropdown_item"><a href="<%=request.getContextPath() %>/deleteComment?id=<%=com.getId() %>" class="dropdown_item-link">削除</a></li>
															<%} %>
															<li class="dropdown_item"><a href="<%=request.getContextPath() %>/mainpage" class="dropdown_item-link">コメントを報告</a></li>
														</ul>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<%if(user_cont == null) {%>
												<td align="right" valign="bottom" width="96px">
													♡<%=com.getFavCount() %>
													<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
													<span class="comCount"><%=com.getComCount() == 0 ? " " : com.getComCount() %></span>
												</td>
											<%}
											else {%>
												<td align="right" valign="bottom" width="96px">
													<span class="fav fav_<%=com.getId()%>" onclick="changeFav(<%=com.getId()%>,<%=com.getFav()%>,<%=com.getFavCount() %>,'<%=user_cont.getUserid() %>');"><%=com.getFav() == true ? "❤" : "♡" %></span>
													<span class="favCount_<%=com.getId()%>"><%=com.getFavCount() %></span>
													<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
													<span class="comCount"><%=com.getComCount() %></span>
												</td>
											<%} %>
										</tr>
									</table>
									<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
									<%} %>
								<%} %>
							</div>
							<div class="body2">
								<%if(timeline_rep_list == null || timeline_rep_list.size() == 0){ %>
									コメントはありません。<br>
								<%}else {%>
									<%for(Comment com : timeline_rep_list) {

										String dif = CompareDate.compareDate(com.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));

									%>
									<table align="left" style="width:calc(98vw - 300px)">
										<tr>
											<td align="left" valign="top" width="72px" rowspan="2">
												<a href="<%=request.getContextPath()%>/userpage?usr=<%=com.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
											</td>
											<td align="left" rowspan="2">
												<%=com.getName() %><font size="2" color="grey">@<%=com.getUserid() %> <%=dif %></font>
												<%=com.getParentid() != null ? "<font color=\"grey\" size=\"2\"><a href=\"" + request.getContextPath() + "/mainpage?id=" + com.getParentid() + "\">←&nbsp;返信</a></font>" : "" %>
												<p><%=com.getText().replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replace("\n", "<br>") %></p>
												<%if(com.getPhoto() != null) {%>
														<img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getPhoto()), request, response) %>" width="256" height="auto" style="padding:0 0 8px">
												<%} %>
											</td>
											<td align="right" valign="top" width="32px">
												<div class="dropdown" align="right">
													<button class="dropdown_btn" id="dropdown_btn_3_<%=com.getId() %>" onclick="dropDown(3, <%=com.getId() %>)">
														<svg viewBox="0 0 512 512"><circle cx="256" cy="256" r="64" fill="grey"/><circle cx="256" cy="448" r="64" fill="grey"/><circle cx="256" cy="64" r="64" fill="grey"/></svg>
													</button>
													<div class="dropdown_body" align="left">
														<ul class="dropdown_list">
															<%if(user_cont != null && (user_cont.getUserid().equals(com.getUserid()) || user_cont.getAuthority())) { %>
															<li class="dropdown_item"><a href="<%=request.getContextPath() %>/deleteComment?id=<%=com.getId() %>" class="dropdown_item-link">削除</a></li>
															<%} %>
															<li class="dropdown_item"><a href="<%=request.getContextPath() %>/mainpage" class="dropdown_item-link">コメントを報告</a></li>
														</ul>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<%if(user_cont == null) {%>
												<td align="right" valign="bottom" width="96px">
													♡<%=com.getFavCount() %>
													<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
													<span class="comCount"><%=com.getComCount() %></span>
												</td>
											<%}
											else {%>
												<td align="right" valign="bottom" width="96px">
													<span class="fav fav_<%=com.getId()%>" onclick="changeFav(<%=com.getId()%>,<%=com.getFav()%>,<%=com.getFavCount() %>,'<%=user_cont.getUserid() %>');"><%=com.getFav() == true ? "❤" : "♡" %></span>
													<span class="favCount_<%=com.getId()%>"><%=com.getFavCount() %></span>
													<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
													<span class="comCount"><%=com.getComCount() %></span>
												</td>
											<%} %>
										</tr>
									</table>
									<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
									<%} %>
								<%} %>
							</div>
							<div class="body3">
								<%if(timeline_img_list == null || timeline_img_list.size() == 0){ %>
									コメントはありません。<br>
								<%}else {%>
									<%for(Comment com : timeline_img_list) {

										String dif = CompareDate.compareDate(com.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));

									%>
									<table align="left" style="width:calc(98vw - 300px)">
										<tr>
											<td align="left" valign="top" width="72px" rowspan="2">
												<a href="<%=request.getContextPath()%>/userpage?usr=<%=com.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
											</td>
											<td align="left" rowspan="2">
												<%=com.getName() %><font size="2" color="grey">@<%=com.getUserid() %> <%=dif %></font>
												<%=com.getParentid() != null ? "<font color=\"grey\" size=\"2\"><a href=\"" + request.getContextPath() + "/mainpage?id=" + com.getParentid() + "\">←&nbsp;返信</a></font>" : "" %>
												<p><%=com.getText().replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replace("\n", "<br>") %></p>
												<%if(com.getPhoto() != null) {%>
														<img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getPhoto()), request, response) %>" width="256" height="auto" style="padding:0 0 8px">
												<%} %>
											</td>
											<td align="right" valign="top" width="32px">
												<div class="dropdown" align="right">
													<button class="dropdown_btn" id="dropdown_btn_3_<%=com.getId() %>" onclick="dropDown(3, <%=com.getId() %>)">
														<svg viewBox="0 0 512 512"><circle cx="256" cy="256" r="64" fill="grey"/><circle cx="256" cy="448" r="64" fill="grey"/><circle cx="256" cy="64" r="64" fill="grey"/></svg>
													</button>
													<div class="dropdown_body" align="left">
														<ul class="dropdown_list">
															<%if(user_cont != null && (user_cont.getUserid().equals(com.getUserid()) || user_cont.getAuthority())) { %>
															<li class="dropdown_item"><a href="<%=request.getContextPath() %>/deleteComment?id=<%=com.getId() %>" class="dropdown_item-link">削除</a></li>
															<%} %>
															<li class="dropdown_item"><a href="<%=request.getContextPath() %>/mainpage" class="dropdown_item-link">コメントを報告</a></li>
														</ul>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<%if(user_cont == null) {%>
												<td align="right" valign="bottom" width="96px">
													♡<%=com.getFavCount() %>
													<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
													<span class="comCount"><%=com.getComCount() %></span>
												</td>
											<%}
											else {%>
												<td align="right" valign="bottom" width="96px">
													<span class="fav fav_<%=com.getId()%>" onclick="changeFav(<%=com.getId()%>,<%=com.getFav()%>,<%=com.getFavCount() %>,'<%=user_cont.getUserid() %>');"><%=com.getFav() == true ? "❤" : "♡" %></span>
													<span class="favCount_<%=com.getId()%>"><%=com.getFavCount() %></span>
													<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
													<span class="comCount"><%=com.getComCount() %></span>
												</td>
											<%} %>
										</tr>
									</table>
									<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
									<%} %>
								<%} %>
							</div>
							<div class="body4">
								<%if(timeline_fav_list == null || timeline_fav_list.size() == 0){ %>
									コメントはありません。<br>
								<%}else {%>
									<%for(Comment com : timeline_fav_list) {

										String dif = CompareDate.compareDate(com.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));

									%>
									<table align="left" style="width:calc(98vw - 300px)">
										<tr>
											<td align="left" valign="top" width="72px" rowspan="2">
												<a href="<%=request.getContextPath()%>/userpage?usr=<%=com.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
											</td>
											<td align="left" rowspan="2">
												<%=com.getName() %><font size="2" color="grey">@<%=com.getUserid() %> <%=dif %></font>
												<%=com.getParentid() != null ? "<font color=\"grey\" size=\"2\"><a href=\"" + request.getContextPath() + "/mainpage?id=" + com.getParentid() + "\">←&nbsp;返信</a></font>" : "" %>
												<p><%=com.getText().replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replace("\n", "<br>") %></p>
												<%if(com.getPhoto() != null) {%>
														<img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getPhoto()), request, response) %>" width="256" height="auto" style="padding:0 0 8px">
												<%} %>
											</td>
											<td align="right" valign="top" width="32px">
												<div class="dropdown" align="right">
													<button class="dropdown_btn" id="dropdown_btn_4_<%=com.getId() %>" onclick="dropDown(4,<%=com.getId() %>)">
														<svg viewBox="0 0 512 512"><circle cx="256" cy="256" r="64" fill="grey"/><circle cx="256" cy="448" r="64" fill="grey"/><circle cx="256" cy="64" r="64" fill="grey"/></svg>
													</button>
													<div class="dropdown_body" align="left">
														<ul class="dropdown_list">
															<%if(user_cont != null && (user_cont.getUserid().equals(com.getUserid()) || user_cont.getAuthority())) { %>
															<li class="dropdown_item"><a href="<%=request.getContextPath() %>/deleteComment?id=<%=com.getId() %>" class="dropdown_item-link">削除</a></li>
															<%} %>
															<li class="dropdown_item"><a href="<%=request.getContextPath() %>/mainpage" class="dropdown_item-link">コメントを報告</a></li>
														</ul>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<%if(user_cont == null) {%>
												<td align="right" valign="bottom" width="96px">
													♡<%=com.getFavCount() %>
													<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
													<span class="comCount"><%=com.getComCount() %></span>
												</td>
											<%}
											else {%>
												<td align="right" valign="bottom" width="96px">
													<span class="fav fav_<%=com.getId()%>" onclick="changeFav(<%=com.getId()%>,<%=com.getFav()%>,<%=com.getFavCount() %>,'<%=user_cont.getUserid() %>');"><%=com.getFav() == true ? "❤" : "♡" %></span>
													<span class="favCount_<%=com.getId()%>"><%=com.getFavCount() %></span>
													<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
													<span class="comCount"><%=com.getComCount() %></span>
												</td>
											<%} %>
										</tr>
									</table>
									<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
									<%} %>
								<%} %>
							</div>

							<div class="body6">
								<%if(followed_list == null || followed_list.size() == 0){ %>
									条件に該当するユーザーは見つかりません。<br>
								<%}else {%>
									<%for(User u : followed_list) {%>
									<table align="left" style="width:calc(98vw - 300px)">
										<tr>
											<td align="left" valign="top" width="72px">
												<a href="<%=request.getContextPath()%>/userpage?usr=<%=u.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(u.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
											</td>
											<td align="left">
												<%=u.getName() %><font size="2" color="grey">@<%=u.getUserid() %></font>
												<%if(user_cont != null && !user_cont.getUserid().equals(u.getUserid())) {%>
												&emsp;<a class="btn btn_follow_<%=u.getUserid() %> btn_follow_<%=u.getFollowed()%>" onclick="changeFollow('<%=u.getUserid()%>',<%=u.getFollowed()%>,'<%=user_cont.getUserid() %>');"><span class="follow follow_<%=u.getUserid()%>" ><%=u.getFollowed() == true ? "フォロー済み" : "フォロー" %></span></a>
												<%} %>
												<%=u.getPrivacy() ? "🔑" : "" %>
												<p><%=u.getProfile().replace("\n", "<br>") %></p>
											</td>
										</tr>
									</table>
									<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
									<%} %>
								<%} %>
							</div>
							<div class="body7">
								<%if(follower_list == null || follower_list.size() == 0){ %>
									条件に該当するユーザーは見つかりません。<br>
								<%}else {%>
									<%for(User u : follower_list) {%>
									<table align="left" style="width:calc(98vw - 300px)">
										<tr>
											<td align="left" valign="top" width="72px">
												<a href="<%=request.getContextPath()%>/userpage?usr=<%=u.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(u.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
											</td>
											<td align="left">
												<%=u.getName() %><font size="2" color="grey">@<%=u.getUserid() %></font>
												<%if(user_cont != null && !user_cont.getUserid().equals(u.getUserid())) {%>
												&emsp;<a class="btn btn_follow_<%=u.getUserid() %> btn_follow_<%=u.getFollowed()%>" onclick="changeFollow('<%=u.getUserid()%>',<%=u.getFollowed()%>,'<%=user_cont.getUserid() %>');"><span class="follow follow_<%=u.getUserid()%>" ><%=u.getFollowed() == true ? "フォロー済み" : "フォロー" %></span></a>
												<%} %>
												<%=u.getPrivacy() ? "🔑" : "" %>
												<p><%=u.getProfile().replace("\n", "<br>") %></p>
											</td>
										</tr>
									</table>
									<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
									<%} %>
								<%} %>
							</div>

						</div>

					</div>
				</div>
				<%}
				else {%>
				<div class= "add-control">
					<div class="c2">
						<input type="radio" id="tab1" class="radio" name="tab" checked="checked" ><label class="tabtitle title1" for="tab1" style="display:none">セルフ</label>
						<div class="tabbody">
							<div class="body1" style="height: calc(98vh - 430px)">
								このアカウントは鍵アカウントに設定されています。
							</div>
						</div>
					</div>
				</div>
				<%}
			}

			else {

				// 非ログイン状態でのメインページレイアウト
				if(user_cont == null) { %>

				<div class= "add-control">
					<div class="c2">

						<input type="radio" id="tab1" class="radio" name="tab" checked="checked" ><label class="tabtitle title1" for="tab1">話題のコメント</label>
						<div class="tabbody">
							<div class="body1" style="height: calc(98vh - 260px)">
								<%if(timeline_list == null || timeline_list.size() == 0){ %>
									コメントはありません。<br>
								<%}else {%>
									<%for(Comment com : timeline_list) {

										String dif = CompareDate.compareDate(com.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));

									%>
									<table align="left" style="width:calc(98vw - 300px)">
										<tr>
											<td align="left" valign="top" width="72px" rowspan="2">
												<a href="<%=request.getContextPath()%>/userpage?usr=<%=com.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
											</td>
											<td align="left" rowspan="2">
												<%=com.getName() %><font size="2" color="grey">@<%=com.getUserid() %> <%=dif %></font>
												<%=com.getParentid() != null ? "<font color=\"grey\" size=\"2\"><a href=\"" + request.getContextPath() + "/mainpage?id=" + com.getParentid() + "\">←&nbsp;返信</a></font>" : "" %>
												<p><%=com.getText().replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replace("\n", "<br>") %></p>
												<%if(com.getPhoto() != null) {%>
														<img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getPhoto()), request, response) %>" width="256" height="auto" style="padding:0 0 8px">
												<%} %>
											</td>
											<td align="right" valign="top" width="32px">
												<div class="dropdown" align="right">
													<button class="dropdown_btn" id="dropdown_btn_1_<%=com.getId() %>" onclick="dropDown(1,<%=com.getId() %>)"">
														<svg viewBox="0 0 512 512"><circle cx="256" cy="256" r="64" fill="grey"/><circle cx="256" cy="448" r="64" fill="grey"/><circle cx="256" cy="64" r="64" fill="grey"/></svg>
													</button>
													<div class="dropdown_body" align="left">
														<ul class="dropdown_list">
															<%if(user_cont != null && (user_cont.getUserid().equals(com.getUserid()) || user_cont.getAuthority())) { %>
															<li class="dropdown_item"><a href="<%=request.getContextPath() %>/deleteComment?id=<%=com.getId() %>" class="dropdown_item-link">削除</a></li>
															<%} %>
															<li class="dropdown_item"><a href="<%=request.getContextPath() %>/mainpage" class="dropdown_item-link">コメントを報告</a></li>
														</ul>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<%if(user_cont == null) {%>
												<td align="right" valign="bottom" width="96px">
													♡<%=com.getFavCount() %>
													<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
													<span class="comCount"><%=com.getComCount() %></span>
												</td>
											<%}
											else {%>
												<td align="right" valign="bottom" width="96px">
													<span class="fav fav_<%=com.getId()%>" onclick="changeFav(<%=com.getId()%>,<%=com.getFav()%>,<%=com.getFavCount() %>,'<%=user_cont.getUserid() %>');"><%=com.getFav() == true ? "❤" : "♡" %></span>
													<span class="favCount_<%=com.getId()%>"><%=com.getFavCount() %></span>
													<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
													<span class="comCount"><%=com.getComCount() %></span>
												</td>
											<%} %>
										</tr>
									</table>
									<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
									<%} %>
								<%} %>
							</div>
						</div>

					</div>
				</div>

				<%}
				// ログイン状態でのメインページレイアウト
				else {%>

					<div class="c1">
						<form method="post" action="<%=request.getContextPath() %>/postComment">
							<div align="center"><textarea name="text" placeholder="いまなにしてる？" style="resize: none; width:90%; height:70%;"></textarea></div>
							<div align="right" style="padding-right:4vw">
								<input type="file" id="uploadImg" accept=".png, .jpg, .jpeg, .bmp">
								<input type="hidden" id="icon" name="icon" value="">
								<input type="submit" value="送信">
							</div><br>
						</form>
					</div>

					<div class= "add-control">
						<div class="c2">

							<input type="radio" id="tab1" class="radio" name="tab" checked="checked" ><label class="tabtitle title1" for="tab1">タイムライン</label>
							<input type="radio" id="tab2" class="radio" name="tab" ><label class="tabtitle title2" for="tab2">セルフ</label>
							<input type="radio" id="tab3" class="radio" name="tab" ><label class="tabtitle title3" for="tab3">リプライ</label>
							<input type="radio" id="tab4" class="radio" name="tab" ><label class="tabtitle title4" for="tab4">画像</label>
							<input type="radio" id="tab5" class="radio" name="tab" ><label class="tabtitle title5" for="tab5">お気に入り</label>
							<input type="radio" id="tab6" class="radio" name="tab" ><label class="tabtitle title6" for="tab6">フォロー</label>
							<input type="radio" id="tab7" class="radio" name="tab" ><label class="tabtitle title7" for="tab7">フォロワー</label>
							<div class="tabbody">
								<div class="body1">
									<%if(timeline_list == null || timeline_list.size() == 0){ %>
										コメントはありません。<br>
									<%}else {%>
										<%for(Comment com : timeline_list) {

											String dif = CompareDate.compareDate(com.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));

										%>
										<table align="left" style="width:calc(98vw - 300px)">
											<tr>
												<td align="left" valign="top" width="72px" rowspan="2">
													<a href="<%=request.getContextPath()%>/userpage?usr=<%=com.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
												</td>
												<td align="left" rowspan="2">
													<%=com.getName() %><font size="2" color="grey">@<%=com.getUserid() %> <%=dif %></font>
													<%=com.getParentid() != null ? "<font color=\"grey\" size=\"2\"><a href=\"" + request.getContextPath() + "/mainpage?id=" + com.getParentid() + "\">←&nbsp;返信</a></font>" : "" %>
													<p><%=com.getText().replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replace("\n", "<br>") %></p>
													<%if(com.getPhoto() != null) {%>
														<img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getPhoto()), request, response) %>" width="256" height="auto" style="padding:0 0 8px">
													<%} %>
												</td>
												<td align="right" valign="top" width="32px">
													<div class="dropdown" align="right">
														<button class="dropdown_btn" id="dropdown_btn_1_<%=com.getId() %>" onclick="dropDown(1,<%=com.getId() %>)"">
															<svg viewBox="0 0 512 512"><circle cx="256" cy="256" r="64" fill="grey"/><circle cx="256" cy="448" r="64" fill="grey"/><circle cx="256" cy="64" r="64" fill="grey"/></svg>
														</button>
														<div class="dropdown_body" align="left">
															<ul class="dropdown_list">
																<%if(user_cont != null && (user_cont.getUserid().equals(com.getUserid()) || user_cont.getAuthority())) { %>
																<li class="dropdown_item"><a href="<%=request.getContextPath() %>/deleteComment?id=<%=com.getId() %>" class="dropdown_item-link">削除</a></li>
																<%} %>
																<li class="dropdown_item"><a href="<%=request.getContextPath() %>/mainpage" class="dropdown_item-link">コメントを報告</a></li>
															</ul>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<%if(user_cont == null) {%>
													<td align="right" valign="bottom" width="96px">
														♡<%=com.getFavCount() %>
														<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
														<span class="comCount"><%=com.getComCount() %></span>
													</td>
												<%}
												else {%>
													<td align="right" valign="bottom" width="96px">
														<span class="fav fav_<%=com.getId()%>" onclick="changeFav(<%=com.getId()%>,<%=com.getFav()%>,<%=com.getFavCount() %>,'<%=user_cont.getUserid() %>');"><%=com.getFav() == true ? "❤" : "♡" %></span>
														<span class="favCount_<%=com.getId()%>"><%=com.getFavCount() %></span>
														<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
														<span class="comCount"><%=com.getComCount() %></span>
													</td>
												<%} %>
											</tr>
										</table>
										<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
										<%} %>
									<%} %>
								</div>
								<div class="body2">
									<%if(timeline_self_list == null || timeline_self_list.size() == 0){ %>
										コメントはありません。<br>
									<%}else {%>
										<%for(Comment com : timeline_self_list) {

											String dif = CompareDate.compareDate(com.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));

										%>
										<table align="left" style="width:calc(98vw - 300px)">
											<tr>
												<td align="left" valign="top" width="72px" rowspan="2">
													<a href="<%=request.getContextPath()%>/userpage?usr=<%=com.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
												</td>
												<td align="left" rowspan="2">
													<%=com.getName() %><font size="2" color="grey">@<%=com.getUserid() %> <%=dif %></font>
													<%=com.getParentid() != null ? "<font color=\"grey\" size=\"2\"><a href=\"" + request.getContextPath() + "/mainpage?id=" + com.getParentid() + "\">←&nbsp;返信</a></font>" : "" %>
													<p><%=com.getText().replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replace("\n", "<br>") %></p>
													<%if(com.getPhoto() != null) {%>
														<img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getPhoto()), request, response) %>" width="256" height="auto" style="padding:0 0 8px">
													<%} %>
												</td>
												<td align="right" valign="top" width="32px">
													<div class="dropdown" align="right">
														<button class="dropdown_btn" id="dropdown_btn_2_<%=com.getId() %>" onclick="dropDown(2,<%=com.getId() %>)">
															<svg viewBox="0 0 512 512"><circle cx="256" cy="256" r="64" fill="grey"/><circle cx="256" cy="448" r="64" fill="grey"/><circle cx="256" cy="64" r="64" fill="grey"/></svg>
														</button>
														<div class="dropdown_body" align="left">
															<ul class="dropdown_list">
																<%if(user_cont != null && (user_cont.getUserid().equals(com.getUserid()) || user_cont.getAuthority())) { %>
																<li class="dropdown_item"><a href="<%=request.getContextPath() %>/deleteComment?id=<%=com.getId() %>" class="dropdown_item-link">削除</a></li>
																<%} %>
																<li class="dropdown_item"><a href="<%=request.getContextPath() %>/mainpage" class="dropdown_item-link">コメントを報告</a></li>
															</ul>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<%if(user_cont == null) {%>
													<td align="right" valign="bottom" width="96px">
														♡<%=com.getFavCount() %>
														<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
														<span class="comCount"><%=com.getComCount() %></span>
													</td>
												<%}
												else {%>
													<td align="right" valign="bottom" width="96px">
														<span class="fav fav_<%=com.getId()%>" onclick="changeFav(<%=com.getId()%>,<%=com.getFav()%>,<%=com.getFavCount() %>,'<%=user_cont.getUserid() %>');"><%=com.getFav() == true ? "❤" : "♡" %></span>
														<span class="favCount_<%=com.getId()%>"><%=com.getFavCount() %></span>
														<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
														<span class="comCount"><%=com.getComCount() %></span>
													</td>
												<%} %>
											</tr>
										</table>
										<br>
										<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
										<%} %>
									<%} %>
								</div>
								<div class="body3">
									<%if(timeline_rep_list == null || timeline_rep_list.size() == 0){ %>
										コメントはありません。<br>
									<%}else {%>
										<%for(Comment com : timeline_rep_list) {

											String dif = CompareDate.compareDate(com.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));

										%>
										<table align="left" style="width:calc(98vw - 300px)">
											<tr>
												<td align="left" valign="top" width="72px" rowspan="2">
													<a href="<%=request.getContextPath()%>/userpage?usr=<%=com.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
												</td>
												<td align="left" rowspan="2">
													<%=com.getName() %><font size="2" color="grey">@<%=com.getUserid() %> <%=dif %></font>
													<%=com.getParentid() != null ? "<font color=\"grey\" size=\"2\"><a href=\"" + request.getContextPath() + "/mainpage?id=" + com.getParentid() + "\">←&nbsp;返信</a></font>" : "" %>
													<p><%=com.getText().replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replace("\n", "<br>") %></p>
													<%if(com.getPhoto() != null) {%>
															<img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getPhoto()), request, response) %>" width="256" height="auto" style="padding:0 0 8px">
													<%} %>
												</td>
												<td align="right" valign="top" width="32px">
													<div class="dropdown" align="right">
														<button class="dropdown_btn" id="dropdown_btn_3_<%=com.getId() %>" onclick="dropDown(3, <%=com.getId() %>)">
															<svg viewBox="0 0 512 512"><circle cx="256" cy="256" r="64" fill="grey"/><circle cx="256" cy="448" r="64" fill="grey"/><circle cx="256" cy="64" r="64" fill="grey"/></svg>
														</button>
														<div class="dropdown_body" align="left">
															<ul class="dropdown_list">
																<%if(user_cont != null && (user_cont.getUserid().equals(com.getUserid()) || user_cont.getAuthority())) { %>
																<li class="dropdown_item"><a href="<%=request.getContextPath() %>/deleteComment?id=<%=com.getId() %>" class="dropdown_item-link">削除</a></li>
																<%} %>
																<li class="dropdown_item"><a href="<%=request.getContextPath() %>/mainpage" class="dropdown_item-link">コメントを報告</a></li>
															</ul>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<%if(user_cont == null) {%>
													<td align="right" valign="bottom" width="96px">
														♡<%=com.getFavCount() %>
														<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
														<span class="comCount"><%=com.getComCount() %></span>
													</td>
												<%}
												else {%>
													<td align="right" valign="bottom" width="96px">
														<span class="fav fav_<%=com.getId()%>" onclick="changeFav(<%=com.getId()%>,<%=com.getFav()%>,<%=com.getFavCount() %>,'<%=user_cont.getUserid() %>');"><%=com.getFav() == true ? "❤" : "♡" %></span>
														<span class="favCount_<%=com.getId()%>"><%=com.getFavCount() %></span>
														<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
														<span class="comCount"><%=com.getComCount() %></span>
													</td>
												<%} %>
											</tr>
										</table>
										<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
										<%} %>
									<%} %>
								</div>
								<div class="body4">
									<%if(timeline_img_list == null || timeline_img_list.size() == 0){ %>
										コメントはありません。<br>
									<%}else {%>
										<%for(Comment com : timeline_img_list) {

											String dif = CompareDate.compareDate(com.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));

										%>
										<table align="left" style="width:calc(98vw - 300px)">
											<tr>
												<td align="left" valign="top" width="72px" rowspan="2">
													<a href="<%=request.getContextPath()%>/userpage?usr=<%=com.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
												</td>
												<td align="left" rowspan="2">
													<%=com.getName() %><font size="2" color="grey">@<%=com.getUserid() %> <%=dif %></font>
													<%=com.getParentid() != null ? "<font color=\"grey\" size=\"2\"><a href=\"" + request.getContextPath() + "/mainpage?id=" + com.getParentid() + "\">←&nbsp;返信</a></font>" : "" %>
													<p><%=com.getText().replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replace("\n", "<br>") %></p>
													<%if(com.getPhoto() != null) {%>
														<img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getPhoto()), request, response) %>" width="256" height="auto" style="padding:0 0 8px">
													<%} %>
												</td>
												<td align="right" valign="top" width="32px">
													<div class="dropdown" align="right">
														<button class="dropdown_btn" id="dropdown_btn_2_<%=com.getId() %>" onclick="dropDown(2,<%=com.getId() %>)">
															<svg viewBox="0 0 512 512"><circle cx="256" cy="256" r="64" fill="grey"/><circle cx="256" cy="448" r="64" fill="grey"/><circle cx="256" cy="64" r="64" fill="grey"/></svg>
														</button>
														<div class="dropdown_body" align="left">
															<ul class="dropdown_list">
																<%if(user_cont != null && (user_cont.getUserid().equals(com.getUserid()) || user_cont.getAuthority())) { %>
																<li class="dropdown_item"><a href="<%=request.getContextPath() %>/deleteComment?id=<%=com.getId() %>" class="dropdown_item-link">削除</a></li>
																<%} %>
																<li class="dropdown_item"><a href="<%=request.getContextPath() %>/mainpage" class="dropdown_item-link">コメントを報告</a></li>
															</ul>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<%if(user_cont == null) {%>
													<td align="right" valign="bottom" width="96px">
														♡<%=com.getFavCount() %>
														<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
														<span class="comCount"><%=com.getComCount() %></span>
													</td>
												<%}
												else {%>
													<td align="right" valign="bottom" width="96px">
														<span class="fav fav_<%=com.getId()%>" onclick="changeFav(<%=com.getId()%>,<%=com.getFav()%>,<%=com.getFavCount() %>,'<%=user_cont.getUserid() %>');"><%=com.getFav() == true ? "❤" : "♡" %></span>
														<span class="favCount_<%=com.getId()%>"><%=com.getFavCount() %></span>
														<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
														<span class="comCount"><%=com.getComCount() %></span>
													</td>
												<%} %>
											</tr>
										</table>
										<br>
										<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
										<%} %>
									<%} %>
								</div>
								<div class="body5">
									<%if(timeline_fav_list == null || timeline_fav_list.size() == 0){ %>
										コメントはありません。<br>
									<%}else {%>
										<%for(Comment com : timeline_fav_list) {

											String dif = CompareDate.compareDate(com.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));

										%>
										<table align="left" style="width:calc(98vw - 300px)">
											<tr>
												<td align="left" valign="top" width="72px" rowspan="2">
													<a href="<%=request.getContextPath()%>/userpage?usr=<%=com.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
												</td>
												<td align="left" rowspan="2">
													<%=com.getName() %><font size="2" color="grey">@<%=com.getUserid() %> <%=dif %></font>
													<%=com.getParentid() != null ? "<font color=\"grey\" size=\"2\"><a href=\"" + request.getContextPath() + "/mainpage?id=" + com.getParentid() + "\">←&nbsp;返信</a></font>" : "" %>
													<p><%=com.getText().replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replaceAll("((\\s|　+)#|^#|\n#)(.+?)((\\s|　+)|$|\n)", "$2<a href=" + request.getContextPath() + "/mainpage?text=%23$3&type=all>#$3</a>$5").replace("\n", "<br>") %></p>
													<%if(com.getPhoto() != null) {%>
														<img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(com.getPhoto()), request, response) %>" width="256" height="auto" style="padding:0 0 8px">
													<%} %>
												</td>
												<td align="right" valign="top" width="32px">
													<div class="dropdown" align="right">
														<button class="dropdown_btn" id="dropdown_btn_5_<%=com.getId() %>" onclick="dropDown(5,<%=com.getId() %>)">
															<svg viewBox="0 0 512 512"><circle cx="256" cy="256" r="64" fill="grey"/><circle cx="256" cy="448" r="64" fill="grey"/><circle cx="256" cy="64" r="64" fill="grey"/></svg>
														</button>
														<div class="dropdown_body" align="left">
															<ul class="dropdown_list">
																<%if(user_cont != null && (user_cont.getUserid().equals(com.getUserid()) || user_cont.getAuthority())) { %>
																<li class="dropdown_item"><a href="<%=request.getContextPath() %>/deleteComment?id=<%=com.getId() %>" class="dropdown_item-link">削除</a></li>
																<%} %>
																<li class="dropdown_item"><a href="<%=request.getContextPath() %>/mainpage" class="dropdown_item-link">コメントを報告</a></li>
															</ul>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<%if(user_cont == null) {%>
													<td align="right" valign="bottom" width="96px">
														♡<%=com.getFavCount() %>
														<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
														<span class="comCount"><%=com.getComCount() %></span>
													</td>
												<%}
												else {%>
													<td align="right" valign="bottom" width="96px">
														<span class="fav fav_<%=com.getId()%>" onclick="changeFav(<%=com.getId()%>,<%=com.getFav()%>,<%=com.getFavCount() %>,'<%=user_cont.getUserid() %>');"><%=com.getFav() == true ? "❤" : "♡" %></span>
														<span class="favCount_<%=com.getId()%>"><%=com.getFavCount() %></span>
														<span class="com" onclick="location.href='<%=request.getContextPath() %>/mainpage?id=<%=com.getId() %>'">💬</span>
														<span class="comCount"><%=com.getComCount() %></span>
													</td>
												<%} %>
											</tr>
										</table>
										<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
										<%} %>
									<%} %>
								</div>
								<div class="body6">
									<%if(followed_list == null || followed_list.size() == 0){ %>
										条件に該当するユーザーは見つかりません。<br>
									<%}else {%>
										<%for(User u : followed_list) {%>
										<table align="left" style="width:calc(98vw - 300px)">
											<tr>
												<td align="left" valign="top" width="72px">
													<a href="<%=request.getContextPath()%>/userpage?usr=<%=u.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(u.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
												</td>
												<td align="left">
													<%=u.getName() %><font size="2" color="grey">@<%=u.getUserid() %></font>
													<%if(user_cont != null && !user_cont.getUserid().equals(u.getUserid())) {%>
													&emsp;<a class="btn btn_follow_<%=u.getUserid() %> btn_follow_<%=u.getFollowed()%>" onclick="changeFollow('<%=u.getUserid()%>',<%=u.getFollowed()%>,'<%=user_cont.getUserid() %>');"><span class="follow follow_<%=u.getUserid()%>" ><%=u.getFollowed() == true ? "フォロー済み" : "フォロー" %></span></a>
													<%} %>
													<%=u.getPrivacy() ? "🔑" : "" %>
													<p><%=u.getProfile().replace("\n", "<br>") %></p>
												</td>
											</tr>
										</table>
										<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
										<%} %>
									<%} %>
								</div>
								<div class="body7">
									<%if(follower_list == null || follower_list.size() == 0){ %>
										条件に該当するユーザーは見つかりません。<br>
									<%}else {%>
										<%for(User u : follower_list) {%>
										<table align="left" style="width:calc(98vw - 300px)">
											<tr>
												<td align="left" valign="top" width="72px">
													<a href="<%=request.getContextPath()%>/userpage?usr=<%=u.getUserid()%>"><img src="data:image/png;base64,<%=ImageConvert.writeImage(ImageConvert.byteToImage(u.getIcon()), request, response) %>" width="64" height="64" style="padding:0 0 8px"></a>
												</td>
												<td align="left">
													<%=u.getName() %><font size="2" color="grey">@<%=u.getUserid() %></font>
													<%if(user_cont != null && !user_cont.getUserid().equals(u.getUserid())) {%>
													&emsp;<a class="btn btn_follow_<%=u.getUserid() %> btn_follow_<%=u.getFollowed()%>" onclick="changeFollow('<%=u.getUserid()%>',<%=u.getFollowed()%>,'<%=user_cont.getUserid() %>');"><span class="follow follow_<%=u.getUserid()%>" ><%=u.getFollowed() == true ? "フォロー済み" : "フォロー" %></span></a>
													<%} %>
													<%=u.getPrivacy() ? "🔑" : "" %>
													<p><%=u.getProfile().replace("\n", "<br>") %></p>
												</td>
											</tr>
										</table>
										<hr align="center" size="1" color="blue" style="width:calc(95vw - 280px)"></hr>
										<%} %>
									<%} %>
								</div>

							</div>

						</div>
					</div>

				<%}
			}

		}

	}%>

<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="<%=request.getContextPath() %>/common/uploadImg.js" type="text/javascript"></script>