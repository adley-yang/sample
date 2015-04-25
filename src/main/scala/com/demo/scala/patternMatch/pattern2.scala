package com.demo.scala.patternMatch

/**
 * Project Name:sample
 * Package Name:com.demo.scala.patternMatch
 * Date:15/4/21 下午4:21
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
object pattern2 extends App {

  class A

  class B(val a: A)

  object TT {
    def unapply(b: B) = Some(new A)
  }

  val b = new B(new A);
  b match {
    case TT(a) => println(a)
  }



  //定义为抽象类型
  trait C

  //然后再实现一个具体的子类，有2个构造参数
  class D (val p1:String, val p2:String) extends C

  //定义一个抽取器
  object MM{
    //抽取器中apply方法是可选的，这里是为了方便构造A的实例
    def apply(p1:String, p2:String) : C = new D(p1,p2);

    //把A分解为(String,String)
    def unapply(c:C) : Option[(String, String)] = {
      if (c.isInstanceOf[D]) {
        val d = c.asInstanceOf[D]
        return Some(d.p1, d.p2)
      }
      None
    }
  }



}
