package webapp

import org.grails.plugin.geoip.GeoIpService
import org.codehaus.groovy.grails.validation.routines.InetAddressValidator

class GetIPController {
	GeoIpService geoIpService
	def defaultLocation = [latitude: 40.7142, longitude: 74.0064, countryName: "United States", countryCode : "US"]
	def testIPGoogleIe = "159.134.168.109"
	def bbcUK = "212.58.246.54"
	def googleDNS = "8.8.8.8"
	
    def index() {

		def ip = request.getRemoteAddr()		
		render "<br>"+"Test IP (Machine code is running on):"+"<br>"
        showLocation(ip)
        render "<br><br>"+"Test IP (www.google.ie):"+"<br>"
        showLocation(testIPGoogleIe)
        render "<br><br>"+"Test IP (www.bbc.co.uk):"+"<br>"
        showLocation(bbcUK)
        render "<br><br>"+"Test IP (Google's DNS):"+"<br>"
        showLocation(googleDNS)
	}
	
	/**
	 * A function that displays the Country for any given IP address.
	 * This should work for real IP addresses, but will not work if
	 * it is run on a local host.
	 * @return
	 */
	def showLocation(String ip){
		def valid = InetAddressValidator.getInstance().isValidInet4Address(ip)
		//getFileLocation()
		// This line causes an error, I'm not sure why, but it always returns NULL
		def location = geoIpService.getLocation(ip)

		/**
		 * If it is a valid IP, check to see if the location is not null, otherwise
		 * display some information about the IP.
		 */
		if(valid){
			if(location == null) render "Sorry location was null"
			else if(location.city != null) render "Country: "+location.countryName + " , City: "+location.city
			else render "Country: " + location.countryName
		}
		/**
		 * Otherwise the IP is invalid, so display the DEFAULT location.
		 */
		else{
			render "Sorry, invalid IP. <br>"
			render "DEFAULT: " + defaultLocation.countryName
		}
	}
}
