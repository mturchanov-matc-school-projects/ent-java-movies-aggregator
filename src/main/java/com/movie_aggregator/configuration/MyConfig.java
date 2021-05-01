package com.movie_aggregator.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Search;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * The type My config.
 */
@Configuration
@ComponentScan(basePackages = "com.movie_aggregator")
@EnableWebMvc
@EnableTransactionManagement
//TODO: check what to do with setting datasource username/pass. hiding to properties or else?
public class MyConfig implements WebMvcConfigurer {

    /**
     * Data source data source.
     *
     * @return the data source
     */
    @Bean
    @Profile("prod")
    public DataSource dataSource()  {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            //TODO: change user to a limited-read-only-user
            System.out.println("PRODPROPDPROD");
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/ent_java_indiv_proj?useSSL=false&serverTimezone=UTC");
            dataSource.setUser("root");
            dataSource.setPassword("root");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        return dataSource;
    }

    /**
     * Data source for test data source.
     *
     * @return the data source
     */
    @Profile("dev")
    @Bean(name = "dataSource")
    public DataSource dataSourceForTest()  {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            System.out.println("TESTTESTTEST!!!!");
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/ent_java_indiv_proj_test?useSSL=false&serverTimezone=UTC");
            dataSource.setUser("root");
            dataSource.setPassword("root");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        return dataSource;
    }


    /**
     * Session factory local session factory bean.
     *
     * @return the local session factory bean
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.movie_aggregator.entity");

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    /**
     * Transaction manager hibernate transaction manager.
     *
     * @return the hibernate transaction manager
     */
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    /**
     * shortcut for controller mapping to view  @return the view resolver
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceViewResolver =
                new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/");
        internalResourceViewResolver.setSuffix(".jsp");
        return  internalResourceViewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }


    //@Bean
    //public ViewResolver htmlViewResolver() {
    //    InternalResourceViewResolver internalResourceViewResolver =
    //            new InternalResourceViewResolver();
    //    internalResourceViewResolver.setPrefix("/WEB-INF/html/");
    //    internalResourceViewResolver.setSuffix(".html");
    //    internalResourceViewResolver.setOrder(2);
    //    return  internalResourceViewResolver;
    //}

}
