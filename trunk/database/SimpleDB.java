package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.DeleteDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectRequest;

public class SimpleDB {
	private static Logger log = Logger.getLogger(SimpleDB.class.getName());
	private static SimpleDB db = null;
	
	private SimpleDB(){
	}
	
	public static SimpleDB GetInstance() {
		if(db == null) db = new SimpleDB();
		return db;
	}
	
	/**
	 * Get connect to the Amazon SimpleDB
	 * @return
	 */
	public AmazonSimpleDB connect() {
		PropertiesCredentials config = null;
		try {
			config = new PropertiesCredentials(
					SimpleDB.class.getResourceAsStream("aws.properties"));
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
		return new AmazonSimpleDBClient(config);
	}
	
	/**
	 * Create a new domain
	 * @param name
	 */
	public void CreateTable(String name) {
		AmazonSimpleDB sdb = connect();
		sdb.createDomain(new CreateDomainRequest(name));	
	}
	
	/**
	 * Delete table
	 * @param name
	 */
	public void DeleteTable(String name) {
		AmazonSimpleDB sdb = connect();
		try{
			sdb.deleteDomain(new DeleteDomainRequest(name));
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			log.warn(ex);
		}
		
	}
	
	private Map<String, List<ReplaceableAttribute>> items = 
		new HashMap<String, List<ReplaceableAttribute>>();
	/**
	 * Add new item. If item already exist, the new item will replace the old one.
	 * Must call CommitTable to update the change
	 * @param item
	 * @param name
	 * @param value
	 */
	public void AddItem(String item, String name, String value) {
		List<ReplaceableAttribute> attributes = null;
		if((attributes = items.get(item)) == null) {
			attributes = new ArrayList<ReplaceableAttribute>();
			items.put(item, attributes);
		}
		attributes.add(new ReplaceableAttribute(name, value, true));
	}
	
	/**
	 * Delete item
	 * @param item
	 * @param table
	 */
	public void DeleteItem(String item, String table) {
		AmazonSimpleDB sdb = connect();
		try{
			sdb.deleteAttributes(new DeleteAttributesRequest(table, item));
		} catch(Exception ex) {
			log.warn(ex);
		}
	}
	
	/**
	 * Delete attribute
	 * @param att
	 * @param item
	 * @param table
	 */
	public void DeleteAttribute(String att, String item, String table) {
		AmazonSimpleDB sdb = connect();
		try {
			sdb.deleteAttributes(new DeleteAttributesRequest(table, item)
			.withAttributes(new Attribute().withName(att)));
		} catch(Exception ex) {
			System.err.println(ex);
		}
	}

	/**
	 * Commit update to table
	 * @param name
	 */
	public void CommitTable(String name) {
		AmazonSimpleDB sdb = connect();
		if(!TableExist(name, sdb)) {
			log.error("table " + name + " is not exist!");
			System.out.println("table " + name + " is not exist!");
			return;
		}
		List<ReplaceableItem> items = new ArrayList<ReplaceableItem>();
		for(String i: this.items.keySet()) {
			List<ReplaceableAttribute> attributes = this.items.get(i);
			items.add(new ReplaceableItem(i).withAttributes(attributes));
		}
		try{
			sdb.batchPutAttributes(new BatchPutAttributesRequest(name, items));
		} catch(Exception ex) {
			System.out.println(ex);
			log.warn(ex);
		}
		this.items = new HashMap<String, List<ReplaceableAttribute>>();
	}

	/**
	 * Check whether a table is exist 
	 * @param table
	 * @param sdb
	 * @return
	 */
	public boolean TableExist(String table, AmazonSimpleDB sdb) {
		List<String> domains = null;
		try {
			domains = sdb.listDomains().getDomainNames();
		}catch(Exception ex) {
			System.out.println(ex);
			log.warn(ex);
			return false;
		}
		if(!domains.contains(table)) {
			log.error(table + " doesn't exist!");
			return false;
		}
		return true;
	}

	/**
	 * Get items
	 * @param item
	 * @param table
	 * @return
	 */
	public List<Item> GetItems(String item, String table) {
		String selectExpr = "Select * from '" + table + 
			"' where itemName() in('" + item + "')";
		SelectRequest request = new SelectRequest(selectExpr);
		AmazonSimpleDB sdb = connect();
		return sdb.select(request).getItems();
	}
	
	/**
	 * Get item
	 * @param item
	 * @param table
	 * @return
	 */
	public Item GetItem(String item, String table) {
		List<Item> items = GetItems(item, table);
		return items.size() != 0? items.get(0) : null;
	}
}