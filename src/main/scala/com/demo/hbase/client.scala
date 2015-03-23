package com.demo.hbase

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.HBaseAdmin

/**
 * Project Name:sample
 * Package Name:com.demo.hbase
 * Date:15-3-23 下午4:16
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
object client extends App{

  val QUORUM = "192.168.86.4,192.168.86.14,192.168.86.15"
  val CLIENTPORT = "2181"
  val configuration = HBaseConfiguration.create()
  val MASTER = "192.168.86.16:600000"
  configuration.set("hbase.zookeeper.property.clientPort", CLIENTPORT)
  configuration.set("hbase.zookeeper.quorum", QUORUM)
  configuration.set("hbase.master", MASTER)


  def createTable(tableName : String)={
    val hBaseAdmin = new HBaseAdmin(configuration)
  }


}
