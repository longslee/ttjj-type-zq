package com.lee.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longslee on 2019/8/12.
 */
public class JsoupUtil {
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
                    map.put(kvp[0].trim(), kvp[1].trim().substring(0, kvp[1].trim().length() - 1).toString());
                }
            }
        }
        return map;
    }

}
