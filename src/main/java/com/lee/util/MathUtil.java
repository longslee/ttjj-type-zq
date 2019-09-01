package com.lee.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MathUtil {
    private static final Map<String,Double> PERFECT = new HashMap<String,Double>(); //基准向量
    static {
        PERFECT.put("scale",10.0); //资金规模 这里为了要与100对齐乘以10 原来=10.0
        PERFECT.put("managerCount",100.0); //经理更换数量 这里为了要与100对齐乘以100 原来=1.0
        //PERFECT.put("managerMajor",1.0); //是否为债券  1是  0不是
        //perfect.put("managerDuration",10.1); //从业时间  单位年
        //perfect.put("managerMaxEarning",);  //单位 w
        PERFECT.put("managerDurationExp",100.0); // 经验值 图
        PERFECT.put("managerEarnRation",100.0); //收益率，也是从图
        PERFECT.put("managerRetreat",100.0); //经理的最大回撤评分
    }

    /**
     * 余弦相似度 带权重
     * @param vector_1
     * @param vector_2
     * @param weight
     * @return
     */
    private static double cosSimilarity(Map<String,Double> vector_1,Map<String,Double> vector_2,Map<String,Double> weight){
        double scalar = 0.0,norm_1 = 0.0,norm_2 = 0.0,similarity;
        Set<String> v1Keys = vector_1.keySet();
        Set<String> v2Keys = vector_2.keySet();
        Set<String> both = new HashSet<String>(v1Keys);
        //下一步求交集
        //both.addAll(v1Keys);
        both.retainAll(v2Keys);

        //分子  向量相乘
        for(String str : both){
            scalar += vector_1.get(str) * vector_2.get(str);
        }

        //分母1  向量1的模（未开平方前）
        for(String str : both){
            norm_1 += Math.pow(vector_1.get(str),2);
        }

        for(String str : both){
            norm_2 += Math.pow(vector_2.get(str),2);
        }

        similarity = scalar/Math.sqrt(norm_1 * norm_2);  // 分母为两个向量模的乘积

        return similarity;
    }

    /**
     * 余弦相似度
     * @param vector_1 向量1
     * @param vector_2 向量2
     * @return -1 截然相反 exactly opposite；1 完全相同 exactly the same；0 不相关 indicating orthogonality or decorrelation
     */
    private static double cosSimilarity(Map<String,Double> vector_1,Map<String,Double> vector_2){

        double scalar = 0.0,norm_1 = 0.0,norm_2 = 0.0,similarity;
        Set<String> v1Keys = vector_1.keySet();
        Set<String> v2Keys = vector_2.keySet();
        Set<String> both = new HashSet<String>(v1Keys);
        //下一步求交集
        //both.addAll(v1Keys);
        both.retainAll(v2Keys);

        //分子  向量相乘
        for(String str : both){
            scalar += vector_1.get(str) * vector_2.get(str);
        }

        //分母1  向量1的模（未开平方前）
        for(String str : both){
            norm_1 += Math.pow(vector_1.get(str),2);
        }

        for(String str : both){
            norm_2 += Math.pow(vector_2.get(str),2);
        }

        similarity = scalar/Math.sqrt(norm_1 * norm_2);  // 分母为两个向量模的乘积

        return similarity;
    }

    /**
     * 与基准值做比较
     * @param target 目标向量
     * @return 越接近1，说明越接近基准
     */
    public static double similarWithPerfect(Map<String,Double> target){
        return cosSimilarity(PERFECT,target);
    }

    /**
     * 获取double 随机数
     * @return 随机数 like 0.6830977451653442
     */
    public static double getRandom(){
        return Math.random();
    }
}
