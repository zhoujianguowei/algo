package mem;

import java.util.ArrayList;
import java.util.List;

public class MemOverflow {

    public static void main(String[] args) {
        final int size = 1024 * 1024 * 1;
        List<byte[]> list = new ArrayList<byte[]>();
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
//                super.run();
                List<byte[]> alloList = new ArrayList<>();
                for (int i = 0; i < 1000; i++) {
                    alloList.add(new byte[3 * size]);
                    System.out.println("写入"+(i+1)+"M数据");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();
        for (int i = 0; i < 1024; i++) {
            System.out.println("JVM 写入数据" + (i + 1) + "M");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(new byte[size]);
        }

    }
}
