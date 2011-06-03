package nealxyc.craigslistmap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.*;

import org.json.JSONArray;

import nealxyc.craigslist.Crawler;

/**
 * Requires a URL and optional result count from request and returns a list of search result in JSON format to response.
 * @author Neal Xiong
 * 
 */
@SuppressWarnings("serial")
public class SearchServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		resp.setContentType("text/plain");
		PrintWriter pw = resp.getWriter() ;	
		
		String link = req.getParameter("url") ;
		String countStr = req.getParameter("count") ;
		int count = countStr == null? 25 : Integer.parseInt(countStr) ;
		int index = 0 ;
		ArrayList<nealxyc.craigslist.Item> result = new ArrayList<nealxyc.craigslist.Item>();
		
		if(link != null && !link.equals("") ){
			ArrayList<nealxyc.craigslist.Item> list ;
			
			fetchLoop:
			while(index < count){
				list = Crawler.fetchItemFromPage(link, index);
				
				if(list != null){
					for(nealxyc.craigslist.Item it : list){
						result.add(it);
						index ++ ;
						if(index >= count){
							break fetchLoop;
						}
					}
				}else{
					break ;
				}
			}
			
		}

		JSONArray array = new JSONArray();
		for(nealxyc.craigslist.Item it: result){
			array.put(it.toJSON());
		}
		
		pw.println(array);
		pw.flush();
		pw.close();
		
	}
}
