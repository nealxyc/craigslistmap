package database;

import java.util.Date;
import java.util.List;

import com.amazonaws.services.simpledb.model.Attribute;

import database.SimpleDB;

public class Item extends nealxyc.craigslist.Item{

	public Item(String l) {
		super(l);
	}
	
	public Item(nealxyc.craigslist.Item item) {
		super(item);
	}
	
	/**
	 * Store this Item into database. Insert or update.
	 */
	public void save(){
		if(this.date == null) date = new Date();
		SimpleDB.GetInstance().AddItem(link, "address", address);
		SimpleDB.GetInstance().AddItem(link, "title", title);
		SimpleDB.GetInstance().AddItem(link, "description", description);
		SimpleDB.GetInstance().AddItem(link, "date", "" + date.getTime());
		SimpleDB.GetInstance().CommitTable("HouseInfo");
	}
	/**
	 * Get the Item with a certain link from database.
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
