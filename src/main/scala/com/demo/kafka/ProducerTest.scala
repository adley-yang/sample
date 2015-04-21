package com.demo.kafka


/**
 * Created by zyyang on 15/4/15.
 */
object ProducerTest extends App{

  val producer = new KafkaProducer("test","tc6:9092,tc7:9092,tc8:9092")


for(i <- 0 until 100){
  producer.send("hello,"+i+"~"+System.currentTimeMillis())
  //Thread sleep 1000
}

}
