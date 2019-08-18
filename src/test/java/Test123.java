import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longslee on 2019/8/12.
 */
public class Test123 {
    public static void main(String[] args) throws Exception{
//        String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=zq&rs=&gs=0&sc=1nzf&st=desc&sd=2018-08-10&ed=2019-08-10&qdii=041|&tabSubtype=041,,,,,&pi=1&pn=50&dx=1&v=0.09748310355112833";
//        byte[] doc = Jsoup.connect(url).execute().bodyAsBytes();
//        System.out.println(new String(doc));

        //Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Gson gson = new Gson();

        String ggUrl = "http://fundf10.eastmoney.com/FundArchivesDatas.aspx?type=jdzf&code=003255&rt=0.7715455665382132";
        byte[] ggDoc = Jsoup.connect(ggUrl).execute().bodyAsBytes();
        String jsCode = new String(ggDoc);
        System.out.println(jsCode);

        jsCode = jsCode.substring(22,jsCode.length()-2);

        //System.out.println("Git Test");

        System.out.println(jsCode);

        Document doc = Jsoup.parseBodyFragment(jsCode);
        Element body = doc.body();

        Elements elements = body.select("li.tlpm");

        for(Element element:elements){
            System.out.println(element);
        }

        Element[] elements_2345 = new Element[4];
        elements_2345[0] = elements.get(2);
        elements_2345[1] = elements.get(3);
        elements_2345[2] = elements.get(4);
        elements_2345[3] = elements.get(5);

        for(Element element : elements_2345){
            System.out.println(element.text());
        }

        // 排名 近1月 3月 6月 1年
        Map<Integer,String> mapRank = new HashMap<Integer, String>();
        mapRank.put(1,elements.get(2).text());
        mapRank.put(3,elements.get(3).text());
        mapRank.put(6,elements.get(4).text());
        mapRank.put(12,elements.get(5).text());

        System.out.println(mapRank);

        //资金规模在这里
        //document.getElementsByClassName('infoOfFund')


        //四个维度 1.资金规模10亿以上会更好，
        // 2.基金经理 更换是否频繁 经理删除领域是否是债券，从业时间长，最大盈利越多越好
        //最大回撤越小越好，年化回报越高越好。
        // 3. 基金费率 其实差不多

//        String func = "var parser = function(){"+new String(ggDoc)+" return JSON.stringfy()}";
//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
//        engine.eval(func);
//        Invocable inv = (Invocable)engine;
//        String result = (String)inv.invokeFunction("parser");
//        String json = gson.toJson(result);


    }
}
