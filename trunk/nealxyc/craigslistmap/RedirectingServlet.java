package nealxyc.craigslistmap;

import java.io.IOException;

import javax.servlet.http.*;

/**
 * 
 * @author Neal Xiong
 * 
 */
@SuppressWarnings("serial")
public class RedirectingServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String http = req.isSecure()? "https://" :"http://";
		resp.sendRedirect(http + "3.craigslistmap.appspot.com");
		
	}
}
