package com.demo.scala.types

import java.util.Comparator

/**
 * Project Name:sample
 * Package Name:com.demo.scala.types
 * Date:15/4/27 上午9:41
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
object type1 extends App {

  /**
   * 在内部定义函数并声明隐式参数
   * 把外部方法的隐式参数隐藏了,放到内部嵌套函数上
   * @param a
   * @param b
   * @tparam T
   * @return
   */
  def max[T : Comparator] (a:T, b:T) = {

    def inner(implicit c:Comparator[T]) = c.compare(a,b)

    if(inner>0) a else b
  }

  def max2[T : Comparator] (a:T, b:T) = {

    val cp = implicitly[Comparator[T]]

    if( cp.compare(a,b)>0) a else b
  }

  implicit val c = new Comparator[Int]{
    override def compare(a:Int, b:Int) = a - b
  }

  implicit val d = new Comparator[String]{
    override def compare(a:String, b:String) = a.compareTo(b)
  }


  println(max(1,2))

  println(max2("a","b"))


}
