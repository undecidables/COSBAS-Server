package authentication;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;

import javax.naming.directory.DirContext;
import java.util.List;


public class LDAPTester {

    public static boolean authenticateLDAP(String username, String passwd) {
        try {
            String userDN;
            if (username.matches(".*[0-9].*"))
            {
                userDN = "uid=" + username + ",ou=Students,ou=Computer Science,o=University of Pretoria,c=ZA";
            } else {
                userDN = "uid=" + username + ",ou=Staff,ou=Computer Science,o=University of Pretoria,c=ZA";
            } //End if

            ApplicationContext ctx = new AnnotationConfigApplicationContext(LDAPSettings.class);


            LdapContextSource contextSource = ctx.getBean(LdapContextSource.class);
            contextSource.setUserDn(userDN);
            contextSource.setPassword(passwd);
            contextSource.afterPropertiesSet();

            LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
            ldapTemplate.afterPropertiesSet();

            return authenticate(getDnForUser(username, ldapTemplate), passwd, contextSource);
        }
        catch(Exception e)
        {
            return false;
        }

    }

    private static String getDnForUser(String uid, LdapTemplate ldapTemplate) {
        Filter f = new EqualsFilter("uid", uid);
        List result = ldapTemplate.search("", f.toString(),
                new AbstractContextMapper() {
                    protected Object doMapFromContext(DirContextOperations ctx) {
                        return ctx.getNameInNamespace();
                    }
                });

        if(result.size() != 1) {
            throw new RuntimeException("User not found or not unique");
        }

        return (String)result.get(0);
    }

    public static boolean authenticate(String userDn, String credentials, ContextSource contextSource) {
        DirContext ctx = null;
        try {
            ctx = contextSource.getContext(userDn, credentials);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            // It is imperative that the created DirContext instance is always closed
            LdapUtils.closeContext(ctx);
        }
    }


}