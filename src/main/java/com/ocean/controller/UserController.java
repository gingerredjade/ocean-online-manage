package com.ocean.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ocean.model.Constant;
import com.ocean.model.dto.UserRoleDTO;
import com.ocean.model.po.UserEntity;
import com.ocean.model.vo.PageVO;
import com.ocean.model.vo.ResponseVO;
import com.ocean.model.vo.UserInfoVO;
import com.ocean.service.UserService;
import com.ocean.util.EncodeUtil;
import com.ocean.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${default.user.password}")
    private String defaultUserPassword;

    @Autowired
    RedisTokenStore redisTokenStore;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/login")
    public ResponseVO login(HttpServletRequest request) {
        JSONObject content = HttpUtil.getPostBodyByJson(request);
        String userAccount = content.getString("account");
        String password = content.getString("password");
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("username", userAccount);
        paramMap.add("password", password);
        paramMap.add("grant_type", Constant.AUTH_TYPE.password.toString());
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            httpHeaders.add("Authorization", Constant.DATA_MANAGER_BASIC);
            HttpEntity<MultiValueMap<String, String>> newRequest = new HttpEntity<>(paramMap, httpHeaders);
            JSONObject response = restTemplate.postForObject(HttpUtil.getOauthTokenURL(), newRequest, JSONObject.class);
            return ResponseVO.success(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("获取授权失败");
        }
    }


    /**
     * 根据角色查询用户
     *
     * @param page 页码
     * @param pageSize 页内个数
     * @param roleSign 角色标记
     * @return 返回值
     */
    @GetMapping("/getUserListByRoleSign")
    public ResponseVO queryAllUserInfo(String page, String pageSize, String roleSign) {
        try {
            if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(pageSize) && StringUtils.isNotBlank(roleSign)) {
                PageVO<UserEntity> userPageList = userService.getPageByRoleSign(roleSign, Integer.parseInt(page), Integer.parseInt(pageSize));
                return ResponseVO.success(userPageList);
            } else {
                return ResponseVO.fail("参数异常！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("服务器发生内部错误！");
        }
    }


    /**
     * 根据姓名查询用户
     *
     * @param page 页码
     * @param pageSize 页内个数
     * @param userName 用户名
     * @return 返回值
     */
    @GetMapping("/getUserListByName")
    public ResponseVO queryUserByName(String page, String pageSize, String userName) {
        try {
            if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(pageSize)) {
                PageVO<UserInfoVO> userPageList = userService.getPageByUserName(userName, Integer.parseInt(page), Integer.parseInt(pageSize));
                return ResponseVO.success(userPageList);
            } else {
                return ResponseVO.fail("参数异常！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("服务器发生内部错误！");
        }
    }

    /**
     * 根据账号查询用户
     *
     * @param account 用户账户
     * @return 返回值
     */
    @GetMapping("/getUserByAccount")
    public ResponseVO queryUserByCount(String account) {
        try {
            if (StringUtils.isNotBlank(account)) {
                UserRoleDTO result = userService.getUserRoleDTOByAccount(account);
                return ResponseVO.success(result);
            } else {
                return ResponseVO.fail("参数有误，联系管理员！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("服务器发生内部错误！");
        }
    }


    /**
     * 删除用户
     *
     * @param request 请求信息
     * @return 返回值
     */
    @DeleteMapping("/delete")
    public ResponseVO deleteUser(HttpServletRequest request) {
        try {
            JSONObject content = HttpUtil.getPostBodyByJson(request);
            JSONArray ids = content.getJSONArray("ids");
            if (ids.size() > 0) {
                if (userService.deleteUser(ids)) {
                    return ResponseVO.success("用户删除成功！");
                }
            }
            return ResponseVO.fail("用户删除失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("服务器发生内部错误！");
        }
    }

    //添加用户
    @PostMapping("/add")
    public ResponseVO addUser(HttpServletRequest request) {
        try {
            JSONObject content = HttpUtil.getPostBodyByJson(request);
            String userAccount = content.getString("account");
            if (!userService.checkUserExistByAccount(userAccount)) {
                if (userService.saveUser(content)) {
                    return ResponseVO.success("用户添加成功！");
                }
                return ResponseVO.fail("用户添加失败！");
            }
            return ResponseVO.fail("该账号已存在！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("服务器发生内部错误！");
        }
    }


    /**
     * 修改用户信息以及角色信息
     * @param request 请求信息
     * @return 返回值
     */
    @PutMapping("/update")
    public ResponseVO updateUser(HttpServletRequest request) {
        try {
            JSONObject content = HttpUtil.getPostBodyByJson(request);
            if (userService.updateUser(content)) {
                return ResponseVO.success("用户信息更新成功！");
            }
            return ResponseVO.fail("该用户不存在！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("服务器发生内部错误！");
        }
    }

    //重置密码
    @PutMapping("/password/reset")
    public ResponseVO resetPassword(HttpServletRequest request) {
        try {
            JSONObject content = HttpUtil.getPostBodyByJson(request);
            String account = content.getString("account");
            UserEntity user = userService.getUserByAccount(account);
            if (user != null) {
                if (userService.updatePassword(user, defaultUserPassword)) {
                    return ResponseVO.success("密码重置成功！");
                }
                return ResponseVO.fail("密码重置失败！");
            }
            return ResponseVO.fail("无此用户！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("服务器发生内部错误！");
        }
    }

    //修改密码
    @PutMapping("/password/update")
    public ResponseVO updateUserPassword(HttpServletRequest request) {
        try {
            JSONObject content = HttpUtil.getPostBodyByJson(request);
            String account = content.getString("account");
            String newPassword = content.getString("password");
            UserEntity user = userService.getUserByAccount(account);
            if (user != null) {
                if (userService.updatePassword(user, newPassword)) {
                    return ResponseVO.success("修改密码成功！");
                }
                return ResponseVO.fail("修改密码失败！");
            }
            return ResponseVO.fail("无此用户！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.fail("服务器发生内部错误！");
        }
    }


    @PostMapping("/login/out")
    public ResponseVO loginOut(HttpServletRequest request, @RequestHeader("Authorization") String authorization) {
        System.out.println(authorization);
        redisTokenStore.removeAccessToken(EncodeUtil.getAccessToken(authorization));
        return ResponseVO.success("退出登录成功！");
    }


    //添加用户，后门使用，避免身份验证
    @GetMapping("/backDoor")
    public ResponseVO addUserBackDoor() {
        if (userService.initAdmin()) {
            return ResponseVO.success("添加管理员成功！");
        }
        return ResponseVO.fail("添加管理员失败或者已存在！");
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseVO listAllUsers() {
        return ResponseVO.success(userService.getLoginUser());
    }

}
