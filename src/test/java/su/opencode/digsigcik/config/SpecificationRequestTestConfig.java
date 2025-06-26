package su.opencode.digsigcik.config;

import com.google.common.collect.ImmutableMap;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@TestConfiguration
@EnableJpaRepositories(basePackages = "su.opencode.digsigcik.repository")
public class SpecificationRequestTestConfig {


//    @Bean
//    @Primary
    @Deprecated
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Deprecated
    private Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
                setProperty("hibernate.hbm2ddl.auto", "create-drop");
//                setProperty("hibernate.hbm2ddl.auto", "update");
                setProperty("hibernate.show_sql", "true");
            }
        };
    }

//    @Bean
    @Deprecated
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) {
        var emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
//        emfb.setPackagesToScan("su.opencode.digsigcik.repository", "su.opencode.digsigcik.model", "su.opencode.digsigcik.service");
        emfb.setPackagesToScan("su.opencode.digsigcik");

        var ada= new HibernateJpaVendorAdapter();

        emfb.setJpaVendorAdapter(ada);
        emfb.setJpaProperties(hibernateProperties());
        emfb.afterPropertiesSet();
        return emfb;
    }

//    @Bean
//    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
    @Deprecated
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        var entityManagerFactory = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                archiverPersistenceUnitInfo(),
                ImmutableMap.<String, Object>builder()
                        .put(JPA_JDBC_DRIVER, "org.h2.Driver")
                        .put(JPA_JDBC_URL, "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1")
                        .put(DIALECT, H2Dialect.class)
                        .put(USER, "sa")
                        .put(PASS, "")
                        .put(HBM2DDL_AUTO, "create")
                        .put(SHOW_SQL, false)
                        .put(QUERY_STARTUP_CHECKING, false)
                        .put(GENERATE_STATISTICS, false)
                        .put(USE_REFLECTION_OPTIMIZER, false)
                        .put(USE_SECOND_LEVEL_CACHE, false)
                        .put(USE_QUERY_CACHE, false)
                        .put(USE_STRUCTURED_CACHE, false)
                        .put(STATEMENT_BATCH_SIZE, 20)
                        .build());

//        entityManager = entityManagerFactory.createEntityManager();
        return entityManagerFactory;
    }

    @Bean
    private static PersistenceUnitInfo archiverPersistenceUnitInfo() {
        return new PersistenceUnitInfo() {
            @Override
            public String getPersistenceUnitName() {
                return "ApplicationPersistenceUnit";
            }

            @Override
            public String getPersistenceProviderClassName() {
                return "org.hibernate.jpa.HibernatePersistenceProvider";
            }

            @Override
            public PersistenceUnitTransactionType getTransactionType() {
                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
            }

            @Override
            public DataSource getJtaDataSource() {
                return null;
            }

            @Override
            public DataSource getNonJtaDataSource() {
                return null;
            }

            @Override
            public List<String> getMappingFileNames() {
                return Collections.emptyList();
            }

            @Override
            public List<java.net.URL> getJarFileUrls() {
                try {
                    return Collections.list(this.getClass()
                            .getClassLoader()
                            .getResources(""));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public URL getPersistenceUnitRootUrl() {
                return null;
            }

            @Override
            public List<String> getManagedClassNames() {
                return Collections.emptyList();
            }

            @Override
            public boolean excludeUnlistedClasses() {
                return false;
            }

            @Override
            public SharedCacheMode getSharedCacheMode() {
                return null;
            }

            @Override
            public ValidationMode getValidationMode() {
                return null;
            }

            @Override
            public Properties getProperties() {
                return new Properties();
            }

            @Override
            public String getPersistenceXMLSchemaVersion() {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }

            @Override
            public void addTransformer(ClassTransformer transformer) {

            }

            @Override
            public ClassLoader getNewTempClassLoader() {
                return null;
            }
        };
    }

    /*
    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory localContainerEntityManagerFactoryBean(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
//        return localContainerEntityManagerFactoryBean.getNativeEntityManagerFactory();
//        Bootstrap.getEntityManagerFactoryBuilder(new CustomPer)
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaStackOver");
        return entityManagerFactory;
//        return new HibernatePersistenceProvider().createEntityManagerFactory("unit", null);
    }
     */

//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//        return new LocalEntityManagerFactoryBean().getNativeEntityManagerFactory();
//    }
//
//    @Bean
//    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
//        return entityManagerFactory.createEntityManager();
//    }

//    @Bean
//    public DynamicScheduler dynamicScheduler() {
//        return Mockito.mock(DynamicScheduler.class);
//    }
//
//    @Bean
//    public PvvService pvvService() {
//        return Mockito.mock(PvvService.class);
//    }

//    @Bean
//    public ISpecificationRepository<TestModel> repo() {
//        return new ISpecificationRepository<TestModel>() {
//        }
//    }
}
