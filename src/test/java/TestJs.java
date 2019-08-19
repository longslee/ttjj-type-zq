import com.lee.util.JsoupUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class TestJs {
    public static void main(String[] args) throws IOException {
        String url = "http://fund.eastmoney.com/pingzhongdata/003255.js?v=20190810221130";
        byte[] ggDoc = Jsoup.connect(url).ignoreContentType(true).execute().bodyAsBytes();
        String jsCode = new String(ggDoc);

//        String text = "var xxas={sa}; /*" +
//                "xxxss" +
//                "*/";
        System.out.println(JsoupUtil.getJsMap(jsCode));

        //Document doc = Jsoup.parse(jsCode);
    }
}
