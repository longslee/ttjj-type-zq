package com.lee.bmo;

import com.alibaba.fastjson.JSONObject;
import com.lee.util.CalendarUtil;
import com.lee.util.JsoupUtil;
import com.lee.util.MathUtil;
import com.lee.util.StringParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by longslee on 2019/8/12.
 */
public class OneYear {

    private final String oneYearUrl = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=zq&rs=&gs=0&sc=1nzf&st=desc&sd=${}&ed=${}&qdii=041|&tabSubtype=041,,,,,&pi=1&pn=50&dx=1&v=${}";

    public List<Map> finalList(){
        getOneYearTopList();
        return null;
    }

    private List getOneYearTopList(){

        JSONObject jsonObject = null;
        String today = CalendarUtil.getNowYYYYMMDD();
        double vRandom = MathUtil.getRandom();
        String realUrl = StringParser.parseDollar(oneYearUrl,today,today,vRandom);
        try {
            String jsBody = JsoupUtil.getBodyAsString(realUrl);
            Map<String,Object> jsMap = JsoupUtil.getJsMap(jsBody);
            System.out.println(jsMap.get("rankData"));
            jsonObject = JSONObject.parseObject((String)jsMap.get("rankData"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
