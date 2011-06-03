package nealxyc.craigslistmap;

import java.io.IOException;
import javax.servlet.http.*;

import nealxyc.craigslist.AddressItem;
import nealxyc.craigslist.FactoryNotSetException;
import nealxyc.craigslistmap.gae.ItemFactory;

@SuppressWarnings("serial")
public class StorageStatusServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String url = "http://test" ;
		AddressItem.setFactory(new ItemFactory());
		
		AddressItem ai = new AddressItem(url);
		ai.setAddress("Test address");
		
		try {
			System.out.println(ai);
			ai.save();
			ai = null ;
			ai = AddressItem.getByUrl(url);
			
			if(ai != null){
				resp.getWriter().println(ai);
			}
		} catch (FactoryNotSetException e) {
			
		}
		
	}
}
