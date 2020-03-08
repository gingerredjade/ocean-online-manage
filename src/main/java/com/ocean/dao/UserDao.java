package com.ocean.dao;

import com.ocean.model.dto.UserRoleDTO;
import com.ocean.model.po.UserEntity;
import com.ocean.model.vo.UserInfoVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends JpaRepository<UserEntity,Integer>, CrudRepository<UserEntity,Integer> {

    List<UserEntity> findAll(Specification<UserEntity> specification);

    UserEntity findOneByAccount(String account);

    @Query(value = "select new UserInfoVO(usr.id,usr.account,usr.userName,usr.userAge,usr.userSex,usr.createTime,ur.roleName,ur.roleSign) from UserEntity usr,UserRoleEntity ur where usr.account = ur.userAccount and usr.deleted = 0 and (usr.userName like %?1% or usr.account like %?1%) ")
    Page<UserInfoVO> getPageByUserName(String userName, Pageable pageable);

    @Query(value = "select usr from UserEntity usr,UserRoleEntity urr where usr.account = urr.userAccount and urr.roleSign = ?1 and usr.deleted =0")
    Page<UserEntity> getPageByRoleSign(String roleSign, Pageable pageable);

    @Query(value = "select dmu.id,dmu.account,dmu.user_name,dmu.user_age,dmu.user_sex,ri.role_name,ri.role_sign,ri.confidence_level from data_manager_user dmu left join user_role ur on dmu.account = ur.user_account left join role_info ri on ur.role_sign = ri.role_sign where dmu.account = ?1",nativeQuery = true)
    List<Object[]> getUserRoleDTO(String userAccount);

    @Query(value = "select new UserRoleDTO(dmu.id,dmu.account,dmu.userName,dmu.userAge,dmu.userSex,ri.roleName,ri.roleSign,ri.confidenceLevel) from UserEntity dmu,UserRoleEntity ur,RoleInfoEntity ri  where  dmu.account = ur.userAccount and ur.roleSign = ri.roleSign and dmu.deleted = 0 and dmu.account = ?1")
    UserRoleDTO getUserRoleDTOByAccount(String userAccount);
}
