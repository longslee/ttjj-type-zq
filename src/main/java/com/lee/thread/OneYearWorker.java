package com.lee.thread;

import java.util.Map;
import java.util.concurrent.Callable;

public class OneYearWorker implements Callable<Map> {

    private Map<String,String> idName;

    public OneYearWorker(Map<String,String> idName){
        this.idName = idName;
    }

    public Map call() throws Exception {
        return null;
    }
}
