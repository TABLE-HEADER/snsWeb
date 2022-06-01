package servlet;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import bean.Comment;
import bean.Inform;
import bean.User;
import dao.CommentDAO;
import dao.InformDAO;

public class PostCommentServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request ,HttpServletResponse response) throws ServletException ,IOException{

		String error = "";
		String cmd = "";

		try {

			// セッションスコープからuser情報を取得
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");

			if(user == null) {

				// セッション切れならerror.jspへフォワード
				error = "セッションがタイムアウトしました。";
				cmd = "signout";

			}
			else {

				// エンコーディング
				request.setCharacterEncoding("UTF-8");
				String text = request.getParameter("text");

				// (あれば)画像を取得
				String str = request.getParameter("icon");
				byte[] bytes = null;
				if(!str.equals("")) {
					bytes = Base64.getDecoder().decode(str);
				}

				// テキストが空白のみの場合、空に変換
				if(text.matches("[\\s|　]*")) {
					text = "";
				}
				if(text.equals("") && str.equals("")) {
					error = "何も入力されていません！";
					cmd = "mainpage";
				}
				else {

					// (あれば)返信先を取得
					String id_s = request.getParameter("reply_id");
					String userid = request.getParameter("reply_userid");
					Integer id = null;
					if(id_s != null && !id_s.equals("")) {
						id = Integer.parseInt(id_s);
					}

					// コメントオブジェクト作成
					Comment com = new Comment(user.getUserid(), text, bytes, id);

					// コメント情報のデータベース挿入
					CommentDAO objDao = new CommentDAO();
					objDao.insert(com);

					// 返信の場合、通知を作成(useridが一致する場合を除く)
					if(id != null && !userid.equals(user.getUserid())) {
						InformDAO infoDao = new InformDAO();
						String info_text = "あなたのコメント(" + id_s + ")に返信しました";
						if(text.length() > 0){
							if(text.length() >= 21){
								text = text.substring(0,20) + "…";
							}
							info_text += "：「" + text + "」";
						}
						Inform info = new Inform(userid, user.getUserid(), "reply", info_text);
						infoDao.insert(info);
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
				request.getRequestDispatcher("/mainpage").forward(request, response);
			}

		}
	}

}
