package board.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")	//application.properties를 사용할 수 있도록 설정 파일의 위치를 지정해주기
public class DatabaseConfiguration {

	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")	//application.properties에서 설정한 데이터베이스 관련 정보를 사용하도록 지정
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource() throws Exception{
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println(dataSource.toString());
		return dataSource;
	}
}
