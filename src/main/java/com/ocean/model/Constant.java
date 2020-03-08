package com.ocean.model;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {

    @Value("${server.port}")
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public static String serverPort;

    public static JSONObject resourcesCatalog = new JSONObject();

    public  enum  CLIENT_ID{
        dataManager,
    }
    public  enum  AUTH_TYPE{
        password, authorization_code, refresh_token, implicit
    }

    public static String DATA_MANAGER_SECRET = "0dde724c-7c39-40d9-ad26-69c785303566";

    public static final String DATA_MANAGER_BASIC = "Basic ZGF0YU1hbmFnZXI6MGRkZTcyNGMtN2MzOS00MGQ5LWFkMjYtNjljNzg1MzAzNTY2";

    public static class RESOURCES_TYPE {
        public static final String REMOTESENSING = "remoteSensing";
        //survey
        public static final String SPACE = "space";
        public static final String MEDIA = "media";
        public static final String BUSINESS = "business";
        //4d
        public static final String PRODUCT = "product";
        public static final String SERVER = "server";

        public static final String MAGNETIC="Magnetic";
        public static final String POSITION="Position";
    }

    public  static class LOG_TYPE {
        public static final String SYSTEM = "system";
        public static final String RESOURCES = "resources";
    }

    public  static class LOG_OPERATION_TYPE {
        public static final String DELETE = "delete";
        public static final String UPDATE = "update";
        public static final String INSERT = "insert";
        public static final String UPLOAD = "upload";
        public static final String DOWNLOAD = "download";
        public static final String SUBSCRIBE = "subscribe";
    }

    public static class FTP_CONFIG{
        public static final String PASSWORD = "ftpserver.user.%s.userpassword=111111\n";
        public static final String HOMEDIR = "ftpserver.user.%s.homedirectory=%s/%s\n";
        public static final String ENABLEFLAGE = "ftpserver.user.%s.enableflag=true\n";
        public static final String WRITEPERMISSION = "ftpserver.user.%s.writepermission=false\n";
        public static final String MAXLOGINNUMBER = "ftpserver.user.%s.maxloginnumber=0\n";
        public static final String MAXLOGINPERIP = "ftpserver.user.%s.maxloginperip=0\n";
        public static final String IDLETIME = "ftpserver.user.%s.idletime=0\n";
        public static final String UPLOADRATE = "ftpserver.user.%s.uploadrate=0\n";
        public static final String DOWNLOADRATE = "ftpserver.user.%s.downloadrate=0\n";
    }


    public static class LOG_CONTENT {
    }


    public static class MESSAGE_BOX {
        public static final String ORDER_STATUS = "您的%s下载申请%s";
        public static final String SUB_STATUS = "您的%s订阅申请%s";
        public static final String SUBINFO_STATUS = "您的%s订阅匹配到了一条新的数据";
    }


}
