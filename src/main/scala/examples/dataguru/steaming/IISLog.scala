package examples.dataguru.steaming

/**
  * Created by Lei on 2016-8-28.
  */
/**
  * Created by Administrator on 2016/7/26.
  */
case class IISLog(datetime: String,
                  s_sitename: String,
                  cs_method: String,
                  cs_uri_stem: String,
                  cs_uri_query: String,
                  cs_username: String,
                  c_ip: String,
                  cs_version: String,
                  cs_user_agent: String,
                  cs_cookie: String,
                  cs_referer: String,
                  cs_host: String,
                  sc_status: Int,
                  sc_bytes: Int,
                  time_taken: Int) {

}

object IISLog {
  private val dateReg = "([-,\\d]+)"
  private val timeReg = "([:,\\d]+)"
  private val stringReg = "([^ ]+)"
  private val numberReg = "(\\d+)"

  //#Fields: date time s-sitename cs-method cs-uri-stem cs-uri-query
  // cs-username c-ip cs-version cs(User-Agent) cs(Cookie) cs(Referer)
  // cs-host sc-status sc-bytes time-taken
  private val seq = Seq(
    dateReg, //date
    timeReg, //time
    stringReg, //s-sitename
    stringReg, //cs-method
    stringReg, //cs-uri-stem
    stringReg, //cs-uri-query
    stringReg, //cs-username
    stringReg, //c-ip
    stringReg, //cs-version
    stringReg, //cs(User-Agent)
    stringReg, //cs(Cookie)
    stringReg, //cs(Referer)
    stringReg, //cs-host
    numberReg, //sc-status
    numberReg, //sc-bytes
    numberReg //time-taken
  )
  private val pattern = ("^" + seq.mkString(" ") + "$").r

  def toIISLog(line: String): Option[IISLog] = {
    val matched = pattern.findFirstMatchIn(line)
    matched match {
      case Some(m) =>
        Some(
          IISLog(m.group(1) + " " + m.group(2),
            s_sitename = m.group(3),
            cs_method = m.group(4),
            cs_uri_stem = m.group(5),
            cs_uri_query = m.group(6),
            cs_username = m.group(7),
            c_ip = IISLogReader.getIp(m),
            cs_version = m.group(9),
            cs_user_agent = m.group(10),
            cs_cookie = m.group(11),
            cs_referer = m.group(12),
            cs_host = m.group(13),
            sc_status = m.group(14).toInt,
            sc_bytes = m.group(15).toInt,
            time_taken = m.group(16).toInt))
      case _ => None
    }
  }
}
