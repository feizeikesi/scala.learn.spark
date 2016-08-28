package examples.dataguru.steaming

/**
  * Created by Lei on 2016-8-28.
  */
import scala.util.Try
import scala.util.matching.Regex.Match

object IISLogReader {

  private val getIpFormCookieReg = ".*url_param_userip=([^;]+).*".r

  def getIp(m: Match): String = {
    m.group(11) match {
      case getIpFormCookieReg(ip) => ip
      case _ => m.group(8)
    }
  }
}
