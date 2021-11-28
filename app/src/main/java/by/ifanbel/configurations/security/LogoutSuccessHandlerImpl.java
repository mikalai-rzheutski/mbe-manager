package by.ifanbel.configurations.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

	@Autowired
	private LocaleResolver localeResolver;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		Locale def = Locale.getDefault();
		localeResolver.setLocale(request, response, Locale.getDefault());
		response.sendRedirect(request.getContextPath());
	}
}
