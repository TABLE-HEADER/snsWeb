package dao;

import java.sql.*;
import java.util.*;

import bean.Inform;

public class InformDAO{

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
	public Inform selectById(int id) {

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Inform inform = new Inform();

		//SQL文
		String sql = "SELECT * FROM informinfo WHERE id = " + id + "";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をInformオブジェクトに格納
			if(rs.next()){

				inform.setId(rs.getInt("id"));
				inform.setUserid(rs.getString("userid"));
				inform.setAgentid(rs.getString("agentid"));
				inform.setType(rs.getString("type"));
				inform.setText(rs.getString("text"));
				inform.setReadflag(rs.getBoolean("readflag"));
				String date = rs.getString("date");
				inform.setDate(date.substring(0, date.length() - 2));

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
		return inform;
	}

	// selectAll
	public ArrayList<Inform> selectByUserid(String userid, int count){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Inform inform;
		ArrayList<Inform> list = new ArrayList<Inform>();

		//SQL文
		String sql = "SELECT * FROM informinfo WHERE userid = '" + userid + "' ORDER BY date DESC";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をInformオブジェクトに格納
			while(rs.next() && count > 0){

				inform = new Inform();
				inform.setId(rs.getInt("id"));
				inform.setUserid(rs.getString("userid"));
				inform.setAgentid(rs.getString("agentid"));
				inform.setType(rs.getString("type"));
				inform.setText(rs.getString("text"));
				inform.setReadflag(rs.getBoolean("readflag"));
				String date = rs.getString("date");
				inform.setDate(date.substring(0, date.length() - 2));

				list.add(inform);
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

	// selectAll
	public ArrayList<Inform> selectAll(){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Inform inform;
		ArrayList<Inform> list = new ArrayList<Inform>();

		//SQL文
		String sql = "SELECT * FROM informinfo";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をInformオブジェクトに格納
			while(rs.next()){
				inform = new Inform();
				inform.setId(rs.getInt("id"));
				inform.setUserid(rs.getString("userid"));
				inform.setAgentid(rs.getString("agentid"));
				inform.setType(rs.getString("type"));
				inform.setText(rs.getString("text"));
				inform.setReadflag(rs.getBoolean("readflag"));
				String date = rs.getString("date");
				inform.setDate(date.substring(0, date.length() - 2));

				list.add(inform);
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


	// データベースに新たなInform情報を登録するメソッド
	public void insert(Inform inform) {

		String sql = "INSERT INTO informinfo VALUES ("
		+ "NULL, "
		+ "'" + inform.getUserid() + "'" + ", "
		+ "'" + inform.getAgentid() + "'" + ", "
		+ "'" + inform.getType() + "'" + ", "
		+ "'" + inform.getText() + "'" + ", "
		+ inform.getReadflag() + ", "
		+ "'" + inform.getDate() + "'" + ")";

		sql = sql.replace("'null'", "NULL");

		Connection con = null;
		Statement  smt = null;

		try{

			con = InformDAO.getConnection();
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

	// データベースからidで指定された1件のInform情報の削除を行うメソッド
	public void delete(int id) {

		String sql = "DELETE FROM informinfo WHERE id = " + id + "";

		Connection con = null;
		Statement  smt = null;

		try{

			con = InformDAO.getConnection();
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


	// データベースのInform情報を更新するメソッド
	public void update(Inform inform) {

		String sql = "UPDATE informinfo SET "
		+ "id = " + inform.getId() + ", "
		+ "userid = '" + inform.getUserid() + "'" + ", "
		+ "agentid = '" + inform.getAgentid() + "'" + ", "
		+ "type = '" + inform.getType() + "'" + ", "
		+ "text = '" + inform.getText() + "'" + ", "
		+ "readflag = " + inform.getReadflag() + ", "
		+ "date = '" + inform.getDate() + "'" + " "
		+ "WHERE id = " + inform.getId() + "";

		Connection con = null;
		Statement  smt = null;

		sql = sql.replace("'null'", "NULL");

		try{

			con = InformDAO.getConnection();
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

	// 選択された通知情報の既読のみ書き換えるメソッド
	public int updateFlag(ArrayList<Inform> info_list) {

		int count = 0;

		String sql = "UPDATE informinfo SET "
				+ "readflag = true "
				+ "WHERE ";

		for(Inform info : info_list) {

			if(!info.getReadflag()) {
				sql += "id = " + info.getId() + " OR ";
				count++;
			}

		}
		sql = sql.substring(0, sql.length() - 4);

		Connection con = null;
		Statement  smt = null;

		try{

			if(sql.length() > 40) {

				con = InformDAO.getConnection();
				smt = con.createStatement();

				smt.executeUpdate(sql);

			}

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

	// search
	public ArrayList<Inform> search(int id){

		//変数宣言
		Connection con = null;
		Statement  smt = null;
		Inform inform;
		ArrayList<Inform> list = new ArrayList<Inform>();

		//SQL文
		String sql = "SELECT * FROM informinfo WHERE id LIKE %" + id + "%";

		try{
			con = getConnection();
			smt = con.createStatement();

			ResultSet rs = smt.executeQuery(sql);

			//取得した結果をInformオブジェクトに格納
			while(rs.next()){
				inform = new Inform();
				inform.setId(rs.getInt("id"));
				inform.setUserid(rs.getString("userid"));
				inform.setAgentid(rs.getString("agentid"));
				inform.setType(rs.getString("type"));
				inform.setText(rs.getString("text"));
				inform.setReadflag(rs.getBoolean("readflag"));
				String date = rs.getString("date");
				inform.setDate(date.substring(0, date.length() - 2));

				list.add(inform);
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


}

