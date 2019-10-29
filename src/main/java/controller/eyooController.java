package controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;
import po.CommentCustom;
import po.MentionCustom;
import po.Page;
import po.ReplyCustom;
import po.User;
import po.UserCustom;
import po.eyooCustom;
import po.eyooVo;
import service.CollectService;
import service.CommentService;
import service.LikesService;
import service.MentionService;
import service.ReplyService;
import service.UserService;
import service.eyooService;
import utils.DateConvert;

@Controller
public class eyooController {

	// eyoo
	@Autowired
	private eyooService eyooService;

	// User
	@Autowired
	private UserService userService;

	// Comment
	@Autowired
	private CommentService commentService;

	// Reply
	@Autowired
	private ReplyService replyService;

	// Mention
	@Autowired
	private MentionService mentionService;

	// Likes
	@Autowired
	private LikesService likesService;

	// Collect
	@Autowired
	private CollectService collectService;

	// date格式化工具类
	private DateConvert dateConvert;

	// 独立微博页面 详细评论页
	@SuppressWarnings("all")
	@RequestMapping(value = "/singleeyoo")
	public void singleeyoo(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("eyooId") Integer eyooId) throws Exception {
		//
		User user = (User) session.getAttribute("user");
		// 微博数
		int eyooCount = userService.queryeyooCount(user.getUserId());
		// 关注
		int followCount = userService.queryFollowCount(user.getUserId());
		// 粉丝
		int fansCount = userService.queryFansCount(user.getUserId());

		// 微博主体
		List<eyooCustom> eyooList = eyooService.queryeyooByeyooId(eyooId);
		eyooCustom eyoo = eyooList.get(0);
		eyoo.setDate(dateConvert.convert2s(eyoo.getPostTime()));

		// 非原创 即属于转发微博
		if (eyoo.getOriginal() == 0) {
			eyooCustom reposteyoo = eyooService.queryeyooByeyooId(eyoo.getRepostId()).get(0);
			reposteyoo.setDate(dateConvert.convert2s(reposteyoo.getPostTime()));
			eyoo.setRepost(reposteyoo);
		}
		// 评论主体
		List<CommentCustom> commentList = commentService.queryComment(eyooId);
		for (CommentCustom commentCustom : commentList) {
			// 遍历回复
			List<ReplyCustom> replyList = (replyService.queryReply(commentCustom.getCommentId()));
			for (ReplyCustom replyCustom : replyList) {
				replyCustom.setRtime(DateConvert.convert2s(replyCustom.getTime()));
			}
			commentCustom.setTime(DateConvert.convert2s(commentCustom.getCommentTime()));
			commentCustom.setReplyList(replyList);
		}

		request.setAttribute("user", user);
		request.setAttribute("eyoo", eyoo);
		request.setAttribute("eyooCount", eyooCount);
		request.setAttribute("followCount", followCount);
		request.setAttribute("fansCount", fansCount);
		request.setAttribute("commentList", commentList);

		request.getRequestDispatcher("/WEB-INF/jsp/eyoo/single.jsp").forward(request, response);

	}

	// 删除微博
	@RequestMapping(value = "deleteeyoo", method = RequestMethod.GET)
	public void deleteeyoo(@RequestParam("eyooId") Integer eyooId, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		eyooService.deleteByeyooId(eyooId);
	}

	// 转发微博
	@RequestMapping(value = "repost")
	public String repost(HttpSession session, @RequestParam("repostId") int repostId,
			@RequestParam("repostContent") String repostContent) throws Exception {

		// 微博扩展类
		eyooCustom eyoo = new eyooCustom();

		// userId
		User user = (User) session.getAttribute("user");
		eyoo.setUserId(user.getUserId());

		// 发送时间
		Date postTime = new java.sql.Date(new java.util.Date().getTime());
		eyoo.setPostTime(postTime);

		// 内容
		eyoo.setContent(repostContent);

		// 转发Id
		eyoo.setRepostId(repostId);

		eyooService.repost(eyoo);
		return "redirect:queryAlleyooNow.action?pageNo=1";
	}

	// 发送微博
	@RequestMapping(value = "post")
	public String post(HttpServletRequest request, HttpSession session, Model model, eyooVo eyooVo) throws Exception {
		int count = 0;
		for (int i = 1; i < 30; i++) {
			String pic = request.getParameter("pic_pic_" + i);
			if (pic != null) {
				count++;
				switch (count) {
				case 1:
					eyooVo.geteyooCustom().setPic1(pic);
					break;
				case 2:
					eyooVo.geteyooCustom().setPic2(pic);
					break;
				case 3:
					eyooVo.geteyooCustom().setPic3(pic);
					break;
				case 4:
					eyooVo.geteyooCustom().setPic4(pic);
					break;
				case 5:
					eyooVo.geteyooCustom().setPic5(pic);
					break;
				case 6:
					eyooVo.geteyooCustom().setPic6(pic);
					break;
				case 7:
					eyooVo.geteyooCustom().setPic7(pic);
					break;
				case 8:
					eyooVo.geteyooCustom().setPic8(pic);
					break;
				case 9:
					eyooVo.geteyooCustom().setPic9(pic);
					break;
				}
			}
		}

		// 用户id
		User user = (User) session.getAttribute("user");
		eyooVo.geteyooCustom().setUserId(user.getUserId());

		// 发送时间
		Date postTime = new java.sql.Date(new java.util.Date().getTime());
		eyooVo.geteyooCustom().setPostTime(postTime);

		eyooService.post(eyooVo);
		return "redirect:queryAlleyooNow.action?pageNo=1";
	}

	// 图片上传
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public @ResponseBody String upload(MultipartFile file, Model model, HttpSession session) throws Exception {
		JSONObject json = new JSONObject();
		// 原始名称
		String originalFilename = file.getOriginalFilename();
		// 上传图片
		if (file != null && originalFilename != null && originalFilename.length() > 0) {

			// 存储图片的物理路径
			String pic_path = "imgUpload";

			// 新的图片名称
			String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
			String pic_1, pic_2, pic_3, pic_4, pic_5, pic_6, pic_7, pic_8, pic_9;
			pic_1 = (String) session.getAttribute("pic_1");
			pic_2 = (String) session.getAttribute("pic_2");
			pic_3 = (String) session.getAttribute("pic_3");
			pic_3 = (String) session.getAttribute("pic_3");
			pic_4 = (String) session.getAttribute("pic_4");
			pic_5 = (String) session.getAttribute("pic_5");
			pic_6 = (String) session.getAttribute("pic_6");
			pic_7 = (String) session.getAttribute("pic_7");
			pic_8 = (String) session.getAttribute("pic_8");
			pic_9 = (String) session.getAttribute("pic_9");
			if (pic_1 == null) {
				pic_1 = newFileName;
				session.setAttribute("pic_1", newFileName);
			} else if (pic_2 == null) {
				pic_2 = newFileName;
				session.setAttribute("pic_2", newFileName);
			} else if (pic_3 == null) {
				pic_3 = newFileName;
				session.setAttribute("pic_3", newFileName);
			} else if (pic_4 == null) {
				pic_3 = newFileName;
				session.setAttribute("pic_4", newFileName);
			} else if (pic_5 == null) {
				pic_3 = newFileName;
				session.setAttribute("pic_5", newFileName);
			} else if (pic_6 == null) {
				pic_3 = newFileName;
				session.setAttribute("pic_6", newFileName);
			} else if (pic_7 == null) {
				pic_3 = newFileName;
				session.setAttribute("pic_7", newFileName);
			} else if (pic_8 == null) {
				pic_3 = newFileName;
				session.setAttribute("pic_8", newFileName);
			} else if (pic_9 == null) {
				pic_3 = newFileName;
				session.setAttribute("pic_9", newFileName);
			}
			// 新图片
			File newFile = new File(pic_path + newFileName);

			// 将内存中的数据写入磁盘
			file.transferTo(newFile);

			// 将新图片名称写到user中

			json.put("status", "OK");
			json.put("picName", newFileName);
			System.out.println(json.toString());
			return json.toString();
		}
		json.put("status", "NO");
		System.out.println(json.toString());
		return json.toString();
	}

	// 遍历所有微博 实时
	@SuppressWarnings("static-access")
	@RequestMapping(value = "queryAlleyooNow")
	public String queryAlleyooNow(HttpSession session, Model model, @RequestParam("pageNo") int pageNo)
			throws Exception {

		// 当前用户信息
		UserCustom user = (UserCustom) session.getAttribute("user");

		// 页码
		if (pageNo == 0) {
			pageNo = 1;
		}
		//
		// 遍历出微博列表
		Page<eyooCustom> page = eyooService.queryAlleyooNow(pageNo);
		for (eyooCustom eyooCustom : page.getResults()) {
			// 将date格式化 精确到s
			eyooCustom.setDate(dateConvert.convert2s(eyooCustom.getPostTime()));

			// 用户是否赞过
			eyooCustom.setLikes(likesService.isLike(user.getUserId(), eyooCustom.geteyooId()));

			// 用户是否收藏
			eyooCustom.setCollect(collectService.isCollect(user.getUserId(), eyooCustom.geteyooId()));

			// 查询微博转发 评论 点赞次数
			eyooCustom.setRepostCount(eyooService.queryRepostCount(eyooCustom.geteyooId()));
			eyooCustom.setCommentCount(eyooService.queryCommentCount(eyooCustom.geteyooId()));
			eyooCustom.setLikeCount(eyooService.queryLikeCount(eyooCustom.geteyooId()));
			// 非原创 即属于转发微博
			if (eyooCustom.getOriginal() == 0) {
				eyooCustom reposteyoo = eyooService.queryeyooByeyooId(eyooCustom.getRepostId()).get(0);
				reposteyoo.setDate(dateConvert.convert2s(reposteyoo.getPostTime()));
				eyooCustom.setRepost(reposteyoo);
			}
		}
		// 与我相关数据库存储值
		MentionCustom mention = mentionService.queryLastMention(user.getUserId());
		user.setMentionCustom(mention);

		// 微博数wi
		int eyooCount = userService.queryeyooCount(user.getUserId());
		// 关注
		int followCount = userService.queryFollowCount(user.getUserId());
		// 粉丝
		int fansCount = userService.queryFansCount(user.getUserId());
		user.seteyooCount(eyooCount);
		user.setFollowCount(followCount);
		user.setFansCount(fansCount);

		// 所在地
		String province = userService.queryPC(user.getProvince());
		String city = userService.queryPC(user.getCity());
		user.setP(province);
		user.setC(city);

		model.addAttribute("eyooList", page.getResults());
		page.setResults(null);
		model.addAttribute("page", page);
		model.addAttribute("user", user);

		session.setAttribute("user", user);
		return "/eyoo/home";

	}

	// 遍历所有微博 好友圈
	@SuppressWarnings("static-access")
	@RequestMapping(value = "queryAlleyooFriends")
	public String queryAlleyooFriends(HttpSession session, Model model, @RequestParam("pageNo") int pageNo)
			throws Exception {

		// 当前用户信息
		UserCustom user = (UserCustom) session.getAttribute("user");

		// 页码
		if (pageNo == 0) {
			pageNo = 1;
		}
		//
		// 遍历出微博列表
		Page<eyooCustom> page = eyooService.queryAlleyooFriends(user.getUserId(), pageNo);
		for (eyooCustom eyooCustom : page.getResults()) {
			// 将date格式化 精确到s
			eyooCustom.setDate(dateConvert.convert2s(eyooCustom.getPostTime()));

			// 用户是否赞过
			eyooCustom.setLikes(likesService.isLike(user.getUserId(), eyooCustom.geteyooId()));

			// 用户是否收藏
			eyooCustom.setCollect(collectService.isCollect(user.getUserId(), eyooCustom.geteyooId()));

			// 查询微博转发 评论 点赞次数
			eyooCustom.setRepostCount(eyooService.queryRepostCount(eyooCustom.geteyooId()));
			eyooCustom.setCommentCount(eyooService.queryCommentCount(eyooCustom.geteyooId()));
			eyooCustom.setLikeCount(eyooService.queryLikeCount(eyooCustom.geteyooId()));
			// 非原创 即属于转发微博
			if (eyooCustom.getOriginal() == 0) {
				eyooCustom reposteyoo = eyooService.queryeyooByeyooId(eyooCustom.getRepostId()).get(0);
				reposteyoo.setDate(dateConvert.convert2s(reposteyoo.getPostTime()));
				eyooCustom.setRepost(reposteyoo);
			}
		}
		// 与我相关数据库存储值
		MentionCustom mention = mentionService.queryLastMention(user.getUserId());
		user.setMentionCustom(mention);

		// 微博数wi
		int eyooCount = userService.queryeyooCount(user.getUserId());
		// 关注
		int followCount = userService.queryFollowCount(user.getUserId());
		// 粉丝
		int fansCount = userService.queryFansCount(user.getUserId());
		user.seteyooCount(eyooCount);
		user.setFollowCount(followCount);
		user.setFansCount(fansCount);

		// 所在地
		String province = userService.queryPC(user.getProvince());
		String city = userService.queryPC(user.getCity());
		user.setP(province);
		user.setC(city);

		model.addAttribute("eyooList", page.getResults());
		page.setResults(null);
		model.addAttribute("page", page);
		model.addAttribute("user", user);

		session.setAttribute("user", user);
		return "/eyoo/home_friends";

	}

	// 遍历所有微博 首页
	@SuppressWarnings("static-access")
	@RequestMapping(value = "queryAlleyooFollow")
	public String queryAlleyooFollow(HttpSession session, Model model, @RequestParam("pageNo") int pageNo)
			throws Exception {

		// 当前用户信息
		UserCustom user = (UserCustom) session.getAttribute("user");

		// 页码
		if (pageNo == 0) {
			pageNo = 1;
		}
		//
		// 遍历出微博列表 首页
		Page<eyooCustom> page = eyooService.queryAlleyooFollow(user.getUserId(), pageNo);
		for (eyooCustom eyooCustom : page.getResults()) {
			// 将date格式化 精确到s
			eyooCustom.setDate(dateConvert.convert2s(eyooCustom.getPostTime()));

			// 用户是否赞过
			eyooCustom.setLikes(likesService.isLike(user.getUserId(), eyooCustom.geteyooId()));

			// 用户是否收藏
			eyooCustom.setCollect(collectService.isCollect(user.getUserId(), eyooCustom.geteyooId()));

			// 查询微博转发 评论 点赞次数
			eyooCustom.setRepostCount(eyooService.queryRepostCount(eyooCustom.geteyooId()));
			eyooCustom.setCommentCount(eyooService.queryCommentCount(eyooCustom.geteyooId()));
			eyooCustom.setLikeCount(eyooService.queryLikeCount(eyooCustom.geteyooId()));
			// 非原创 即属于转发微博
			if (eyooCustom.getOriginal() == 0) {
				eyooCustom reposteyoo = eyooService.queryeyooByeyooId(eyooCustom.getRepostId()).get(0);
				reposteyoo.setDate(dateConvert.convert2s(reposteyoo.getPostTime()));
				eyooCustom.setRepost(reposteyoo);
			}
		}
		// 与我相关数据库存储值
		MentionCustom mention = mentionService.queryLastMention(user.getUserId());
		user.setMentionCustom(mention);

		// 微博数wi
		int eyooCount = userService.queryeyooCount(user.getUserId());
		// 关注
		int followCount = userService.queryFollowCount(user.getUserId());
		// 粉丝
		int fansCount = userService.queryFansCount(user.getUserId());
		user.seteyooCount(eyooCount);
		user.setFollowCount(followCount);
		user.setFansCount(fansCount);

		// 所在地
		String province = userService.queryPC(user.getProvince());
		String city = userService.queryPC(user.getCity());
		user.setP(province);
		user.setC(city);

		model.addAttribute("eyooList", page.getResults());
		page.setResults(null);
		model.addAttribute("page", page);
		model.addAttribute("user", user);

		session.setAttribute("user", user);
		return "/eyoo/home_follow";

	}

	// 根据关键字搜索相关微博
	@RequestMapping(value = "queryeyooByWord")
	public String queryeyooByWord(
			Model model,
			HttpSession session, 
			@RequestParam("keyWord") String keyWord,
			@RequestParam("pageNo") int pageNo) throws Exception {
		// 当前用户信息
		UserCustom user = (UserCustom) session.getAttribute("user");
		// 遍历出微博列表 首页
		Page<eyooCustom> page = eyooService.queryeyooByWord(keyWord, pageNo);
		for (eyooCustom eyooCustom : page.getResults()) {
			// 将date格式化 精确到s
			eyooCustom.setDate(dateConvert.convert2s(eyooCustom.getPostTime()));

			// 用户是否赞过
			eyooCustom.setLikes(likesService.isLike(user.getUserId(), eyooCustom.geteyooId()));

			// 用户是否收藏
			eyooCustom.setCollect(collectService.isCollect(user.getUserId(), eyooCustom.geteyooId()));

			// 查询微博转发 评论 点赞次数
			eyooCustom.setRepostCount(eyooService.queryRepostCount(eyooCustom.geteyooId()));
			// eyooCustom.setCommentCount(eyooService.queryCommentCount(eyooCustom.geteyooId()));
			eyooCustom.setLikeCount(eyooService.queryLikeCount(eyooCustom.geteyooId()));
			// 非原创 即属于转发微博
			if (eyooCustom.getOriginal() == 0) {
				eyooCustom reposteyoo = eyooService.queryeyooByeyooId(eyooCustom.getRepostId()).get(0);
				reposteyoo.setDate(dateConvert.convert2s(reposteyoo.getPostTime()));
				eyooCustom.setRepost(reposteyoo);
			}
		}
		// 与我相关数据库存储值
		MentionCustom mention = mentionService.queryLastMention(user.getUserId());
		user.setMentionCustom(mention);

		// 微博数wi
		int eyooCount = userService.queryeyooCount(user.getUserId());
		// 关注
		int followCount = userService.queryFollowCount(user.getUserId());
		// 粉丝
		int fansCount = userService.queryFansCount(user.getUserId());
		user.seteyooCount(eyooCount);
		user.setFollowCount(followCount);
		user.setFansCount(fansCount);

		// 所在地
		String province = userService.queryPC(user.getProvince());
		String city = userService.queryPC(user.getCity());
		user.setP(province);
		user.setC(city);

		model.addAttribute("eyooList", page.getResults());
		page.setResults(null);
		model.addAttribute("page", page);
		model.addAttribute("user", user);

		session.setAttribute("user", user);

		return "/search/search_eyoo";
	}
}
