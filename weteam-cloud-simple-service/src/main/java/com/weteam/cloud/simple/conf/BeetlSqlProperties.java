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

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "beetlsql")
public class BeetlSqlProperties {

    /**
     * 哪些Dao类可以自动注入,DAO接口所在包名
     */
    private String basePackage;

    /**
     * 是否开启debug,用来打印sql语句，参数和执行时间
     */
    private boolean debug = false;

    /**
     * sql语句加载来源;
     * sql文件(.md)的路径
     */
    private String sqlRoot = "/sql";

    /**
     * 通过类后缀来自动注入Dao
     */
    private String daoSuffix = "Dao";

    /**
     * DefaultNameConversion:默认java风格
     * UnderlinedNameConversion:下划线命名转换
     * UpperCaseUnderlinedNameConversion:下划线命名转换,对应的数据库全大写
     * HumpNameConversion:驼峰命名转换
     */
    private String nameConversion = "UnderlinedNameConversion";
}
