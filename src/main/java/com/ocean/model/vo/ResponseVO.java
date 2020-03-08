package com.ocean.model.vo;

/**
 * 返回结果集
 */
public class ResponseVO {

    //返回结果状态值
    private boolean flag;

    //返回提示信息
    private String message;

    //返回结果体
    private Object data;

    public boolean getFlag() {
        return flag;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public ResponseVO() {

    }

    public static ResponseVO success(Object data) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.flag = true;
        responseVO.message="";
        responseVO.data = data;
        return responseVO;
    }

    public static ResponseVO success(String message) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.flag = true;
        responseVO.message = message;
        responseVO.data = "";
        return responseVO;
    }


    public static ResponseVO fail(String message) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.flag = false;
        responseVO.message = message;
        responseVO.data = "";
        return responseVO;
    }

}
