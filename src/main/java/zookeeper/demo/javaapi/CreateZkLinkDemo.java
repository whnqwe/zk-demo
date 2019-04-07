package zookeeper.demo.javaapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.CreateMode.PERSISTENT_SEQUENTIAL;

public class CreateZkLinkDemo implements Watcher {

    private final  static  String connect = "192.168.159.134:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zookeeper = null;
    private static Stat stat=new Stat();
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zookeeper = new ZooKeeper(connect, 10000,new CreateZkLinkDemo());
        countDownLatch.await();
        //创建节点
//        String zodeName = zookeeper.create("/demo8","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zookeeper.getData("/demo8",new CreateZkLinkDemo(),new Stat());
//        Thread.sleep(2000);
        //修改数据
//        zookeeper.setData("/demo6","345".getBytes(),-1);
//        Thread.sleep(2000);

        //指定权限
//        ACL acl=new ACL(ZooDefs.Perms.ALL,new Id("ip","192.168.11.129"));
        ACL acl=new ACL(ZooDefs.Perms.ALL,new Id("digest","root:root"));
        List<ACL> acls=new ArrayList<ACL>();
        acls.add(acl);

        zookeeper.create("/authTest-1","123".getBytes(),acls, CreateMode.PERSISTENT);


    }

    public void process(WatchedEvent watchedEvent) {
        //如果当前的连接状态是连接成功的，那么通过计数器去控制
        if(watchedEvent.getState()==Event.KeeperState.SyncConnected){
            if(Event.EventType.None==watchedEvent.getType()&&null==watchedEvent.getPath()){
                countDownLatch.countDown();
                System.out.println(watchedEvent.getState()+"-->"+watchedEvent.getType());
            }else if(watchedEvent.getType()== Event.EventType.NodeDataChanged){
                try {
                    System.out.println("数据变更触发路径："+watchedEvent.getPath()+"->改变后的值："+
                            zookeeper.getData(watchedEvent.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(watchedEvent.getType()== Event.EventType.NodeChildrenChanged){//子节点的数据变化会触发
                try {
                    System.out.println("子节点数据变更路径："+watchedEvent.getPath()+"->节点的值："+
                            zookeeper.getData(watchedEvent.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(watchedEvent.getType()== Event.EventType.NodeCreated){//创建子节点的时候会触发
                try {
                    System.out.println("节点创建路径："+watchedEvent.getPath()+"->节点的值："+
                            zookeeper.getData(watchedEvent.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(watchedEvent.getType()== Event.EventType.NodeDeleted){//子节点删除会触发
                System.out.println("节点删除路径："+watchedEvent.getPath());
            }
            System.out.println(watchedEvent.getType());
        }

    }

//    public void process(WatchedEvent watchedEvent) {
//        //如果当前的连接状态是连接成功的，那么通过计数器去控制
//        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
//            // Event.EventType.None 表示已经建立连接
//            if(watchedEvent.getType() == Event.EventType.None && null==watchedEvent.getPath()){
//                countDownLatch.countDown();
//                System.out.println(watchedEvent.getState()+"-->"+watchedEvent.getType());
//            }else if(watchedEvent.getType() == Event.EventType.NodeCreated){
//                try {
//                    System.out.println("创建数据："+watchedEvent.getPath()+"->值："+
//                            zooKeeper.getData(watchedEvent.getPath(),true,stat));
//                } catch (KeeperException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }else if(watchedEvent.getType() == Event.EventType.NodeDataChanged){
//                try {
//                    System.out.println("数据变更触发路径："+watchedEvent.getPath()+"->改变后的值："+
//                            zooKeeper.getData(watchedEvent.getPath(),true,stat));
//                } catch (KeeperException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }else if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged){
//                try {
//                    System.out.println("子节点数据变更路径："+watchedEvent.getPath()+"->节点的值："+
//                            zooKeeper.getData(watchedEvent.getPath(),true,stat));
//                } catch (KeeperException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }else if(watchedEvent.getType() == Event.EventType.NodeDeleted){
//                System.out.println("节点删除路径："+watchedEvent.getPath());
//            }
//
//        }
//
//        System.out.println(watchedEvent.getType());
//    }
}
