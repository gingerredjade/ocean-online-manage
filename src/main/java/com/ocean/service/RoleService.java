package com.ocean.service;

import com.ocean.dao.RoleInfoDao;
import com.ocean.dao.UserRoleRelationDao;
import com.ocean.model.po.RoleInfoEntity;
import com.ocean.model.vo.PageVO;
import com.ocean.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RoleService {
    @Autowired
    private RoleInfoDao roleInfoDao;

    @Autowired
    private UserRoleRelationDao userRoleRelationDao;

    @Autowired
    private UserService userService;

    /**
     * 根据roleSign查询角色信息
     * @param roleSign 角色标记
     * @return 返回值
     */
    public RoleInfoEntity getRoleByRoleSign(String roleSign) {
        return roleInfoDao.findAllByRoleSign(roleSign);
    }

    /***
     * 分页查询角色列表
     * @param page 页码
     * @param pageSize 页内个数
     * @return 返回值
     */
    public PageVO<RoleInfoEntity> getPageAll(int page, int pageSize) {
        Pageable pageable = HttpUtil.getPageable(page,pageSize);
        Page<RoleInfoEntity> list = roleInfoDao.findAll(pageable);
        return HttpUtil.parsePage2PageVO(list,pageable);
    }

    /**
     * 获取所有角色数据
     * @return 返回值
     */
    public List<RoleInfoEntity> getAll(){
        return roleInfoDao.findAll();
    }

    /**
     * 添加角色
     * @param roleName 角色名
     * @param describe 描述
     * @return 返回值
     */
    public boolean addRole(String roleName, String describe) {
       try {
           RoleInfoEntity roleInfoEntity = new RoleInfoEntity();
           roleInfoEntity.setRoleName(roleName);
           roleInfoEntity.setDescribe(describe);
           roleInfoEntity.setRoleSign(UUID.randomUUID().toString());
           roleInfoEntity.setCreateTime(new Date());
           roleInfoEntity.setConfidenceLevel(1);
           roleInfoDao.save(roleInfoEntity);
           return true;
       } catch (Exception e) {
           e.printStackTrace();
           return false;
       }
    }

    /**
     * 更新角色数据
     * @param roleSign 角色标记
     * @param roleName 角色名
     * @param describe 描述
     * @return 返回值
     */
    public boolean updateRole(String roleSign, String roleName, String describe) {
      try {
          RoleInfoEntity roleInfoEntity = roleInfoDao.findAllByRoleSign(roleSign);
          roleInfoEntity.setRoleName(roleName);
          roleInfoEntity.setDescribe(describe);
          roleInfoEntity.setUpdateTime(new Date());
          roleInfoDao.save(roleInfoEntity);
          return true;
      } catch (Exception e) {
          e.printStackTrace();
          return false;
      }
    }

    /**
     * 删除角色
     * @param roleSign 角色标记
     */
    public void deleteRole(String roleSign) {
        RoleInfoEntity role = roleInfoDao.findAllByRoleSign(roleSign);
        roleInfoDao.delete(role);
    }

    /**
     * 检查角色是否已使用
     * @param roleSign 角色标记
     * @return 返回值
     */
    public boolean checkRoleUsed(String roleSign) {
        long roleUserCount = userRoleRelationDao.countByRoleSign(roleSign);
        //每当添加一种类型资源时，这里的判断就需要多一个
       // int roleOriginCount = roleResourcesOriginDao.getCountByRoleSign(roleSign);
        return roleUserCount > 0;
    }

    /**
     * 更新角色数据
     * @param roleSign 角色标记
     * @param confidenceLevel 置信度
     * @return 返回值
     */
    public boolean updateConfidence(String roleSign,  int confidenceLevel) {
        try {
            RoleInfoEntity roleInfoEntity = roleInfoDao.findAllByRoleSign(roleSign);
            roleInfoEntity.setConfidenceLevel(confidenceLevel);
            roleInfoEntity.setUpdateTime(new Date());
            roleInfoDao.save(roleInfoEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int checkRoleNameExist(String roleName) {
        List<RoleInfoEntity> list = roleInfoDao.findAllByRoleName(roleName);
        return list.size();
    }

}
