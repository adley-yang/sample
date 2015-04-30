package com.demo.scala.types

import scala.reflect.ClassTag

/**
 * Project Name:sample
 * Package Name:com.demo.scala.types
 * Date:15/4/27 下午7:49
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
object type3 extends App {


  //在《scala in depth》中总结了type classes模式的几个优点：
  //1) Separation of abstractions (抽象分离)
  //2) Composability (可组合)
  //3) Overridable (可覆盖)
  //4) Type safety (类型安全)


  //Ordering[_]抽象了大于、等于、小于等用于比较的方法。它是一个典型的type class
  def foo1[T: Ordering](l: List[T]) {
    l.sorted.foreach(print)
  }

  foo1(List(2, 3, 1))
  println
  foo1(List("b", "c", "a"))
  println

  //Numeric[_]对抽象了所有数字类型中的加、减、乘、除等操作。
  def foo2[T: Numeric](l: List[T]) {
    print(l.sum) // List只对数字类型才能使用sum方法
  }

  //Manifest/TypeTag/ClassTag 等
  def buildArray[T: ClassTag](len: Int) = new Array[T](len)

  println(buildArray[Int](3) mkString(","))

  //<:<[-From, +To], =:=[From,To]
  def test[T](i: T)(implicit ev: T <:< java.io.Serializable) {
    print("OK")
  }

  test[String]("str")


}
