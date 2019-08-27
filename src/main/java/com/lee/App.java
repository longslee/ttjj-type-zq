package com.lee;

import com.lee.bmo.OneYear;

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
        List<Map> lastFinalList  = oneYear.finalList();
        List<Map> sortedList = oneYear.cosSimDesc(lastFinalList);
    }
}
