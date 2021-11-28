package by.ifanbel.configurations.data;
//import org.hibernate.ejb.HibernatePersistence;

import by.ifanbel.data.cache.Cache;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration for database and cache storage.
 * Database and cache settings are stored in environment variables (see the attached readme-file).
 * Hibernate settings are stored in properties-file.
 */

@Configuration
@EnableTransactionManagement
@ComponentScan("by.ifanbel.data.database")
@PropertySource("classpath:app.properties")
@EnableJpaRepositories("by.ifanbel.data.database.repositories")
public class JpaRepoConfig {

	private static final String PROP_DATABASE_DRIVER = "db.driver";

	private static final String PROP_DATABASE_PASSWORD = "db.password";

	private static final String PROP_DATABASE_URL = "db.url";

	private static final String PROP_DATABASE_USERNAME = "db.username";

	private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";

	private static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

	private static final String PROP_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

	private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

	private static final String PROP_FILE_CACHE_FOLDER = "file.cache.folder";

	@Resource
	private Environment env;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty(PROP_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROP_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROP_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));
		Cache.setResourcefolder(env.getRequiredProperty(PROP_FILE_CACHE_FOLDER));
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROP_ENTITYMANAGER_PACKAGES_TO_SCAN));
		entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put(PROP_HIBERNATE_DIALECT, env.getRequiredProperty(PROP_HIBERNATE_DIALECT));
		properties.put(PROP_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
		properties.put(PROP_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO));
		return properties;
	}

}
