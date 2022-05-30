package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class SignoutServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request ,HttpServletResponse response) throws ServletException ,IOException{

		String error = "";
		String cmd = "";

		try {

			// セッション情報のクリア
			// セッションオブジェクトの取得
			HttpSession session = request.getSession();
			// セッションがある場合、セッションを破棄
			if(session != null){
				session.invalidate();
			}

 		}catch(Exception e){
 			error ="予期せぬエラーが発生しました。<br>"+e;
 			cmd = "signout";
 		}finally{

 			// jspへのフォワード
 			if(error != "") {
 				request.setAttribute("error", cmd);
 				request.getRequestDispatcher("/mainpage").forward(request, response);
 			}
 			else {
 				request.getRequestDispatcher("/mainpage").forward(request, response);
 			}

 		}
 	}

}
