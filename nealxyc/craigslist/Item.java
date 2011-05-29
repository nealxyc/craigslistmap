package nealxyc.craigslist;

import java.util.Date;
import java.util.List;

import com.amazonaws.services.simpledb.model.Attribute;

import database.SimpleDB;

public class Item {

	private String link ;	
	private String address ;
	private Date date ;
	private String title ;	
	private String description ;
	
	private Item() {
		link = "";
		address = "";
		title = "";
		this.description = "";
	}
	
	public Item(String link){
		this();
		setLink(link);
	}
	
	public Item(Item item){
		this();
		setLink(item.getLink());
		setAddress(item.getAddress());
		setDate(item.getDate());
		this.setDescription(item.getDescription());
		this.setTitle(item.getTitle());
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
		if(date == null) date = new Date();
		SimpleDB.GetInstance().AddAttribute(link, "address", address);
		SimpleDB.GetInstance().AddAttribute(link, "title", title);
		SimpleDB.GetInstance().AddAttribute(link, "description", description);
		SimpleDB.GetInstance().AddAttribute(link, "date", "" + date.getTime());
		SimpleDB.GetInstance().CommitTable("HouseInfo");
	}
	/**
	 * Retreive the Item with a certain link from database.
	 * @param l
	 * @return Null if not found in database
	 */
	public static Item getByLink(String l){
		Item item = new Item(l);
		List<Attribute> attributes;
		try {
			attributes = SimpleDB.GetInstance().GetItem(l, "HouseInfo").getAttributes();
		} catch (NullPointerException e) {
			return null;
		}
		for(Attribute a: attributes) {
			if(a.getName().equals("address")) {
				item.setAddress(a.getValue());
			} else if(a.getName().equals("date")) {
				item.setDate(new Date(Long.parseLong(a.getValue())));
			} else if(a.getName().equals("title")) {
				item.setTitle(a.getValue());
			} else if(a.getName().equals("description")) {
				item.setDescription(a.getValue());
			}
		}
		return item;
	}
}
