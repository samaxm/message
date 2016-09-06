package online.decentworld.message.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 
 * @author Sammax
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"online.decentworld.message.http"})
public class ServletConfig extends WebMvcConfigurerAdapter{

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	/**
	 * 异常脑残。。方法名字必须这样
	 * @return
	 */
	@Bean
	public MultipartResolver multipartResolver(){
			return new StandardServletMultipartResolver();
	}
}
