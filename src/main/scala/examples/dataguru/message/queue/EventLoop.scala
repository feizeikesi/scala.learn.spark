package examples.dataguru.message.queue

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

import scala.util.control.NonFatal

/**
  * Created by Lei on 2016-8-7.
  */
private[dataguru] abstract class EventLoop[E](name: String) {

  private val eventQueue: BlockingQueue[E] = new LinkedBlockingQueue[E]()

  private val stopped = new AtomicBoolean(false)

  private val eventThread = new Thread(name) {
    setDaemon(true)

    override def run(): Unit = {
        try {
          while (!stopped.get()){
            val event=eventQueue.take()
            try{
              onReceive(event)
            }catch {
              case NonFatal(e)=>
                try{
                  onError(e)
                }catch {
                  case  NonFatal(ee)=>println("错误",ee)
                }
            }
          }
        }catch {
          case ie:InterruptedException=>
          case NonFatal(e)=>println("错误",e)
        }
    }
  }

  protected def onStart(): Unit = {}

  protected def onStop(): Unit = {}

  protected def onReceive(event: E): Unit

  protected def onError(e: Throwable): Unit

  def start(): Unit = {
    if (stopped.get()) {
      throw new IllegalArgumentException(name + " 已停止了!")
    }
    onStart()
    eventThread.start()
  }

  def stop(): Unit = {
    //更新stopped的状态
    if (stopped.compareAndSet(false, true)) {
      eventThread.interrupt()
      var onStopCalled = false
      try {
        eventThread.join()
        onStopCalled = true
        onStop()
      } catch {
        case ie: InterruptedException =>
          Thread.currentThread().interrupt()
          if (!onStopCalled) {
            onStop()
          }
      }
    }
  }

  def post(event:E): Unit ={
    eventQueue.put(event)
  }

  def isActive:Boolean=eventThread.isAlive

  def queueLength:Int=eventQueue.size()

}
