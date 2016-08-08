package examples.dataguru.message.queue

/**
  * Created by Lei on 2016-8-7.
  */
object TestEventLoop {
  def main(args: Array[String]) {
    val eventLoop=new EventProcessLoop()
    println("启动队列")
    eventLoop.start()
    for (i <- 1 until 40000){
      eventLoop.post(i.toString)
    }
    eventLoop.stop()
    println("关闭队列")
  }
}
