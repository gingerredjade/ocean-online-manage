package com.ocean.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * 数据获取服务
 */
@Service
public class ReadFromFileService {

    private static final Logger _logger = LoggerFactory.getLogger(ReadFromFileService.class);

    /**
     * Recommended！
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     */
    public byte[] readFile(String path) throws FileNotFoundException {
        File file = new File(path);
        FileInputStream fis = null; // 标准的字节流

        byte[] dataValue = null;
        try {
            if (file.isFile() && file.exists()) {
                fis = new FileInputStream(file);

                dataValue = new byte[fis.available()];
                fis.read(dataValue);
                return dataValue;
            }
        } catch (Exception e) {
            _logger.error(e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    _logger.error(e.getMessage());
                }
            }
        }
        return dataValue;
    }

    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     */
    public static byte[] readFileByBytes(String path) throws FileNotFoundException {
        File file = new File(path);
        InputStream in = null; // 标准的字节流
        StringBuffer sb = new StringBuffer();

        try {
            if (file.isFile() && file.exists()) {
                _logger.info("以字节为单位读取文件内容，一次读一个字节：");

                // 一次读多个字节
                byte[] buff = new byte[1024];
                in.read(buff);

                int byteread = 0;
                in = new FileInputStream(path);
                ReadFromFileService.showAvailableBytes(in);

                // 读入多个字节到字节数组中，byteread为一次读入的字节数
                while ((byteread = in.read(buff)) != -1) {
                    System.out.write(buff, 0, byteread);
                }
            }
        } catch (Exception e) {
            _logger.error(e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    _logger.error(e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    public static void readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        try {
            _logger.info("以字符为单位读取文件内容，一次读多个字节：");

            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));

            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            _logger.error(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    _logger.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;

            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            _logger.error(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    _logger.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 随机读取文件内容
     */
    public static void readFileByRandomAccess(String fileName) {
        RandomAccessFile randomFile = null;
        try {
            _logger.info("随机读取一段文件内容：");

            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 4 : 0;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[10];
            int byteread = 0;
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            // 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                System.out.write(bytes, 0, byteread);
            }
        } catch (IOException e) {
            _logger.error(e.getMessage());
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e) {
                    _logger.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 显示输入流中还剩的字节数
     */
    private static void showAvailableBytes(InputStream in) {
        try {
            _logger.info("当前字节输入流中的字节数为:{}.", in.available());
        } catch (IOException e) {
            _logger.error(e.getMessage());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "D:\\testData1\\航海通告\\test001\\2020-01-01\\TheNewStack_GuideToCloudNativeDevOps.pdf";
        ReadFromFileService.readFileByBytes(fileName);
        ReadFromFileService.readFileByChars(fileName);
        ReadFromFileService.readFileByLines(fileName);
        ReadFromFileService.readFileByRandomAccess(fileName);
    }

}
