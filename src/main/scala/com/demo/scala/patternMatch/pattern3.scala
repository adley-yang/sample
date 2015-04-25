package com.demo.scala.patternMatch

/**
 * Project Name:sample
 * Package Name:com.demo.scala.patternMatch
 * Date:15/4/21 下午4:41
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
object pattern3 extends App {

  for (i <- List(1, 2, 3)) {
    println(i)
  }
  println("------------------------------")
  for (i@2 <- List(1, 2, 3)) {
    println(i)
  }
  println("------------------------------")
  for ((name, "female") <- Set("wang" -> "male", "zhang" -> "female")) print(name)
  println("------------------------------")
  for ((k, v: Int) <- List(("A" -> 2), ("B" -> "C"))) {
    println(k)
  }

}
