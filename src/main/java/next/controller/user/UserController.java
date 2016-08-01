package next.controller.user;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import next.controller.UserSessionUtils;
import next.controller.qna.AddAnswerController;
import next.dao.UserDao;
import next.model.User;

@Controller
@RequestMapping("users")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	private UserDao userDao = UserDao.getInstance();
	
	@RequestMapping(value="new" ,method= RequestMethod.GET)
	public String form() {
	
		return "/user/form";
	}

	@RequestMapping(value="create" ,method= RequestMethod.POST)
	public String create(User user) {
		 
		 log.debug("User : {}", user);
		userDao.insert(user);
		return "redirect:/";
	}
	@RequestMapping(value="loginForm",method =  RequestMethod.GET)
	public String loginForm(){
		
		return "/user/login";
	}
	@RequestMapping(value="login",method =  RequestMethod.POST)
	public String login(HttpSession session,User user){
		
		User findUser=userDao.findByUserId(user.getUserId());
		
		  if (findUser == null) {
	            throw new NullPointerException("사용자를 찾을 수 없습니다.");
	        }
	        
	        if (findUser.matchPassword(user.getPassword())) {
	           
	            session.setAttribute("user", user);
	            return ("redirect:/");
	        } else {
	            throw new IllegalStateException("비밀번호가 틀립니다.");
	        }
		
	}

	
	@RequestMapping(value="{id}" ,method= RequestMethod.POST)
	public String show(@PathVariable String id,HttpSession session) {
		if (!UserSessionUtils.isLogined(session)) {
			return "redirect:/users/loginForm";
		}
    	
        return "/user/list";
		
		
		
	}
	
	@RequestMapping(value="{id}/edit", method=RequestMethod.GET)
	public String edit(){
		return "";
	}
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	public String update(){
		return "";
	}
	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	public String destroy(){
		return "";
	}
	
	
}
