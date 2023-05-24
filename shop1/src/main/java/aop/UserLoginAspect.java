package aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import exception.LoginException;
import logic.User;

/*
 * 	1. pountcut : UserController 클래스의 idCheck로 시작하는 메서드이고,
				 마지막 매개변수가 userid, session인 경우
	2. 로그인 여부 검증
		-로그인이 안 된 경우 로그인 후 거래하세요. 메세지 출력. login페이지 호출
	3. admin이 아니면서, 로그인 아이디와 파라미터 userid값이 다른 경우
		-본인만 거래 가능합니다. 메세지 출력. item/list 페이지 호출
 * 
 */

@Component
@Aspect
public class UserLoginAspect {
	@Before("execution(* controller.User*.idCheck*(..)) && args(..,userid, session)")
	public void userIdCheck(String userid, HttpSession session) throws Throwable{
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser == null) {
			throw new LoginException("[idCheck]회원만 가능합니다.","../user/login");
		}
		if(!loginUser.getUserid().equals("admin") && !loginUser.getUserid().equals(userid)) {
			throw new LoginException("본인만 가능합니다.","../item/list");
		}
	}
	@Before("execution(* controller.User*.loginCheck*(..)) && args(.., session)")
	public void loginCheck(HttpSession session) throws Throwable{
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser == null) {
			throw new LoginException("[idCheck]회원만 가능합니다.","../user/login");
		}
	}
}
