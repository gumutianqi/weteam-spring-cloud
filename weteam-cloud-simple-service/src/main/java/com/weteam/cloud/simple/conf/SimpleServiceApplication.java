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

import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.beetl.sql.ext.spring4.BeetlSqlScannerConfigurer;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(BeetlSqlProperties.class)
public class SimpleServiceApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(SimpleServiceApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Autowired
    private BeetlSqlProperties properties;

    @Bean(name = "beetlSqlScannerConfigurer")
    public BeetlSqlScannerConfigurer getBeetlSqlScannerConfigurer() {
        BeetlSqlScannerConfigurer conf = new BeetlSqlScannerConfigurer();
        conf.setBasePackage("com.weteam.cloud.simple");
        conf.setDaoSuffix("Dao");
        conf.setSqlManagerFactoryBeanName("sqlManagerFactoryBean");
        return conf;
    }

    @Bean(name = "sqlManagerFactoryBean")
    public SqlManagerFactoryBean sqlManagerFactoryBean(@Qualifier("datasource") DataSource datasource) {
        SqlManagerFactoryBean factory = new SqlManagerFactoryBean();

        BeetlSqlDataSource source = new BeetlSqlDataSource();
        source.setMasterSource(datasource);

        factory.setCs(source);
        factory.setDbStyle(new MySqlStyle());
        factory.setNc(new UnderlinedNameConversion());
        factory.setSqlLoader(new ClasspathLoader("/sql"));
//        factory.setNc(getNameConversion(properties.getNameConversion()));
//        factory.setSqlLoader(getClasspathLoader());
//        if (properties.isDebug()) {
        factory.setInterceptors(new Interceptor[]{new DebugInterceptor()});
//        }
        return factory;
    }

    @Bean(name = "txManager")
    public DataSourceTransactionManager getDataSourceTransactionManager(@Qualifier("datasource") DataSource datasource) {
        DataSourceTransactionManager dsm = new DataSourceTransactionManager();
        dsm.setDataSource(datasource);
        return dsm;
    }

    @Bean(name = "datasource")
    public DataSource getDataSource() {
        System.out.println("-------------------- primaryDataSource init ---------------------");
        try {
            System.out.println("========== dataSourceProperties:" + properties.getUrl() + "=========");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return DataSourceBuilder.create()
                .url("jdbc:mysql://127.0.0.1/weteam_spring_cloud?characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull")
                .username("root")
                .password("root")
                .build();
//
//        return DataSourceBuilder.create()
//                .url(dataSourceProperties.getUrl())
//                .username(dataSourceProperties.getUsername())
//                .password(dataSourceProperties.getPassword())
//                .build();
    }

//    public ClasspathLoader getClasspathLoader() {
//        ClasspathLoader classpathLoader = new ClasspathLoader();
//        if (properties.getSqlRoot() != null) {
//            classpathLoader.setSqlRoot(properties.getSqlRoot());
//        }
//        return classpathLoader;
//    }
//
//
//    /**
//     * 判断 NameConversion 选择规则
//     *
//     * @param nameConversionStr
//     * @return
//     */
//    private NameConversion getNameConversion(String nameConversionStr) {
//        NameConversion nameConversion = null;
//        String packageName = NameConversion.class.getPackage().getName();
//        try {
//            Class nameConversionClass = Class.forName(packageName + "." + nameConversionStr);
//            nameConversion = (NameConversion) nameConversionClass.newInstance();
//        } catch (Exception e) {
//            log.error("beetlsql.nameConversion不存在, 使用UnderlinedNameConversion");
//        }
//        if (nameConversion == null) {
//            nameConversion = new UnderlinedNameConversion();
//        }
//        return nameConversion;
//    }
}

