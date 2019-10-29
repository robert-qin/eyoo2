package mapper;

import java.util.List;
import java.util.Map;

import po.Page;
import po.eyooCustom;
import po.eyooVo;

public interface eyooMapperCustom {

	// 遍历所有Eyoo
	// public List<eyooCustom> queryAlleyoo() throws Exception;

	// 分页遍历——实时
	public List<eyooCustom> queryAlleyooNow(Page<eyooCustom> page) throws Exception;

	// 分页遍历——首页（我关注）
	public List<eyooCustom> queryAlleyooFollow(Page<eyooCustom> page) throws Exception;

	// 分页遍历——好友圈（相互关注）
	public List<eyooCustom> queryAlleyooFriends(Page<eyooCustom> page) throws Exception;

	
	// 根据用户id查询Eyoo
	public List<eyooCustom> qeuryByUserId(Page<eyooCustom> page) throws Exception;

	// 发Eyoo
	public void post(eyooVo eyooVo) throws Exception;

	// 根据Eyooid删除Eyoo
	public void deleteByeyooId(Integer eyooId) throws Exception;

	// 根据Eyooid查询Eyoo
	public List<eyooCustom> queryeyooByeyooId(Integer eyooId) throws Exception;

	// 转发Eyoo
	public void repost(eyooCustom eyoo) throws Exception;

	// 查询赞次数
	public int queryLikeCount(Integer eyooId);

	// 查询转发次数
	public int queryRepostCount(Integer eyooId);

	// 查询评论次数
	public int queryCommentCount(Integer eyooId);

	// 根据用户id查询被转发的Eyoo
	public List<eyooCustom> queryRepostByUserId(Page<eyooCustom> page);

	// 根据关键字搜索Eyoo
	public List<eyooCustom> queryeyooByWord(Page<eyooCustom> page);

	// 查询回复次数
	public int queryReplyCount(Integer eyooId);

}