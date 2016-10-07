package examples.dataguru.steaming.Geo

import com.maxmind.geoip2.model.CityResponse

/**
  * Created by Administrator on 2016/7/11.
  */
case class IpLocation(
                       countryCode: Option[String],
                       countryName: Option[String],
                       region: Option[String],
                       city: Option[String],
                       postalCode: Option[String],
                       continent: Option[String])

object IpLocation {

  def jDoubleOptionify(jd: java.lang.Double): Option[Double] = Option(jd)

  def apply(omni: CityResponse): IpLocation = new IpLocation(
    if (omni.getCountry != null) Option(omni.getCountry.getIsoCode) else None,
    if (omni.getCountry != null) Option(omni.getCountry.getName) else None,
    if (omni.getMostSpecificSubdivision != null) Option(omni.getMostSpecificSubdivision.getName) else None,
    if (omni.getCity != null) Option(omni.getCity.getName) else None,
    if (omni.getPostal != null) Option(omni.getPostal.getCode) else None,
    if (omni.getContinent != null) Option(omni.getContinent.getName) else None
  )
}