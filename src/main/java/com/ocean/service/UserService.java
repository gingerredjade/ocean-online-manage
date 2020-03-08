package com.ocean.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ocean.dao.RoleInfoDao;
import com.ocean.dao.UserDao;
import com.ocean.dao.UserRoleRelationDao;
import com.ocean.model.Constant;
import com.ocean.model.dto.UserRoleDTO;
import com.ocean.model.po.RoleInfoEntity;
import com.ocean.model.po.UserEntity;
import com.ocean.model.po.UserRoleEntity;
import com.ocean.model.vo.PageVO;
import com.ocean.model.vo.UserInfoVO;
import com.ocean.util.EncodeUtil;
import com.ocean.util.EntityUtil;
import com.ocean.util.FileUtil;
import com.ocean.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleRelationDao userRoleRelationDao;

    @Autowired
    private RoleInfoDao roleInfoDao;


    /**
     * 根据账号查询用户单表信息
     *
     * @param account 用户账号
     * @return 返回值
     */
    public UserEntity getUserByAccount(String account) {
        return userDao.findOneByAccount(account);
    }

    /**
     * 根据用户账号查询用户和角色信息
     *
     * @param account 用户账号
     * @return 返回值
     */
    public UserRoleDTO getUserRoleDTOByAccount(String account) {
        return userDao.getUserRoleDTOByAccount(account);
    }

    /**
     * 根据用户id查询单表信息
     *
     * @param id 用户编号
     * @return 返回值
     */
    public UserEntity getUserById(int id) {
        //return userDao.findOne(id);
        return userDao.findById(id).orElse(null);
    }

    /**
     * 检查用户账号是否已存在
     *
     * @param account 用户账号
     * @return 返回值
     */
    public boolean checkUserExistByAccount(String account) {
        return getUserByAccount(account) != null;
    }

    /**
     * 根据角色sign查询用户信息
     *
     * @param roleSign 角色标记
     * @param page 页码
     * @param pageSize 页内个数
     * @return 返回值
     */
    public PageVO<UserEntity> getPageByRoleSign(String roleSign, int page, int pageSize) {
        Pageable pageable = HttpUtil.getPageable(page, pageSize);
        Page<UserEntity> list = userDao.getPageByRoleSign(roleSign, pageable);
        return HttpUtil.parsePage2PageVO(list, pageable);
    }

    /**
     * 添加用户
     *
     * @param content 用户信息内容
     * @return  返回值
     */
    @Transactional
    public boolean saveUser(JSONObject content) {
        try {
            String userAccount = content.getString("account");
            //添加用户
            UserEntity userEntity = new UserEntity();
            userEntity.setAccount(userAccount);
            userEntity.setUserAge(content.getInteger("userAge"));
            userEntity.setUserName(content.getString("userName"));
            userEntity.setUserSex(content.getString("userSex"));
            userEntity.setPassword(EncodeUtil.BCryp("111111"));//密码默认111111
            userEntity.setCreateTime(new Date());
            userEntity.setDeleted(0);
            userDao.save(userEntity);

            //添加用户角色关联
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            RoleInfoEntity roleInfoEntity = roleInfoDao.findAllByRoleSign(content.getString("roleSign"));
            userRoleEntity.setRoleSign(content.getString("roleSign"));
            userRoleEntity.setRoleName(roleInfoEntity.getRoleName());
            userRoleEntity.setUserAccount(content.getString("account"));
            userRoleEntity.setUserName(content.getString("userName"));
            userRoleEntity.setCreateTime(new Date());
            userRoleRelationDao.save(userRoleEntity);


            //添加用户的ftp目录和权限
            //insertFtpUserConfig(userAccount);

            UserRoleDTO loginUser = getLoginUser();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新用户信息以及角色信息
     *
     * @param content 用户信息内容
     * @return 返回值
     */
    public boolean updateUser(JSONObject content) {
        try {
            int userId = content.getInteger("id");
            //UserEntity userEntity = userDao.findOne(userId);
            UserEntity userEntity = userDao.findById(userId).get();
            userEntity.setUserAge(content.getInteger("userAge"));
            userEntity.setUserName(content.getString("userName"));
            userEntity.setUserSex(content.getString("userSex"));
            userEntity.setUpdateTime(new Date());
            userDao.save(userEntity);

            UserRoleEntity userRoleEntity = userRoleRelationDao.findAllByUserAccount(userEntity.getAccount());
            if (userRoleEntity != null) {
                userRoleRelationDao.delete(userRoleEntity);
            }
            RoleInfoEntity roleInfoEntity = roleInfoDao.findAllByRoleSign(content.getString("roleSign"));
            userRoleEntity = new UserRoleEntity();
            userRoleEntity.setRoleSign(content.getString("roleSign"));
            userRoleEntity.setRoleName(roleInfoEntity.getRoleName());
            userRoleEntity.setUserName(userEntity.getUserName());
            userRoleEntity.setCreateTime(new Date());
            userRoleEntity.setUserAccount(userEntity.getAccount());
            userRoleRelationDao.save(userRoleEntity);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除用户
     *
     * @param list 待删除用户列表
     * @return 返回值
     */
    public boolean deleteUser(JSONArray list) {
        try {
            StringBuilder names = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                //UserEntity user = userDao.findOne(Integer.parseInt(list.get(i).toString()));
                UserEntity user = userDao.findById(Integer.parseInt(list.get(i).toString())).get();
                user.setDeleted(1);
                userDao.save(user);
                names.append(user.getAccount()).append(",");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 修改密码
     *
     * @param userEntity 用户实体
     * @param password 密码
     * @return 返回值
     */
    public boolean updatePassword(UserEntity userEntity, String password) {
        try {
            userEntity.setPassword(EncodeUtil.BCryp(password));
            userEntity.setUpdateTime(new Date());
            userDao.save(userEntity);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据用户姓名查询
     *
     * @param keyWord 用户姓名
     * @param page 页码
     * @param pageSize 页内个数
     * @return 返回值
     */
    public PageVO<UserInfoVO> getPageByUserName(String keyWord, int page, int pageSize) {
        Pageable pageable = HttpUtil.getPageable(page, pageSize);
        Page<UserInfoVO> list = userDao.getPageByUserName(keyWord, pageable);
        return HttpUtil.parsePage2PageVO(list, pageable);
    }


    //提供给后门使用
    public boolean initAdmin() {
        try {
            UserEntity user = userDao.findOneByAccount("admin");
            if (user == null) {
                RoleInfoEntity role = new RoleInfoEntity();
                role.setRoleSign("admin");
                role.setRoleName("admin");
                role.setDescribe("管理员");
                role.setCreateTime(new Date());
                role.setConfidenceLevel(5);
                roleInfoDao.save(role);

                UserEntity userEntity = new UserEntity();
                userEntity.setAccount("admin");
                userEntity.setUserAge(18);
                userEntity.setUserName("admin");
                userEntity.setUserSex("男");
                userEntity.setPassword(EncodeUtil.BCryp("111111"));
                userEntity.setCreateTime(new Date());
                userEntity.setDeleted(0);
                userDao.save(userEntity);

                UserRoleEntity relation = new UserRoleEntity();
                relation.setRoleSign(role.getRoleSign());
                relation.setRoleName(role.getRoleName());
                relation.setUserName(userEntity.getUserName());
                relation.setUserAccount(userEntity.getAccount());
                relation.setCreateTime(new Date());
                userRoleRelationDao.save(relation);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据当前登录人身份或者登陆人信息
     *
     * @return 返回值
     */
    public UserRoleDTO getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userAccount = authentication.getName();
        List<Object[]> queryList = userDao.getUserRoleDTO(userAccount);
        return EntityUtil.getEntity(queryList, UserRoleDTO.class, new UserRoleDTO());
    }

    /**
     * 获取人员和角色信息dto
     *
     * @return 返回值
     */
    public UserRoleDTO getUserRoleDTO(String userAccount) {
        List<Object[]> queryList = userDao.getUserRoleDTO(userAccount);
        return EntityUtil.getEntity(queryList, UserRoleDTO.class, new UserRoleDTO());
    }

}
