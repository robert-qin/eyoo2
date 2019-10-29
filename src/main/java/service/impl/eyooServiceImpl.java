package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import po.Page;
import mapper.eyooMapperCustom;
import po.eyooCustom;
import po.eyooVo;
import service.eyooService;

@Transactional
public class eyooServiceImpl implements eyooService {

	@Autowired
	private eyooMapperCustom eyooMapperCustom;


	// 根据用户id查询微博列表
	@Override
	public Page<eyooCustom> qeuryByUserId(Integer userId, int pageNo) throws Exception {
		Page<eyooCustom> page = new Page<eyooCustom>();
		page.setPageNo(pageNo);
		page.setPageSize(8);
		page.getParams().put("userId", userId);
		List<eyooCustom> eyooList = eyooMapperCustom.qeuryByUserId(page);
		page.setResults(eyooList);
		
		return page;
	}

	// 发送微博
	@Override
	public void post(eyooVo eyooVo) throws Exception {
		eyooMapperCustom.post(eyooVo);
	}

	// 删除微博
	@Override
	public void deleteByeyooId(Integer eyooId) throws Exception {
		eyooMapperCustom.deleteByeyooId(eyooId);
	}

	// 根据微博id查询微博
	@Override
	public List<eyooCustom> queryeyooByeyooId(Integer eyooId) throws Exception {
		return eyooMapperCustom.queryeyooByeyooId(eyooId);
	}

	// 转发微博
	@Override
	public void repost(eyooCustom eyoo) throws Exception {
		eyooMapperCustom.repost(eyoo);
	}

	// 查询赞次数
	@Override
	public int queryLikeCount(Integer eyooId) {
		return eyooMapperCustom.queryLikeCount(eyooId);
	}

	// 查询转发次数
	@Override
	public int queryRepostCount(Integer eyooId) {
		return eyooMapperCustom.queryRepostCount(eyooId);
	}

	// 查询评论次数
	@Override
	public int queryCommentCount(Integer eyooId) {
		
		return eyooMapperCustom.queryCommentCount(eyooId)+eyooMapperCustom.queryReplyCount(eyooId);
	}

	// 根据用户id查询被转发的微博
	@Override
	public Page<eyooCustom> queryRepostByUserId(Page<eyooCustom> page) {
		
		List<eyooCustom> eyooList = eyooMapperCustom.queryRepostByUserId(page);
		page.setResults(eyooList);
		return page;
	}

	// 首页遍历微博——实时
	@Override
	public Page<eyooCustom> queryAlleyooNow(int pageNo) throws Exception {
		Page<eyooCustom> page = new Page<eyooCustom>();
		page.setPageNo(pageNo);
		page.setPageSize(8);
		List<eyooCustom> eyooList = eyooMapperCustom.queryAlleyooNow(page);
		page.setResults(eyooList);
		
		return page;
	}

	// 首页遍历微博——好友圈	
	@Override
	public Page<eyooCustom> queryAlleyooFriends(int userId, int pageNo) throws Exception {
		Page<eyooCustom> page = new Page<eyooCustom>();
		page.setPageNo(pageNo);
		page.setPageSize(8);
		page.getParams().put("userId1", userId);
		page.getParams().put("userId2", userId);
		List<eyooCustom> eyooList = eyooMapperCustom.queryAlleyooFriends(page);
		page.setResults(eyooList);
		
		return page;
	}

	// 首页遍历微博——首页	
	@Override
	public Page<eyooCustom> queryAlleyooFollow(Integer userId, int pageNo) throws Exception {
		Page<eyooCustom> page = new Page<eyooCustom>();
		page.setPageNo(pageNo);
		page.setPageSize(8);
		page.getParams().put("userId", userId);
		List<eyooCustom> eyooList = eyooMapperCustom.queryAlleyooFollow(page);
		page.setResults(eyooList);
		
		return page;
	}

	// 根据关键字搜索微博
	@Override
	public Page<eyooCustom> queryeyooByWord(String keyWord, int pageNo) throws Exception {
		Page<eyooCustom> page = new Page<eyooCustom>();
		page.setPageNo(pageNo);
		page.setPageSize(8);
		page.getParams().put("keyWord", keyWord);
		List<eyooCustom> eyooList = eyooMapperCustom.queryeyooByWord(page);
		page.setResults(eyooList);
		
		return page;
	}

}
