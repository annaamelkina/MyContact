package ContactManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ContactEntry.ContactEntry;

//contacts management class
public class ContactManager {
	 private final List<ContactEntry> contacts = new ArrayList<>();//field for whole contacts
	
	 //constructor(initiate default contact contents)
	 public ContactManager()
	 {
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream("ContactDB.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		 String strLine;

		 //Read File Line By Line
		 try {
			while ((strLine = br.readLine()) != null)   {
			   // Print the content on the console
			   System.out.println (strLine);
			   String[] contact_entry_data = strLine.split(" ");
			   addContact(new ContactEntry(contact_entry_data[0],contact_entry_data[1], contact_entry_data[2], contact_entry_data[3], contact_entry_data[4]));
			 }

			 //Close the input stream
			 br.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	     
	 }
	 
	 //add new contact
	 public Long addContact(ContactEntry contact) {
	     contacts.add(contact);
	     return (long) 0;
	}
	
	 //edit specified contact
	 public void editContact(int index, String firstName,String lastName,String phone,String email) {
	     contacts.get(index).setFirstName(firstName);
	     contacts.get(index).setLastName(lastName);
	     contacts.get(index).setPhone(phone);
	     contacts.get(index).setEmail(email);
	}
	 
	 //search by field 
	 public ContactEntry searchContacts(String firstName,String SecondName,String phoneValue,String emailValue)
	 {
		 ContactEntry ce = null;
		 
		 for(ContactEntry contact : contacts) {
			 
			 String fname = contact.getFirstName();
			 String lname = contact.getLastName();
			 String pv = contact.getPhone();
			 String emv = contact.getEmail();
			 System.out.println(fname+"  "+firstName);
	         if(firstName != "" && fname.equals(firstName))
	        	 ce = contact;
	         if(SecondName != "" && lname.equals(SecondName))
	        	 ce = contact;
	         if(phoneValue != "" && pv.equals(phoneValue))
	        	 ce = contact;
	         if(emailValue != "" && emv.equals(emailValue))
	        	 ce = contact;
	     }
		 return ce;
		 
	 }
	 //delete specified contact
	 public void deleteContact(int index)
	 {
		 contacts.remove(index);
		 System.out.println(contacts.size());
	 }
	 
	 //generate contact id in random
	 public Long generateContactId() {
		 Long contactId = Math.round(Math.random() * 1000 + System.currentTimeMillis());
		 while(getContact(contactId) != null) {
	            contactId = Math.round(Math.random() * 1000 + System.currentTimeMillis());
	        }
	     return contactId;   
	}
	 
	 //get specified contact entry
	 public ContactEntry getContact(Long contactId) {
		 for(ContactEntry contact : contacts) {
			 Long curContactID = Long.valueOf(contact.getIDName()); 
	         if(curContactID == contactId) {
	                return contact;
	         }
	     }
	     return null;   
	} 
	
	//retrive whole contact entries
	public List<ContactEntry> retrieveContactArray()
	{
		return contacts;
	}
	
}
