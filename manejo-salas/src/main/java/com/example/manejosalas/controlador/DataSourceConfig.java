package com.example.manejosalas.controlador;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	
	public DataSource getDataSource(){
		DataSource dataSource = null;
		
		DataSourceBuilder builder = DataSourceBuilder.create();
		
		builder.url("jdbc:mysql://labrds.cuenz66svftm.us-east-1.rds.amazonaws.com:3306/proyecto");
		builder.username("admin");
		builder.password("aljumapasa7");
		builder.driverClassName("com.mysql.cj.jdbc.Driver");
		
		dataSource = builder.build();
		
		return dataSource;
		
	}
}
