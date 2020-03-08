package com.ocean.manager;


import com.ocean.dao.UserDao;
import com.ocean.model.po.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;

/**
* Created by
* on 2018/12/15.
*/
public class UserDetailsConfig implements UserDetailsService {

   @Autowired
   private UserDao userDao;

   /**
    * 根据用户名获取登录用户信息
    * @param username 用户名
    * @return 返回值
    * @throws UsernameNotFoundException 异常信息
    */
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserEntity userInfo = userDao.findOneByAccount(username);
       if(userInfo == null){
            throw new UsernameNotFoundException("用户名："+ username + "不存在！");
       }
       Collection<SimpleGrantedAuthority> collection = new HashSet<SimpleGrantedAuthority>();
       collection.add(new SimpleGrantedAuthority("admin"));
       return new User(username,userInfo.getPassword(),collection);
   }
}
