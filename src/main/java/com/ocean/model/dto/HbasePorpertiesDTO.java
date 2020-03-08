package com.ocean.model.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
//@Component
public class HbasePorpertiesDTO {

    private static String ROOTDIR;
    private static String QUORUM;
    private static String CLIENT_PORT;
    private static String keyvalueMaxsize;
    private static String retries;
    private static String zNodeParent;
    private static boolean isDebug;

    public static boolean isIsDebug() {
        return isDebug;
    }

//    @Value("${hbase.isDebug}")
//    public void setIsDebug(boolean isDebug) {
//        HbasePorpertiesDTO.isDebug = isDebug;
//    }
//
//    public static String getROOTDIR() {
//        return ROOTDIR;
//    }
//
//    @Value("${hbase.rootdir}")
//    public void setROOTDIR(String ROOTDIR) {
//        HbasePorpertiesDTO.ROOTDIR = ROOTDIR;
//    }
//
//    public static String getQUORUM() {
//        return QUORUM;
//    }
//
//    @Value("${hbase.zookeeper.quorum}")
//    public void setQUORUM(String QUORUM) {
//        HbasePorpertiesDTO.QUORUM = QUORUM;
//    }
//
//    public static String getClientPort() {
//        return CLIENT_PORT;
//    }
//
//    @Value("${hbase.zookeeper.property.clientPort}")
//    public void setClientPort(String clientPort) {
//        CLIENT_PORT = clientPort;
//    }
//
//    public static String getKeyvalueMaxsize() {
//        return keyvalueMaxsize;
//    }
//
//    @Value("${hbase.client.keyvalue.maxsize}")
//    public void setKeyvalueMaxsize(String keyvalueMaxsize) {
//        HbasePorpertiesDTO.keyvalueMaxsize = keyvalueMaxsize;
//    }
//
//    public static String getRetries() {
//        return retries;
//    }
//
//    @Value("${hbase.client.retries.number}")
//    public void setRetries(String retries) {
//        HbasePorpertiesDTO.retries = retries;
//    }
//
//    public static String getzNodeParent() {
//        return zNodeParent;
//    }
//
//    @Value("${zookeeper.znode.parent}")
//    public void setzNodeParent(String zNodeParent) {
//        HbasePorpertiesDTO.zNodeParent = zNodeParent;
//    }
}
