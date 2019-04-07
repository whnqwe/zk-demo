package zookeeper.demo.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CreateZkLinkDemo {

    private final  static  String connect = "192.168.159.134:2181";

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZkClient zkClient = new ZkClient(connect,4000);

        //创建临时节点
        //zkClient.createEphemeral("/tempZone");

        //循环创建子节点
        //zkClient.createPersistent("/zkclient/zkclient1/zkclient1-1/zkclient1-1-1",true);

        //删除
        //System.out.println(zkClient.delete("/demo4"));

        //递归删除
        //System.out.println(zkClient.deleteRecursive("/zkclient"));

        //事件订阅
        zkClient.subscribeDataChanges("/demo8", new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("数据改变"+o);
            }

            public void handleDataDeleted(String s) throws Exception {
                System.out.println("节点删除");
            }
        });

        zkClient.writeData("/demo8","newNodeValue");
        //监控事件为异步进行，
        TimeUnit.SECONDS.sleep(2);

        zkClient.delete("/demo8");
        TimeUnit.SECONDS.sleep(2);
    }

}
