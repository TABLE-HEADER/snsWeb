package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import bean.User;
import dao.SQLDAO;

public class ExecuteSQLServlet extends HttpServlet{
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
				ArrayList<String> sql_list = new ArrayList<String>();

				for(int i = 1; i <= 4; i++) {
					String sql = request.getParameter("sql" + i);
					if(!sql.equals("")) {
						sql_list.add(sql);
					}
				}

				// SQL実行
				SQLDAO sqlDao = new SQLDAO();
				sqlDao.execute(sql_list);

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

 		}
 	}

}
