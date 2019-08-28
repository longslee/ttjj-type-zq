package com.lee.util;

import java.util.Comparator;
import java.util.Map;

/** 倒序 分数高的在前面
 * Created by longslee on 2019/8/28.
 */
public class ScoreComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Double score1 =  (Double)(((Map) o1).values().toArray()[0]) ;
        Double score2 =  (Double)(((Map) o2).values().toArray()[0]) ;
        //double score1 = Double.parseDouble(s1);
        //double score2 = Double.parseDouble(s2);
        if(score1 > score2){
            return -1;
        }else if(score1 == score2){
            return 0;
        }else {
            return 1;
        }
    }
}
