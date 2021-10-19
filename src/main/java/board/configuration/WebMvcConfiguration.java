package board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import board.interceptor.LoggerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
	}
	
	/* 스프링부트 2.1.x 버전에는 이미 인코딩 필터가 적용되어 있음
	 * 아래 버전을 쓰거나 다른 인코딩 필터를 추가해야하는 경우에만 사용
	 * 
	 * @Bean public Filter<?> characterEncodingFilter() { CharacterEncodingFilter
	 * characterEncodingFilter = new CharacterEncodingFilter();
	 * 
	 * characterEncodingFilter.setEncoding("UTF-8");
	 * characterEncodingFilter.setForceEncoding(true);
	 * 
	 * return characterEncodingFilter(); }
	 * 
	 * @Bean public HttpMessageConverter<String> responseBodyConverter() { return
	 * new StringHttpMessageConverter(Charset.forName("UTF-8")); }
	 */
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonMultipartResolver = new CommonsMultipartResolver();
		
		commonMultipartResolver.setDefaultEncoding("UTF-8");	//파일의 인코딩을 UTF-8로 설정
		commonMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024);	//업로드되는 파일의 크기 제한, byte단위로 설정
																			//5mb로 설정
		return commonMultipartResolver;
	}
}
