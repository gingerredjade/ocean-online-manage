package com.ocean.util;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

public class FileUtil {

    /**
     * 读入整个文件的内容，返回字节数组。
     *
     * @param inputFileName 输入文件名。
     * @return 字节数组。
     * @throws Exception
     */
    public static byte[] readWholeFile(String inputFileName) throws Exception {
        InputStream fis = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource(inputFileName);
            fis = classPathResource.getInputStream();
            int nlen = fis.available();
            byte[] buffer = new byte[nlen];
            int offset = 0;
            int total = 0;
            for (; ; ) {
                int nread = fis.read(buffer, offset, nlen);
                if (nread <= 0) {
                    break;
                }
                offset += nread;
                nlen -= nread;
                total += nread;
            }

            if (nlen != total) {
                byte[] b = new byte[total];
                System.arraycopy(buffer, 0, b, 0, total);
                buffer = b;
            }

            return buffer;
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }


    /**
     * 复制文件
     *
     * @param src 待复制文件路径
     * @param des 复制生成文件路径
     * @throws IOException 抛IOException
     */
    public static void copyFile(String src, String des) {
        FileInputStream input;
        try {
            input = new FileInputStream(new File(src));
            File descFile = new File(des);
            if (!descFile.getParentFile().exists()) {
                descFile.getParentFile().mkdirs();
            }
            if(!descFile.exists()){
                descFile.createNewFile();
            }
            BufferedInputStream bis = new BufferedInputStream(input);
            FileOutputStream output = new FileOutputStream(descFile);
            BufferedOutputStream bos = new BufferedOutputStream(output);
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            bos.close();
            output.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 写文件
     */
    public static void writeFile(File file, String content, boolean append) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(file, append);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (osw != null) {
                    osw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }


    /**
     * 递归删除文件
     */
    public static void deleteAllFile(File fileDic) {
        File[] files = fileDic.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                files[i].delete();
            } else {
                deleteAllFile(files[i]);
            }
        }
        fileDic.delete();
    }


    /**
     * 获取文件流
     */
    public static byte[]  getFileByte(File file) {
        try {
            if (file.exists()){
                FileInputStream fis = new FileInputStream(file);
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                return bytes;
            }
            return new byte[0];
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }


}
