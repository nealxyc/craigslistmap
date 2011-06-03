package nealxyc.craigslistmap.gae;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class StorageAddressItem extends nealxyc.craigslist.AddressItem {
	
	@PrimaryKey
	@Persistent
	protected String url ;	
	@Persistent
	protected String address ;
	@Persistent
	protected Date date ;
	
	public StorageAddressItem(nealxyc.craigslist.AddressItem ai){
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
}
