package th.co.bookstore.config.db;

import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import th.co.bookstore.common.constant.ProjectConstant.EntityFactoryRef;
import th.co.bookstore.common.constant.ProjectConstant.JDBC_TEMPLATE;
import th.co.bookstore.common.constant.ProjectConstant.TransactionManagerRef;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "th.co.bookstore.dao", entityManagerFactoryRef = EntityFactoryRef.MYSQL_DB, transactionManagerRef = TransactionManagerRef.MYSQl_DB)
public class DataAccessConfig {
	
	private static final Logger log = LoggerFactory.getLogger(DataAccessConfig.class);
	
	@Value("${app.datasource.url}")
	private String url;
	@Value("${app.datasource.username}")
	private String username;
	@Value("${app.datasource.password}")
	private String password;
	
	@Bean
	@Primary
	public DataSource dataSource() throws IllegalArgumentException {
		log.info("@@ Load JDBC MySql DataAccessConfig...");
		return DataSourceBuilder
		        .create()
		        .username(username)
		        .password(password)
		        .url(url)
		        .build();
	}
	
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) throws IllegalArgumentException {
		return builder
			.dataSource(dataSource())
			.packages(
				"th.co.bookstore.dao.entity"
			)
			.persistenceUnit("system")
			.build();
	}
	
	@Primary
	@Bean(name = TransactionManagerRef.MYSQl_DB)
	public PlatformTransactionManager transactionManager(@Qualifier(EntityFactoryRef.MYSQL_DB) EntityManagerFactory  entityManagerFactory) throws IllegalArgumentException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}
	
	@Primary
	@Bean(name = JDBC_TEMPLATE.MYSQL_DB)
	public JdbcTemplate systemJdbcTemplate() throws SQLException, IllegalArgumentException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
		return jdbcTemplate;
	}

}
