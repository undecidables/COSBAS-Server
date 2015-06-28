package authentication;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;

public class AppAuthenticationProvider  {

    public boolean authenticate(String username, String password){
        try {
            // Setup the LDAP client (normally done via Spring context file).
            LdapContextSource contextSource = new LdapContextSource();
            contextSource.setUrl("ldap://adserver.mycompany.com:3268");
            contextSource.setBase("DC=AD,DC=MYCOMPANY,DC=COM");
            contextSource.setUserDn("readonlyuser@ad.mycompany.com");
            contextSource.setPassword("password1");
            contextSource.afterPropertiesSet();

            LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
            ldapTemplate.afterPropertiesSet();

            // Perform the authentication.
            Filter filter = new EqualsFilter("sAMAccountName", "mpilone");

            boolean authed = ldapTemplate.authenticate("OU=CorpUsers",
                    filter.encode(),
                    "user-entered-password");

            // Display the results.
            System.out.println("Authenticated: " + authed);
            return true;
        }
        catch(Exception e)
        {

            e.printStackTrace();
            return false;
        }

    }

}