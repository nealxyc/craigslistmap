package nealxyc.craigslistmap;

import nealxyc.craigslist.AddressItem;
import nealxyc.craigslist.FactoryNotSetException;
import nealxyc.craigslistmap.gae.ItemFactory;

public class TestStorage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String url = "" ;
		AddressItem.setFactory(new ItemFactory());
		
		AddressItem ai = new AddressItem(url);
		ai.setAddress("Test address");
		
		try {
			
			ai.save();
			ai = null ;
			ai = AddressItem.getByUrl(url);
			
			if(ai != null){
				System.out.println(ai);
			}
		} catch (FactoryNotSetException e) {
			
		}
		
		
	}

}
