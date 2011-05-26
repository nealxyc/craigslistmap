package nealxyc.craigslist;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.neallab.craigslistmap.MoreInfoItem;
import org.neallab.craigslistmap.RSSReader;

public class Crawler {

	private static Logger logger = Logger.getLogger(Crawler.class.getName());
//	public static 
	
	private void fetchRSS(String pageLink, boolean search, String startStr, RSSReader rr, PrintWriter pw){
		
		String urlStr = null ;
		JSONObject obj = new JSONObject();		
		JSONArray arr = new JSONArray();
		
		try{
			if( pageLink != null && !pageLink.equals("") && checkPageLink(pageLink) ){
				
				//为了sfbay以外的区域改的
				readArea(pageLink);
				
				java.net.URL url3 = new URL(pageLink);		
				HttpURLConnection conn3 = (HttpURLConnection)url3.openConnection() ;
				conn3.setRequestMethod("GET");
				
				conn3.connect();
				InputStream is3 = new BufferedInputStream(conn3.getInputStream());
				urlStr = rr.getRSSLink(is3);
				is3.close();
				
				search = testSearchRSSlink(urlStr);
				urlStr = checkSearchRSSlink(urlStr, search, startStr);				
				
			}
			
			logger.info("Fetching RSS from "+ urlStr);
			//test
			System.out.println("Fetching RSS from "+ urlStr +"\npageLink=" + pageLink);
			
			if( urlStr != null){
				java.net.URL url = new URL(urlStr);		
				HttpURLConnection conn = (HttpURLConnection)url.openConnection() ;
				conn.setRequestMethod("GET");				
				conn.connect();
				
				InputStream is = new BufferedInputStream(conn.getInputStream());				
				ArrayList<MoreInfoItem> miiList = rr.parse(is);
				if( miiList != null ){
					for( MoreInfoItem mii : miiList){
						JSONObject it = new JSONObject();
						
						it.put("link", mii.getLink());
						it.put("address","null" );
						it.put("title", mii.getTitle());
						it.put("description", mii.getDescription());
						it.put("date", mii.getDate().getTime());
						arr.put(it);
					}
					
					obj.put("items", arr);
					if( arr.length() > 0){
						obj.put("success", true);
					}else{
						obj.put("success", false);
					}
					
				}
				
			}else{
								
				obj.put("success", false);
			}			
			
		}catch(JSONException jsone){
			logger.warn(jsone.getMessage());
		}catch(IOException ioe){
			logger.warn(ioe.getMessage());
		}
		
		pw.println(obj);
		
	}
	

	
	/**
	 * Read the RSS link of a specific page. This page is passed in as an InputStream
	 * @param is
	 * @return A String of RSS link. "" if not found.
	 * @throws IOException
	 */
	public static String getRSSLink(InputStream is) throws IOException{
		
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
	
	private static String readArea(String urlStr){
		String s = urlStr.substring(urlStr.indexOf("http://") + 7 , urlStr.indexOf(".craigslist.org"));
		if(s != null && !s.equals("")){
			this.area = s ;			
		}
	}

}
