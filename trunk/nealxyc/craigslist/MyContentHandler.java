package nealxyc.craigslist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler ;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


public class MyContentHandler implements ContentHandler{	
	
	private boolean inItem ;	
	private boolean inTitle ;	
	private boolean inDescription ;	
	private boolean inDate ;
	
	private int count;
	
	private StringBuffer sb;
	private ArrayList<Item> resources;
	private Item it;
	private SimpleDateFormat sdf;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public MyContentHandler(){
		
		inItem = false;	
		inTitle = false ;	
		inDescription = false ;	
		inDate = false ;
		
		count = 100 ;
		
		sb = new StringBuffer("");
		
		resources = new ArrayList<Item>();

		it = new Item("");
		
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	}
	
	
	public void characters(char[] ch,
            int start,
            int length) throws SAXException {

		if( this.inItem){
			if(this.inTitle || this.inDescription || this.inDate){	
				sb.append(ch, start, length);									
			}
			
		}		
		/*
		System.out.print("Characters:    \"");
		for (int i = start; i < start + length; i++) {
		    switch (ch[i]) {
		    case '\\':
			System.out.print("\\\\");
			break;
		    case '"':
			System.out.print("\\\"");
			break;
		    case '\n':
			System.out.print("\\n");
			break;
		    case '\r':
			System.out.print("\\r");
			break;
		    case '\t':
			System.out.print("\\t");
			break;
		    default:
			System.out.print(ch[i]);
			break;
		    }
		}
		System.out.print("\"\n");
		*/
	}

	public void endDocument() throws SAXException {
		//System.out.println("End");
		
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if( this.inItem ){
			if(qName.equals("title")){
				it.setTitle(sb.toString());
				clearBuffer();
				
				this.inTitle = false ;
			}else if(qName.equals("description")){
				//
				it.setDescription(sb.toString());
				clearBuffer();
				
				this.inDescription = false ;
			}else if(localName.equals("date")){
				
				Date date = null ;
				
				String s = sb.toString();
				String tzs = s.substring(19, s.length());
				tzs = "GMT" + tzs ;
				TimeZone tz = TimeZone.getTimeZone(tzs);
				sdf.setTimeZone(tz);
				s = s.substring(0,19);
				s = s.replaceAll("T", " ");
				
				try {						
					date = this.sdf.parse(s);
				} catch (ParseException e) {					
					logger.warning(e.getMessage());
				}
				
				it.setDate(date);
				clearBuffer();
				
				this.inDate = false ;
			}
			
			if(qName.equals("item")){
				this.inItem = false ;
				this.inDate = false ;
				this.inDescription = false ;
				this.inTitle = false ;
				
				this.resources.add(this.it);
				this.it = new Item("");
				
			}
		}
		

		
		
	}

	public void endPrefixMapping(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void processingInstruction(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void setDocumentLocator(Locator arg0) {
		// TODO Auto-generated method stub
		
	}

	public void skippedEntity(String name) throws SAXException {
		//System.out.println("skip: " + name);
		
	}

	public void startDocument() throws SAXException {
		//System.out.println("Start");
		
	}

	public void startElement(String uri,
            String localName,
            String qName,
            Attributes atts) throws SAXException {
		//this.deepth ++ ;
		//System.out.println(qName + " " + localName + " " + uri);
		
		if( this.inItem ){
			if(qName.equals("title")){
				this.inTitle = true ;
			}else if(qName.equals("description")){
				this.inDescription = true ;
			}else if(localName.equals("date")){
				this.inDate = true ;
			}
		}
		
		if(qName.equals("item")){
			
			if(count-- > 0){
				String v = atts.getValue("rdf:about");
				if( v != null){
					it.setLink(v);
				}
				this.inItem = true ;
			}
			
			
		}
		
	}

	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Item> getResources(){
		return this.resources ;
	}

	/**
	 * Limits the number of <code>Item</code>s read from InputStream.
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	
	private void clearBuffer(){
		sb.delete(0, sb.length());
	}
}
