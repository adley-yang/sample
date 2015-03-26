package com.demo.hbase

import java.io.IOException

import org.apache.hadoop.hbase._
import org.apache.hadoop.hbase.client.HBaseAdmin

/**
 * Project Name:sample
 * Package Name:com.demo.hbase
 * Date:15-3-23 下午4:16
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
object client extends App {

  val QUORUM = "bd-002,bd-003,bd-004"
  val CLIENTPORT = "2181"
  val configuration = HBaseConfiguration.create()
  val MASTER = "192.168.86.41:60000"
  configuration.set("hbase.zookeeper.property.clientPort", CLIENTPORT)
  configuration.set("hbase.zookeeper.quorum", QUORUM)
  configuration.set("hbase.master", MASTER)


  def createTable(tableName: String, cols: Array[String]) = {
    try {
      val hBaseAdmin = new HBaseAdmin(configuration)
      /*if (hBaseAdmin.tableExists(tableName)) {
        println("此表已经存在.......");
      } else {*/
        val table = new HTableDescriptor(tableName);
        for (c <- cols) {
          val col = new HColumnDescriptor(c); //列簇名
          table.addFamily(col); //添加到此表中
        }
      //hBaseAdmin.createTable(table) //创建一个表
        println(hBaseAdmin.listTableNames())
        println("创建表成功!")
     // }
    } catch {
      case e: MasterNotRunningException => println("创建表异常：" + e)
      case e: ZooKeeperConnectionException => println("创建表异常：" + e)
      case e: IOException => println("创建表异常：" + e)
      case e: Exception => println("创建表异常：" + e)
    }
  }


  println("start create table")
  //创建表
  createTable("test_1", Array("name", "age"))


}
