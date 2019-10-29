package po;

import java.util.List;

public class CommentCustom extends Comment {

	// 扩展User
	private User user;

	// 被评论微博用户昵称
	private String eyoo_nickname;

	private eyoo eyoo;

	// json评论时间
	private String time;

	// 评论回复数
	private int countReply;

	// 回复list
	private List<ReplyCustom> replyList;

	public String geteyoo_nickname() {
		return eyoo_nickname;
	}

	public void seteyoo_nickname(String eyoo_nickname) {
		this.eyoo_nickname = eyoo_nickname;
	}

	public eyoo geteyoo() {
		return eyoo;
	}

	public void seteyoo(eyoo eyoo) {
		this.eyoo = eyoo;
	}

	public List<ReplyCustom> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<ReplyCustom> replyList) {
		this.replyList = replyList;
	}

	public int getCountReply() {
		return countReply;
	}

	public void setCountReply(int countReply) {
		this.countReply = countReply;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
