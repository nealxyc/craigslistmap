package nealxyc.craigslist;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class AddressItem {
	protected String url ;	
	protected String address ;
	
	private Date date ;//Stores when this address is fetched. It has nothing to do with the date on Craigslist post.
	protected static AddressItemFactory factory ;
	
	protected AddressItem() {
		setUrl("");;
		setAddress("");
		setDate(new Date());
	}
		
	public AddressItem(String u){
		this();
		setUrl(u);
	}
	
	public AddressItem(AddressItem ai){
		this.setUrl( ai.getUrl()) ;
		this.setAddress(ai.getAddress());
		this.setDate(ai.getDate());
	}
	
	/**
	 * @param link the link to set
	 */
	public void setUrl(String link) {
		this.url = link;
	}

	/**
	 * @return the link
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Store this <code>AddressItem</code> into database. Insert or update.
	 * @throws FactoryNotSetException If <code>setFactory()</code> is not called before calling this method.
	 */
	public void save() throws FactoryNotSetException{
		if(factory == null)
			throw new FactoryNotSetException();
		factory.makeItemPersistent(this);
		
	}
	
	/**
	 * Delete this <code>Item</code> from database. Throws <code>FactoryNotSetException</code> if <code>setFactory()</code> is not called before this method.
	 * @throws FactoryNotSetException If <code>setFactory()</code> is not called before calling this method.
	 */
	public void delete() throws FactoryNotSetException{
		if(factory == null)
			throw new FactoryNotSetException();
		factory.removeItemPersistent(this);
		
	}
	
	/**
	 * Get the <code>AddressItem</code> with a specified url from factory(database). Throws <code>FactoryNotSetException</code> if <code>setFactory()</code> is not called before this calling method.
	 * @param u
	 * @return
	 * @throws FactoryNotSetException If <code>setFactory()</code> is not called before calling this method.
	 */
	public static AddressItem getByUrl(String u) throws FactoryNotSetException{
		if(factory == null)
			throw new FactoryNotSetException();
		return factory.getByUrl(u);
		
	}
	
	/**
	 * Set the <code>AddressItemFactory</code> of <code>AddressItem</code>.
	 * @param f
	 */
	public static void setFactory(AddressItemFactory f) {
		if(factory == null)
			factory = f ;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	
	public String toString(){
		return toJSON().toString();
		
	}
	
	public JSONObject toJSON(){
		
		JSONObject obj = new JSONObject();
		try {
			obj.put("url", getUrl());
			obj.put("address", getAddress());
			obj.put("date", getDate().getTime());
		} catch (JSONException e) {
			
		}
		
		return obj ;
	}
}
