package examples.dataguru.message.queue

/**
  * Created by Lei on 2016-8-7.
  */
class EventProcessLoop extends EventLoop[String]("t") {
  private val  maxQueueLength:Int=30000
  override protected def onReceive(event: String): Unit = {
    println("处理事件内容: "+event)
  }

  override protected def onError(e: Throwable): Unit = {}

  override def post(event: String): Unit = {
    if (super.queueLength<maxQueueLength){
      super.post(event)
    }else{
      println(s"已超出队列最大${maxQueueLength}数量")
    }
  }
}
