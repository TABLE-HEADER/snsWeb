package dao;

import java.sql.*;
import java.util.*;

import bean.User;
import util.ImageConvert;

public class UserDAO{

	private static String RDB_DRIVE="com.mysql.jdbc.Driver";
	private static String URL="jdbc:mysql://localhost/snsdb";
	private static String USER="root";
	private static String PASSWD="root123";

	// データベース接続メソッド
	public static Connection getConnection()
	{
		try{
			Class.forName(RDB_DRIVE);
			Connection con = DriverManager.getConnection(URL, USER, PASSWD);
			return con;
		}catch(Exception e){
			throw new IllegalStateException(e);
		}
	}

	// selectByUserid
	public User selectByUserid(String userid) {

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user = new User();

		//SQL文
		String sql = "SELECT * FROM userinfo WHERE userid = '" + userid + "'";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			if(rs.next()){
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				user.setDate(rs.getString("date"));

			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return user;
	}

	// selectByEmail
	public User selectByEmail(String email) {

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user = new User();

		//SQL文
		String sql = "SELECT * FROM userinfo WHERE email = '" + email + "'";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			if(rs.next()){
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				user.setDate(rs.getString("date"));

			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return user;
	}

	// selectByUseridAndPass
	public User selectByUseridAndPass(String userid, String password) {

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user = new User();

		//SQL文
		String sql = "SELECT * FROM userinfo WHERE userid = '" + userid + "' and password = '" + password + "'" ;

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			if(rs.next()){
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				String date = rs.getString("date");
				user.setDate(date.substring(0, date.length() - 2));

			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return user;
	}

	// selectByEmailAndPass
	public User selectByEmailAndPass(String email, String password) {

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user = new User();

		//SQL文
		String sql = "SELECT * FROM userinfo WHERE email = '" + email + "' and password = '" + password + "'" ;

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			if(rs.next()){
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				String date = rs.getString("date");
				user.setDate(date.substring(0, date.length() - 2));

			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return user;
	}

	// selectByUserid(自分情報と別)
	public User selectByUserid(String userid, String myid) {

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user = new User();

		//SQL文
		//String sql = "SELECT * FROM userinfo WHERE userid = '" + userid + "'";

		String sql = "SELECT userid, password, name, birthday, email, phone, profile, address, authority, privacy, allow_dm, ban, icon, date, "
				+ "(SELECT count(*) FROM followinfo WHERE followerid = '" + myid + "' AND followedid = '" + userid + "') AS followed, "
				+ "(SELECT count(*) FROM followinfo WHERE followerid = '" + userid + "' AND followedid = '" + myid + "') AS follower "
				+ "FROM userinfo WHERE userid = '" + userid + "'";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			if(rs.next()){

				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				user.setFollowed(rs.getBoolean("followed"));
				user.setFollower(rs.getBoolean("follower"));
				user.setDate(rs.getString("date"));

			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return user;
	}

	// selectFollowed
	public ArrayList<User> selectFollowed(String userid, int count) {

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user;
		ArrayList<User> list = new ArrayList<User>();

		//SQL文
		String sql = "SELECT * FROM userinfo WHERE userid = ANY (SELECT followedid FROM followinfo WHERE followerid = '" + userid + "')" ;

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			while(rs.next()){
				user = new User();
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				String date = rs.getString("date");
				user.setDate(date.substring(0, date.length() - 2));

				list.add(user);

			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return list;
	}

	// selectFollower
	public ArrayList<User> selectFollower(String userid, int count) {

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user;
		ArrayList<User> list = new ArrayList<User>();

		//SQL文
		String sql = "SELECT userid, password, name, birthday, email, phone, profile, address, authority, privacy, allow_dm, ban, icon, date, "
				+ "(SELECT count(*) FROM followinfo WHERE followerid = userid AND followedid = '" + userid + "') AS followed "
				+ "FROM userinfo WHERE userid = ANY (SELECT followerid FROM followinfo WHERE followedid = '" + userid + "')" ;

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			while(rs.next()){
				user = new User();
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				String date = rs.getString("date");
				user.setDate(date.substring(0, date.length() - 2));

				list.add(user);

			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return list;
	}

	// selectFollowed(user情報有)
	public ArrayList<User> selectFollowed(String userid, String myid, int count) {

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user;
		ArrayList<User> list = new ArrayList<User>();

		//SQL文
		String sql = "SELECT userid, password, name, birthday, email, phone, profile, address, authority, privacy, allow_dm, ban, icon, date, "
				+ "(SELECT count(*) FROM followinfo WHERE followerid = userid AND followedid = '" + myid + "') AS follower, "
				+ "(SELECT count(*) FROM followinfo WHERE followerid = '" + myid + "' AND followedid = userid) AS followed "
				+ "FROM userinfo WHERE userid = ANY (SELECT followedid FROM followinfo WHERE followerid = '" + userid + "')" ;

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			while(rs.next()){
				user = new User();
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				user.setFollowed(rs.getBoolean("followed"));
				user.setFollower(rs.getBoolean("follower"));
				String date = rs.getString("date");
				user.setDate(date.substring(0, date.length() - 2));

				list.add(user);

			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return list;
	}

	// selectFollower
	public ArrayList<User> selectFollower(String userid, String myid, int count) {

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user;
		ArrayList<User> list = new ArrayList<User>();

		//SQL文
		String sql = "SELECT userid, password, name, birthday, email, phone, profile, address, authority, privacy, allow_dm, ban, icon, date, "
				+ "(SELECT count(*) FROM followinfo WHERE followerid = userid AND followedid = '" + myid + "') AS follower, "
				+ "(SELECT count(*) FROM followinfo WHERE followerid = '" + myid + "' AND followedid = userid) AS followed "
				+ "FROM userinfo WHERE userid = ANY (SELECT followerid FROM followinfo WHERE followedid = '" + userid + "')" ;

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			while(rs.next()){
				user = new User();
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				user.setFollowed(rs.getBoolean("followed"));
				user.setFollower(rs.getBoolean("follower"));
				String date = rs.getString("date");
				user.setDate(date.substring(0, date.length() - 2));

				list.add(user);

			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return list;
	}

	// selectAll
	public ArrayList<User> selectAll(){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user;
		ArrayList<User> list = new ArrayList<User>();

		//SQL文
		String sql = "SELECT * FROM userinfo";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			while(rs.next()){
				user = new User();
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				String date = rs.getString("date");
				user.setDate(date.substring(0, date.length() - 2));

				list.add(user);
			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return list;
	}

	// selectAll(myid)
	public ArrayList<User> selectAll(String myid){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user;
		ArrayList<User> list = new ArrayList<User>();

		//SQL文
		String sql = "SELECT userid, password, name, birthday, email, phone, profile, address, authority, privacy, allow_dm, ban, icon, date, "
				+ "(SELECT count(*) FROM followinfo WHERE followerid = '" + myid + "' AND followedid = userid) AS followed, "
				+ "(SELECT count(*) FROM followinfo WHERE followerid = userid AND followedid = '" + myid + "') AS follower "
				+ "FROM userinfo";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			while(rs.next()){
				user = new User();
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				user.setFollowed(rs.getBoolean("followed"));
				user.setFollower(rs.getBoolean("follower"));
				String date = rs.getString("date");
				user.setDate(date.substring(0, date.length() - 2));

				list.add(user);
			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return list;
	}


	// データベースに新たなUser情報を登録するメソッド
	public void insert(User user) {

		String sql = "INSERT INTO userinfo VALUES ("
		+ "'" + user.getUserid() + "'" + ", "
		+ "'" + user.getPassword() + "'" + ", "
		+ "'" + user.getName() + "'" + ", "
		+ "'" + user.getBirthday() + "'" + ", "
		+ "'" + user.getEmail() + "'" + ", "
		+ "'" + user.getPhone() + "'" + ", "
		+ "'" + user.getProfile() + "'" + ", "
		+ "'" + user.getAddress() + "'" + ", "
		+ user.getAuthority() + ", "
		+ user.getPrivacy() + ", "
		+ user.getAllow_dm() + ", "
		+ user.getBan() + ", "
		+ "?" + ", "
		+ "'" + user.getDate() + "'" + ")";

		sql = sql.replace("'null'", "NULL");

		Connection con = null;
		PreparedStatement  smt = null;

		try{

			con = UserDAO.getConnection();
			smt = con.prepareStatement(sql);
			smt.setBytes(1, user.getIcon());

			smt.executeUpdate();

		}
		catch(Exception e){
			throw new IllegalStateException(e);
		}
		finally{
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}

	}

	// データベースからuseridで指定された1件のUser情報の削除を行うメソッド
	public void delete(String userid) {

		String sql = "DELETE FROM userinfo WHERE userid = '" + userid + "'";

		Connection con = null;
		Statement  smt = null;

		try{

			con = UserDAO.getConnection();
			smt = con.createStatement();

			smt.executeUpdate(sql);

		}
		catch(Exception e){
			throw new IllegalStateException(e);
		}
		finally{
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}

	}


	// データベースのUser情報を更新するメソッド
	public void update(User user) {

		String sql = "UPDATE userinfo SET "
		+ "userid = '" + user.getUserid() + "'" + ", "
		+ "password = '" + user.getPassword() + "'" + ", "
		+ "name = '" + user.getName() + "'" + ", "
		+ "birthday = '" + user.getBirthday() + "'" + ", "
		+ "email = '" + user.getEmail() + "'" + ", "
		+ "phone = '" + user.getPhone() + "'" + ", "
		+ "profile = '" + user.getProfile() + "'" + ", "
		+ "address = '" + user.getAddress() + "'" + ", "
		+ "authority = " + user.getAuthority() + ", "
		+ "privacy = " + user.getPrivacy() + ", "
		+ "allow_dm = " + user.getAllow_dm() + ", "
		+ "ban = " + user.getBan() + ", "
		+ "icon = ? , "
		+ "date = '" + user.getDate() + "'" + " "
		+ "WHERE userid = '" + user.getUserid() + "'";

		sql = sql.replace("'null'", "NULL");

		Connection con = null;
		PreparedStatement  smt = null;

		try{

			con = UserDAO.getConnection();
			smt = con.prepareStatement(sql);
			smt.setBytes(1, user.getIcon());

			smt.executeUpdate();

		}
		catch(Exception e){
			throw new IllegalStateException(e);
		}
		finally{
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}

	}

	// search
	public ArrayList<User> search(String userid){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user;
		ArrayList<User> list = new ArrayList<User>();

		//SQL文
		String sql = "SELECT * FROM userinfo WHERE userid LIKE '%" + userid + "%'";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			while(rs.next()){
				user = new User();
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				String date = rs.getString("date");
				user.setDate(date.substring(0, date.length() - 2));

				list.add(user);
			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return list;
	}

	// search(ユーザー名)
	public ArrayList<User> searchUsername(String name, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user;
		ArrayList<User> list = new ArrayList<User>();

		//SQL文
		String sql = "SELECT * FROM userinfo WHERE name LIKE '%" + name + "%'";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			while(rs.next()){
				user = new User();
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				String date = rs.getString("date");
				user.setDate(date.substring(0, date.length() - 2));

				list.add(user);
			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return list;
	}

	// search(ユーザー名/ユーザー情報有)
	public ArrayList<User> searchUsername(String myid, String name, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		User user;
		ArrayList<User> list = new ArrayList<User>();

		//SQL文
		String sql = "SELECT userid, password, name, birthday, email, phone, profile, address, authority, privacy, allow_dm, ban, icon, date, "
				+ "(SELECT count(*) FROM followinfo WHERE followerid = '" + myid + "' AND followedid = userid) AS followed, "
				+ "(SELECT count(*) FROM followinfo WHERE followerid = userid AND followedid = '" + myid + "') AS follower "
				+ "FROM userinfo WHERE name LIKE '%" + name + "%'";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			while(rs.next()){
				user = new User();
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBirthday(rs.getString("birthday"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setProfile(rs.getString("profile"));
				user.setAddress(rs.getString("address"));
				user.setAuthority(rs.getBoolean("authority"));
				user.setPrivacy(rs.getBoolean("privacy"));
				user.setAllow_dm(rs.getBoolean("allow_dm"));
				user.setBan(rs.getBoolean("ban"));
				user.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				user.setFollowed(rs.getBoolean("followed"));
				user.setFollower(rs.getBoolean("follower"));
				String date = rs.getString("date");
				user.setDate(date.substring(0, date.length() - 2));

				list.add(user);
			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return list;
	}

	// フォローとフォロワーのカウント
	public int[] followCount(String userid){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		int[] count = new int[2];

		//SQL文
		String sql = "SELECT count(followerid = '" + userid + "' OR NULL) AS followerCount, "
				+ "count(followedid = '" + userid + "' OR NULL) AS followedCount FROM followinfo";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			if(rs.next()){
				count[0] = rs.getInt("followerCount");
				count[1] = rs.getInt("followedCount");
			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return count;
	}

	// フォローとフォロワーのカウント
	public boolean isFollower(String userid, String myid){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		boolean count = false;

		//SQL文
		String sql = "SELECT count((followerid = '" + userid + "' AND followedid = '" + myid + "') OR NULL) "
				+ "AS count FROM followinfo";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をUserオブジェクトに格納
			if(rs.next()){
				count = rs.getInt("count") > 0;
			}

		}catch(Exception e){
			throw new IllegalStateException(e);
		}finally{
			//リソースの開放
			if(smt != null){
				try{smt.close();}catch(SQLException ignore){}
			}
			if(con != null){
				try{con.close();}catch(SQLException ignore){}
			}
		}
		return count;
	}


}

