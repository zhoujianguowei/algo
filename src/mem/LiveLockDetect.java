package mem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 示例代码演示线程活锁等待和死循环问题
 */
public class LiveLockDetect {
    /**
     * 线程死循环问题
     */
    public static void createBusyThread() {
        Thread busyThread = new Thread("busyThread") {
            @Override
            public void run() {
                while (true) {
                    ;
                }
            }
        };
        busyThread.start();
    }

    /**
     * 线程等待锁演示
     *
     * @param lock
     */
    public static void createLockThread(final Object lock) {
        Thread lockThread = new Thread("lockThread") {
            @Override
            public void run() {
//                super.run();
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        lockThread.start();
    }

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        createBusyThread();
        Object lock = new Object();
        createLockThread(lock);
    }
}
