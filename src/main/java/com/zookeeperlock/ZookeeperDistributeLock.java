package com.zookeeperlock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

public class ZookeeperDistributeLock extends ZookeeperAbstractLock {
    private CountDownLatch countDownLatch = null;

    protected boolean tryLock() {
        try {
            zkClient.createEphemeral(PATH);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void waitLock() {
        IZkDataListener listener = new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {

            }

            public void handleDataDeleted(String s) throws Exception {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                    System.out.println("删除节点...");
                }
            }
        };
        zkClient.subscribeDataChanges(PATH, listener);
        if (zkClient.exists(PATH)) {
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        zkClient.unsubscribeDataChanges(PATH, listener);
    }
}
