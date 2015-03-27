package com.demo.hbase

import java.io.IOException
import java.util


import org.apache.hadoop.hbase._
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes

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
  val MASTER = "bd-001:60000"
  configuration.set("hbase.zookeeper.property.clientPort", CLIENTPORT)
  configuration.set("hbase.zookeeper.quorum", QUORUM)
  configuration.set("hbase.master", MASTER)
  val hBaseAdmin = new HBaseAdmin(configuration)

  def createTable(tableName: String, cols: Array[String]) = {
    try {
      if (hBaseAdmin.tableExists(tableName)) {
        hBaseAdmin.disableTable(tableName)
        hBaseAdmin.deleteTable(tableName)
        println(tableName + "已经存在，删除并重建....")
      }
      Thread sleep 1000
      val table = new HTableDescriptor(tableName)
      for (c <- cols) {
        val col = new HColumnDescriptor(c); //列簇名
        table.addFamily(col); //添加到此表中
        hBaseAdmin.createTable(table) //创建一个表
        println("创建表成功!")
      }
    } catch {
      case e: MasterNotRunningException => println("创建表异常：" + e)
      case e: ZooKeeperConnectionException => println("创建表异常：" + e)
      case e: IOException => println("创建表异常：" + e)
      case e: Exception => println("创建表异常：" + e)
    }
  }

  def insertData(tableName: String, row: String, columnFamily: String, column: String, value: String) = {
    try {
      val table = new HTable(configuration, tableName)
      val put = new Put(Bytes.toBytes(row))
      put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value))
      table.put(put)
      table.close() //关闭
      println("数据插入成功!")
    } catch {
      case e: Exception => println("数据插入失败!" + e)
    }
  }

  def deleteByRow(tableName: String, rowkey: String): Unit = {
    try {
      val table = new HTable(configuration, tableName)
      val deleteRow = new Delete(Bytes.toBytes(rowkey))
      table.delete(deleteRow)
      table.close()
      println("数据删除成功!")
    } catch {
      case e: Exception => println("删除数据失败!" + e)
    }
  }

  def deleteByRow(tableName: String, rowkey: Array[String]): Unit = {
    try {
      val table = new HTable(configuration, tableName)
      val deleteList: util.ArrayList[Delete] = new util.ArrayList[Delete]()
      for (key <- rowkey) {
        deleteList.add(new Delete(Bytes.toBytes(key)))
      }
      table.delete(deleteList)
      table.close()
      println("数据删除成功!")
    } catch {
      case e: Exception => println("删除数据失败!" + e)
    }
  }

  def getOneDataByRowKey(tableName: String, rowkey: String): Unit = {
    val table = new HTable(configuration, tableName)
    val get = new Get(Bytes.toBytes(rowkey))
    val result = table.get(get)
    for (k <- result.rawCells()) {
      println("getFamilyArray:" + Bytes.toStringBinary(k.getFamilyArray))
      println("getRowArray:" + Bytes.toStringBinary(k.getRowArray))
      println("getTimestamp:" + k.getTimestamp)
      println("getMvccVersion:" + k.getMvccVersion)
    }
  }


  //创建表
  createTable("test_1", Array("name", "age"))
  insertData("test_1", "1", "age", "myage", "100")
  insertData("test_1", "1", "name", "myname", "zyyang")
  insertData("test_1", "2", "name", "myname", "test")
  insertData("test_1", "3", "age", "myage", "80")
  insertData("test_1", "4", "age", "myage", "4")
  insertData("test_1", "5", "age", "myage", "5")

  deleteByRow("test_1", "1")
  deleteByRow("test_1", Array("4", "5"))
  getOneDataByRowKey("test_1", "3")
}
