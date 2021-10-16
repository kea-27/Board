package board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

//HandlerInterceptorAdapter 클래스를 상속받아 구현
@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter{

	//컨트롤러가 실행되기 전 수행
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		log.debug("====================START====================");
		log.debug(" Request URI \t:  " + request.getRequestURI());
		
		return super.preHandle(request,  response, handler);
	}
	
	//컨트롤러가 정상적으로 샐행된 후 수행
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.debug("=====================END=====================\n");
	}
}
