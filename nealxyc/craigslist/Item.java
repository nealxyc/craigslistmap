package nealxyc.craigslist;

import java.util.Date;

public class Item {

	private String link ;	
	private String address ;
	private Date date ;
	private String title ;	
	private String description ;
	
	
	public Item(String link){
		setLink(link);
	}
	
	public Item(Item item){
		setLink(item.getLink());
		setAddress(item.getAddress());
		setDate(item.getDate());
		
		
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
	
	/**
	 * Store this Item into database. Insert or update.
	 */
	public void save(){
		
	}
	/**
	 * Retreive the Item with a certain link from database.
	 * @param l
	 * @return Null if not found in database
	 */
//	public static Item getByLink(String l){
//		
//	}
	
	
}
