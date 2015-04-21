package com.demo.akka.Actors

import akka.actor.{ActorSystem, Actor, Props}
import akka.event.Logging

/**
 * Project Name:sample
 * Package Name:com.demo.akka.Actors
 * Date:15/4/16 下午3:21
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
class FirstActor(a : String) extends Actor {
  val log = Logging(context.system, this)

  import context._

  val myActor = actorOf(Props[MyActor], name = "myactor")

  def receive = {
    case x => myActor ! x
  }

  override def preStart(): Unit = {
    log.info("FirstActir preStart:"+a)
  }

}

class MyRunnable extends Runnable {
  override def run() {
    val system = ActorSystem("Main")
    System.out.println(Thread.currentThread().getName() + ":MyThread running")
    val firstActor = system.actorOf(Props(new FirstActor("test........")), name = "firstActor")
  }
}

  object Main2 extends App {

    //firstActor ! "test"
    //firstActor ! "test1"
    //system.shutdown()

    for(i<- 0 until 10) {
      val thread = new Thread(new MyRunnable())
      thread.start()
    }



  }
