package zookeeper.demo.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CreateZkLinkDemo{

    private final  static  String connect = "192.168.159.134:2181";

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        CuratorFramework curatorFramework= CuratorFrameworkFactory.newClient(connect,4000,4000, new ExponentialBackoffRetry(1000,3));

        curatorFramework.start();

        //fluent风格
        CuratorFramework curatorFramework1=CuratorFrameworkFactory.builder().connectString(connect).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).
                namespace("/dubbo").build();

        curatorFramework1.start();
        System.out.println("success");



    }
}
