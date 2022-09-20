package model;

/**This class is the model for Contacts. */
public class Contact {

    private int contactId;
    private String contactName;
    private String email;

    /**This method is the constructor for new contacts. */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**This method is used for displaying in the combo box. */
    @Override
    public String toString(){
        return contactName;
    }
}
