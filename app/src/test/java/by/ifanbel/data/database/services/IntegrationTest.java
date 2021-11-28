package by.ifanbel.data.database.services;

import by.ifanbel.configurations.data.JpaRepoConfig;
import by.ifanbel.configurations.logging.LoggingConfig;
import by.ifanbel.configurations.security.WebSecurityConfig;
import by.ifanbel.configurations.view.ViewConfig;
import by.ifanbel.data.database.services.exceptions.NoSuchHeterostructureException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ViewConfig.class, WebSecurityConfig.class, JpaRepoConfig.class, LoggingConfig.class })
@WebAppConfiguration
public class IntegrationTest {

	@Autowired
	HeterostructureService heterostructureService;

	@Test
	public void testDB() throws NoSuchHeterostructureException {
		//System.out.println(heterostructureService.getHeterostructureBySampleNumber("B001").getComments());
	}
}
