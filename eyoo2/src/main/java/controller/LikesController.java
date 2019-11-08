package controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import po.UserCustom;
import service.LikesService;

@Controller
public class LikesController {

	@Autowired
	private LikesService likesService;
	
	// 点赞
	@RequestMapping(value="like" )
	public void like(
			@RequestParam("eyooId") int eyooId,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		UserCustom user  = (UserCustom) session.getAttribute("user");
		likesService.like(eyooId,user.getUserId());
	}
	
	// 取消赞
	@RequestMapping(value="unlike")
	public void unlike(
			@RequestParam("eyooId") int eyooId,
			HttpServletResponse response,
			HttpSession session) throws Exception{
		UserCustom user  = (UserCustom) session.getAttribute("user");
		likesService.unlike(eyooId,user.getUserId());
	}
}
