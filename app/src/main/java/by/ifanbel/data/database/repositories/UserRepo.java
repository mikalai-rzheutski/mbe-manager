package by.ifanbel.data.database.repositories;

import by.ifanbel.data.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

	public User getByLogin(String login);

	public void deleteByLogin(String login);

	public User getByEmail(String email);

	@Query("SELECT user.login, user.email, user.password, user.role FROM User user ORDER BY user.login ASC")
	public List<Object[]> getListOfUsers();

	@Query("SELECT user.preferredLocale FROM User user WHERE user.login = :login")
	public String getUserPreferredLocale(@Param("login") String login);

	@Modifying
	@Query("UPDATE User user SET user.preferredLocale = :locale WHERE user.login = :login")
	public void setUserPreferredLocale(@Param("login") String login, @Param("locale") String locale);
}
