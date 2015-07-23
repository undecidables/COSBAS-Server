package biometric;

import org.springframework.data.annotation.Id;

/**
 * Created by Vivian Venter
 */
public class Person {
    @Id
    private String PersonID;

    private String EmployID; //StaffID
    private String Name;
    private String Surname;
    private String Email;
    private String CellNo;

    // Each staff member will have its own dedicated Authentication Key
    // TODO: Add this requirement to the documentation
    private String AutenticationKey;

    //I am not sure as what datatype this should be stored??
    //private Image FaceData;
    //private Image FingerPrintData;


    public Person(String PersonID, String EmployID, String Name, String Surname, String Email, String CellNo ) {
        this.PersonID = PersonID;
        this.EmployID = EmployID;
        this.Name = Name;
        this.Surname = Surname;
        this.Email = Email;
        this.CellNo = CellNo;
    }


    /**
     * Getter and Setter Function
     */

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        PersonID = personID;
    }

    public String getEmployID() {
        return EmployID;
    }

    public void setEmployID(String employID) {
        EmployID = employID;
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

    public String getAutenticationKey() {
        return AutenticationKey;
    }

    public void setAutenticationKey(String autenticationKey) {
        AutenticationKey = autenticationKey;
    }
}
