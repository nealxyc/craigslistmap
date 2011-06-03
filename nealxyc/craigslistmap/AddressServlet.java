package nealxyc.craigslistmap;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import nealxyc.craigslist.AddressItem;
import nealxyc.craigslist.Crawler;
import nealxyc.craigslist.LoggerInit;
import nealxyc.craigslistmap.gae.ItemFactory;

/**
 * Requires a page URL and returns the address in that page. Returns "" if no address found.
 * @author Neal Xiong
 * 
 */
@SuppressWarnings("serial")
public class AddressServlet extends HttpServlet {
	
	
	private static Logger logger = Logger.getLogger(AddressServlet.class.getName());
	static{
		LoggerInit.init();
		AddressItem.setFactory(new ItemFactory());
		logger.setLevel(Level.TRACE);
	}
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		resp.setContentType("text/plain");
		PrintWriter pw = resp.getWriter() ;	
		
		String url = req.getParameter("url") ;	
		String addr = "" ;
		JSONObject obj = new JSONObject();
		
		if(url != null && !url.equals("")){
			
			//check if database has stored this url
			AddressItem it = AddressItem.getByUrl(url);
			if(it != null){
				//database has it
				addr = it.getAddress() ;
//				if(addr.equals("")){
//					//database has it but doesn't have a valid address of this url
//					//fetch from craigslist
//					addr = Crawler.fetchAddr(url);	
//					
//					//save this address into database
//					it.setAddress(addr);
//					it.save();
//				}
				logger.trace("AddressItem read from database: " + it);
			}else{
				//fetch from craigslist
				addr = Crawler.fetchAddr(url);	
				it = new AddressItem(url);
				it.setAddress(addr);
				it.save();
				logger.trace("Saving new AddressItem into database: " + it);
			} 
						
			try {
				obj.put("url", url);
				obj.put("address", addr);
			} catch (JSONException e) {
				
			}
			
			
		}
		pw.println(obj);		
		pw.flush();
		pw.close();
		
	}
}
