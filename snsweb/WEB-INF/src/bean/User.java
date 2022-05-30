package bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {

	// 変数宣言
	private String	userid;
	private String	password;
	private String	name;
	private String birthday;
	private String	email;
	private String	phone;
	private String	profile;
	private String	address;
	private boolean	authority;
	private boolean	privacy;
	private boolean	allow_dm;
	private boolean	ban;
	private boolean	follower;
	private boolean	followed;
	private byte[]	icon;
	private String	date;

	// 引数無しコンストラクタ
	public User() {
		this.userid	= null;
		this.password	= null;
		this.name	= null;
		this.birthday = null;
		this.email	= null;
		this.phone	= null;
		this.profile	= null;
		this.address	= null;
		this.authority	= false;
		this.privacy	= false;
		this.allow_dm	= false;
		this.ban	= false;
		this.icon	= null;
		this.follower	= false;
		this.followed	= false;
		this.date	= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()); // YYYY-MM-DD hh:nn:dd
	}

	// 引数有りコンストラクタ
	public User(String userid, String password, String name, String birthday, String email, boolean privacy, byte[] bytes) {
		this.userid	= userid;
		this.password	= password;
		this.name	= name;
		this.birthday = birthday;
		this.email	= email;
		this.phone	= null;
		this.profile	= null;
		this.address	= null;
		this.authority	= false;
		this.privacy	= privacy;
		this.allow_dm	= false;
		this.ban	= false;
		try {
			this.icon	= bytes;
		}catch(Exception e) {
			this.icon	= null;
		}
		this.follower	= false;
		this.followed	= false;
		this.date	= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()); // YYYY-MM-DD hh:nn:dd
	}

	// 引数有りコンストラクタ
	public User(String userid, String password, String name, String birthday, String email, String phone, String profile, String address, boolean authority, boolean privacy, boolean allow_dm, boolean ban, byte[] icon, String date) {
		this.userid	= userid;
		this.password	= password;
		this.name	= name;
		this.birthday = birthday;
		this.email	= email;
		this.phone	= phone;
		this.profile	= profile;
		this.address	= address;
		this.authority	= authority;
		this.privacy	= privacy;
		this.allow_dm	= allow_dm;
		this.ban	= ban;
		this.icon	= icon;
		this.follower	= false;
		this.followed	= false;
		this.date	= date;
	}

	// セッターメソッド
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setAuthority(boolean authority) {
		this.authority = authority;
	}
	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}
	public void setAllow_dm(boolean allow_dm) {
		this.allow_dm = allow_dm;
	}
	public void setBan(boolean ban) {
		this.ban = ban;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	public void setFollower(boolean follower) {
		this.follower = follower;
	}
	public void setFollowed(boolean followed) {
		this.followed = followed;
	}
	public void setDate(String date) {
		this.date = date;
	}

	// ゲッターメソッド
	public String getUserid() {
		return this.userid;
	}
	public String getPassword() {
		return this.password;
	}
	public String getName() {
		return this.name;
	}
	public String getBirthday() {
		return this.birthday;
	}
	public String getEmail() {
		return this.email;
	}
	public String getPhone() {
		return this.phone;
	}
	public String getProfile() {
		return this.profile;
	}
	public String getAddress() {
		return this.address;
	}
	public boolean getAuthority() {
		return this.authority;
	}
	public boolean getPrivacy() {
		return this.privacy;
	}
	public boolean getAllow_dm() {
		return this.allow_dm;
	}
	public boolean getBan() {
		return this.ban;
	}
	public byte[] getIcon() {
		return this.icon;
	}
	public boolean getFollower() {
		return this.follower;
	}
	public boolean getFollowed() {
		return this.followed;
	}
	public String getDate() {
		return this.date;
	}

}
