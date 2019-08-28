package com.lee.util;

import java.io.*;

/**
 * Created by longslee on 2019/8/12.
 */
public class FileUtil {

    public static String jsFileContent(String var,String content){
        StringBuilder sb = new StringBuilder("var ");
        sb.append(var).append("=").append(content).append(";");
        return sb.toString();
    }

    public static void writeFile(String path,String fileName,String content){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path+fileName), "utf-8"))) {
            writer.write(content);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
