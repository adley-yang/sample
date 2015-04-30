package com.demo.scala.types

/**
 * Project Name:sample
 * Package Name:com.demo.scala.types
 * Date:15/4/27 上午11:41
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
object type2 extends App {

  def foo[T](x: List[T])(implicit m: Manifest[T]) = {
    if (m <:< manifest[String])
      println("Hey, this list is full of strings")
    else
      println("Non-stringy list")
  }


  foo(List("one", "two")) // Hey, this list is full of strings
  foo(List(1, 2)) // Non-stringy list
  foo(List("one", 2)) // Non-stringy list


  // 让参数类型满足Int或String
  def f[A](a: A)(implicit ev: (Int with String) <:< A) = println("OK")
  f(2)
  f("hi")


}
