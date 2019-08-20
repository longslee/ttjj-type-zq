package com.lee;

import com.lee.bmo.OneYear;

import java.util.concurrent.ExecutionException;

/**
 * Created by longslee on 2019/8/12.
 * 目前先做1年的
 */
public class App {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        OneYear oneYear = new OneYear();
        oneYear.finalList();
    }
}
