package com.ocean.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ocean.model.vo.ResponseVO;
import com.ocean.service.BootstrapUiTreeNode;
import com.ocean.service.CatalogService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 目录服务REST（本地文件模式）
 */
@RestController
@RequestMapping("/catalog")
public class CatalogController {
    private static final Logger _logger = LoggerFactory.getLogger(CatalogController.class);

    @Value("${data.catalog}")
    private String dataCatalog;

    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @ApiOperation(value = "loadData", notes = "获取指定目录下的所有数据目录。")
    @GetMapping("/loadData")
    public ResponseVO loadData() {
        try {
            if (StringUtils.isNotEmpty(dataCatalog)) {
                File file = new File(dataCatalog);
                if (file.isDirectory()) {
                    List<BootstrapUiTreeNode> result = catalogService.buildBootStrapTreeData(dataCatalog);
                    JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(result));
                    return ResponseVO.success(jsonArray);
                } else {
                    String err = "参数有误，请确保data.catalog为目录！";
                    return ResponseVO.fail(err);
                }
            } else {
                return ResponseVO.fail("参数有误，请检查data.catalog数据目录配置！");
            }
        } catch (Exception e) {
            String errMsg = "获取数据目录异常！";
            _logger.error(errMsg + e.getMessage());
            return ResponseVO.fail(errMsg);
        }
    }

    @ApiOperation(value = "getAllCatalog", notes = "获取指定目录下的所有数据目录。")
    @GetMapping("/getAllCatalog")
    public ResponseVO getDataCatalog() {
        try {
            if (StringUtils.isNotEmpty(dataCatalog)) {
                File file = new File(dataCatalog);
                if (file.isDirectory()) {
                    ArrayList<Object> result = catalogService.getDataCatalog(dataCatalog);
                    return ResponseVO.success(result);
                } else {
                    String err = "参数有误，请确保data.catalog为目录！";
                    return ResponseVO.fail(err);
                }
            } else {
                return ResponseVO.fail("参数有误，请检查data.catalog数据目录配置！");
            }
        } catch (Exception e) {
            String errMsg = "获取数据目录异常！";
            _logger.error(errMsg + e.getMessage());
            return ResponseVO.fail(errMsg);
        }
    }

    @ApiOperation(value = "getDataListByCatalog", notes = "通过指定目录获取其包含的数据列表。")
    @GetMapping("/getDataListByCatalog")
    public ResponseVO getDataListByCatalog(
            @ApiParam(name = "ctl",
                    value = "某一数据目录路径，如D:/testData/。")
            @RequestParam(value = "ctl", defaultValue = "") String ctl) {
        try {
            if (StringUtils.isNotEmpty(ctl)) {
                ArrayList<Object> result = catalogService.getDataListByCatalog(ctl);
                return ResponseVO.success(result);
            } else {
                return ResponseVO.fail("参数有误，数据所属目录不能为空！");
            }
        } catch (Exception e) {
            String errMsg = "获取数据目录异常！";
            _logger.error(errMsg + e.getMessage());
            return ResponseVO.fail(errMsg);
        }
    }



}
