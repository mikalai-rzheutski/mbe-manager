package by.ifanbel.data.database.initialization;

import by.ifanbel.data.database.entities.User;
import by.ifanbel.data.database.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit {

	private static final String PROP_DEFAULT_SUPERADMIN_USERNAME = "mbe.superadmin.username";

	private static final String PROP_DEFAULT_SUPERADMIN_BCRYPT_PW = "mbe.superadmin.password.bcrypt.encoded";

	@Autowired
	UserService userService;

	@Autowired
	private Environment env;

	@EventListener
	public void createDefaultSuperadmin(ContextRefreshedEvent event) {
		String username = env.getProperty(PROP_DEFAULT_SUPERADMIN_USERNAME);
		String password = env.getProperty(PROP_DEFAULT_SUPERADMIN_BCRYPT_PW);
		userService.createIfNotExists(new User(username, password, "", User.UserRole.ROLE_SUPERADMIN.name(), false));
	}
}
