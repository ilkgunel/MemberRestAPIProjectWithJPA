package com.ilkaygunel.application;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.eclipse.persistence.jpa.PersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfiguration {

	@Autowired
	private Environment env;

	@Autowired
	private ResourceLoader resourceLoader;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "com.ilkaygunel.entities" });
		em.setPersistenceProviderClass(PersistenceProvider.class);
		em.setJpaProperties(additionalProperties());

		return em;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("javax.persistence.schema-generation.database.action", "drop-and-create");
		properties.setProperty("eclipselink.weaving", "false");
		try {
			properties.put("javax.persistence.sql-load-script-source",
					resourceLoader.getResource("classpath:import.sql").getURL().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("datasource.url"));
		dataSource.setUsername(env.getProperty("datasource.username"));
		dataSource.setPassword(env.getProperty("datasource.password"));
		return dataSource;
	}
}
