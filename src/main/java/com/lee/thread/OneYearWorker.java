package com.lee.thread;

import com.lee.util.JsoupUtil;
import com.lee.util.MathUtil;
import com.lee.util.StringParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 要做什么事呢
 * 就是根据传过来的基金代码 去查询个股的基本信息和个股的排名信息，最后组合成一个基金的必要信息，包含向量的信息但不限于向量
 */
public class OneYearWorker implements Callable<Map> {
    private final String fundInfo = "http://fund.eastmoney.com/pingzhongdata/${}.js?v=${}"; //个股信息的js
    private final String fundRankInfo = "http://fundf10.eastmoney.com/FundArchivesDatas.aspx?type=jdzf&code=${}&rt=${}"; // 个股同类排行的html
    private Map<String,String> idName;
    private double randomNum;

    public OneYearWorker(Map<String,String> idName){
        this.idName = idName;
        randomNum = MathUtil.getRandom();
    }

    public Map call() throws Exception {
        Map map = new HashMap();
        String foundKey = ""; //个股的key
        for (String key : idName.keySet()) {
            foundKey = key;
            break;
        }

        return map;
    }

     /**
     * 获取个股必要信息
     * @param key 个股代码
     * @return
     */
    private Map<String,Double> getSingleInfo(String key){
        return null;
    }

    /**
     * 获取个股的同类排行 值是算好的比率, 比如 50|100  = 0.5
     * @param key 个股代码
     * @return
     */
    private Map<String,Double> getSingleRank(String key){
        Map<String,Double> rankMap = new HashMap<>();
        String finalUrl = StringParser.parseDollar(fundRankInfo,key,randomNum);
        try {
            String htmlStr = JsoupUtil.getBodyAsString(finalUrl);
            Document doc = Jsoup.parseBodyFragment(htmlStr);
            Element body = doc.body();
            Elements elements = body.select("li.tlpm");
            String m_1 = elements.get(2).text();
        } catch (IOException e) {
            System.out.println("取同类排名出错");
            e.printStackTrace();
            rankMap.put(key,0.0);
            return rankMap;
        }
        return rankMap;
    }
}