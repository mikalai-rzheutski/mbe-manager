package by.ifanbel.configurations.security;

import by.ifanbel.data.database.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Configuration
public class ApplicationLocaleResolver extends SessionLocaleResolver {
	@Autowired
	UserService userService;

	@Autowired
	MessageSource messageSource;

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() != null) {
			String login = authentication.getName();
			if (login != null && !login.isEmpty()) {
				String localeOption = userService.getUserPreferredLocale(login);
				if (localeOption != null && !localeOption.isEmpty()) {
					return Locale.forLanguageTag(localeOption);
				}
			}
		}
		return super.resolveLocale(request);
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		super.setLocale(request, response, locale);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() != null) {
			String login = authentication.getName();
			if (login != null && !login.isEmpty()) {
				userService.setUserPreferredLocale(login, locale.toLanguageTag());
			}
		}
	}
}
