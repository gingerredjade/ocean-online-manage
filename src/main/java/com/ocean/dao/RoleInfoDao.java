package com.ocean.dao;

import com.ocean.model.po.RoleInfoEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface RoleInfoDao extends JpaRepository<RoleInfoEntity,Integer> {

    RoleInfoEntity findAllByRoleSign(String roleSign);

    @Modifying
    @Transactional
    void deleteAllByRoleSign(String roleSign);

    List<RoleInfoEntity> findAll(Specification<RoleInfoEntity> specification);

    List<RoleInfoEntity> findAllByRoleName(String name);

}
