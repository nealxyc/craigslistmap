package database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.xerox.amazonws.sdb.Domain;
import com.xerox.amazonws.sdb.ItemAttribute;
import com.xerox.amazonws.sdb.ListDomainsResult;
import com.xerox.amazonws.sdb.SDBException;
import com.xerox.amazonws.sdb.SimpleDB;

public class DatabaseHandler {
	private String username;
	private String password;
	private List<ItemAttribute> items = new ArrayList<ItemAttribute>();
	
	public DatabaseHandler() {
		if(loadConfig() != Status.OK) System.exit(-1);
	}
	
	/**
	 * Get connect with Amazon simpleDB
	 * @return 
	 */
	private SimpleDB connect() {
		return new SimpleDB(username, password, true);
	}
	
	/**
	 * Create a new table
	 * @param name
	 * @return
	 */
	public Status createTable(String name) {
		Status status = Status.ERROR;
		SimpleDB sdb = connect();
		try{
			sdb.createDomain(name);
			status = Status.OK;
		}catch(SDBException ex){
			status = Status.ERROR;
		}
		return status;
	}
	
	/**
	 * Delete a existing table
	 * @param name
	 */
	public void deleteTable(String name) {
		SimpleDB sdb = connect();
		try {
			sdb.deleteDomain(name);
		} catch (SDBException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	/**
	 * Get all tables in Amazon SimpleDB
	 * @return
	 */
	public List<Domain> getTables() {
		SimpleDB sdb = connect();
		List<Domain> domains = new ArrayList<Domain>();
		try{
			ListDomainsResult d = sdb.listDomains();
			domains = d.getDomainList();
		} catch(SDBException ex) {
			System.err.println(ex.getMessage());
		}
		return domains;
	}
	
	public void printTables() {
		List<Domain> domains = getTables();
		for(Domain d: domains) 
			System.out.println(d.getName());
	}
	
	/**
	 * Loads the database configuration
	 * @return
	 */
	private Status loadConfig() {
		Properties config = new Properties();
		Status status = Status.ERROR;
		try {
			config.load(new FileReader("aws.properties"));
			this.username = config.getProperty("awsAccessID");
			this.password = config.getProperty("awsSecretKey");
			status = Status.OK;
		} catch (FileNotFoundException e) {
			status = Status.CONFIG_NOT_FOUND;
		} catch (IOException e) {
			status = Status.ERROR;
		}
		return status;
	}
	
	public static void main(String[] args) {
		DatabaseHandler db = new DatabaseHandler();
		db.createTable("Testing");
		db.printTables();
		db.deleteTable("Testing");
		db.printTables();
	}
}
