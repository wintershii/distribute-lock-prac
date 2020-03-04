package com;

import com.zookeeperlock.Lock;
import com.zookeeperlock.ZookeeperDistributeLock;

public class Test implements Runnable {

    private static int res = 0;
    private Lock lock = new ZookeeperDistributeLock();

    public static void main(String[] args) throws InterruptedException {
        //Thread.sleep(10);
        for (int i = 0; i < 100; ++i) {
            new Thread(new Test()).start();
        }
        Thread.sleep(10);
        System.out.println(res);
    }

    public void run() {
        //synchronized (this.getClass()) {
            //lock.getLock();
            for (int i = 0; i < 100; ++i) {
                res++;
            }
            System.out.println("线程:" + Thread.currentThread().getName() + "数量:" + res);
            //lock.unlock();
        }
    //}
}
