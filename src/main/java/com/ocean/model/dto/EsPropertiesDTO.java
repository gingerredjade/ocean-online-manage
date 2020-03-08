package com.ocean.model.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class EsPropertiesDTO {

    private static String esIP;
    private static String esPort;
    private static String es_isOpen;
    private static String clusterName;
    private static String searchType;
    private static String analyzer;
    private static String connectType;
    private static String clientNode;
    private static String indexName;
    private static String indexType;
    private static String indices;
    private static String replicas;
    private static String refresh_interval;
    private static String suggestWordSize;
    private static String maxSearchSize;
    private static String beginIndex;
    private static String pageSize;


//    @Value("${esIP}")
//    public void setEsIP(String esIP) {
//        EsPropertiesDTO.esIP = esIP;
//    }
//
//    @Value("${esPort}")
//    public void setEsPort(String esPort) {
//        EsPropertiesDTO.esPort = esPort;
//    }
//
//    @Value("${es_isOpen}")
//    public void setEs_isOpen(String es_isOpen) {
//        EsPropertiesDTO.es_isOpen = es_isOpen;
//    }
//
//    @Value("${searchType}")
//    public static void setSearchType(String searchType) {
//        EsPropertiesDTO.searchType = searchType;
//    }
//
//    @Value("${clusterName}")
//    public void setClusterName(String clusterName) {
//        EsPropertiesDTO.clusterName = clusterName;
//    }
//
//    @Value("${analyzer}")
//    public void setAnalyzer(String analyzer) {
//        EsPropertiesDTO.analyzer = analyzer;
//    }
//
//    @Value("${connectType}")
//    public void setConnectType(String connectType) {
//        EsPropertiesDTO.connectType = connectType;
//    }
//
//    @Value("${clientName}")
//    public void setClientNode(String clientNode) {
//        EsPropertiesDTO.clientNode = clientNode;
//    }
//
//    @Value("${indexName}")
//    public void setIndexName(String indexName) {
//        EsPropertiesDTO.indexName = indexName;
//    }
//
//    @Value("${indexType}")
//    public void setIndexType(String indexType) {
//        EsPropertiesDTO.indexType = indexType;
//    }
//
//    @Value("${indices}")
//    public void setIndices(String indices) {
//        EsPropertiesDTO.indices = indices;
//    }
//
//    @Value("${replicas}")
//    public void setReplicas(String replicas) {
//        EsPropertiesDTO.replicas = replicas;
//    }
//
//    @Value("${refresh_interval}")
//    public void setRefresh_interval(String refresh_interval) {
//        EsPropertiesDTO.refresh_interval = refresh_interval;
//    }
//
//    @Value("${suggestWordSize}")
//    public void setSuggestWordSize(String suggestWordSize) {
//        EsPropertiesDTO.suggestWordSize = suggestWordSize;
//    }
//
//    @Value("${maxSearchSize}")
//    public void setMaxSearchSize(String maxSearchSize) {
//        EsPropertiesDTO.maxSearchSize = maxSearchSize;
//    }
//
//    @Value("${beginIndex}")
//    public void setBeginIndex(String beginIndex) {
//        EsPropertiesDTO.beginIndex = beginIndex;
//    }
//
//    @Value("${pageSize}")
//    public void setPageSize(String pageSize) {
//        EsPropertiesDTO.pageSize = pageSize;
//    }

    public static String getEsIP() {
        return esIP;
    }

    public static String getEsPort() {
        return esPort;
    }

    public static String getEs_isOpen() {
        return es_isOpen;
    }

    public static String getClusterName() {
        return clusterName;
    }

    public static String getSearchType() {
        return searchType;
    }

    public static String getAnalyzer() {
        return analyzer;
    }

    public static String getConnectType() {
        return connectType;
    }

    public static String getClientNode() {
        return clientNode;
    }

    public static String getIndexName() {
        return indexName;
    }

    public static String getIndexType() {
        return indexType;
    }

    public static String getIndices() {
        return indices;
    }

    public static String getReplicas() {
        return replicas;
    }

    public static String getRefresh_interval() {
        return refresh_interval;
    }

    public static String getSuggestWordSize() {
        return suggestWordSize;
    }

    public static String getMaxSearchSize() {
        return maxSearchSize;
    }

    public static String getBeginIndex() {
        return beginIndex;
    }

    public static String getPageSize() {
        return pageSize;
    }
}
