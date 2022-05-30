package bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment {

	// 変数宣言
	private int	id;
	private String	userid;
	private String	name;
	private byte[] icon;
	private String	text;
	private byte[]	photo;
	private Integer parentid;
	private Boolean fav;
	private int favCount;
	private int comCount;
	private String	date;

	// 引数無しコンストラクタ
	public Comment() {
		this.id	= 0;
		this.userid	= null;
		this.name	= null;
		this.icon	= null;
		this.text	= null;
		this.photo	= null;
		this.parentid	= null;
		this.fav	= false;
		this.favCount = 0;
		this.comCount = 0;
		this.date	= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()); // YYYY-MM-DD hh:nn:dd
	}

	// 引数有りコンストラクタ(DB挿入用)
	public Comment(String userid, String text, byte[] photo, Integer parentid) {
		this.id	= 0;
		this.userid	= userid;
		this.name = null;
		this.icon	= null;
		this.text	= text;
		this.photo	= photo;
		this.parentid	= parentid;
		this.fav	= false;
		this.favCount = 0;
		this.comCount = 0;
		this.date	= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
	}

	// 引数有りコンストラクタ
	public Comment(int id, String userid, String name, byte[] icon, String text, byte[] photo, Integer parentid, Boolean fav, int favCount, int comCount, String date) {
		this.id	= id;
		this.userid	= userid;
		this.name = name;
		this.icon	= icon;
		this.text	= text;
		this.photo	= photo;
		this.parentid	= parentid;
		this.fav	= fav;
		this.favCount = favCount;
		this.comCount = comCount;
		this.date	= date;
	}

	// セッターメソッド
	public void setId(int id) {
		this.id = id;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public void setFav(Boolean fav) {
		this.fav = fav;
	}
	public void setFavCount(int favCount) {
		this.favCount = favCount;
	}
	public void setComCount(int comCount) {
		this.comCount = comCount;
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
	public String getName() {
		return this.name;
	}
	public byte[] getIcon() {
		return this.icon;
	}
	public String getText() {
		return this.text;
	}
	public byte[] getPhoto() {
		return this.photo;
	}
	public Integer getParentid() {
		return this.parentid;
	}
	public Boolean getFav() {
		return this.fav;
	}
	public int getFavCount() {
		return this.favCount;
	}
	public int getComCount() {
		return this.comCount;
	}
	public String getDate() {
		return this.date;
	}

}
