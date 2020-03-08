//package com.ocean.controller;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.ocean.model.po.UserEntity;
//import com.ocean.model.vo.ResponseVO;
//import com.ocean.service.UserService;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 测试日志接口类（北京开发人员，可根据需求自行修改）
// */
//@RestController
//@RequestMapping("/log")
//public class LogController {
//
//    @Autowired
//    private OperationLogService operationLogService;
//
//    @Autowired
//    private UserService userService;
//
//    /**
//     * 仅做测试使用
//     *
//     * @return
//     */
//    @PostMapping("/add")
//    public ResponseVO addLog() {
//        boolean flag = operationLogService.addLogData("resources", "delete", "admin", "张三删除了DEM类型数据");
//        if (flag) {
//            return ResponseVO.success("添加成功");
//        } else {
//            return ResponseVO.fail("添加失败");
//        }
//    }
//
//
//    /**
//     *
//     * @return 返回值
//     */
//    @GetMapping("/getList")
//    public ResponseVO getLogList(String logType, String operationType, String userAccount, String content, String page, String pageSize) {
//        try {
//            if (StringUtils.isBlank(logType) && StringUtils.isBlank(operationType) && StringUtils.isBlank(userAccount) && StringUtils.isBlank(content)) {
//                return ResponseVO.fail("查询条件为空，请输入查询条件");
//            }
//            if (StringUtils.isNotBlank(userAccount)) {
//                UserEntity user = userService.getUserByAccount(userAccount);
//                if(user == null) {
//                    return ResponseVO.fail("没有该账号！");
//                }
//            }
//            JSONObject resJson = operationLogService.queryLogByField(logType, operationType, userAccount, content, page, pageSize);
//            return ResponseVO.success(resJson);
//        } catch (Exception e) {
//            return ResponseVO.fail("查询异常，请联系管理员！");
//        }
//    }
//
//
//
//    /**
//     * 仅做测试使用
//     *
//     * @return 返回值
//     */
//    @GetMapping("/querySuggest")
//    public ResponseVO querySuggestInfo(String text) {
//        try {
//            String[] str = {"DM", "DEM"};
//            JSONObject resJson = operationLogService.querySuggest(text, str);
//            return ResponseVO.success(resJson);
//        } catch (Exception e) {
//            return ResponseVO.fail("查询异常，请联系管理员！");
//        }
//    }
//
//    /**
//     * 仅做测试使用
//     *
//     * @return 返回值
//     */
//    @GetMapping("/deleteLog")
//    public ResponseVO deleteLog(String id, String field, String value) {
//        if (StringUtils.isNotBlank(id)) {
//            boolean flag = operationLogService.deleteById(id);
//            if (flag) {
//                return ResponseVO.success("删除成功！");
//            } else {
//                return ResponseVO.fail("删除失败！");
//            }
//        }
//
//        if (StringUtils.isNotBlank(field) && StringUtils.isNotBlank(value)) {
//            boolean flag = operationLogService.deleteByField(field, value);
//            if (flag) {
//                return ResponseVO.success("删除成功！");
//            } else {
//                return ResponseVO.fail("删除失败！");
//            }
//        }
//        return ResponseVO.fail("参数不全");
//    }
//
//}
