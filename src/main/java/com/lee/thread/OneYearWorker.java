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
public class OneYearWorker implements Callable<Map<String,Map<String,Double>>> {
    private final String fundInfo = "http://fund.eastmoney.com/pingzhongdata/${}.js?v=${}"; //个股信息的js
    private final String fundRankInfo = "http://fundf10.eastmoney.com/FundArchivesDatas.aspx?type=jdzf&code=${}&rt=${}"; // 个股同类排行的html
    private final String fundZjgmPage = "http://fundf10.eastmoney.com/gmbd_${}.html";  //资金规模专页
    private final String fundZjgmJs = "http://fundf10.eastmoney.com/FundArchivesDatas.aspx?type=jzcgm&code=${}&rt=${}";  // 资金规模js
    private Map<String,String> idName;
    private double randomNum;

    public OneYearWorker(Map<String,String> idName){
        this.idName = idName;
        randomNum = MathUtil.getRandom();
    }

    public Map<String,Map<String,Double>> call() throws Exception {
        Map<String,Map<String,Double>> map = new HashMap();
        String foundKey = ""; //个股的key
        for (String key : idName.keySet()) {
            foundKey = key;
            break;
        }

        //Map<String,List<Double>> rankMap = getSingleRank(foundKey);
        List<Double> ranks = getSingleRankList(foundKey); //长度为4
        double ttt = is222(ranks);
        Map<String,Double> singleInfo = getSingleInfo(foundKey);
        singleInfo.put("two",ttt); // 222 原则
        double zjgm  = getZjgm(foundKey);
        singleInfo.put("scale",zjgm);
        map.put(foundKey,singleInfo);

        return map;
    }


    private double getZjgm(String key){ // 资金规模  div.id=gmbdtable>table.class=w782 comm gmbd> 第一个 tr > 第 5个 td  不行，此时还为加载
        String finalUrl = StringParser.parseDollar(fundZjgmJs,key,randomNum);
        double zjgm = 0.0;
        try {
            String jsStr = JsoupUtil.getBodyAsString(finalUrl);  //是js
            Map jsMap = JsoupUtil.getJsMap(jsStr);
            String[] values = ((String)jsMap.get("jzcgm_apidata")).split(","); // 取最后一个值，去掉末尾的] 就是目前的规模
            String zjgmStr = (values[values.length-1]);
            zjgmStr = zjgmStr.substring(0,zjgmStr.length()-1);
            if(zjgmStr != null){
                zjgm = Double.valueOf(zjgmStr);
            }
        } catch (IOException e) {
            System.out.println("获取资金规模出错");
            e.printStackTrace();
        }
        return zjgm;
    }

    /**
     * 是否符合222原则
     * @param ranks 1 3 6 12 mon.
     * @return 1.0 是, 0.0 否
     */
    private double is222(List<Double> ranks){
        double result = 0.0;
        double m1 = ranks.get(0);
        double m3 = ranks.get(1);
        double m6 = ranks.get(2);
        double m12 = ranks.get(3);

        if(m3 > 0.5 || m6 > 0.5 || m12 > 0.5){
            result = 1.0;
        }
        return result;
    }

     /**
     * 获取个股必要信息
     * @param key 个股代码
     * @return
     */
    private Map<String,Double> getSingleInfo(String key){
        Map<String,Double> starMap = new HashMap<>();
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
            double experience = 0.0,earnings = 0.0,retreat = 0.0; // 0 经验 1 收益  3 回撤 都是评分越高越好
            if(datas.get(0) != null){
                experience = ((BigDecimal)datas.get(0)).doubleValue();
            }
            if(datas.get(1) != null){
                earnings = ((BigDecimal)datas.get(1)).doubleValue();
            }
            if(datas.get(2) != null){
                retreat = ((BigDecimal)datas.get(2)).doubleValue();
            }

            starMap.put("managerCount",managerCount);
            starMap.put("managerDurationExp",experience);
            starMap.put("managerEarnRation",earnings);
            starMap.put("managerRetreat",retreat);

        } catch (IOException e) {
            System.out.println("获取经理图出错");
            e.printStackTrace();
        }
        return starMap;
    }

    /**
     * 获取个股的同类排行 值是算好的比率, 比如 50|100  = 0.5
     * @param key 个股代码
     * @return 4位 1 3 6 12
     */
    private List<Double> getSingleRankList(String key){
        List<Double> vs = new ArrayList<>();
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
            vs.add(v1);
            vs.add(v3);
            vs.add(v6);
            vs.add(v12);
        }catch(IOException e){
            System.out.println("取同类排名出错");
            e.printStackTrace();
            return vs;
        }
        return vs;
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