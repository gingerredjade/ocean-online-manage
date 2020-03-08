package com.ocean.controller;

import com.alibaba.fastjson.JSONObject;
import com.ocean.model.po.RoleInfoEntity;
import com.ocean.model.vo.ResponseVO;
import com.ocean.service.RoleService;
import com.ocean.service.UserService;
import com.ocean.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    //获取角色单个
    @GetMapping("/getRoleInfoByRoleSign")
    public ResponseVO findRole(HttpServletRequest request) {
        try {
            JSONObject content = HttpUtil.getPostBodyByJson(request);
            String roleSign = content.getString("roleSign");
            return ResponseVO.success(roleService.getRoleByRoleSign(roleSign));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("服务器发生内部错误！");
        }
    }


    /**
     * 获取所有角色数据
     * @return 返回值
     */
    @GetMapping("/getAllRoleList")
    public ResponseVO getAllRoleList(){
        try {
            return ResponseVO.success(roleService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("服务器发生内部错误！");
        }
    }


    //分页查询角色列表
    @GetMapping("/getRoleList")
    public ResponseVO getRoleList(String page,String pageSize) {
        try {
            if (StringUtils.isNotBlank(page)&& StringUtils.isNotBlank(pageSize)){
                return ResponseVO.success(roleService.getPageAll(Integer.parseInt(page),Integer.parseInt(pageSize)));
            }else {
                return  ResponseVO.fail("参数异常,请联系管理员！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("服务器发生内部错误！");
        }
    }


    //添加角色
    @PostMapping("/add")
    public ResponseVO addRole(HttpServletRequest request) {
        try {
            JSONObject content = HttpUtil.getPostBodyByJson(request);
            String roleName = content.getString("roleName");
            String describe = content.getString("describe");
            if (roleService.checkRoleNameExist(roleName) > 0) {
                return ResponseVO.fail("该角色已存在！");
            }
            if (roleService.addRole(roleName, describe)) {
                return ResponseVO.success("角色创建成功！");
            }
            return ResponseVO.fail("角色创建失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("服务器发生内部错误！");
        }
    }

    //修改角色
    @PutMapping("/update")
    public ResponseVO updateRole(HttpServletRequest request) {
        try {
            JSONObject content = HttpUtil.getPostBodyByJson(request);
            String roleName = content.getString("roleName");
            String describe = content.getString("describe");
            String roleSign = content.getString("roleSign");
            if (roleService.updateRole(roleSign, roleName, describe)) {
                return ResponseVO.success("角色修改成功！");
            }

            return ResponseVO.fail("角色修改失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("检查传入参数！");
        }
    }

    //删除角色
    @DeleteMapping("/delete")
    public ResponseVO deleteRole(HttpServletRequest request) {
        try {
            JSONObject content = HttpUtil.getPostBodyByJson(request);
            String roleSign = content.getString("roleSign");
            RoleInfoEntity role = roleService.getRoleByRoleSign(roleSign);
            if (role != null) {
                if (!roleService.checkRoleUsed(role.getRoleSign())) {
                    roleService.deleteRole(roleSign);
                    return ResponseVO.success("删除成功！");
                }
                return ResponseVO.fail("角色已经被使用不允许删除！");
            }
            return ResponseVO.fail("没有该角色！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("检查传入参数！");
        }
    }


    //修改角色
    @PutMapping("/updateConfidence")
    public ResponseVO updateConfidence(HttpServletRequest request) {
        try {
            JSONObject content = HttpUtil.getPostBodyByJson(request);
            String roleSign = content.getString("roleSign");
            int confidenceLevel = content.getInteger("confidenceLevel");
            if (roleService.updateConfidence(roleSign, confidenceLevel)) {
                return ResponseVO.success("角色密级修改成功！");
            }
            return ResponseVO.fail("角色密级修改失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("检查传入参数！");
        }
    }


}
