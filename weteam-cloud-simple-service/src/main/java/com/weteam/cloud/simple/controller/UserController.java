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
package com.weteam.cloud.simple.controller;

import com.weteam.cloud.simple.domain.UserService;
import com.weteam.cloud.simple.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 创建一个新用户
     *
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseBody
    public Long createUser() {
        User user = new User();
        user.setUserName("larrykoo");
        user.setNickName("Larry Koo");
        user.setAge(27);
        user.setRoleId(1L);

        return userService.save(user);
    }

    /**
     * 展示用户列表
     *
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> showUser() {
        List<User> users = userService.findAll();
        return users;
    }

    /**
     * 查询用户总数
     *
     * @return
     */
    @RequestMapping(value = "/user_count", method = RequestMethod.GET)
    public long userCount() {
        long count = userService.count();
        return count;
    }


}
