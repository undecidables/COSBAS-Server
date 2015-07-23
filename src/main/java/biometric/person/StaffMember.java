package biometric.person;

/**
 * @author  Vivian Venter
 */
public class StaffMember extends BiometricUser{

    private String Name;
    private String Surname;
    private String Email;
    private String CellNo;

    // Each staff member will have its own dedicated Authentication Key
    // TODO: Add this requirement to the documentation
    private String AuthenticationKey;

    public StaffMember(String userID, String Name, String Surname, String Email, String CellNo) {
        this.userID = userID;
        this.Name = Name;
        this.Surname = Surname;
        this.Email = Email;
        this.CellNo = CellNo;
    }


    /**
     * Getter and Setter Function
     */
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCellNo() {
        return CellNo;
    }

    public void setCellNo(String cellNo) {
        CellNo = cellNo;
    }

    public String getAuthenticationKey() {
        return AuthenticationKey;
    }

    public void setAuthenticationKey(String autenticationKey) {
        AuthenticationKey = autenticationKey;
    }


}