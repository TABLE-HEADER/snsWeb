package bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Inform {

	// 変数宣言
	private int	id;
	private String	userid;
	private String	agentid;
	private String	type;
	private String	text;
	private boolean	readflag;
	private String	date;

	// 引数無しコンストラクタ
	public Inform() {
		this.id	= 0;
		this.userid	= null;
		this.agentid	= null;
		this.type	= null;
		this.text	= null;
		this.readflag	= false;
		this.date	= null;
	}

	// 引数有りコンストラクタ
	public Inform(String userid, String agentid, String type, String text) {
		this.id	= 0;
		this.userid	= userid;
		this.agentid	= agentid;
		this.type	= type;
		this.text	= text;
		this.readflag	= false;
		this.date	= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());;
	}

	// 引数有りコンストラクタ
	public Inform(int id, String userid, String agentid, String type, String text, boolean readflag, String date) {
		this.id	= id;
		this.userid	= userid;
		this.agentid	= agentid;
		this.type	= type;
		this.text	= text;
		this.readflag	= readflag;
		this.date	= date;
	}

	// セッターメソッド
	public void setId(int id) {
		this.id = id;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setReadflag(boolean readflag) {
		this.readflag = readflag;
	}
	public void setDate(String date) {
		this.date = date;
	}

	// ゲッターメソッド
	public int getId() {
		return this.id;
	}
	public String getUserid() {
		return this.userid;
	}
	public String getAgentid() {
		return this.agentid;
	}
	public String getType() {
		return this.type;
	}
	public String getText() {
		return this.text;
	}
	public boolean getReadflag() {
		return this.readflag;
	}
	public String getDate() {
		return this.date;
	}

}
