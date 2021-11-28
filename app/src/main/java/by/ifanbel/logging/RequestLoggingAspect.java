package by.ifanbel.logging;

import by.ifanbel.data.database.entities.RequestEvent;
import by.ifanbel.data.database.services.RequestEventService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


//The @Component is necessary when Spring AOP is used. But now Spring uses Aspectj.
//@Component


@Aspect
public class RequestLoggingAspect {

	@Autowired
	RequestEventService requestEventService;

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void allRequestMappingMethods() {}

//	@Before("allRequestMappingMethods()")
	public void beforeCallMethod1(JoinPoint jp) {
		RequestAttributes attribs = RequestContextHolder.getRequestAttributes();

		if (RequestContextHolder.getRequestAttributes() != null) {
			HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
			String url = request.getRequestURL()
					.toString();
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (auth != null) {
				Object principal = auth.getPrincipal();
				if (principal instanceof UserDetails) {
					String username = ((UserDetails) principal).getUsername();
					String userRole = ((UserDetails) principal).getAuthorities()
							.toString();
					// the persistence is disabled because of the limited size of the Heroku database
				//	requestEventService.saveRequestEvent(new RequestEvent(username, userRole, url));
				}
			}
		}
	}
}