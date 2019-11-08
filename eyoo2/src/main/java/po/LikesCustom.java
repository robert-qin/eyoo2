package po;

public class LikesCustom extends Likes {

	// 扩展自定义的date String类型
	private String date;

	private eyooCustom eyoo;
	private UserCustom user;

	private String eyoo_nickname;
	private String eyoo_face;

	public String geteyoo_face() {
		return eyoo_face;
	}

	public void seteyoo_face(String eyoo_face) {
		this.eyoo_face = eyoo_face;
	}

	public String geteyoo_nickname() {
		return eyoo_nickname;
	}

	public void seteyoo_nickname(String eyoo_nickname) {
		this.eyoo_nickname = eyoo_nickname;
	}

	public eyooCustom geteyoo() {
		return eyoo;
	}

	public void seteyoo(eyooCustom eyoo) {
		this.eyoo = eyoo;
	}

	public UserCustom getUser() {
		return user;
	}

	public void setUser(UserCustom user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}