package com.wora.smartbank.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;

import javax.sql.DataSource;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class UnitTestPersistenceUnitInfo implements PersistenceUnitInfo {
    @Override
    public String getPersistenceUnitName() {
        return "unit-testing";
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
        return getNonJtaDataSource();
    }

    @Override
    public DataSource getNonJtaDataSource() {
        HikariDataSource ds = new HikariDataSource();
//        ds.setJdbcUrl(Env.get("DB_URL"));
//        ds.setUsername(Env.get("DB_USERNAME"));
//        ds.setPassword(Env.get("DB_PASSWORD"));
        ds.setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        ds.setUsername("sa");
        ds.setPassword("");
        return ds;
    }

    @Override
    public List<String> getMappingFileNames() {
        return List.of();
    }

    @Override
    public List<URL> getJarFileUrls() {
        return List.of();
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    @Override
    public List<String> getManagedClassNames() {
        return List.of(
                "com.wora.smartbank.loanRequest.domain.Request"
        );
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
        Properties pr = new Properties();
        pr.setProperty("hibernate.hbm2ddl.auto", "update");
        return pr;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return "";
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public void addTransformer(ClassTransformer classTransformer) {

    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}