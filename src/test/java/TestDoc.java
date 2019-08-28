import com.lee.util.JsoupUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;

/**
 * Created by longslee on 2019/8/28.
 */
public class TestDoc {
    public static void main(String[] args) throws IOException {
//        Document doc = JsoupUtil.getDocFromUrl("http://fundf10.eastmoney.com/gmbd_003498.html");  // html
//        Element div = doc.getElementById("gmbdtable");
//        div.select("table.w782 comm gmbd");

        String zjgmJs = "http://fundf10.eastmoney.com/FundArchivesDatas.aspx?type=jzcgm&code=003498&rt=0.8950047445126426";
        String jsBody = JsoupUtil.getBodyAsString(zjgmJs);
        Map<String,Object> jsMap = JsoupUtil.getJsMap(jsBody);
        String[] values = ((String)jsMap.get("jzcgm_apidata")).split(","); // 取最后一个值，去掉末尾的] 就是目前的规模
        String zjgmStr = (values[values.length-1]);
        zjgmStr = zjgmStr.substring(0,zjgmStr.length()-1);
    }
}
