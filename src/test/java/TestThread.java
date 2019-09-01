/**
 * Created by longslee on 2019/8/30.
 */
public class TestThread {
//    public static void main(String[] args) {
////        new Thread(SafePoint::re).start();
//        new Thread(()-> this.gee()).start();
//    }

    public static void main(String[] args) {
        //TestThread testThread = new TestThread();
        //testThread.test();
    }


//    public void test(){
//        new Thread(()-> gee()).start();
//    }

    public void gee(){
        System.out.println("gee");
    }

    class SafePoint {
        public void re(){
            System.out.println("re");
        };
    }
}
