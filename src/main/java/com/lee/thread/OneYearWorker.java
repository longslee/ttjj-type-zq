package com.lee.thread;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lee.util.JsoupUtil;
import com.lee.util.MathUtil;
import com.lee.util.StringParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * 要做什么事呢
 * 就是根据传过来的基金代码 去查询个股的基本信息和个股的排名信息，最后组合成一个基金的必要信息，包含向量的信息但不限于向量
 */
public class OneYearWorker implements Callable<Map<String,List<Double>>> {
    private final String fundInfo = "http://fund.eastmoney.com/pingzhongdata/${}.js?v=${}"; //个股信息的js
    private final String fundRankInfo = "http://fundf10.eastmoney.com/FundArchivesDatas.aspx?type=jdzf&code=${}&rt=${}"; // 个股同类排行的html
    private Map<String,String> idName;
    private double randomNum;

    public OneYearWorker(Map<String,String> idName){
        this.idName = idName;
        randomNum = MathUtil.getRandom();
    }

    public Map<String,List<Double>> call() throws Exception {
        Map map = new HashMap();
        String foundKey = ""; //个股的key
        for (String key : idName.keySet()) {
            foundKey = key;
            break;
        }

        Map<String,List<Double>> rankMap = getSingleRank(foundKey);
        Map<String,Map<String,Double>> singleInfo = getSingleInfo(foundKey);

        return map;
    }

     /**
     * 获取个股必要信息
     * @param key 个股代码
     * @return
     */
    private Map<String,Map<String,Double>> getSingleInfo(String key){
        Map<String,Map<String,Double>> singleInfo = new HashMap<>();
        String finalUrl = StringParser.parseDollar(fundInfo,key,randomNum);
        try {
            String jsStr = JsoupUtil.getBodyAsString(finalUrl);  //是js
            Map jsMap = JsoupUtil.getJsMap(jsStr);
            String currentFundManager = (String)jsMap.get("Data_currentFundManager");
            JSONArray managers = JSONArray.parseArray(currentFundManager);
            double managerCount = managers.size();
            JSONObject nowManager = managers.getJSONObject(0);
            JSONObject power = nowManager.getJSONObject("power");
            JSONArray datas = power.getJSONArray("data");
            double experience = ((BigDecimal)datas.get(0)).doubleValue();
            System.out.println(experience);

            //受益 1  回撤 2  资金规模没有惹
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取个股的同类排行 值是算好的比率, 比如 50|100  = 0.5
     * @param key 个股代码
     * @return
     */
    private Map<String,List<Double>> getSingleRank(String key){
        Map<String,List<Double>> rankMap = new HashMap<>();
        String finalUrl = StringParser.parseDollar(fundRankInfo,key,randomNum);
        try {
            String htmlStr = JsoupUtil.getBodyAsString(finalUrl);
            Document doc = Jsoup.parseBodyFragment(htmlStr);
            Element body = doc.body();
            Elements elements = body.select("li.tlpm");
            String m_1 = elements.get(2).text();
            String m_3 = elements.get(3).text();
            String m_6 = elements.get(4).text();
            String m_12 = elements.get(5).text();
            String m1[] = m_1.split("\\|");
            String m3[] = m_3.split("\\|");
            String m6[] = m_6.split("\\|");
            String m12[] = m_12.split("\\|");
            double v1 = Double.valueOf(m1[0])/Double.valueOf(m1[1]);
            double v3 = Double.valueOf(m3[0])/Double.valueOf(m3[1]);
            double v6 = Double.valueOf(m6[0])/Double.valueOf(m6[1]);
            double v12 = Double.valueOf(m12[0])/Double.valueOf(m12[1]);
            List<Double> vs = new ArrayList<>();
            vs.add(v1);
            vs.add(v3);
            vs.add(v6);
            vs.add(v12);
            rankMap.put(key,vs);
        } catch (IOException e) {
            System.out.println("取同类排名出错");
            e.printStackTrace();
            List<Double> vs = new ArrayList<>();
            rankMap.put(key,vs);
            return rankMap;
        }
        return rankMap;
    }
}