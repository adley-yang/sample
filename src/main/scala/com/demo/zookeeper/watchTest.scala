package com.demo.zookeeper

import java.util.concurrent.Executors

import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.framework.recipes.cache.{NodeCache, NodeCacheListener}
import org.apache.curator.retry.RetryNTimes
import org.apache.zookeeper.{WatchedEvent, Watcher}

/**
 * Project Name:sample
 * Package Name:com.demo.zookeeper
 * Date:15-3-23 下午3:09
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
object watchTest extends App {
  val pool = Executors.newFixedThreadPool(2)
  val client = CuratorFrameworkFactory.builder().connectString("192.168.86.39:2189,192.168.86.39:2190,192.168.86.39:2191")
    .namespace("ldarec").retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).connectionTimeoutMs(5000).build()
  client.start()
  //一直监听数据节点的变化情况，每次改变都触发监听
  val nodeCache = new NodeCache(client, "/test", false);
  nodeCache.start(true);
  nodeCache.getListenable().addListener(
    new NodeCacheListener() {
      @Override def nodeChanged() {
        println("Node data is changed, new data: " +
          new String(nodeCache.getCurrentData().getData()));
      }
    },
    pool
  )

  // 注册观察者，当节点变动时触发，只能使用一次
  client.getData().usingWatcher(new Watcher() {
    @Override def process(event: WatchedEvent) {
      println("node is changed");
    }
  }).inBackground().forPath("/test")

  Thread.sleep(10000000)
}
