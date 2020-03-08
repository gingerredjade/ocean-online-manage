package com.ocean.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ocean.model.Constant;
import com.ocean.model.vo.PageVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpUtil {

    public static JSONObject getPostBodyByJson(HttpServletRequest request) {
        String content = getPostBodyContent(request);
        if (StringUtils.isNotBlank(content)) {
            return JSONObject.parseObject(content);
        } else {
            return new JSONObject();
        }
    }

    public static JSONObject getPostBodyByJsonForUpload(HttpServletRequest request) {
        String content = getPostBodyContentForISO(request);
        if (StringUtils.isNotBlank(content)) {
            try {
                return JSONObject.parseObject(new String(content.getBytes(StandardCharsets.ISO_8859_1), "GBK"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return new JSONObject();
            }
        } else {
            return new JSONObject();
        }
    }


    public static JSONArray getPostBodyByArray(HttpServletRequest request) {
        String content = getPostBodyContent(request);
        if (StringUtils.isNotBlank(content)) {
            return JSONObject.parseArray(content);
        } else {
            return new JSONArray();
        }
    }


    public static <T> List<T> getPostBodyByList(HttpServletRequest request, Class<T> clazz) {
        String content = getPostBodyContent(request);
        if (StringUtils.isNotBlank(content)) {
            return JSONObject.parseArray(content, clazz);
        } else {
            return new ArrayList<>();
        }
    }


    public static <T> PageVO<T> parsePage2PageVO(Page<T> list, Pageable pageable) {
        PageVO<T> result = new PageVO<>();
        result.setPageNumber(pageable.getPageNumber() + 1);
        result.setPageSize(pageable.getPageSize());
        result.setTotalRow(list.getTotalElements());
        result.setTotalPage(((int) list.getTotalElements() / pageable.getPageSize()) + 1);
        result.setList(list.getContent());
        return result;
    }


    public static Pageable getPageable(int page, int pageSize) {
        //jpa的page是从0页开始算的
        return new PageRequest(page - 1, pageSize);
    }

    public static Pageable getPageable(int page, int pageSize, Sort sort) {
        //jpa的page是从0页开始算的
        return new PageRequest(page - 1, pageSize, sort);
    }


    private static String getPostBodyContent(HttpServletRequest request) {
        InputStream is;
        try {
            is = request.getInputStream();
            StringBuilder content = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
            br.close();
            is.close();
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String getPostBodyContentForISO(HttpServletRequest request) {
        InputStream is;
        try {
            is = request.getInputStream();
            StringBuilder content = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.ISO_8859_1));
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
            br.close();
            is.close();
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getOauthTokenURL() {
        return "http://127.0.0.1:" + Constant.serverPort + "/oauth/token";
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url;
            if (StringUtils.isNotBlank(param)) {
                urlNameString = urlNameString + "?" + param;
            }
            URL realUrl = new URL(urlNameString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) realUrl.openConnection();
            httpURLConnection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            httpURLConnection.setRequestProperty("Charsert", "utf-8");
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0(compatible;MSIE 6.0;Windows NT 5.1;SV1)");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result.toString();
    }
}
