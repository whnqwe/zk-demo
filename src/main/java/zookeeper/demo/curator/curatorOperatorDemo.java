package zookeeper.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;

public class curatorOperatorDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework =  CuratorClientUtils.getInstance();

        //创建节点
//        curatorFramework.
//                create().
//                creatingParentsIfNeeded().
//                withMode(CreateMode.PERSISTENT).
//                forPath("/curatorDemo","444".getBytes());

        //删除节点
       // curatorFramework.delete().deletingChildrenIfNeeded().forPath("/demo1");

        //获取节点
//        Stat stat = new Stat();
//        byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/demo2");
//
//        System.out.println(new String(bytes));
//        System.out.println(stat);



         //更新


//        try {
//            Stat stat=curatorFramework.setData().forPath("/curator","123".getBytes());
//            System.out.println(stat);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



        /**
         * 异步操作
         */
        /*ExecutorService service= Executors.newFixedThreadPool(1);
        CountDownLatch countDownLatch=new CountDownLatch(1);
        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).
                    inBackground(new BackgroundCallback() {
                        @Override
                        public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                            System.out.println(Thread.currentThread().getName()+"->resultCode:"+curatorEvent.getResultCode()+"->"
                            +curatorEvent.getType());
                            countDownLatch.countDown();
                        }
                    },service).forPath("/mic","123".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        countDownLatch.await();
        service.shutdown();*/


        /**
         * 事务操作（curator独有的）
         */

        try {
            Collection<CuratorTransactionResult> resultCollections=curatorFramework.inTransaction().create().forPath("/trans","111".getBytes()).and().
                    setData().forPath("/curator","111".getBytes()).and().commit();
            for (CuratorTransactionResult result:resultCollections){
                System.out.println(result.getForPath()+"->"+result.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
