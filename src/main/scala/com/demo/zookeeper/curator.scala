package com.demo.zookeeper

import java.nio.charset.Charset
import java.util.concurrent.Executors

import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.retry.RetryNTimes
import org.apache.zookeeper.CreateMode

/**
 * Project Name:sample
 * Package Name:com.demo.zookeeper
 * Date:15-3-23 上午11:05
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
object curator extends App {

  val path = "/test_path"
  val client = CuratorFrameworkFactory.builder().connectString("192.168.86.39:2189,192.168.86.39:2190,192.168.86.39:2191")
    .namespace("ldarec").retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).connectionTimeoutMs(5000).build()
  val pool = Executors.newFixedThreadPool(2)
  // 启动 上面的namespace会作为一个最根的节点在使用时自动创建
  client.start()

  // 创建一个节点
  println("创建一个节点:" + client.create().forPath("/head", new Array[Byte](0)))

  // 异步地删除一个节点
  println("异步地删除一个节点:" + client.delete().inBackground().forPath("/head"))

  // 创建一个临时节点
  println("创建一个临时节点:" + client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/headtmp", new Array[Byte](0)))

  //检查节点是否存在
  println("检查节点是否存在:" + client.checkExists().forPath("/test"))

  //写数据
  println("写数据:" + client.setData().forPath("/test", "你好1".getBytes(Charset.forName("UTF-8"))))

  // 取数据
  println("取数据:" + new String(client.getData().watched().forPath("/test")))

  // 检查路径是否存在
  println("检查路径是否存在:" + client.checkExists().forPath(path))

  // 异步删除
  println("异步删除:" + client.delete().inBackground().forPath("/head"))

  //写数据
  println("写数据:" + client.setData().forPath("/test", "你好2".getBytes(Charset.forName("UTF-8"))))

  // 结束使用
  client.close();
}
