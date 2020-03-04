package com.zookeeperlock;

import org.I0Itec.zkclient.ZkClient;

public abstract class ZookeeperAbstractLock implements Lock{
    private static final String CONNECT_ADDRESS = "127.0.0.1:2181";
    protected ZkClient zkClient = new ZkClient(CONNECT_ADDRESS);
    protected static final String PATH = "/lock";

    public void getLock() {
        if (tryLock()) {
            System.out.println("成功获取到资源");
        } else {
            waitLock();

            getLock();
        }
    }

    protected abstract boolean tryLock();

    protected abstract void waitLock();

    public void unlock() {
        if (zkClient != null) {
            zkClient.close();
        }
        System.out.println("释放资源");
    }

}
