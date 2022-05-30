/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.32
 * Generated at: 2022-05-30 06:12:29 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.view;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import util.ImageConvert;
import bean.User;

public final class profile_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/common/header.jsp", Long.valueOf(1653874899337L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("bean.User");
    _jspx_imports_classes.add("util.ImageConvert");
    _jspx_imports_classes.add("java.util.ArrayList");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");


// セッションからユーザー情報を取得
User user_prof = (User)session.getAttribute("user");
// セッション切れか確認
if(user_prof == null){
	//セッション切れならerror.jspへフォワード
	request.setAttribute("error","セッションが切れています。");
	request.setAttribute("cmd","signout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}

String[] birthday = {"", "", ""};
if(user_prof.getBirthday() != null){
	birthday = user_prof.getBirthday().split("-");
}

String[] phone = {"", "", ""};
if(user_prof.getPhone() != null){
	phone = user_prof.getPhone().split("-");
}

String profile	= user_prof.getProfile() != null ? user_prof.getProfile() : "";
String address	= user_prof.getAddress() != null ? user_prof.getAddress() : "";

String name		= request.getParameter("name") != null ? request.getParameter("name") : user_prof.getName();
String userid_prof	= request.getParameter("userid") != null ? request.getParameter("userid") : user_prof.getUserid();
String year		= request.getParameter("year") != null ? request.getParameter("year") : birthday[0];
String month	= request.getParameter("month") != null ? request.getParameter("month") : birthday[1];
String day		= request.getParameter("day") != null ? request.getParameter("day") : birthday[2];
String email	= request.getParameter("email") != null ? request.getParameter("email") : user_prof.getEmail();
String phone1	= request.getParameter("phone1") != null ? request.getParameter("phone1") : phone[0];
String phone2	= request.getParameter("phone2") != null ? request.getParameter("phone2") : phone[1];
String phone3	= request.getParameter("phone3") != null ? request.getParameter("phone3") : phone[2];
profile			= request.getParameter("profile") != null ? request.getParameter("profile") : profile;
address			= request.getParameter("address") != null ? request.getParameter("address") : address;
String pass1	= request.getParameter("pass1") != null ? request.getParameter("pass1") : user_prof.getPassword();
String pass2	= request.getParameter("pass2") != null ? request.getParameter("pass2") : user_prof.getPassword();
boolean privacy	= request.getParameter("privacy") != null ? Boolean.parseBoolean(request.getParameter("privacy")) : user_prof.getPrivacy();
boolean allow_dm	= request.getParameter("allow_dm") != null ? Boolean.parseBoolean(request.getParameter("allow_dm")) : user_prof.getAllow_dm();
 
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("\t<head>\r\n");
      out.write("\t\t<title>KanDan</title>\r\n");
      out.write("\t\t<link rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath() );
      out.write("/view/style.css\">\r\n");
      out.write("\t</head>\r\n");
      out.write("\t<body>\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"flex_field\">\r\n");
      out.write("\r\n");
      out.write("\t\t\t<div class = \"header\">\r\n");
      out.write("\t\t\t\t");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	//セッションからユーザー情報を取得
	User user = (User)session.getAttribute("user");

      out.write("\r\n");
      out.write("<table align=\"center\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td style=\"width:40vw\">\r\n");
      out.write("\t\t\t<div align=\"left\"><a href=\"");
      out.print(request.getContextPath() );
      out.write("/mainpage\">\r\n");
      out.write("\t\t\t<img src=\"");
      out.print(request.getContextPath() );
      out.write("/file/kandan.png\" alt=\"kandan\" width=\"298px\" height=\"52px\" style=\"margin: 0 0 0 10px\"></a></div>\r\n");
      out.write("\t\t</td>\r\n");

	//セッション情報によって表示する内容を変更
	if(user != null){

      out.write("\r\n");
      out.write("\t\t<td align=\"right\" style=\"width:55vw\">\r\n");
      out.write("\t\t\t<br><br>\r\n");
      out.write("\t\t\t<a href=\"");
      out.print(request.getContextPath() );
      out.write("/signout\">サインアウト</a><br>\r\n");
      out.write("\t\t</td>\r\n");

	}
	else{

      out.write("\r\n");
      out.write("\t\t<td align=\"center\" style=\"width:35vw\">\r\n");
      out.write("\t\t\t");
if(request.getAttribute("login_failure") != null){ 
      out.write("\r\n");
      out.write("\t\t\t<font color=\"red\">ログインに失敗しました。</font>\r\n");
      out.write("\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t<br>\r\n");
      out.write("\t\t\t");

				String userid = "", password = "";

				//クッキーの取得
				Cookie[] userCookie = request.getCookies();

				//クッキーがあるか判定
				if(userCookie != null){
					for(int i = 0 ; i < userCookie.length; i++){
						//クッキーからユーザー情報の取得
						if(userCookie[i].getName().equals("userid")){
							userid = userCookie[i].getValue();
						}
						//クッキーからパスワード情報の取得
						if(userCookie[i].getName().equals("password")){
							password = userCookie[i].getValue();
						}
					}
			}
			
      out.write("\r\n");
      out.write("\t\t\t<form name=\"signin\" action=\"");
      out.print(request.getContextPath());
      out.write("/signin\" method=\"post\">\r\n");
      out.write("\t\t\t\t<input type=\"text\" name=\"userid\" placeholder=\"ユーザーID/メールアドレス\" value=\"@");
      out.print(userid );
      out.write("\"><br>\r\n");
      out.write("\t\t\t\t<input type=\"text\" name=\"password\" placeholder=\"パスワード\" value=\"");
      out.print(password );
      out.write("\"><br>\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t<td align=\"right\" style=\"width:20vw\">\r\n");
      out.write("\t\t\t<br>\r\n");
      out.write("\t\t\t<button onclick=\"location.href='");
      out.print(request.getContextPath() );
      out.write("/view/signup.jsp'\" style=\"color:white; background-color:green;\">サインアップ</button><br>\r\n");
      out.write("\t\t\t<button onclick=\"document.signin.submit();return false;\" style=\"color:white; background-color:blue;\">サインイン</button><br>\r\n");
      out.write("\t\t</td>\r\n");

	}

      out.write("\r\n");
      out.write("\t\t<td style=\"width:2vw\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("<hr align=\"center\" size=\"5\" color=\"blue\" style=\"width:98vw\"></hr>");
      out.write("\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<!--\r\n");
      out.write("\r\n");
      out.write("\t\t\t<div class=\"main\">\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<div class=\"sidebar\" style=\"width:100px\">\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<div class=\"content\">\r\n");
      out.write("\r\n");
      out.write("\t\t\t-->\r\n");
      out.write("\t\t\t\t\t");

					ArrayList<String> formMessage = (ArrayList<String>)request.getAttribute("formMessage");
					if(formMessage != null && formMessage.size() >= 1) {
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<p align=\"center\">\r\n");
      out.write("\t\t\t\t\t\t\t<font size=\"5\" color=\"red\">\r\n");
      out.write("\t\t\t\t\t\t\t\t** 入力に誤りがあります。以下のご確認をお願いします。 **<br>\r\n");
      out.write("\t\t\t\t\t\t\t</font>\r\n");
      out.write("\t\t\t\t\t\t\t<font size=\"4\" color=\"red\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								for(String message : formMessage){
									
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      out.print( message );
      out.write("<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");

								}
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t</font>\r\n");
      out.write("\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t\t");

					}
					
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<h1 align=\"center\">ユーザー情報変更</h1>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<form action=\"");
      out.print(request.getContextPath());
      out.write("/updateProfile\" method=\"post\">\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<table style=\"border-spacing:10px;\" align=\"center\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th width=\"250\" align=\"right\">名前<font color=\"red\">（必須）</font>：</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input type=text size=\"30\" id=\"name\" name=\"name\" value=\"");
      out.print(name );
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td rowspan=\"0\" valign=\"top\">&emsp;<a href=\"");
      out.print(request.getContextPath());
      out.write("/userpage?usr=");
      out.print(user_prof.getUserid());
      out.write("\"><img id=\"thumbnail\" src=\"data:image/png;base64,");
      out.print(ImageConvert.writeImage(ImageConvert.byteToImage(user_prof.getIcon()), request, response) );
      out.write("\" width=\"128\" height=\"128\" align=top style=\"padding:0 0 8px\"></a><br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=\"file\" id=\"uploadImg\" accept=\".png, .jpg, .jpeg, .bmp\"><br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<font color=\"grey\" size=\"2\">画像は128*128に縮小表示されます。</font>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=\"hidden\" id=\"icon\" name=\"icon\" value=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th width=\"250\" align=\"right\">ユーザーID<font color=\"red\">（必須）</font>：</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td>@<input type=text size=\"27\" name=\"userid\" placeholder=\"半角英数字を入力\" value=\"");
      out.print(userid_prof );
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th width=\"250\" align=\"right\">生年月日<font color=\"red\">（必須）</font>：</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=text size=\"5\" name=\"year\" placeholder=\"年\" value=\"");
      out.print(year );
      out.write("\">/\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=text size=\"5\" name=\"month\"  placeholder=\"月\" value=\"");
      out.print(month );
      out.write("\">/\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=text size=\"5\" name=\"day\"  placeholder=\"日\" value=\"");
      out.print(day );
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th width=\"250\" align=\"right\">メールアドレス<font color=\"red\">（必須）</font>：</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input type=text size=\"30\" name=\"email\" value=\"");
      out.print(email );
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th width=\"250\" align=\"right\">電話番号：</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=text size=\"5\" name=\"phone1\" value=\"");
      out.print(phone1 );
      out.write("\">-\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=text size=\"5\" name=\"phone2\" value=\"");
      out.print(phone2 );
      out.write("\">-\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=text size=\"5\" name=\"phone3\" value=\"");
      out.print(phone3 );
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th width=\"250\" align=\"right\">プロフィール：</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input type=text size=\"30\" name=\"profile\" value=\"");
      out.print(profile );
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th width=\"250\" align=\"right\">住所：</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input type=text size=\"30\" name=\"address\" value=\"");
      out.print(address );
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th width=\"250\" align=\"right\">パスワード<font color=\"red\">（必須）</font>：</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input type=text size=\"30\" name=\"pass1\" placeholder=\"半角英数字8文字以上\" value=\"");
      out.print(pass1 );
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<th width=\"250\" align=\"right\">パスワード（確認用）<font color=\"red\">（必須）</font>：</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td><input type=text size=\"30\" name=\"pass2\" value=\"");
      out.print(pass2 );
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<table align=\"center\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" name=\"privacy\" value=\"1\" ");
      out.print(privacy ? "checked" : "" );
      out.write("></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>アカウントを非公開にする</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" name=\"allow_dm\" value=\"1\" ");
      out.print(allow_dm ? "checked" : "" );
      out.write("></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td>フォロワー以外からのDMを許可しない</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<p align=\"center\" style=\"margin: 20px\"><input type=\"submit\" value=\"変更を保存\"></p>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t</form>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<!--\r\n");
      out.write("\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t</div> -->\r\n");
      out.write("\r\n");
      out.write("\t</body>\r\n");
      out.write("\t<script src=\"http://code.jquery.com/jquery-1.11.1.min.js\"></script>\r\n");
      out.write("\t<script src=\"");
      out.print(request.getContextPath() );
      out.write("/common/uploadImg.js\" type=\"text/javascript\"></script>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
