package ContactEntry;

import javafx.beans.property.SimpleStringProperty;

//Contact Entry Management Class
public class ContactEntry {
	 
    private final SimpleStringProperty idName;//id field
    private final SimpleStringProperty firstName;//firstName field
    private final SimpleStringProperty lastName;//lastName field
    private final SimpleStringProperty email;//email field
    private final SimpleStringProperty phone;//phone field
    
    
    //constructor
    public ContactEntry(String idName, String firstName,String lastName,String phone,String email) {
        this.idName = new SimpleStringProperty(idName);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
    }

    //id management functions
    public String getIDName() {
        return idName.get();
    }

    public void setIDName(String idName) {
        this.idName.set(idName);
    }

    //name management functions
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String fName) {
        firstName.set(fName);
    }
    
    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lName) {
        lastName.set(lName);
    }
    
    //phone number management functions
    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    
    //email management functions
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}