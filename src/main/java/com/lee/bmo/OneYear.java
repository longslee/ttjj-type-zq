package com.lee.bmo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lee.thread.NameableThreadFactory;
import com.lee.thread.OneYearWorker;
import com.lee.util.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** 一年排行的情况
 * Created by longslee on 2019/8/12.
 */
public class OneYear {

    private final String oneYearUrl = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=zq&rs=&gs=0&sc=1nzf&st=desc&sd=${}&ed=${}&qdii=041|&tabSubtype=041,,,,,&pi=1&pn=50&dx=1&v=${}";
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2,new NameableThreadFactory("OneYearPool",true));

    private List<Future<Map<String,Map<String,Double>>>> futureList = new LinkedList<Future<Map<String,Map<String,Double>>>>();
    private static List<Map<String,String>> top10 = new LinkedList<Map<String,String>>();
    private static Map<String,String> fundKV = new HashMap<>();

    public List<Map<String,Map<String,Double>>> finalList() throws ExecutionException, InterruptedException {
        List<Map> lastList = new LinkedList<Map>();
        top10 = getOneYearTopList();

        for(Map<String,String> map : top10){
            for (String key : map.keySet()) {
                fundKV.put(key,map.get(key));
            }
        }

        //System.out.println(top10);
        for(Map<String,String> top : top10){
            Future<Map<String,Map<String,Double>>> future = threadPool.submit(new OneYearWorker(top));
            futureList.add(future);
        }

        for(Future<Map<String,Map<String,Double>>> future:futureList){
            Map<String,Map<String,Double>> map = future.get();
            lastList.add(map);
        }
        //System.out.println(lastList); // 去掉不符合222以前
        // 去掉不符合222
        List<Map<String,Map<String,Double>>> lastFinalList = new LinkedList<Map<String,Map<String,Double>>>();
        for(Map<String,Map<String,Double>> single : lastList){
            for (String key : single.keySet()) {
                Map<String,Double> innerMap = single.get(key);
                if(innerMap.get("two") == 0.0){
                    lastFinalList.add(single);
                }
                break;
            }
        }
        //System.out.println(lastFinalList);
        return lastFinalList;
    }

    private List<Map<String,String>> getOneYearTopList(){
        List<Map<String,String>> topList = new LinkedList<Map<String,String>>();  // 就取10个
        JSONObject rankData = null;
        String today = CalendarUtil.getNowYYYYMMDD();
        double vRandom = MathUtil.getRandom();
        String realUrl = StringParser.parseDollar(oneYearUrl,today,today,vRandom);
        try {
            String jsBody = JsoupUtil.getBodyAsString(realUrl);
            Map<String,Object> jsMap = JsoupUtil.getJsMap(jsBody);
            //System.out.println(jsMap.get("rankData"));
            rankData = JSONObject.parseObject((String)jsMap.get("rankData"));
            JSONArray datas = rankData.getJSONArray("datas");
            for (int i = 0; i < 10; i++) { //就取10个
                String item = (String)datas.get(i);
                String[] strings = item.split(",");
                Map<String,String> map = new HashMap<String,String>();
                map.put(strings[0],strings[1]); // [006145->前海开源鼎欣债券A]
                topList.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return topList;
    }

    /**
     * 运用余弦相似后排序，得分最高的排前面
     * @param lastFinalList
     * @return
     */
    public List<Map<String,Double>> cosSimDesc(List<Map<String,Map<String,Double>>> lastFinalList){
        List<Map<String,Double>> sortedList = new ArrayList<>();
        for(Map<String,Map<String,Double>> single : lastFinalList){
            Map<String,Double> scoreMap = new HashMap<>();
            for (String key : single.keySet()) {
                Map<String,Double> singleVector = single.get(key);
                double score = MathUtil.similarWithPerfect(singleVector);
                scoreMap.put(key,score);
                sortedList.add(scoreMap);
            }
        }
        //排序
        Collections.sort(sortedList, new ScoreComparator());
        System.out.println(sortedList);
        //System.out.println(top10);
        return sortedList;
    }

    /**
     * 展示给前端的json
     * @param sortedList
     * @return
     */
    public Map<String,String> getFinalJson(List<Map<String,Double>> sortedList){
        Map<String,String> finalMap = new LinkedHashMap();
        for(Map<String,Double> single :sortedList){
            for (String key : single.keySet()) {
                String name = fundKV.get(key);
                finalMap.put(key,name);
            }
        }
        //System.out.println(finalMap);
        return finalMap;
    }
}
