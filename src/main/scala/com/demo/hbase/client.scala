package com.demo.hbase

import org.apache.hadoop.hbase.HBaseConfiguration

/**
 * Project Name:sample
 * Package Name:com.demo.hbase
 * Date:15-3-23 下午4:16
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
object client extends App{

  val QUORUM = "192.168.86.4,192.168.86.14,192.168.86.15"
  val CLIENTPORT = "2181"
  val conf  = new HBaseConfiguration()




}
