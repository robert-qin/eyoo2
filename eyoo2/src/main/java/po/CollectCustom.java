package po;

public class CollectCustom extends Collect {

	private String date;
	private String eyoo_nickname;
	private String eyoo_face;

	private eyooCustom eyoo;
	private UserCustom user;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String geteyoo_nickname() {
		return eyoo_nickname;
	}

	public void seteyoo_nickname(String eyoo_nickname) {
		this.eyoo_nickname = eyoo_nickname;
	}

	public String geteyoo_face() {
		return eyoo_face;
	}

	public void seteyoo_face(String eyoo_face) {
		this.eyoo_face = eyoo_face;
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

}
