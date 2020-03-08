package com.ocean.controller;

import com.ocean.service.ReadFromFileService;
import com.ocean.util.FileTypeImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 数据获取服务REST
 */
@RestController
@RequestMapping("/data")
public class DataController {

    private static final Logger _logger = LoggerFactory.getLogger(DataController.class);

    private final ReadFromFileService readFromFileService;

    @Autowired
    public DataController(ReadFromFileService readFromFileService) {
        this.readFromFileService = readFromFileService;
    }

    @ApiOperation(value = "viewData", notes = "浏览指定路径对应的数据。")
    @GetMapping("/view")
    public void viewData(
            @ApiParam(name = "filePath", required = true, value = "数据文件全路径。")
            @RequestParam(value = "filePath") String filePath,

            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        if (filePath.equals("") || filePath.length() == 0) {
            _logger.error("文件全路径不为空！");
            return;
        }

        File file = new File(filePath);
        if (!file.isFile() || !file.exists()) {
            _logger.error("必须是文件全路径！");
            return;
        }

        // 获取文件的扩展名
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());

        // 依文件扩展名判断其输出的Mime-Type
        String mimeType = FileTypeImpl.getFileMimeType(suffix);
        if (mimeType.isEmpty()) {
            //mimeType = FileTypeImpl.getFileMimeType("*");
            String errMsg = "can't find appropriate MIMEType with it's type ["+suffix+"],check the suffix type.";
            int errCode = HttpServletResponse.SC_EXPECTATION_FAILED;

            _logger.error(errMsg);
            return;
        }

        // 获取文件对应的数据
        byte[] dataVal = readFromFileService.readFile(filePath);

        // 封装应答
        sendBlobAsResponse(request, response, mimeType, dataVal);
    }

    public static void sendBlobAsResponse(HttpServletRequest request,
                                          HttpServletResponse response,
                                          String mimeType, byte[] value) throws IOException {

        response.reset();
        response.setContentLength(value.length);
        response.setContentType(mimeType);

        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(value);

        outputStream.flush();
        outputStream.close();
    }

}
