package nealxyc.craigslist;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {

	protected String link ;	
	protected String address ;
	protected Date date ;
	protected String title ;	
	protected String description ;
	
	protected Item() {
		link = "";
		address = "";
		title = "";
		description = "";
		date = new Date();
	}
	
	public Item(String link){
		this();
		setLink(link);
	}
	
	public Item(Item item){
		
		setLink(item.getLink());
		setAddress(item.getAddress());
		setDate(item.getDate());
		setDescription(item.getDescription());
		setTitle(item.getTitle());
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
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
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
//	/**
//	 * Store this Item into database. Insert or update. Must override.
//	 */
//	public void save(){
//		
//	}
//	
//	/**
//	 * Delete this Item from database. Must override.
//	 */
//	public void delete(){
//		
//	}
//	/**
//	 * Get the Item with a certain link from database. Must override.
//	 * @param l
//	 * @return Null if not found in database
//	 */
//	public static Item getByLink(String l){
//		return null ;
//	}
	
	public String toString(){
		return toJSON().toString();
		
	}
	
	public JSONObject toJSON(){
		
		JSONObject obj = new JSONObject();
		try {
			obj.put("link", getLink());
			obj.put("address", getAddress());
			obj.put("date", getDate().getTime());
			obj.put("title", getTitle());
			obj.put("description", getDescription());
		} catch (JSONException e) {
			
		}
		
		return obj ;
	}
	

}
