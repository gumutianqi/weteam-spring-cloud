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

import org.beetl.sql.ext.spring4.BeetlSqlScannerConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * BeetlConfigurationApplicationContextInitializer
 */

@Configuration
@AutoConfigureAfter(BeetlSqlAutoConfiguration.class)
public class BeetlConfigurationApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        Environment env = applicationContext.getEnvironment();

        String basePackage = env.getProperty("beetlsql.basePackage");
        String daoSuffix = env.getProperty("beetlsql.daoSuffix");

        addScannerConfigurerToBeanFactory(basePackage, daoSuffix);
    }

    private void addScannerConfigurerToBeanFactory(String basePackage, String daoSuffix) {

        if (basePackage == null || basePackage.length() == 0) {
            return;
        }

        Class<?> beanClass = BeetlSqlScannerConfigurer.class;
        String beanName = beanClass.getName();

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext
                .getBeanFactory();
        if (!beanFactory.containsBean(beanName)) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(beanClass);
            beanDefinitionBuilder.addPropertyValue("basePackage", basePackage);
            if (daoSuffix != null && daoSuffix.length() > 0) {
                beanDefinitionBuilder.addPropertyValue("daoSuffix", daoSuffix);
            }
            beanDefinitionBuilder.addPropertyValue("sqlManagerFactoryBeanName", "sqlManagerFactoryBean");
            beanDefinitionBuilder.addPropertyValue("beanName", beanName);
            beanDefinitionBuilder.addPropertyValue("applicationContext", applicationContext);
            beanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        }
    }
}
