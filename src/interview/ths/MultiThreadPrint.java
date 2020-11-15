package interview.ths;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 4个线程交替打印A、B、C、D
 */
public class MultiThreadPrint {
    public static void main(String[] args) {
        Caller call = new Caller();
        Thread thread1 = new Thread(call);
        Thread thread2 = new Thread(call);
        Thread thread3 = new Thread(call);
        Thread thread4 = new Thread(call);
        Thread thread5=new Thread(call);
        call.addThread(thread1);
        call.addThread(thread2);
        call.addThread(thread3);
        call.addThread(thread4);
        call.addThread(thread5);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}

class Caller implements Runnable {

    private char ch = 'A';
    private HashMap<Integer, Thread> order2ThreadMap;
    private volatile  int next;
    Caller() {
        order2ThreadMap = new HashMap<>();
    }

    void addThread(Thread thread) {
        order2ThreadMap.put(order2ThreadMap.size(), thread);
//        lock.incrementAndGet();
    }

    @Override
    public void run() {
        while (true) {
            Thread currentThread=Thread.currentThread();
            Thread targetThread=order2ThreadMap.get(next);
            if(!currentThread.equals(targetThread)){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            char v= (char) (ch+next);
            System.out.println("thread id:"+currentThread.getId()+",val:"+v);
            next=(next+1)%order2ThreadMap.size();
        }
    }
}
