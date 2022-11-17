package org.springframework.core.type.classreading;

import java.util.concurrent.SynchronousQueue;

public class Atest {
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue synchronousQueue = new SynchronousQueue();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronousQueue.put(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("唤醒");
            }
        }).start();
        Thread.sleep(3000l);
        System.out.println("准备唤醒");
        synchronousQueue.take();
    }
}
