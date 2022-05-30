<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList"%>

<%

 %>

<html>
	<head>
		<title>KanDan</title>
		<link rel="stylesheet" href="<%=request.getContextPath() %>/view/style.css">
		<link rel="stylesheet" href="<%=request.getContextPath() %>/view/tabmenu.css">
		<link rel="stylesheet" href="<%=request.getContextPath() %>/view/dropdown.css">
		<link rel="stylesheet" href="<%=request.getContextPath() %>/view/follow.css">
		<script src="<%=request.getContextPath() %>/common/fav.js" type="text/javascript"></script>
		<script src="<%=request.getContextPath() %>/common/dropdown.js" type="text/javascript"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	</head>
	<body>

		<div class="flex_field">

			<div class = "header">
				<%@include file= "/common/header.jsp" %>
			</div>

			<div class="main">

				<div class="sidebar">
					<%@include file= "/common/sidebar.jsp" %>
				</div>

				<div class="content">

					<form method="get" action="<%=request.getContextPath() %>/mainpage">
						<div style="display: flex; flex-direction: row; margin:10px 0 10px 10px">
							<input type="text" name="text" placeholder="検索ワードを入力" style="flex-grow: 1; margin-right:5px">
							<select name="type" style="width:90px;">
								<option value="all">全文検索</option>
								<%if(user != null) {%>
								<option value="follower">フォロワーのみ</option>
								<%} %>
								<option value="username">ユーザー検索</option>
							</select>
						</div>
					</form>


					<%@include file= "/common/content.jsp" %>

				</div>

			</div>

		</div>

	</body>
</html>