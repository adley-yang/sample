package com.demo.scala.types

import scala.collection.generic.CanBuildFrom

/**
 * Project Name:sample
 * Package Name:com.demo.scala.types
 * Date:15/4/27 下午8:03
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
object type4 extends App {

  //Matrix  *   Matrix  -> Matrix  //矩阵 * 矩阵 得到 矩阵
  //Matrix  *   Vector  -> Vector  //矩阵 * 矢量 得到 矢量
  //Matrix  *   Int     -> Matrix  //矩阵 * Int 得到 矩阵
  //Int     *   Matrix  -> Matrix  //Int * 矩阵 得到 矩阵

  trait Matrix

  trait Vector

  trait MultDep[A, B, C]

  //定义一个笛卡尔运算时需要依赖类型，用于约束结果类型

  //我们通过隐式对象提供各种类型的组合时的依赖类型：
  implicit object mmm extends MultDep[Matrix, Matrix, Matrix]

  implicit object mvv extends MultDep[Matrix, Vector, Vector]

  implicit object mim extends MultDep[Matrix, Int, Matrix]

  implicit object imm extends MultDep[Int, Matrix, Matrix]

  //检验一下隐式对象：
  implicitly[MultDep[Matrix, Matrix, Matrix]]
  implicitly[MultDep[Matrix, Vector, Vector]]

  //implicitly[MultDep[Matrix, Vector, Matrix]]   // Error 没有定义过这种组合


  def mult[A, B, C](a: A, b: B)(implicit instance: MultDep[A, B, C]): C = {
    null.asInstanceOf[C]
  }


  /*Matrix = mult(new Matrix {}, new Matrix{}) // Compiles
  Vector = mult(new Matrix {}, new Vector{}) // Compiles
  Matrix = mult(new Matrix {}, 2)            // Compiles
  Matrix = mult(2, new Matrix {})            // Compiles
  Matrix = mult(new Matrix {}, new Vector{}) // 错误,结果类型应该是Vector*/





}
