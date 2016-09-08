package online.decentworld.message.util;

import online.decentworld.message.config.ConfigLoader;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CuratorClient {
	private CuratorFramework client;
	private String connectString= ConfigLoader.ZKConfig.ZK_CONNECTSTR;
	private static CuratorClient instance=new CuratorClient();
	private static Logger logger= LoggerFactory.getLogger(CuratorClient.class);
	
	private CuratorClient(){
		RetryPolicy policy= new ExponentialBackoffRetry(3000,3);
		client=CuratorFrameworkFactory.builder()
				.connectString(connectString)
				.retryPolicy(policy)
				.namespace(ConfigLoader.ZKConfig.NAMESPACE)
				.sessionTimeoutMs(10000)
				.connectionTimeoutMs(3000)
				.build();
		client.start();
	}
	
	public static CuratorClient instance(){
		return instance;
	}
	
	public void createNode(String path,String data) throws Exception{
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).withACL(Ids.OPEN_ACL_UNSAFE)
			.forPath(path,data.getBytes());
		logger.info("【CREATE_ZK_NODE】 path#"+path+" data#"+data);
	}
	public void closeClient(){
		if(client!=null){
			client.close();
		}
	}
	
	public void addNodeCache(String node) throws Exception{
		final NodeCache cache=new NodeCache(client,node);
		cache.start(true);
		cache.getListenable().addListener(new NodeCacheListener() {
			public void nodeChanged() throws Exception {
				String path=cache.getCurrentData().getPath();
				String data=new String(cache.getCurrentData().getData());
				logger.info("【ZKNOTIFY】 path#"+path+" data#"+data);
			}
		});
	}
	
	public void addPathChildrenCache(String path,final PathChildrenCacheListener listener) throws Exception{
		final PathChildrenCache cache=new PathChildrenCache(client,path,true);
		cache.start(StartMode.POST_INITIALIZED_EVENT);
		cache.getListenable().addListener(listener);
	}
	
	public void setData(String path,byte[] data,int version) throws Exception{
		client.setData().withVersion(version).inBackground().forPath(path, data);
	}
	public void setData(String path,byte[] data) throws Exception{
		client.setData().inBackground().forPath(path, data);
	}
	
	public CuratorFramework getCuratorCilent(){
		return client;
	}
	
	public static void main(String[] args) throws Exception {
		CuratorClient client=CuratorClient.instance;
//		CuratorFramework curator=client.getCuratorCilent();
//		List<String> children=curator.getChildren().forPath("/openfire");
//		for(String s:children){
//			System.out.println(s);
//		}
//		PathChildrenCache cache=new PathChildrenCache(curator, "/openfire", true);
//		cache.start(StartMode.BUILD_INITIAL_CACHE);
//		cache.getListenable().addListener(new PathChildrenCacheListener() {
//			@Override
//			public void childEvent(CuratorFramework arg0, PathChildrenCacheEvent event)
//					throws Exception {
//				System.out.println(event.getData().getPath());
//				System.out.println(event.getData().getData());
//			}
//		});
		//		client.addNodeCache("/openfire/node1");
////		client.addPathChildrenCache("/openfire");
//		client.createNode("/openfire/node1","node1address");
		Thread.sleep(1000000);
////		client.createNode("/openfire/node2","node2address");
//		client.setData("/openfire/node1", "newaddress".getBytes());
//		Thread.sleep(10000);
		client.closeClient();
		
	}
}
