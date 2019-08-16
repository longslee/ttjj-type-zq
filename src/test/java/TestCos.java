import com.lee.util.MathUtil;

import java.util.HashMap;
import java.util.Map;

public class TestCos {
    public static void main(String[] args) {
        Map<String,Double> target = new HashMap<String, Double>();
        target.put("scale",0.10); //5亿资金规模
        target.put("managerCount",3.0); //经理更换数量
        target.put("managerMajor",0.0); //是否为债券  1是  0不是
        target.put("managerDurationExp",67.0); // 经验值 图
        target.put("managerEarnRation",70.0); //收益率，也是从图

        System.out.println(MathUtil.similarWithPerfect(target));
    }

}
