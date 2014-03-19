package models;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class EmailListModel extends DefaultListModel<String> {
	
	
	
	
	/**
	 * Create a new empty list
	 */
	public EmailListModel() {
		super();
	}
	
	
	/**
	 * Copy constructor
	 * 
	 * @param oldList
	 */
	public EmailListModel(EmailListModel oldList) {
		super();
		if (oldList != null) for (Object email : oldList.toArray()){
			this.addElement((String) email);
		}
	}
	
	
	@Override
	public void addElement(String email){
		if (! this.contains(email)){
			super.addElement(email);
		}
	}
	
	
	
}
