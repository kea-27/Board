package board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")	//application.properties를 사용할 수 있도록 설정 파일의 위치를 지정해주기
@EnableTransactionManagement
public class DatabaseConfiguration {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")	//application.properties에서 설정한 데이터베이스 관련 정보를 사용하도록 지정
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	//데이터벵스와 연결하는 데이터 소스 생성
	@Bean
	public DataSource dataSource() throws Exception{
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println(dataSource.toString());
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		//Spring-MyBatis에서는 SqlSessionFactory를 생성하기 위해서 SqlSessionFactoryBean을 사용함
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		//데이터소스 설정
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		//mapper파일 위치 설정 - mapper : 애플리케이션에서 사용할 SQL을 담고있는 XML파일을 의미함
		//일반적으로 하나의 애플리케이션에는 많은 수의 매퍼 파일이 존재하므로 패턴을 기반으로 한번에 등록하는 것이 좋음
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));	
		
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		
		return sqlSessionFactoryBean.getObject();
	}
	
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")	//application.properties의 설정 중 마이바티스에 관련된 설정 가져오기
	public org.apache.ibatis.session.Configuration mybatisConfig(){
		return new org.apache.ibatis.session.Configuration();	//가져온 마이바티스 설정을 자바 클래스로 만들어서 반환
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception{
		return new DataSourceTransactionManager(dataSource());
	}
}
