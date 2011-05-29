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
	
	public AmazonSimpleDB connect() {
		PropertiesCredentials config = null;
		try {
			config = new PropertiesCredentials(
					SimpleDB.class.getResourceAsStream("/aws.properties"));
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
		return new AmazonSimpleDBClient(config);
	}
	
	public void CreateTable(String name) {
		AmazonSimpleDB sdb = connect();
		sdb.createDomain(new CreateDomainRequest(name));	
	}
	
	private Map<String, List<ReplaceableAttribute>> item = 
		new HashMap<String, List<ReplaceableAttribute>>();
	private String receiver = "";
	public void AddAttribute(String item, String name, String value) {
		List<ReplaceableAttribute> attributes = null;
		if((attributes = this.item.get(item)) == null) {
			attributes = new ArrayList<ReplaceableAttribute>();
			this.item.put(item, attributes);
		}
		attributes.add(new ReplaceableAttribute(name, value, true));
		receiver = item;
	}

	public void PrintAttribute() {
		System.out.println(item);
	}

	public void CommitTable(String name) {
		AmazonSimpleDB sdb = connect();
		if(!tableExist(name, sdb)) System.err.println("table " + name + " is not exist!");
		List<ReplaceableItem> items = new ArrayList<ReplaceableItem>();
		for(String i: item.keySet()) {
			List<ReplaceableAttribute> attributes = item.get(i);
			items.add(new ReplaceableItem(i).withAttributes(attributes));
		}
		sdb.batchPutAttributes(new BatchPutAttributesRequest(name, items));
		item = new HashMap<String, List<ReplaceableAttribute>>();
	}

	private boolean tableExist(String domain, AmazonSimpleDB sdb) {
		List<String> domains = sdb.listDomains().getDomainNames();
		if(!domains.contains(domain)) {
			log.error(domain + " doesn't exist!");
			return false;
		}
		return true;
	}

	public List<Item> GetItems(String item, String domain) {
		String selectExpr = "Select * from " + domain + 
			" where itemName() in('" + item + "')";
		SelectRequest request = new SelectRequest(selectExpr);
		AmazonSimpleDB sdb = connect();
		return sdb.select(request).getItems();
	}
	
	public Item GetItem(String item, String domain) {
		List<Item> items = GetItems(item, domain);
		return items.size() != 0? items.get(0) : null;
	}

	public void PrintTables() {
		AmazonSimpleDB sdb = connect();
		System.out.println(sdb.listDomains().getDomainNames());
	}
}
