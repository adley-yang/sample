package com.demo.kafka

import kafka.utils.Logging

/**
 * Created by zyyang on 15/4/15.
 */
object ConsumerTest extends App with Logging {

  val consumer = new KafkaConsumer("tcdata", "tcdata_group", "tc6:2189,tc7:2189,tc8:2189/kafka-tc")

  def exec(binaryObject: Array[Byte]) = {
    val message = new String(binaryObject)
    println("consumed message = " + message)
    //consumer.close()
  }

  info("KafkaSpec is waiting some seconds")
  try {
    while (true) {
      consumer.read(exec)
    }
  } catch {
    case e: Exception => info("Exception" + e)
  } finally {
    consumer.close()
  }
  info("KafkaSpec consumed")

}
