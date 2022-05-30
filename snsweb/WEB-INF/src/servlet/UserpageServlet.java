package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import bean.Comment;
import bean.User;
import dao.CommentDAO;
import dao.UserDAO;

public class UserpageServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request ,HttpServletResponse response) throws ServletException ,IOException{
		commonProcess(request, response);
	}

	public void doPost(HttpServletRequest request ,HttpServletResponse response) throws ServletException ,IOException{
		commonProcess(request, response);
	}

	private void commonProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String error = "";
		String cmd = "";

		// コメント表示件数
		int count = 100;

		try {

			// セッションスコープからuser情報を取得
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");

			// パラメータからユーザID取得
			request.setCharacterEncoding("UTF-8");
			String userid = request.getParameter("usr");
			if(userid == null) {
				error = "不正なアクセスです。";
				cmd = "mainpage";
			}
			else {

				// ユーザーの取得
				UserDAO objDao = new UserDAO();
				User user_page;

				if(user == null) {
					user_page = objDao.selectByUserid(userid);
				}
				else {
					user_page = objDao.selectByUserid(userid, user.getUserid());
				}
				if(user_page.getUserid() == null) {
					error = "該当ユーザーが存在しません";
					cmd = "mainpage";
				}
				else {

					// 該当ユーザーのみタイムラインに表示
					CommentDAO comDao = new CommentDAO();
					UserDAO usrDao = new UserDAO();
					ArrayList<Comment> com_self_list;
					ArrayList<Comment> com_fav_list;
					ArrayList<Comment> com_img_list;
					ArrayList<Comment> com_rep_list;
					ArrayList<User> followed_list;
					ArrayList<User> follower_list;

					if(!user_page.getPrivacy() || (user != null && usrDao.isFollower(user_page.getUserid(), user.getUserid()))) {

						if(user == null) {
							com_self_list = comDao.selectSelfComment(user_page.getUserid(), count);
							com_fav_list = comDao.selectFavComment(user_page.getUserid(), count);
							com_img_list = comDao.selectSelfCommentWithPhoto(user_page.getUserid(), count);
							com_rep_list = comDao.selectReplyAll(user_page.getUserid(), count);
							followed_list = usrDao.selectFollowed(user_page.getUserid(), count);
							follower_list = usrDao.selectFollower(user_page.getUserid(), count);
						}
						else {
							com_self_list = comDao.selectSelfComment(user_page.getUserid(), user.getUserid(), count);
							com_fav_list = comDao.selectFavComment(user_page.getUserid(), user.getUserid(), count);
							com_img_list = comDao.selectSelfCommentWithPhoto(user_page.getUserid(), user.getUserid(), count);
							com_rep_list = comDao.selectReplyAll(user_page.getUserid(), user_page.getUserid(), count);
							followed_list = usrDao.selectFollowed(user_page.getUserid(), user.getUserid(), count);
							follower_list = usrDao.selectFollower(user_page.getUserid(), user.getUserid(), count);
						}

						// リクエストスコープへ送信
						request.setAttribute("user_page", user_page);
						request.setAttribute("timeline_self_list", com_self_list);
						request.setAttribute("timeline_fav_list", com_fav_list);
						request.setAttribute("timeline_img_list", com_img_list);
						request.setAttribute("timeline_rep_list", com_rep_list);

						request.setAttribute("followed_list", followed_list);
						request.setAttribute("follower_list", follower_list);

						request.setAttribute("visible", true);

					}
					else {

						// リクエストスコープへ送信
						request.setAttribute("user_page", user_page);
					}

				}

			}

		}catch (IllegalStateException e) {
			error = "DB接続エラーの為、ログインはできませんでした。";
			cmd = "signout";
 		}catch(Exception e){
 			error ="予期せぬエラーが発生しました。<br>"+e;
 			cmd = "signout";
 		}finally{

 			// jspへのフォワード
 			if(error != "") {
 				request.setAttribute("error", error);
 				request.setAttribute("cmd", cmd);
 				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
 			}
 			else {
 				request.getRequestDispatcher("/view/mainpage.jsp").forward(request, response);
 			}

 		}
 	}

}
