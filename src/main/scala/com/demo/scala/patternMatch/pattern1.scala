package com.demo.scala.patternMatch

/**
 * Project Name:sample
 * Package Name:com.demo.scala.patternMatch
 * Date:15/4/21 下午3:34
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
object pattern1 extends App {

  // 匹配一个数组，它由三个元素组成，第一个元素为1，第二个元素为2，第三个元素为3
  Array(1, 2, 3) match {
    case Array(1, 2, 3) => println("ok")
  }

  // 匹配一个数组，它至少由一个元素组成，第一个元素为1
  Array(1, 2, 3) match {
    case Array(1, _*) => println("ok")
  }

  // 匹配一个List，它由三个元素组成，第一个元素为“A"，第二个元素任意类型，第三个元素为"C"
  List("A", "B", "C") match {
    case List("A", _, "C") => println("ok")
  }

  // 常量模式，如果a与100相等则匹配成功
  val num: Int = 1000
  num match {
    case 1000 => println("ok")
    case _ => println("unknown")
  }
  // 类型模式，如果a是Int类型就匹配成功
  num match {
    case _: Int => println("ok")
  }

  /** ****************************************************************************/
  //1)常量模式(constant patterns) 包含常量变量和常量字面量
  val site = "todaycity.cn"
  site match {
    case "todaycity.cn" => println("ok")
    case _ => println("unknown")
  }

  //2) 变量模式(variable patterns)
  site match { case whateverName => println(whateverName) }

  List(1,2) match{ case List(x,2) => println(x) }


  //3) 通配符模式(wildcard patterns)
  List(1,2,3) match{ case List(_,_,3) => println("ok") }

  //4) 构造器模式(constructor patterns)
  //抽象节点
  trait Node
  //具体的节点实现，有两个子节点
  case class TreeNode(v:String, left:Node, right:Node) extends Node
  //Tree，构造参数是根节点
  case class Tree(root:TreeNode)

  val tree = Tree(TreeNode("root",TreeNode("left",null,null),TreeNode("right",null,null)))

  tree.root match {
    case TreeNode(_, TreeNode("left",_,_), TreeNode("right",null,null)) =>
      println("bingo")
  }


  //5) 类型模式(type patterns)
  "hello" match { case _:String => println("ok") }

  //6) 变量绑定模式 (variable binding patterns)
  tree.root match {
    case TreeNode(_, leftNode@TreeNode("left",_,_), _) => leftNode
  }





}
