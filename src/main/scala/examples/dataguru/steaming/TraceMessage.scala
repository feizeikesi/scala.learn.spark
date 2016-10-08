package examples.dataguru.steaming

/**
  * ClassName: TraceMessage <br/>
  * Date:16/9/2 <br/>
  *
  * @author yanglei
  * @version 1.0.0
  * @see
  * @since JDK 1.7.79
  */
case class TraceMessage(userid:Int,userIp:String,modelName:String)


object TraceMessage{
  def apply(msg: String):Option[TraceMessage] = {
     val cols =msg.split(" ").toList
    cols match {
      case userid::userip::modelName::Nil=>
        Some( new TraceMessage(userid.toInt,userip,modelName))
      case _=> None
    }
  }
}
