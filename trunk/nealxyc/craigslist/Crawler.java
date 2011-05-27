package nealxyc.craigslist;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.log4j.Logger;


public class Crawler {

	private static Logger logger = Logger.getLogger(Crawler.class.getName());
	
	/**
	 * Returns an ArrayList of <code>Item</code> which read from <code>pageLink</code>. Note that at this point the <code>Item</code>s have no address information yet.<br>
	 * You need to call 
	 * @param pageLink
	 * @param fromIndex
	 * @return
	 */
	public static ArrayList<Item> fetchItemFromPage(String pageLink,  String fromIndex){
		
		RSSReader rr = new RSSReader();
		ArrayList<Item> itemList = null ;
		boolean isSearch;
		
		String rss = null ;
		String area ;
		
		try{
			if( pageLink != null && !pageLink.equals("") ){
				
				//为了sfbay以外的区域改的
				area = readArea(pageLink);
				
				java.net.URL url3 = new URL(pageLink);		
				HttpURLConnection conn3 = (HttpURLConnection)url3.openConnection() ;
				conn3.setRequestMethod("GET");
				
				conn3.connect();
				InputStream is3 = new BufferedInputStream(conn3.getInputStream());
				rss = getRSSLink(is3);
				is3.close();
				
				isSearch = isSearch(rss);
				rss = reformRSSlink(rss, isSearch, fromIndex, area);				
				
			}
			
			if( rss != null){
				logger.info("Fetching RSS from "+ rss +"\npageLink=" + pageLink);
				java.net.URL url = new URL(rss);		
				HttpURLConnection conn = (HttpURLConnection)url.openConnection() ;
				conn.setRequestMethod("GET");				
				conn.connect();
				
				InputStream is = new BufferedInputStream(conn.getInputStream());				
				itemList = rr.parse(is);
				
			}		
			
		} catch(IOException ioe){
			logger.warn(ioe.getMessage());
		}
	
		return itemList ;
		
	}
	
	/**
	 * Returns the address information on an item page. 
	 * @param pageLink
	 * @return The address. "" if not found.
	 */
	public static String fetchAddr(String pageLink){
		
		
		String addr = "" ;
		RSSReader rr = new RSSReader();
		
		try{
			java.net.URL url2 = new URL(pageLink);		
			HttpURLConnection conn2 = (HttpURLConnection)url2.openConnection() ;
			conn2.setRequestMethod("GET");						
			conn2.connect();
			InputStream is2 = new BufferedInputStream(conn2.getInputStream());
			
			//如果无法查找到address，则返回值为""
			addr = rr.parseHtml(is2);
			is2.close();
			
			
		}catch(IOException ioe){
			logger.warn(ioe.getMessage());
		}	
		
		return addr ;
	}

	
	/**
	 * Read the RSS link of a specific page. The page is passed in as an InputStream
	 * @param is
	 * @return A String of RSS link. "" if not found.
	 * @throws IOException
	 */
	private static String getRSSLink(InputStream is) throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String s = br.readLine();
		
		String q = "" ;
		
		//Key Word
		String kw = ">RSS</a>";
		
		while(s != null ){
			
			if(s.contains(kw.subSequence(0, kw.length())) && s.indexOf("<a class=\"l\" href=\"") >0 && s.indexOf("\">RSS</a>") > 0){
				q = s.substring(s.indexOf("<a class=\"l\" href=\"") + 19 , s.indexOf("\">RSS</a>"));
				break ;
			}			
			s = br.readLine();
		}		
		
		return q ;
	}
	
	/**
	 * Returns the area code. For example sfbay from sfbay.craigslist.org
	 * @param urlStr
	 * @return null if not found
	 */
	private static String readArea(String urlStr){
		
		String s = urlStr.substring(urlStr.indexOf("http://") + 7 , urlStr.indexOf(".craigslist.org"));
		if(s != null && !s.equals("")){
			return s ;			
		}
		
		return null ;
	}
	
	
	private static String reformRSSlink(String urlStr, boolean search, String fromIndex, String area) {
		
		if(urlStr != null && !urlStr.equals("")){
			if( search ){
				if(fromIndex != null && !fromIndex.equals("")){
					urlStr += "&s=" + fromIndex ;
				}
				
			}else{
				return "http://"+ area + ".craigslist.org" + urlStr ;
			}
		}
		
		return urlStr ;		
	}
	
	/**
	 * Return whether this RSS link is a search link.
	 * @param urlStr
	 * @return
	 */
	private static boolean isSearch(String urlStr) {
		
		if(urlStr != null && !urlStr.equals("")){
			if( urlStr.startsWith("/")){
				return false ;
			}			
		}
		
		return true ;		
	}

}
