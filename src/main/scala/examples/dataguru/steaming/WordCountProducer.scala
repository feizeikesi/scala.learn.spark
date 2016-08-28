package examples.dataguru.steaming

/**
  * Created by Lei on 2016-8-28.
  */
import java.io.PrintStream
import java.net.Socket

import scala.util.Random
import scala.util.control.Exception._

/**
  * Created by Administrator on 2016/8/18.
  */
object WordCountProducer extends LoanPattern {
  def main(args: Array[String]) {
    val host = "localhost"
    val port = 9999
    val socket = new Socket(host, port)
    using(socket)(socket => {
      val out = new PrintStream(socket.getOutputStream)
      val r=new Random(1)
      while (true) {
        val msg =(1 until  r.nextInt(10)).map("word").mkString(" ")
        out.println()
        out.flush()
        Thread.sleep(100)
      }
    })
  }
}

trait LoanPattern {
  type Closed = { def close() }

  def using[R <: Closed, A](r: R)(f: R => A): Unit = {
    try {
      f(r)
    } finally {
      ignoring(classOf[Throwable]).apply(r.close())
    }
  }

}