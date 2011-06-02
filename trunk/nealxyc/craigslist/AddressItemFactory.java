package nealxyc.craigslist;

public interface AddressItemFactory {

	public AddressItem getByUrl(String url);
	
	public void makeItemPersistent(AddressItem ai);
	
	public void removeItemPersistent(AddressItem ai);
	
}
