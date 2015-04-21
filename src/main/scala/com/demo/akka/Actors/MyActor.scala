package com.demo.akka.Actors
import akka.actor.{Props, ActorSystem, Actor}


/**
 * Project Name:sample
 * Package Name:com.demo.akka.Actors
 * Date:15/4/16 下午2:56
 * Copyright (c) 2015, adleyyang.cn@gmail.com All Rights Reserved.
 */
class MyActor extends Actor with akka.actor.ActorLogging{
  def receive = {
    case "test" ⇒ log.info("received test")
    case _      ⇒ log.warning("received unknown message")
  }
  override  def preStart(): Unit = {
    log.info("MyActor preStart")
  }
  override def postStop() = {
    log.info("MyActor postStop")
  }

}

object Main1 extends App {
  val system = ActorSystem("Main1")
  val myActor = system.actorOf(Props[MyActor], name = "myactor")
  myActor ! "test"
  myActor ! "test1"
  system.shutdown()


}