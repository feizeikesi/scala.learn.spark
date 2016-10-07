package examples.dataguru.steaming.Geo

import java.io.{File, FileInputStream, InputStream}
import java.net.InetAddress

import com.maxmind.geoip2.DatabaseReader
import com.maxmind.geoip2.model.CityResponse
import com.twitter.util.{LruMap, SynchronizedLruMap}

/**
  * Created by Administrator on 2016/7/11.
  */
class MaxMindIpGeo(dbInputStream: InputStream,
                   lruCache: Int = 1000,
                   synchronized: Boolean = false,
                   postFilterIpLocation: MaxMindIpGeo.IpLocationFilter = MaxMindIpGeo.unitFilter){

  protected val maxmind = new DatabaseReader.Builder(dbInputStream).build

  def getInetAddress(address: String): Option[InetAddress] = {
    val validNum = """(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])"""
    val dot = """\."""
    val validIP = (validNum + dot + validNum + dot + validNum + dot + validNum).r

    try {
      address match {
        case validIP(_, _, _, _) => Some(InetAddress.getByAddress(address.split('.').map(_.toInt.toByte)))
        case _ => Some(InetAddress.getByName(address))
      }
    } catch {
      case _: Throwable => None
    }
  }

  def chooseAndCreateNewLru = {
    if (synchronized)
      new SynchronizedLruMap[String, Option[IpLocation]](lruCache)
    else
      new LruMap[String, Option[IpLocation]](lruCache)
  }

  private val lru = if (lruCache > 0) chooseAndCreateNewLru else null

  def getLocationWithLruCache(address: String): Option[IpLocation] = {
    lru.get(address) match {
      case Some(loc) => loc
      case None => {
        val loc = getLocationWithoutLruCache(address)
        lru.put(address, loc)
        loc
      }
    }
  }

  /**
    *
    * @param address
    * @return
    */
  private def getLocationFromDB(address: String): Option[CityResponse] = try {
    getInetAddress(address).map(maxmind.city(_))
  } catch {
    case _: Throwable => None
  }

  /**
    *
    */
  val getLocation: String => Option[IpLocation] = if (lruCache > 0) getLocationWithLruCache else getLocationWithoutLruCache

  /**
    *
    * @param address
    * @return
    */
  def getLocationWithoutLruCache(address: String): Option[IpLocation] = getLocationFromDB(address)
    .map(IpLocation(_)).flatMap(o => postFilterIpLocation(o))
}

object MaxMindIpGeo {
  type IpLocationFilter = IpLocation => Option[IpLocation]
  val unitFilter: IpLocationFilter = ipLocation => Some(ipLocation)

  def apply(dbFile: String, lruCache: Int = 10000, synchronized: Boolean = false, postFilterIpLocation: IpLocationFilter = unitFilter):MaxMindIpGeo = {
    new MaxMindIpGeo(new FileInputStream(new File(dbFile)), lruCache, synchronized, postFilterIpLocation)
  }

}