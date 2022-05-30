package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import bean.User;
import dao.CommentDAO;

public class DeleteCommentServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request ,HttpServletResponse response) throws ServletException ,IOException{

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
		 		int id = Integer.parseInt(request.getParameter("id"));

		 		// コメント情報の削除
		 		CommentDAO objDao = new CommentDAO();
		 		int delcount = objDao.delete(id);

		 		if(delcount <= 0) {
		 			error = "コメントの削除に失敗しました。";
					cmd = "mainpage";
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
