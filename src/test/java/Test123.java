import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

        System.out.println("Git Test");

        System.out.println(jsCode);

        Document doc = Jsoup.parseBodyFragment(jsCode);
        Element body = doc.body();

        Elements elements = body.select("li.tlpm");

//        String func = "var parser = function(){"+new String(ggDoc)+" return JSON.stringfy()}";
//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
//        engine.eval(func);
//        Invocable inv = (Invocable)engine;
//        String result = (String)inv.invokeFunction("parser");
//        String json = gson.toJson(result);
    }
}
