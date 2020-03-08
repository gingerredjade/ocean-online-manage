package com.ocean.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 目录服务
 */
@Service
public class CatalogService {

    private static final Logger _logger = LoggerFactory.getLogger(CatalogService.class);


    public String getDataCatalogStr(String dir) throws Exception {
        ArrayList<Object> dirList = new ArrayList<>();
        scanDirsWithRecursion(dir, dirList);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(dirList));
        return jsonArray.toJSONString();
    }

    public String getDataListByCatalogStr(String ctl) throws Exception {
        ArrayList<Object> fileList = new ArrayList<>();
        scanFilesWithRecursion(ctl, fileList);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(fileList));
        return jsonArray.toJSONString();
    }

    public ArrayList<Object> getDataCatalog(String dir) throws Exception {
        ArrayList<Object> dirList = new ArrayList<>();
        scanDirsWithRecursion(dir, dirList);
        return dirList;
    }

    public ArrayList<Object> getDataListByCatalog(String ctl) throws Exception {
        ArrayList<Object> fileList = new ArrayList<>();
        scanFilesWithRecursion(ctl, fileList);
        return fileList;
    }


    /**
     * 递归扫描指定文件夹下的目录集合
     * @param folderPath 文件夹
     */
    private static void scanDirsWithRecursion(String folderPath, ArrayList<Object> scanDirs)
            throws Exception {
        File directory = new File(folderPath);
        if(directory.isDirectory()){
            File [] filelist = directory.listFiles();
            if (filelist != null && filelist.length != 0) {
                for(File file : filelist){
                    /* 如果当前是文件夹，进入递归扫描文件夹 **/
                    if(file.isDirectory()){
                        /* 递归扫描下面的文件夹 **/
                        scanDirsWithRecursion(file.getAbsolutePath(), scanDirs);
                    } else {
                        String fileParent = file.getParent();
                        if (!scanDirs.contains(fileParent)) {
                            scanDirs.add(file.getParent());
                        }
                    }
                }
            } else {
                scanDirs.add(directory.getAbsolutePath());
            }
        } else {
            throw new Exception('"' + folderPath + '"' + " input path is not a Directory, please input the right path of the Directory.");
        }
    }


    /**
     * 递归扫描指定文件夹下的文件集合
     * @param folderPath 文件夹
     */
    private static void scanFilesWithRecursion(String folderPath, ArrayList<Object> scanFiles) throws Exception {
        ArrayList<String> directoryList = new ArrayList<String>();
        File directory = new File(folderPath);

        if(directory.isDirectory()){
            File[] filelist = directory.listFiles();
            if (filelist != null) {
                for (File curFile : filelist) {
                    /* 如果当前是文件夹，进入递归扫描文件夹 **/
                    if(curFile.isDirectory()){
                        directoryList.add(curFile.getAbsolutePath());
                        /* 递归扫描下面的文件夹 **/
                        scanFilesWithRecursion(curFile.getAbsolutePath(), scanFiles);
                    } else{
                        /* 非文件夹 **/
                        String absolutePath = curFile.getAbsolutePath();
                        scanFiles.add(absolutePath);
                    }
                }
            }
        } else {
            throw new Exception('"' + folderPath + '"' + " input path is not a Directory, please input the right path of the Directory.");
        }
    }

    /**
     * 获取某个目录下的文件
     */
    private static void getFilesByDir(String dir, List<String> fileList) {
        File file = new File(dir);
        File[] fileArr = file.listFiles();

        if (fileArr != null) {
            for (File curFile : fileArr) {
                if (curFile.isDirectory()) {
                    getFilesByDir(curFile.getAbsolutePath(), fileList);
                } else {
                    fileList.add(curFile.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 获取某个目录下的文件
     */
    private static void getFileNamesByDir(String dir, List<String> fileNameList) {
        File file = new File(dir);
        File[] fileArr = file.listFiles();
        if (fileArr != null) {
            for (File curFile : fileArr) {
                if (curFile.isDirectory()) {
                    getFileNamesByDir(curFile.getAbsolutePath(), fileNameList);
                } else {
                    fileNameList.add(curFile.getName());
                }
            }
        }
    }

    /**
     * 判断文件目录是否存在
     *
     * @param filePath 文件目录
     * @return boolean 返回类型
     */
    private static boolean isExist(String filePath) {
        boolean exists = true;
        File file = new File(filePath);
        if (!file.exists()) {
            exists = false;
        }
        return exists;
    }

    /**
     * 获取文件路径，统一格式全部以分隔符结束
     *
     * @param interDirectory 文件路径
     * @return String 返回类型
     */
    private static String unifiedSeparator(String interDirectory) {
        String separator = "/";
        if (interDirectory.endsWith(separator)) {
            return interDirectory;
        } else {
            return interDirectory + separator;
        }
    }


    /**
     * 构建Bootstrap-treeview结构数据
     * @param dir 数据目录
     * @return 返回树形数据信息
     * @throws Exception 异常信息
     */
    public List<BootstrapUiTreeNode> buildBootStrapTreeData(String dir) throws Exception {
        // 存放树形数据
        List<BootstrapUiTreeNode> btNodeList = new ArrayList<>();

        // 初始化当前节点ID为0
        int id = 0;

        // 初始化父节点ID为0
        int parentId = 0;
        scanFilesWithRecursion(dir, id, parentId, btNodeList);
        return btNodeList;
    }


    /**
     * 递归扫描指定文件夹下的文件，构建Bootstrap-treeview数据结构，并返回
     * @param folderPath 文件夹
     */
    private static void scanFilesWithRecursion(String folderPath, int id, int parentId, List<BootstrapUiTreeNode> btNodeList) throws Exception {
        ArrayList<String> directoryList = new ArrayList<String>();
        File file = new File(folderPath);

        // 当前路径是文件路径
        if (file.isFile()) {
            String text = file.getName();
            String path = file.getAbsolutePath();
            BootstrapUiTreeNode btn = new BootstrapUiTreeNode(String.valueOf(id), String.valueOf(parentId), text, path);
            btNodeList.add(btn);
            return;
        }

        // 当前路径是文件夹路径（递归获取文件夹路径下的所有文件）
        if (file.isDirectory()) {
            String text = file.getName();
            String path = file.getAbsolutePath();
            BootstrapUiTreeNode btn = new BootstrapUiTreeNode(String.valueOf(id), String.valueOf(parentId), text, path);
            String[] list = file.list();
            String parent = file.getParent();

            if (list != null) {
                List<BootstrapUiTreeNode> subTreeList = new ArrayList<>();
                for (int i=0; i<list.length; i++) {
                    String curs = list[i];
                    String newFilePath = path + File.separator + curs;
                    BootstrapUiTreeNode curBtn = new BootstrapUiTreeNode(String.valueOf(id), String.valueOf(parentId), curs, newFilePath);

                    scanFilesWithRecursion(newFilePath, i, Integer.parseInt(curBtn.getId()), subTreeList);
                }
                btn.setChildren(subTreeList);
                btNodeList.add(btn);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        String filepath = "D:\\testData2";// 默认路径，扫描此文件夹下面的所有文件
        CatalogService cs = new CatalogService();
        List<BootstrapUiTreeNode> btNodeList = cs.buildBootStrapTreeData(filepath);

        // 过滤JSON中的id属性，否则BootstrapTree不能识别数据
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(btNodeList));
        System.out.println(jsonArray.toJSONString());
    }

}
