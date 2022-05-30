package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import bean.Comment;
import bean.User;
import dao.CommentDAO;
import dao.UserDAO;

public class MainpageServlet extends HttpServlet{
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

			// searchを実行した場合、パラメータ取得
			request.setCharacterEncoding("UTF-8");
			String text = request.getParameter("text");
			String type = request.getParameter("type");

			// リプライページをクリックした場合、パラメータ取得
			String id_s = request.getParameter("id");
			int id = 0;

			// searchの場合
			if(text != null && type != null) {

				// typeに応じて検索方式を変更。とりあえず現段階では「全文検索」/「フォロワーのみ」/「ユーザー検索」
				if(type.equals("all")) {

					CommentDAO comDao = new CommentDAO();

					if(user == null) {
						request.setAttribute("timeline_list", comDao.searchAll(text.split("\\s|　+"), count));
					}
					else {
						request.setAttribute("timeline_list", comDao.searchAll(user.getUserid(), text.split("\\s|　+"), count));
					}

				}
				else if(type.equals("follower")) {

					if(user == null) {
						// セッション切れならerror.jspへフォワード
						error = "セッションがタイムアウトしました。";
						cmd = "signout";
					}
					else {
						CommentDAO comDao = new CommentDAO();
						request.setAttribute("timeline_list", comDao.searchFollowComment(user.getUserid(), text.split("\\s|　+"), count));
					}

				}

				else if(type.equals("username")) {

					UserDAO usrDao = new UserDAO();

					if(user == null) {
						request.setAttribute("user_list", usrDao.searchUsername(text, count));
					}
					else {
						request.setAttribute("user_list", usrDao.searchUsername(user.getUserid(), text, count));
					}

				}

			}
			else {

				// replyの場合
				if(id_s != null) {

					id = Integer.parseInt(id_s);

					// コメントの詳細表示の準備
					CommentDAO objDao = new CommentDAO();
					Comment com;
					ArrayList<Comment> com_list = null;

					if(user == null) {
						com = objDao.selectById(id);
						if(com.getName() != null) {
							com_list = objDao.selectReplyComment(id, count);
						}
					}
					else {
						com = objDao.selectById(id, user.getUserid());
						if(com.getName() != null) {
							com_list = objDao.selectReplyComment(id, user.getUserid(), count);
						}
					}


					request.setAttribute("Comment", com);
					request.setAttribute("timeline_list", com_list);

				}
				else {

					if(user == null) {

						// 権限がない場合、とりあえず直近のコメントをcount件表示
						// 余裕があれば過去24時間のお気に入り件数でソートとかに変更したい
						CommentDAO comDao = new CommentDAO();
						ArrayList<Comment> com_list = comDao.selectHot(count);

						// リクエストスコープへ送信
						request.setAttribute("timeline_list", com_list);

					}

					else {

						// フォローしているユーザーと自分のみタイムラインに表示
						CommentDAO comDao = new CommentDAO();
						ArrayList<Comment> com_list = comDao.selectFollowComment(user.getUserid(), count);

						// 自分のみタイムラインに表示
						ArrayList<Comment> com_self_list = comDao.selectSelfComment(user.getUserid(), user.getUserid(), count);

						// お気に入りのみタイムラインに表示
						ArrayList<Comment> com_fav_list = comDao.selectFavComment(user.getUserid(), user.getUserid(), count);

						// 画像付きコメントのみタイムラインに表示
						ArrayList<Comment> com_img_list = comDao.selectSelfCommentWithPhoto(user.getUserid(), user.getUserid(), count);

						// リプライのみタイムラインに表示
						ArrayList<Comment> com_rep_list = comDao.selectReplyAll(user.getUserid(), user.getUserid(), count);

						// フォロー・フォロワーの表示
						UserDAO usrDao = new UserDAO();
						ArrayList<User> followed_list = usrDao.selectFollowed(user.getUserid(), user.getUserid(), count);
						ArrayList<User> follower_list = usrDao.selectFollower(user.getUserid(), user.getUserid(), count);

						// リクエストスコープへ送信
						request.setAttribute("timeline_list", com_list);
						request.setAttribute("timeline_self_list", com_self_list);
						request.setAttribute("timeline_fav_list", com_fav_list);
						request.setAttribute("timeline_img_list", com_img_list);
						request.setAttribute("timeline_rep_list", com_rep_list);

						request.setAttribute("followed_list", followed_list);
						request.setAttribute("follower_list", follower_list);

					}

				}

			}


		}catch (NumberFormatException e) {
			error = "不正なフォームアクセスです。";
			cmd = "signout";
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
