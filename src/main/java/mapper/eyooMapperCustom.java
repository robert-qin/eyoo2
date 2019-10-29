package mapper;

import java.util.List;
import java.util.Map;

import po.Page;
import po.eyooCustom;
import po.eyooVo;

public interface eyooMapperCustom {

	// 遍历所有微博
	// public List<eyooCustom> queryAlleyoo() throws Exception;

	// 分页遍历——实时
	public List<eyooCustom> queryAlleyooNow(Page<eyooCustom> page) throws Exception;

	// 分页遍历——首页（我关注）
	public List<eyooCustom> queryAlleyooFollow(Page<eyooCustom> page) throws Exception;

	// 分页遍历——好友圈（相互关注）
	public List<eyooCustom> queryAlleyooFriends(Page<eyooCustom> page) throws Exception;

	
	// 根据用户id查询微博
	public List<eyooCustom> qeuryByUserId(Page<eyooCustom> page) throws Exception;

	// 发微博
	public void post(eyooVo eyooVo) throws Exception;

	// 根据微博id删除微博
	public void deleteByeyooId(Integer eyooId) throws Exception;

	// 根据微博id查询微博
	public List<eyooCustom> queryeyooByeyooId(Integer eyooId) throws Exception;

	// 转发微博
	public void repost(eyooCustom eyoo) throws Exception;

	// 查询赞次数
	public int queryLikeCount(Integer eyooId);

	// 查询转发次数
	public int queryRepostCount(Integer eyooId);

	// 查询评论次数
	public int queryCommentCount(Integer eyooId);

	// 根据用户id查询被转发的微博
	public List<eyooCustom> queryRepostByUserId(Page<eyooCustom> page);

	// 根据关键字搜索微博
	public List<eyooCustom> queryeyooByWord(Page<eyooCustom> page);

	// 查询回复次数
	public int queryReplyCount(Integer eyooId);

}