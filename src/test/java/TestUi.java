import io.webfolder.ui4j.api.browser.Page;

import static io.webfolder.ui4j.api.browser.BrowserFactory.getWebKit;

public class TestUi {
    public static void main(String[] args) {
        System.out.println(11111);
        try (Page page = getWebKit().navigate("https://baidu.com")) {
//            page
//                    .getDocument()
//                    .queryAll(".title a")
//                    .forEach(e -> {
//                        System.out.println(e.getText());
//                    });
        }
    }
}
