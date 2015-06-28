package authentication;
public class User
{
    private String userID = "Pankaj";
    private String password = "journaldev";
    public User(String usr, String pw)
    {
        userID = usr;
        password = pw;
    }

    public String getUserID()
    {
        return userID;
    }

    public String getPassword()
    {
        return password;
    }
}