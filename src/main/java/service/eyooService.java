package service;

import java.util.List;

import po.Page;
import po.eyooCustom;
import po.eyooVo;

public interface eyooService {


	// 根据用户id查询Eyoo列表
	public Page<eyooCustom> qeuryByUserId(Integer userId, int pageNo) throws Exception;

	// 发Eyoo
	public void post(eyooVo eyooVo) throws Exception;

	// 删除Eyoo
	public void deleteByeyooId(Integer eyooId) throws Exception;

	// 根据EyooId查询Eyoo信息
	public List<eyooCustom> queryeyooByeyooId(Integer eyooId) throws Exception;

	// 转发
	public void repost(eyooCustom eyoo) throws Exception;

	// 查询点赞次数
	public int queryLikeCount(Integer eyooId);

	// 查询转发次数
	public int queryRepostCount(Integer eyooId);

	// 查询评论次数
	public int queryCommentCount(Integer eyooId);

	// 根据用户id查询被转发的Eyoo列表
	public Page<eyooCustom> queryRepostByUserId(Page<eyooCustom> page);

	// 遍历所有Eyoo 实时
	public Page<eyooCustom> queryAlleyooNow(int pageNo) throws Exception;

	// 遍历所有Eyoo 好友圈
	public Page<eyooCustom> queryAlleyooFriends(int userId, int pageNo) throws Exception;

	// 遍历所有Eyoo 首页
	public Page<eyooCustom> queryAlleyooFollow(Integer userId, int pageNo) throws Exception;

	// 根据关键字搜索相关Eyoo
	public Page<eyooCustom> queryeyooByWord(String keyWord, int pageNo) throws Exception;

}
