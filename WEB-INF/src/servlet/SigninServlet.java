package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import bean.User;
import dao.UserDAO;

public class SigninServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request ,HttpServletResponse response) throws ServletException ,IOException{

		String error = "";
		String cmd = "";

		try {

			// 変数宣言
			UserDAO objDao = new UserDAO();
			User user = new User();

			// エンコーディング
			request.setCharacterEncoding("UTF-8");
	 		String userid = request.getParameter("userid");
	 		String password = request.getParameter("password");

	 		// idがユーザーIDかメールアドレスかで分岐
	 		if(userid.matches("@" + "[a-zA-Z0-9_]+")) {

	 			userid = userid.substring(1);

	 			// DTOオブジェクトの生成
				user = objDao.selectByUseridAndPass(userid, password);

	 		}
	 		else if(userid.matches("^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$")) {

	 			// DTOオブジェクトの生成
				user = objDao.selectByEmailAndPass(userid, password);

	 		}

			// ユーザが存在しない場合の処理
			if(user.getUserid() == null) {
				error = "入力データが間違っています。";
				cmd = "login";
			}
			else {

				// user情報をクッキーに登録
	 			Cookie userCookie = new Cookie("userid", user.getUserid());
	 			userCookie.setMaxAge(60 * 60 * 24 * 5);
	 			response.addCookie(userCookie);

	 			Cookie passwordCookie = new Cookie("password", user.getPassword());
	 			passwordCookie.setMaxAge(60 * 60 * 24 * 5);
	 			response.addCookie(passwordCookie);

				// user情報をセッションスコープへ
				HttpSession session = request.getSession();
				session.setAttribute("user", user);

			}

//			ArrayList<User> user_list = objDao.selectAll();
//			for(User u : user_list) {
//
//				// DTOオブジェクトの初期化
//				String path = getServletContext().getRealPath("./file/icon.png").toString();
//				BufferedImage img = ImageConvert.readImage(path);
//				ImageConvert.changeRGB(img);
//				byte[] bytes = ImageConvert.imageToByte(img);
//
//				u.setIcon(bytes);
//
//				// ユーザーをデータベースに登録
//				objDao.update(u);
//
//			}

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
