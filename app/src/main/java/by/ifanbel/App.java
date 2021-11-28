package by.ifanbel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
@EnableSpringConfigured
@EnableAspectJAutoProxy
@EnableLoadTimeWeaving(aspectjWeaving=EnableLoadTimeWeaving.AspectJWeaving.ENABLED)

public class App extends SpringBootServletInitializer {


	@Bean
	public InstrumentationLoadTimeWeaver loadTimeWeaver() throws Throwable   {
		InstrumentationLoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
		return loadTimeWeaver;
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.addListener(new RequestContextListener());
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

	public static void main(String[] args) {
		/*SpringApplication app = new SpringApplication(App.class);
		app.run(args);*/


		SpringApplicationBuilder builder = new SpringApplicationBuilder(App.class);
	//	builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);

	}

}
