package by.ifanbel.configurations.security;

import by.ifanbel.data.database.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	UserService userService;

	@Autowired
	private LocaleResolver localeResolver;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		setLocale(authentication, request, response);
		super.onAuthenticationSuccess(request, response, authentication);
	}

	private void setLocale(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		if (authentication != null &&authentication.getPrincipal() != null) {
			String login = ((UserDetails)authentication.getPrincipal()).getUsername();
			if (login != null) {
				String localeOption = userService.getUserPreferredLocale(login);
				if (localeOption != null && !localeOption.isEmpty()) {
					Locale userLocale = Locale.forLanguageTag(localeOption);
					localeResolver.setLocale(request, response, userLocale);
				}

			}
		}
	}
}
