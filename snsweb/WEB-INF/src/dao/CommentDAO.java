package dao;

import java.sql.*;
import java.util.*;

import bean.Comment;
import util.ImageConvert;

public class CommentDAO{

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

	// selectById
	public Comment selectById(int id) {

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment = new Comment();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid WHERE c.id = " + id + " AND u.privacy = false";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			if(rs.next()){
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				// nullの可能性があるため特殊な挙動？default:-1にすれば問題ないはず…
				// と考えたがどうやら外部キー制約に引っかかるらしい(id:-1のコメントが存在しないため)
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

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
		return comment;
	}

	// selectById(ユーザー情報有)
	public Comment selectById(int id, String userid) {

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment = new Comment();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id AND f.userid = '" + userid + "') AS fav, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid WHERE c.id = " + id + " "
				+ "AND (u.privacy = false OR (SELECT count(*) FROM followinfo WHERE followerid = c.userid AND followedid = '" + userid + "') > 0 OR c.userid = '" + userid + "') ";


		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			if(rs.next()){
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFav(rs.getBoolean("fav"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

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
		return comment;
	}

	// selectAll
	public ArrayList<Comment> selectAll(){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c "
				+ "INNER JOIN userinfo u ON c.userid = u.userid "
				+ "WHERE u.privacy = false AND c.parentid IS NULL";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next()){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
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

	// selectAll(count件版)
	public ArrayList<Comment> selectHot(int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ "WHERE u.privacy = false AND c.parentid IS NULL "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next() && count > 0){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
				count--;
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

	// selectAll(count件版/follow限定)
	public ArrayList<Comment> selectFollowComment(String userid, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id AND f.userid = '" + userid + "') AS fav, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ "WHERE (c.userid = ANY (SELECT followedid FROM followinfo WHERE followerid = '" + userid + "') "
				+ "OR c.userid = '" + userid + "') "
				+ "AND (u.privacy = false OR (SELECT count(*) FROM followinfo WHERE followerid = c.userid AND followedid = '" + userid + "') > 0 OR c.userid = '" + userid + "') "
				+ "AND c.parentid IS NULL "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next() && count > 0){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFav(rs.getBoolean("fav"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
				count--;
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

	// selectAll(count件版/self限定)
	public ArrayList<Comment> selectSelfComment(String userid, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ "WHERE c.userid = '" + userid + "' "
				+ "AND u.privacy = false AND c.parentid IS NULL "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next() && count > 0){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
				count--;
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

	// selectAll(count件版/self限定/userid有(他者))
	public ArrayList<Comment> selectSelfComment(String userid, String myid, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id AND f.userid = '" + myid + "') AS fav, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ "WHERE c.userid = '" + userid + "' "
				+ "AND (u.privacy = false OR (SELECT count(*) FROM followinfo WHERE followerid = c.userid AND followedid = '" + userid + "') > 0 OR c.userid = '" + userid + "') "
				+ "AND c.parentid IS NULL "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next() && count > 0){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFav(rs.getBoolean("fav"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
				count--;
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

	// selectAll(count件版/self限定/Photo限定)
	public ArrayList<Comment> selectSelfCommentWithPhoto(String userid, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ "WHERE c.userid = '" + userid + "' "
				+ "AND u.privacy = false "
				+ "AND photo IS NOT NULL "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next() && count > 0){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
				count--;
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

	// selectAll(count件版/self限定/userid有(他者)/Photo限定)
	public ArrayList<Comment> selectSelfCommentWithPhoto(String userid, String myid, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id AND f.userid = '" + myid + "') AS fav, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ "WHERE c.userid = '" + userid + "' "
				+ "AND (u.privacy = false OR (SELECT count(*) FROM followinfo WHERE followerid = c.userid AND followedid = '" + userid + "') > 0 OR c.userid = '" + userid + "') "
				+ "AND photo IS NOT NULL "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next() && count > 0){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFav(rs.getBoolean("fav"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
				count--;
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

	// selectAll(count件版/fav限定)
		public ArrayList<Comment> selectFavComment(String userid, int count){

			//変数宣言
			Connection con = null;
			Statement  smt = null;
			Comment comment;
			ArrayList<Comment> list = new ArrayList<Comment>();

			//SQL文
			String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
					+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
					+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id AND f.userid = '" + userid + "') AS fav, "
					+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
					+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
					+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
					+ "WHERE u.privacy = false "
					+ "ORDER BY c.date DESC";

			try{
				con = getConnection();
				smt = con.createStatement();

				ResultSet rs = smt.executeQuery(sql);

				//取得した結果をCommentオブジェクトに格納
				while(rs.next() && count > 0){

					if(rs.getBoolean("fav") == true) {

						comment = new Comment();
						comment.setId(rs.getInt("id"));
						comment.setUserid(rs.getString("userid"));
						comment.setName(rs.getString("name"));
						comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
						comment.setText(rs.getString("text"));
						if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
							comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
						}
						else {
							comment.setPhoto(null);
						}
						comment.setParentid((Integer)rs.getObject("parentid"));
						comment.setFavCount(rs.getInt("favCount"));
						comment.setComCount(rs.getInt("comCount"));
						String date = rs.getString("date");
						comment.setDate(date.substring(0, date.length() - 2));

						list.add(comment);

						count--;

					}

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


	// selectAll(count件版/fav限定/userid有(他者))
	public ArrayList<Comment> selectFavComment(String userid, String myid, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id AND f.userid = '" + userid + "') AS fav, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id AND f.userid = '" + myid + "') AS myfav, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ "WHERE (u.privacy = false OR (SELECT count(*) FROM followinfo WHERE followerid = c.userid AND followedid = '" + userid + "') > 0 OR c.userid = '" + userid + "') "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			//取得した結果をCommentオブジェクトに格納
			while(rs.next() && count > 0){

				if(rs.getBoolean("fav") == true) {

					comment = new Comment();
					comment.setId(rs.getInt("id"));
					comment.setUserid(rs.getString("userid"));
					comment.setName(rs.getString("name"));
					comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
					comment.setText(rs.getString("text"));
					if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
						comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
					}
					else {
						comment.setPhoto(null);
					}
					comment.setParentid((Integer)rs.getObject("parentid"));
					comment.setFav(rs.getBoolean("myfav"));
					comment.setFavCount(rs.getInt("favCount"));
					comment.setComCount(rs.getInt("comCount"));
					String date = rs.getString("date");
					comment.setDate(date.substring(0, date.length() - 2));

					list.add(comment);

					count--;

				}

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

	// selectAll(count件版/reply限定)
	public ArrayList<Comment> selectReplyComment(int id, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ "WHERE u.privacy = false AND c.parentid = " + id + " "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next() && count > 0){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
				count--;
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

	// selectAll(count件版/reply限定/userid有)
	public ArrayList<Comment> selectReplyComment(int id, String userid, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id AND f.userid = '" + userid + "') AS fav, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ "WHERE (u.privacy = false OR (SELECT count(*) FROM followinfo WHERE followerid = c.userid AND followedid = '" + userid + "') > 0 OR c.userid = '" + userid + "') "
				+ "AND c.parentid = " + id + " "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next() && count > 0){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFav(rs.getBoolean("fav"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
				count--;
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

	// selectAll(count件版/reply限定)
	public ArrayList<Comment> selectReplyAll(String userid, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ "WHERE u.privacy = false AND parentid IS NOT NULL AND c.userid = '" + userid + "' "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next() && count > 0){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
				count--;
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

	// selectAll(count件版/reply限定/userid有)
	public ArrayList<Comment> selectReplyAll(String userid, String myid, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id AND f.userid = '" + myid + "') AS fav, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ "WHERE (u.privacy = false OR (SELECT count(*) FROM followinfo WHERE followerid = c.userid AND followedid = '" + userid + "') > 0 OR c.userid = '" + userid + "') "
				+ "AND parentid IS NOT NULL AND c.userid = '" + userid + "' "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next() && count > 0){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFav(rs.getBoolean("fav"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
				count--;
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

	// データベースに新たなComment情報を登録するメソッド
	public void insert(Comment comment) {

		String sql = "INSERT INTO commentinfo VALUES (null, "
		+ "'" + comment.getUserid() + "'" + ", "
		+ "'" + comment.getText() + "'" + ", "
		+ "? , "
		+ comment.getParentid() + ", "
		+ "'" + comment.getDate() + "'" + ")";

		sql = sql.replace("'null'", "NULL");

		Connection con = null;
		PreparedStatement  smt = null;

		try{

			con = UserDAO.getConnection();
			smt = con.prepareStatement(sql);
			smt.setBytes(1, comment.getPhoto());

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

	// データベースからidで指定された1件のComment情報の削除を行うメソッド
	public int delete(int id) {

		String sql = "DELETE FROM commentinfo WHERE id = " + id + "";

		Connection con = null;
		Statement  smt = null;
		int count = 0;

		try{

			con = CommentDAO.getConnection();
			smt = con.createStatement();

			count = smt.executeUpdate(sql);

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

		return count;

	}


	// データベースのComment情報を更新するメソッド
	public void update(Comment comment) {

		String sql = "UPDATE commentinfo SET "
		+ "id = " + comment.getId() + ", "
		+ "userid = '" + comment.getUserid() + "'" + ", "
		+ "text = '" + comment.getText() + "'" + ", "
		+ "photo = ? , "
		+ "parentid = " + comment.getParentid() + ", "
		+ "date = '" + comment.getDate() + "'" + " "
		+ "WHERE id = " + comment.getId() + "";

		sql = sql.replace("'null'", "NULL");

		Connection con = null;
		PreparedStatement  smt = null;

		try{

			con = UserDAO.getConnection();
			smt = con.prepareStatement(sql);
			smt.setBytes(1, comment.getPhoto());

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

	// searchAll
	public ArrayList<Comment> searchAll(String[] text_list, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		String sql_like = "WHERE ";
		for(String text : text_list) {
			sql_like += "(c.text LIKE '%" + text + "%') AND ";
		}
		sql_like = sql_like.substring(0, sql_like.length() - 4);

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ sql_like
				+ "AND u.privacy = false "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next()){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
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

	// searchAll(userid有)
	public ArrayList<Comment> searchAll(String userid, String[] text_list, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		String sql_like = "WHERE (";
		for(String text : text_list) {
			sql_like += "(c.text LIKE '%" + text + "%') AND ";
		}
		sql_like = sql_like.substring(0, sql_like.length() - 5);

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id AND f.userid = '" + userid + "') AS fav, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ sql_like
				+ ") AND (u.privacy = false OR (SELECT count(*) FROM followinfo WHERE followerid = c.userid AND followedid = '" + userid + "') > 0 OR c.userid = '" + userid + "') "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next() && count > 0){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFav(rs.getBoolean("fav"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
				count--;
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

	// searchAll(userid有/フォロワーのみ)
	public ArrayList<Comment> searchFollowComment(String userid, String[] text_list, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Comment comment;
		ArrayList<Comment> list = new ArrayList<Comment>();

		String sql_like = "WHERE ";
		for(String text : text_list) {
			sql_like += "(c.text LIKE '%" + text + "%') AND ";
		}
		sql_like = sql_like.substring(0, sql_like.length() - 4);

		//SQL文
		String sql = "SELECT c.id AS id, c.userid AS userid, u.name AS name, u.icon AS icon, "
				+ "c.text AS text, c.photo AS photo, c.parentid AS parentid, c.date AS date, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id AND f.userid = '" + userid + "') AS fav, "
				+ "(SELECT count(*) FROM favinfo f WHERE f.id = c.id) AS favCount, "
				+ "(SELECT count(*) FROM commentinfo c2 WHERE c.id = c2.parentid) AS comCount "
				+ "FROM commentinfo c INNER JOIN userinfo u ON c.userid = u.userid "
				+ sql_like
				+ "AND (c.userid = ANY (SELECT followedid FROM followinfo WHERE followerid = '" + userid + "'))"
				+ "AND (u.privacy = false OR (SELECT count(*) FROM followinfo WHERE followerid = c.userid AND followedid = '" + userid + "') > 0 OR c.userid = '" + userid + "') "
				+ "ORDER BY c.date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をCommentオブジェクトに格納
			while(rs.next() && count > 0){
				comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserid(rs.getString("userid"));
				comment.setName(rs.getString("name"));
				comment.setIcon(ImageConvert.getByteFromResult(rs, "icon"));
				comment.setText(rs.getString("text"));
				if(rs.getString("photo") != null && !rs.getString("photo").equals("null")) {
					comment.setPhoto(ImageConvert.getByteFromResult(rs, "photo"));
				}
				else {
					comment.setPhoto(null);
				}
				comment.setParentid((Integer)rs.getObject("parentid"));
				comment.setFav(rs.getBoolean("fav"));
				comment.setFavCount(rs.getInt("favCount"));
				comment.setComCount(rs.getInt("comCount"));
				String date = rs.getString("date");
				comment.setDate(date.substring(0, date.length() - 2));

				list.add(comment);
				count--;
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

//	// sql実行メソッド
//	public void execute(String sql) {
//
//		Connection con = null;
//		Statement  smt = null;
//
//		try{
//
//			con = CommentDAO.getConnection();
//			smt = con.createStatement();
//
//			smt.executeUpdate(sql);
//
//		}
//		catch(Exception e){
//			throw new IllegalStateException(e);
//		}
//		finally{
//			if(smt != null){
//				try{smt.close();}catch(SQLException ignore){}
//			}
//			if(con != null){
//				try{con.close();}catch(SQLException ignore){}
//			}
//		}
//
//	}

}
