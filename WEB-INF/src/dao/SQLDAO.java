package dao;

import java.sql.*;
import java.util.*;

public class SQLDAO{

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

	// sql実行メソッド
	public void execute(ArrayList<String> sql_list) {

		Connection con = null;
		Statement  smt = null;

		try{

			con = SQLDAO.getConnection();
			smt = con.createStatement();

			for(String sql : sql_list) {
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

	}
}
