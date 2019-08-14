package com.lee.util;

import java.util.HashMap;
import java.util.Map;

public class MathUtil {
    private static final Map<String,Double> perfect = new HashMap<String,Double>();
    static {
        perfect.put("scale",10.0); //资金规模
        perfect.put("managerCount",1.0); //经理更换数量
        perfect.put("managerMajor",1.0); //是否为债券  1是  0不是
        //perfect.put("managerDuration",10.1); //从业时间  单位年
        //perfect.put("managerMaxEarning",);  //单位 w
        perfect.put("managerDurationExp",100.0); // 经验值 图
        perfect.put("managerEarnRation",100.0); //收益率，也是从图
    }
}
