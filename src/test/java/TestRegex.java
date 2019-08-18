import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

    private static final String REGEX = "\\bcat\\b";
    private static final String INPUT = "cat cat cat cattie cat";

    public static void main(String[] args) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT); // 获取 matcher 对象
        if(m.find()){
            System.out.println(m.end());
        }

        String js = "var ddjs=aass{sas}; /* asssaad */";
        System.out.println(js.length());
        //String reg = "^/\\*/$";
        String reg = "/\\*.*\\*/";
        p = Pattern.compile(reg);
        m = p.matcher(js);
        int start=0,end = 0;
        if(m.find()){
            System.out.println(m.start());
            start = m.start();
            end = m.end();
        }
        String newJs = js.substring(start,end);
        System.out.println(newJs);
        System.out.println(js.substring(0,start));

        String goodWord = js.substring(0,start);
        System.out.println(goodWord);
        String rg = ";*$";
        p = Pattern.compile(rg);
        m = p.matcher(goodWord);
        if(m.find()){
            System.out.println(m.start());
        }
    }

}
