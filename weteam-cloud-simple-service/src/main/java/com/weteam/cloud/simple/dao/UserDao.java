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
package com.weteam.cloud.simple.dao;

import com.weteam.cloud.simple.model.User;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao extends BaseMapper<User> {

    //使用"user.queryUser"语句, 传入年龄age
    List<User> queryUser(@Param("age") Integer age);

    //使用"user.getCount"语句,无参数
    Long getCount();

    //使用"user.findById", 传入参数id
    User findById(@Param("id") Long id);
}
