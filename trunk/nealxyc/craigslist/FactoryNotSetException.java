package nealxyc.craigslist;

import java.io.IOException;

public class FactoryNotSetException extends IOException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1702003631413029824L;

	public FactoryNotSetException(){
		super("AddressItemFacotry is not set.");
	}
}
