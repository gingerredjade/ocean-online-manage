package com.ocean.dao;

import com.ocean.model.po.UserRoleEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface UserRoleRelationDao extends JpaRepository<UserRoleEntity,Integer>, CrudRepository<UserRoleEntity,Integer> {

    UserRoleEntity findAllByUserAccount(String userAccount);

    Long countByRoleSign(String roleSign);

    @Modifying
    @Transactional
    void deleteAllByUserAccount(String userAccount);

    List<UserRoleEntity> findAll(Specification<UserRoleEntity> specification);
}
