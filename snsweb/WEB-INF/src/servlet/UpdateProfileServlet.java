package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import bean.User;
import dao.UserDAO;

public class UpdateProfileServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request ,HttpServletResponse response) throws ServletException ,IOException{

		String error = "";
		String cmd = "";
		ArrayList<String> formMessage = new ArrayList<String>();

		// セッションスコープからuser情報を取得
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");

		if(user == null) {

			// セッション切れならerror.jspへフォワード
			error = "セッションがタイムアウトしました。";
			cmd = "signout";
			return;

		}

		// 変数宣言
		UserDAO objDao = new UserDAO();
		User user_new = new User();

		// エンコーディング
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
 		String userid = request.getParameter("userid");
 		String year = request.getParameter("year");
 		String month = request.getParameter("month");
 		String day = request.getParameter("day");
 		String phone1 = request.getParameter("phone1");
 		String phone2 = request.getParameter("phone2");
 		String phone3 = request.getParameter("phone3");
 		String profile = request.getParameter("profile");
 		String address = request.getParameter("address");
 		String email = request.getParameter("email");
 		String pass1 = request.getParameter("pass1");
 		String pass2 = request.getParameter("pass2");
 		Boolean privacy = request.getParameter("privacy") != null;
 		Boolean allow_dm = request.getParameter("allow_dm") != null;

 		String birthday = null;
 		String phone = null;

		try {

			// 各種入力の検査

			if(name == null || userid == null || year == null || month == null || month == null || day == null || email == null || pass1 == null || pass2 == null) {
				error = "不正なフォームアクセスです。";
				cmd = "mainpage";
			}
			else {

				if(name.equals("")) {
					formMessage.add("【名前】名前が未入力です。");
				}

				if(userid.equals("")) {
					formMessage.add("【ユーザーID】ユーザーIDが未入力です。");
				}
				else if(!userid.matches("[a-zA-Z0-9_]+")) {
					formMessage.add("【ユーザーID】ユーザーIDには半角英数字またはアンダーバー(_)のみが使用できます。");
					userid = "";
				}
				else if(!user.getUserid().equals(userid) && objDao.selectByUserid(userid).getUserid() != null) {
					formMessage.add("【ユーザーID】そのユーザーIDは既に使用されています。");
					userid = "";
				}

				if(year.equals("") || month.equals("") || day.equals("")) {
					formMessage.add("【生年月日】生年月日が未入力です。");
				}
				else {

					try {

						if(month.length() == 1) {
							month = "0" + month;
						}
						if(day.length() == 1) {
							day = "0" + day;
						}

						int y = Integer.parseInt(year);
						int m = Integer.parseInt(month);
						int d = Integer.parseInt(day);

						birthday = year + "-" + month + "-" + day;

						if(!isDay(y, m, d)) {
							formMessage.add("【生年月日】生年月日が無効です。");
						}

					}catch(NumberFormatException e) {
						formMessage.add("【生年月日】生年月日が無効です。");
						year = "";
						month = "";
						day = "";
						birthday = "";
					}
				}

				if(email.equals("")) {
					formMessage.add("【メールアドレス】メールアドレスが未入力です。");
				}
				else if(!email.matches("^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$")) {
					formMessage.add("【メールアドレス】メールアドレスが無効です。");
					email = "";
				}
				else if(!user.getEmail().equals(email) && objDao.selectByEmail(email).getUserid() != null) {
					formMessage.add("【メールアドレス】そのメールアドレスは既に使用されています。");
					email = "";
				}

				if(pass1.equals("")) {
					formMessage.add("【パスワード】パスワードが未入力です。");
				}
				else if(!pass1.matches("[a-zA-Z0-9_]{8,}")) {
					formMessage.add("【パスワード】パスワードは半角英数字8文字以上を指定してください。");
				}

				if(pass2.equals("")) {
					formMessage.add("【パスワード（確認用）】パスワード（確認用）が未入力です。");
				}


				if(!pass1.equals(pass2)) {
					formMessage.add("【パスワード】パスワードと確認用パスワードが一致していません。");
				}

				if(!phone1.equals("") || !phone2.equals("") || !phone3.equals(""))

				try {

					Integer.parseInt(phone1);
					Integer.parseInt(phone2);
					Integer.parseInt(phone3);

					phone = phone1 + "-" + phone2 + "-" + phone3;

				}catch(NumberFormatException e) {
					formMessage.add("【電話番号】電話番号が無効です。");
					phone = "";
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

 				// 入力に不備があった場合、その不備をリクエストスコープに格納し、再入力のフォワード
				if(formMessage.size() >= 1) {
	 				request.setAttribute("formMessage", formMessage);
					request.getRequestDispatcher("/view/profile.jsp").forward(request, response);
				}
				// 正常な入力の場合、確認画面へのフォワード
				else {

					// Base64型を受け取り、byte[]データにデコード
					String str = request.getParameter("icon");
					byte[] bytes;
					if(!str.equals("")) {
						bytes = Base64.getDecoder().decode(str);
					}
					else {
						// セッションスコープからuser情報を取得
						bytes = user.getIcon();
					}

					user_new = new User(userid, pass1, name, birthday, email, phone, profile, address, false, privacy, allow_dm, false, bytes, user.getDate());

					// ユーザーをデータベースに登録
					objDao.update(user_new);

					// セッションスコープに値を追加後、フォワード
					session.setAttribute("user", user_new);
					request.getRequestDispatcher("/mainpage").forward(request, response);

				}

 			}

 		}
 	}

	private boolean isDay(int y, int m, int d) {

		if(1 <= d) {

			switch(m) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				if(d <= 31) {
					return true;
				}
			case 4:
			case 6:
			case 9:
			case 11:
				if(d <= 30) {
					return true;
				}
			case 2:
				if(d <= 28 || (d == 29 && (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0)))) {
					return true;
				}
			}

		}

		return false;

	}

}
