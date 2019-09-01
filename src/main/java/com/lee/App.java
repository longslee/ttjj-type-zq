package com.lee;

import com.alibaba.fastjson.JSON;
import com.lee.bmo.OneYear;
import com.lee.util.FileUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by longslee on 2019/8/12.
 * 目前先做1年的
 */
public class App {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        OneYear oneYear = new OneYear();
        List<Map<String,Map<String,Double>>> lastFinalList  = oneYear.finalList();
        List<Map<String,Double>> sortedList = oneYear.cosSimDesc(lastFinalList);
        Map<String,String> finalMap = oneYear.getFinalJson(sortedList);
        List<Map<String,String>> finalList = oneYear.getFinalJsonArray(sortedList);
        //序列化
        String jsonStr = JSON.toJSONString(finalList);
        String jsStr = FileUtil.jsFileContent("zjList",jsonStr);
        System.out.println(jsStr);
        //FileUtil.writeFile("D:/","ttjj.js",jsStr);
        FileUtil.writeFile("/home/coffee/java-files/ttjj/ttjj-zq/","ttjj_zq.js",jsStr);  //linux
    }
}
