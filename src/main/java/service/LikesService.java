package service;

import po.LikesCustom;
import po.Page;

public interface LikesService {

	// 点赞
	void like(int eyooId, Integer userId);

	// 取消赞
	void unlike(int eyooId, Integer userId);

	// 是否已赞 0——否 1——是
	int isLike(Integer userId, Integer eyooId);

	// 查询用户被赞过信息
	Page<LikesCustom> queryLikesByUserId(int userId,int pageNo);

	// 查询我赞过的微博
	Page<LikesCustom> queryLikedeyooSelf(Integer userId, int pageNo);


}
