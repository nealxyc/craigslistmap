package nealxyc.craigslist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class RSSReader {
	
	private XMLReader xr ;
	
	private MyContentHandler mch ;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public RSSReader(){
		
		mch = new MyContentHandler();
		try {
			xr = XMLReaderFactory.createXMLReader();
			xr.setContentHandler(mch);
			xr.setErrorHandler(new MyErrorHandler());
			
		} catch (SAXException e) {
			logger.warn(e.getMessage());
		}
		
	}
	
	public ArrayList<Item> parse(InputStream is) throws IOException{
		
		try{
			xr.parse(new InputSource(is));			
			
			
		} catch (SAXException e) {
			
			logger.warn(e.getMessage());
		}
		
		return mch.getResources();
	}
	
	public ArrayList<Item> parse(InputStream is, int count) throws IOException{
		
		this.mch.setCount(count);
		
		try{
			xr.parse(new InputSource(is));			
			
			
		} catch (SAXException e) {
			logger.warn(e.getMessage());
		}
		
		return mch.getResources();
	}
	
	/**
	 * 从InputStream找到并返回address信息。采用很简单的关键字查找法。
	 * @param is 输入源
	 * @return String 找不到则返回空字符串
	 * @throws IOException 如果发生IO异常
	 */
	public String parseHtml(InputStream is)throws IOException{	
		
	
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String s = br.readLine();
		
		String q = "" ;
		
		//Key Word
		String kw = "http://maps.google.com";
		
		while(s != null ){
			
			if(s.contains(kw.subSequence(0, kw.length())) && s.indexOf("http://maps.google.com/?q=") >0 && s.indexOf("\">google map") > 0){
				q = s.substring(s.indexOf("http://maps.google.com/?q=") + 26 , s.indexOf("\">google map"));
				break ;
			}
			
			s = br.readLine();
		}
		
		q = java.net.URLDecoder.decode(q , "utf-8");
		
		if( q.contains("loc:")){
			q = q.replace("loc:", "");
			q = q.trim();
		}
		
		return q ;
			
		
	}
	
	/**
	 * Read the RSS link of a specific page. This page is passed in as an InputStream
	 * @param is
	 * @return A String of RSS link. "" if not found.
	 * @throws IOException
	 */
	public String getRSSLink(InputStream is) throws IOException{
		
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
}
