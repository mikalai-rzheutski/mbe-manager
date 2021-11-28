package by.ifanbel.configurations.security;

import by.ifanbel.data.database.entities.User;
import by.ifanbel.data.database.repositories.UserRepo;
import by.ifanbel.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

	@Autowired
	@Qualifier("oauth2ClientContext")
	OAuth2ClientContext oAuth2ClientContext;

	@Autowired
	UserRepo userRepo;

	@Autowired
	ObjectFactory<HttpSession> httpSessionFactory;

	@Autowired
	LoginSuccessHandler loginSuccessHandler;

	@Autowired
	LogoutSuccessHandler logoutSuccessHandler;

	@Bean
	@ConfigurationProperties("security.oauth2.client.google")
	public AuthorizationCodeResourceDetails google()
	{
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("security.oauth2.resource.google")
	public ResourceServerProperties googleResource()
	{
		return new ResourceServerProperties();
	}

	@Bean
	@ConfigurationProperties("security.oauth2.client.github")
	public AuthorizationCodeResourceDetails github()
	{
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("security.oauth2.resource.github")
	public ResourceServerProperties githubResource()
	{
		return new ResourceServerProperties();
	}

	@Bean
	public FilterRegistrationBean oAuth2ClientFilterRegistration(OAuth2ClientContextFilter oAuth2ClientContextFilter)
	{
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(oAuth2ClientContextFilter);
		registration.setOrder(-100);
		return registration;
	}

	private Filter ssoFilter()
	{
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		AuthenticationFailureHandler authenticationFailureHandler = new ForwardAuthenticationFailureHandler("/invalidOauth2User");

		//Google
		OAuth2ClientAuthenticationProcessingFilter googleFilter =
				new OAuth2ClientAuthenticationProcessingFilter("/login/google");
		OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oAuth2ClientContext);
		googleFilter.setRestTemplate(googleTemplate);
		UserInfoTokenServices googleTokenServices = new UserInfoTokenServices(googleResource().getUserInfoUri(),
				google().getClientId());
		googleTokenServices.setRestTemplate(googleTemplate);
		googleTokenServices.setPrincipalExtractor(principalExtractor(userRepo));
		googleTokenServices.setAuthoritiesExtractor(authoritiesExtractor(userRepo));
		googleFilter.setTokenServices(googleTokenServices);
		googleFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		filters.add(googleFilter);

		//Github
		OAuth2ClientAuthenticationProcessingFilter githubFilter =
				new OAuth2ClientAuthenticationProcessingFilter("/login/github");
		OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oAuth2ClientContext);
		githubFilter.setRestTemplate(githubTemplate);
		UserInfoTokenServices githubTokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(),
				github().getClientId());
		githubTokenServices.setRestTemplate(githubTemplate);
		githubTokenServices.setPrincipalExtractor(principalExtractor(userRepo));
		githubTokenServices.setAuthoritiesExtractor(authoritiesExtractor(userRepo));
		githubFilter.setTokenServices(githubTokenServices);
		githubFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		filters.add(githubFilter);
		filter.setFilters(filters);
		return filter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
				.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers("/resources/**", "/login/**", "/")
				.permitAll()
				.antMatchers("/browseHeterostructure/*", "/viewHeterostructure/*", "/noSuchPage")
				.hasAnyRole("USER", "ADMIN", "SUPERADMIN")
				.antMatchers("/editHeterostructure/*", "/settings", "/settings/", "/settings/data/**")
				.hasAnyRole("ADMIN", "SUPERADMIN")
				.antMatchers("/settings/registration/**")
				.hasRole("SUPERADMIN")
				.and()
				.exceptionHandling()
				.accessDeniedPage("/accessDenied");

		http
				.addFilterBefore(ssoFilter(), UsernamePasswordAuthenticationFilter.class);

		http.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/j_spring_security_check")
				.failureUrl("/login?error=true")
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				.permitAll()
				.successHandler(loginSuccessHandler);
		http.logout()
				.permitAll()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true)
				.logoutSuccessHandler(logoutSuccessHandler)
				.and()
				.rememberMe()
				.key("kljijnghDFRDcfc423fddx")
				.alwaysRemember(true)
				.rememberMeParameter("rememberMe")
				.rememberMeCookieName("javasampleapproach-remember-me")
				.tokenValiditySeconds(24 * 60 * 60)
			;
	//	http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(getBCryptPasswordEncoder());
	}

	@Bean("passwordEncoder")
	public PasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private PrincipalExtractor principalExtractor(UserRepo userRepo) {
		return map -> getUser(map, userRepo);
	}

	private AuthoritiesExtractor authoritiesExtractor(UserRepo userRepo) {
		return map -> {
			List<GrantedAuthority> authorities = new ArrayList();
			User user = getUser(map, userRepo);
			authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
			return authorities;
		};
	}

	private User getUser(Map<String, Object> map, UserRepo userRepo) throws BadCredentialsException {
		String email = (String)map.get("email");
		User user;
		try {
			user = userRepo.getByEmail(email);
			Assert.notNull(user, "");
			return user;
		} catch (Exception e) {
			String message = String.format("Access is denied: the user with email %s is not registered in the MBE-Manager", email);
			throw new BadCredentialsException(message);
		}
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
}
