package by.ifanbel.configurations.logging;

import by.ifanbel.logging.RequestLoggingAspect;
import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

/*
The @ComponentScan and @EnableAspectJAutoProxy are necessary when Spring AOP is used. But now Spring uses Aspectj.
@ComponentScan("by.ifanbel.logging")
@EnableAspectJAutoProxy
*/

public class LoggingConfig {

	// This forces Spring to use Aspectj
	@Bean
	public RequestLoggingAspect theAspect() {
		RequestLoggingAspect aspect = Aspects.aspectOf(RequestLoggingAspect.class);
		return aspect;
	}
}
