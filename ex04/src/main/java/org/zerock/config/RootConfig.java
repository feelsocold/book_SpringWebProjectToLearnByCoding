package org.zerock.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//JAVA 설정을 이용하는 경우
/*
@Configuration
@ComponentScan(basePackages = {"org.zerock.service"})
@ComponentScan(basePackages = "org.zerock.aop")
@EnableAspectJAutoProxy

	@MapperScan(basePackages = {"org.zerock.mapper"})
	public class RootConfig {
	
	}

	@Bean
	public DataSource dataSource() {
		
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throw Exception{
		
	}
	
	@Bean
	public DataSourceTransactioManager txManager(){
		return new DataSourceTransactionManager(dataSource());
	
*/