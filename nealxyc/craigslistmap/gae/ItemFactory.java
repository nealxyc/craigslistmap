package nealxyc.craigslistmap.gae;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import nealxyc.craigslist.AddressItemFactory;

public class ItemFactory implements AddressItemFactory {

	@Override
	public nealxyc.craigslist.AddressItem getByUrl(String url) {
	
		StorageAddressItem ai = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			ai = pm.getObjectById(StorageAddressItem.class, url);
		}catch(JDOObjectNotFoundException e){
			ai = null ;
		}			
		pm.close();
		
		return ai ;		
	}


	@Override
	public void makeItemPersistent(nealxyc.craigslist.AddressItem ai) {
		
		StorageAddressItem it ;
		if(ai instanceof StorageAddressItem){
			it = (StorageAddressItem)ai ;
		}else{
			it = new StorageAddressItem(ai) ;
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(it);		
		pm.close();
	}

	@Override
	public void removeItemPersistent(nealxyc.craigslist.AddressItem ai) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(ai);
		pm.close();		
	}

}
