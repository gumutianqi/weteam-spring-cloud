/**
 * Copyright (c) 2009-2016, LarryKoo 老古 (gumutianqi@gmail.com)
 * Created on 16/7/25
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weteam.cloud.simple.conf;

import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration
@ConditionalOnBean(DataSource.class)
@EnableConfigurationProperties(BeetlSqlProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@Slf4j
public class BeetlSqlAutoConfiguration implements TransactionManagementConfigurer {

    @Autowired
    private BeetlSqlProperties properties;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    DataSource dataSource;

    @Bean
    @ConditionalOnMissingBean
    public BeetlSqlDataSource beetlSqlDataSource() {
        BeetlSqlDataSource beetlSqlDataSource = new BeetlSqlDataSource();
        beetlSqlDataSource.setMasterSource(dataSource);
        return beetlSqlDataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public ClasspathLoader classpathLoader() {
        ClasspathLoader classpathLoader = new ClasspathLoader();
        if (properties.getSqlRoot() != null) {
            classpathLoader.setSqlRoot(properties.getSqlRoot());
        }
        return classpathLoader;
    }

    @Bean()
    @ConditionalOnMissingBean
    public SqlManagerFactoryBean sqlManagerFactoryBean(BeetlSqlDataSource beetlSqlDataSource, SQLLoader sqlLoader) {
        SqlManagerFactoryBean factory = new SqlManagerFactoryBean();
        factory.setCs(beetlSqlDataSource);

        MySqlStyle dbStyle = new MySqlStyle();
        factory.setDbStyle(dbStyle);

        factory.setSqlLoader(sqlLoader);
        NameConversion nameConversion = getNameConversion(properties.getNameConversion());
        factory.setNc(nameConversion);

        if (properties.isDebug()) {
            Interceptor[] interceptors = new Interceptor[]{new DebugInterceptor()};
            factory.setInterceptors(interceptors);
        }
        return factory;
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    private NameConversion getNameConversion(String nameConversionStr) {
        NameConversion nameConversion = null;
        String packageName = NameConversion.class.getPackage().getName();
        try {
            Class nameConversionClass = Class.forName(packageName + "." + nameConversionStr);
            nameConversion = (NameConversion) nameConversionClass.newInstance();
        } catch (Exception e) {
            log.error("beetlsql.nameConversion不存在, 使用UnderlinedNameConversion");
        }
        if (nameConversion == null) {
            nameConversion = new UnderlinedNameConversion();
        }
        return nameConversion;
    }
}

