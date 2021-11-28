package by.ifanbel.security;

import by.ifanbel.data.database.entities.User;
import by.ifanbel.data.database.services.UserService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userService.getUser(userName);
		Set<GrantedAuthority> roles = new HashSet();
		roles.add(new SimpleGrantedAuthority(user.getRole()
				.name()));
		UserDetails userDetails =
				new org.springframework.security.core.userdetails.User(user.getLogin(),
						user.getPassword(),
						roles);
		return userDetails;
	}

	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private PrometheusMeterRegistry meterRegistry;

	@PostConstruct
	private void initMetrics() {
		Gauge gauge = Gauge
				.builder("principals.quantity", sessionRegistry, (sessionRegistry) -> sessionRegistry.getAllPrincipals().size())
				.register(meterRegistry);
	}
}
