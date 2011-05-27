package nealxyc.craigslist;

import org.apache.log4j.Logger;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MyErrorHandler implements ErrorHandler{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());

	public void error(SAXParseException e) throws SAXException {
		// TODO Auto-generated method stub
		logger.warn(e.getMessage());
		//System.out.println(e.getMessage());
		
	}

	public void fatalError(SAXParseException e) throws SAXException {
		// TODO Auto-generated method stub
		logger.warn(e.getMessage());
		//System.out.println(e.getMessage());
	}

	public void warning(SAXParseException e) throws SAXException {
		// TODO Auto-generated method stub
		logger.warn(e.getMessage());
		//System.out.println(e.getMessage());
	}

}
