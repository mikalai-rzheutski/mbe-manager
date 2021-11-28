package by.ifanbel.data.database.services;

import by.ifanbel.data.database.entities.User;
import by.ifanbel.data.database.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Transactional
	public User getUser(String login) {
		User user = userRepo.getByLogin(login);
		return user;
	}

	@Transactional
	public boolean createOrUpdate(User user) {
		String login = user.getLogin();
		if (userRepo.getByLogin(login) != null) userRepo.deleteByLogin(login);
		return (userRepo.saveAndFlush(user) != null);
	}

	@Transactional
	public boolean createIfNotExists(User user) {
		String login = user.getLogin();
		if (userRepo.getByLogin(login) != null) return false;
		return (userRepo.saveAndFlush(user) != null);
	}

	@Transactional
	public List<Object[]> getListOfUsers() {
		return userRepo.getListOfUsers();
	}

	@Transactional
	public String getUserPreferredLocale(String login) {
		return userRepo.getUserPreferredLocale(login);
	}

	@Transactional
	public void setUserPreferredLocale(String login, String locale) {
		userRepo.setUserPreferredLocale(login, locale);
	}
}
