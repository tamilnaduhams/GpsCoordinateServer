package ca.dait.gps.config;

import ca.dait.gps.data.ConfigService;
import ca.dait.gps.data.CoordinateService;
import ca.dait.gps.data.UserCredentialsService;
import ca.dait.gps.entities.Coordinate;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

/**
 * Created by darinamos on 2016-11-24.
 */
@Configuration
@Order(1)
@PropertySource("classpath:properties/jdbc.properties")
@PropertySource("classpath:properties/site.properties")
public class ApplicationConfig {

    @Autowired
    private SqlSessionFactory factory;

    @Bean
    public SqlSessionFactory getSqlSessionFactory(Environment env, ApplicationContext context) throws Exception {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(env.getProperty("jdbc.driver"));
        ds.setUrl(env.getProperty("jdbc.url"));
        ds.setUsername(env.getProperty("jdbc.username"));
        ds.setPassword(env.getProperty("jdbc.password"));

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds);
        factoryBean.setMapperLocations(
                context.getResources("classpath:/mappers/*-mapper.xml")
        );
        factoryBean.setTypeAliases(new Class<?>[]{Coordinate.class});
        return factoryBean.getObject();
    }

    @Bean
    public CoordinateService getCoordinateService() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(this.factory);
        return new SqlSessionTemplate(this.factory).getMapper(CoordinateService.class);
    }

    @Bean
    public UserCredentialsService getUserCredentialsService() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(this.factory);
        return sessionTemplate.getMapper(UserCredentialsService.class);
    }

    @Bean
    public ConfigService getConfigService() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(this.factory);
        return sessionTemplate.getMapper(ConfigService.class);
    }
}
