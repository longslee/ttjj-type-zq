import java.util.HashMap;
import java.util.Map;

/**
 * Created by longslee on 2019/8/28.
 */
public class TestMap {
    public static void main(String[] args) {
        Map<String,Double> map = new HashMap<>();
        map.put("1",1.1);
        map.put("2",3.1);
        map.put("3",0.5);
        System.out.println(map.values().toArray()[0]);
    }
}
