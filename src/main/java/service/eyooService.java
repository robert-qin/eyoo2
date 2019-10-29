package service;

import java.util.List;

import po.Page;
import po.eyooCustom;
import po.eyooVo;

public interface eyooService {


	// 根据用户id查询微博列表
	public Page<eyooCustom> qeuryByUserId(Integer userId, int pageNo) throws Exception;

	// 发微博
	public void post(eyooVo eyooVo) throws Exception;

	// 删除微博
	public void deleteByeyooId(Integer eyooId) throws Exception;

	// 根据微博Id查询微博信息
	public List<eyooCustom> queryeyooByeyooId(Integer eyooId) throws Exception;

	// 转发
	public void repost(eyooCustom eyoo) throws Exception;

	// 查询点赞次数
	public int queryLikeCount(Integer eyooId);

	// 查询转发次数
	public int queryRepostCount(Integer eyooId);

	// 查询评论次数
	public int queryCommentCount(Integer eyooId);

	// 根据用户id查询被转发的微博列表
	public Page<eyooCustom> queryRepostByUserId(Page<eyooCustom> page);

	// 遍历所有微博 实时
	public Page<eyooCustom> queryAlleyooNow(int pageNo) throws Exception;

	// 遍历所有微博 好友圈
	public Page<eyooCustom> queryAlleyooFriends(int userId, int pageNo) throws Exception;

	// 遍历所有微博 首页
	public Page<eyooCustom> queryAlleyooFollow(Integer userId, int pageNo) throws Exception;

	// 根据关键字搜索相关微博
	public Page<eyooCustom> queryeyooByWord(String keyWord, int pageNo) throws Exception;

}
