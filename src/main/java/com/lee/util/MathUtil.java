package com.lee.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    /**
     * 余弦相似度
     * @param vector_1 向量1
     * @param vector_2 向量2
     * @return -1 截然相反 exactly opposite；1 完全相同 exactly the same；0 不相关 indicating orthogonality or decorrelation
     */
    public static double cosSimilarity(Map<String,Double> vector_1,Map<String,Double> vector_2){

        double scalar = 0.0,norm_1 = 0.0,norm_2 = 0.0;
        Set<String> v1Keys = vector_1.keySet();
        Set<String> v2Keys = vector_2.keySet();

        //下一步求交集

        return 2.0;
    }
}
