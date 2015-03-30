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


  def createTable(tableName: String, cols: Array[String]) = {
    val hBaseAdmin = new HBaseAdmin(configuration)
    try {
      val table = new HTableDescriptor(TableName.valueOf(tableName))
      for (c <- cols) {
        val col = new HColumnDescriptor(c); //列簇名
        table.addFamily(col); //添加到此表中
      }
      hBaseAdmin.createTable(table) //创建一个表
      println("创建表成功!")
    } catch {
      case e: MasterNotRunningException => println("创建表异常：" + e)
      case e: ZooKeeperConnectionException => println("创建表异常：" + e)
      case e: IOException => println("创建表异常：" + e)
      case e: Exception => println("创建表异常：" + e)
    } finally {
      hBaseAdmin.close()
    }
  }

  def deleteTable(tableName: String) = {
    val hBaseAdmin = new HBaseAdmin(configuration)
    try {
      if (hBaseAdmin.tableExists(tableName)) {
        hBaseAdmin.disableTable(tableName)
        hBaseAdmin.deleteTable(tableName)
        println("删除表成功!")
      }
    } catch {
      case e: Exception => println("删除表异常：" + e)
    } finally {
      hBaseAdmin.close()
    }
  }


  def insertData(tableName: String, row: String, columnFamily: String, column: String, value: String) = {
    val table = new HTable(configuration, tableName)
    try {
      val put = new Put(Bytes.toBytes(row))
      put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value))
      table.put(put)
      println("数据插入成功!")
    } catch {
      case e: Exception => println("数据插入失败!" + e)
    } finally {
      table.close()
    }
  }

  def deleteByRow(tableName: String, rowkey: String): Unit = {
    val table = new HTable(configuration, tableName)
    try {
      val deleteRow = new Delete(Bytes.toBytes(rowkey))
      table.delete(deleteRow)
      table.close()
      println("数据删除成功!")
    } catch {
      case e: Exception => println("删除数据失败!" + e)
    } finally {
      table.close()
    }
  }

  def deleteByRow(tableName: String, rowkey: Array[String]): Unit = {
    val table = new HTable(configuration, tableName)
    try {
      val deleteList: util.ArrayList[Delete] = new util.ArrayList[Delete]()
      for (key <- rowkey) {
        deleteList.add(new Delete(Bytes.toBytes(key)))
      }
      table.delete(deleteList)
      table.close()
      println("数据删除成功!")
    } catch {
      case e: Exception => println("删除数据失败!" + e)
    } finally {
      table.close()
    }
  }

  def getOneDataByRowKey(tableName: String, rowkey: String): Unit = {
    val table = new HTable(configuration, tableName)
    try {
      val get = new Get(Bytes.toBytes(rowkey))
      val result = table.get(get)
      for (cell <- result.rawCells()) {
        println("Row Name:" + new String(CellUtil.cloneRow(cell)))
        println("column Family:" + new String(CellUtil.cloneFamily(cell)))
        println("column Name:" + new String(CellUtil.cloneQualifier(cell)))
        println("Value:" + new String(CellUtil.cloneValue(cell)))
        println("getTimestamp:" + cell.getTimestamp)
      }
    } catch {
      case e: Exception => println("查询数据异常:" + e)
    } finally {
      table.close()
    }
  }


  def getAllRows(tableName: String) = {
    val table = new HTable(configuration, tableName)
    val scan = new Scan()
    try {
      val results: util.Iterator[Result] = table.getScanner(scan).iterator()
      while (results.hasNext) {
        val result = results.next()
        for (cell <- result.rawCells()) {
          println("Row Name:" + new String(CellUtil.cloneRow(cell)))
          println("column Family:" + new String(CellUtil.cloneFamily(cell)))
          println("column Name:" + new String(CellUtil.cloneQualifier(cell)))
          println("Value:" + new String(CellUtil.cloneValue(cell)))
          println("getTimestamp:" + cell.getTimestamp)
        }
      }
    } catch {
      case e: Exception => println("查询数据异常:" + e)
    } finally {
      table.close()
    }
  }

  deleteTable("test_2")
  createTable("test_2", Array("name", "age"))
  insertData("test_2", "1", "age", "myage", "100")
  insertData("test_2", "1", "name", "myname", "zyyang")
  insertData("test_2", "2", "name", "myname", "test")
  insertData("test_2", "3", "name", "myname", "3test")
  insertData("test_2", "3", "age", "myage", "80")
  insertData("test_2", "4", "age", "myage", "4")
  insertData("test_2", "5", "age", "myage", "5")
  insertData("test_2", "6", "age", "myage", "6")
  insertData("test_2", "7", "age", "myage", "7")

  deleteByRow("test_2", "1")
  deleteByRow("test_2", Array("4", "5"))
  println("----------getOneDataByRowKey:3-------------------------------")
  getOneDataByRowKey("test_2", "3")
  println("--------------getAllRows: test_2-----------------------------")
  getAllRows("test_2")
}
