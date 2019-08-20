package com.lee.util;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by longslee on 2019/8/12.
 */
public class JsoupUtil {

    public static final String REGEX = "\\/\\/[^\\n]*|\\/\\*(\\s|.)*?\\*\\/";  // 正则,寻找JS注释的

    /**
     * 根据url 返回body的字符串形式
     * @param url
     * @return
     * @throws IOException
     */
    public static String getBodyAsString(String url) throws IOException {
        byte[] bodyBytes = Jsoup.connect(url).ignoreContentType(true).execute().bodyAsBytes();
        String bodyStr = new String(bodyBytes);
        return bodyStr;
    }

    /**
     *  提取k-v Map
     * @param jsText String形式的Js
     * @return
     */
    public static Map<String, Object> getJsMap(String jsText) {
        /*用來封裝要保存的参数*/
        Map<String, Object> map = new HashMap<String, Object>();
        /*取得JS变量数组*/
        String[] data = jsText.split("var");
        /*取得单个JS变量*/
        for (String variable : data) {
            /*过滤variable为空的数据*/
            if (variable.contains("=")) {
                String[] kvp = variable.split("=");
                /*取得JS变量存入map*/
                if (!map.containsKey(kvp[0].trim())) {
                    //首先去掉注释
                    String value = getValue(kvp[1].trim());
                    //map.put(kvp[0].trim(), kvp[1].trim().substring(0, kvp[1].trim().length() - 1).toString());
                    map.put(kvp[0].trim(),value);
                }
            }
        }
        return map;
    }


    /**
     * 用于去掉带注释的v
     * @param source var后的值 截取前
     * @return
     */
    private static String getValue(String source) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(source); // 获取 matcher 对象
        int start = 0;
        if (m.find()) {
            start = m.start();
            return source.substring(0,start-1);  //为什么要-1，因为末尾有一个分号
        }else{
            return source.substring(0,source.length()-1);  // 去掉分号啦
        }
    }

}
