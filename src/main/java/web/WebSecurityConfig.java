package web;

import authentication.LDAPSettings;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * This class is a Spring Security configuration file. This class is responsible for authenticating a user via LDAP and automatically integrates with the Spring MVC.
 */
@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * This is the basic navigation/security configuration. Currently, it's setup so that all links which have login, or nav, or root do not need to authenticate to navigate. Any other request
     * will require the user to be logged in.
     * Note: We permit to /login so that we don't infinitely get asked to log in when we try to log in.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {



        http
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll() //This allows us to navigate all pages without having to login. This may need some customization.
                .antMatchers("/nav/*").permitAll() //This allows us to navigate all pages without having to login. This may need some customization.
                .antMatchers("/login").permitAll() //This allows us to navigate all pages without having to login. This may need some customization.
                .antMatchers("/").permitAll() //This allows us to navigate all pages without having to login. This may need some customization.
                .antMatchers("/makeAppointment").permitAll() //This allows us to navigate all pages without having to login. This may need some customization.
                .antMatchers("/cancel").permitAll() //This allows us to navigate all pages without having to login. This may need some customization.
                .antMatchers("/status").permitAll() //This allows us to navigate all pages without having to login. This may need some customization.
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll();
    }


    /**
     * This configures the authentication method used - it's basically used to customize authentication to integrate with LDAP.
     * We can't get the username from the login request via the configuration as far as I could see, so we have to add userDN patterns (the username is plugged into 'uid={0}' part.
     */
    @Configuration
    protected static class AuthenticationConfiguration extends
            GlobalAuthenticationConfigurerAdapter {

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            ApplicationContext ctx = new AnnotationConfigApplicationContext(LDAPSettings.class);
            LdapContextSource contextSource = ctx.getBean(LdapContextSource.class);
            LdapTemplate ldapTemplate = ctx.getBean(LdapTemplate.class);
            LdapAuthenticationProviderConfigurer<AuthenticationManagerBuilder> ldapAuthenticationProviderConfigurer = auth.ldapAuthentication().userDnPatterns("uid={0},ou=Staff,ou=Computer Science,o=University of Pretoria,c=ZA", "uid={0},ou=Students,ou=Computer Science,o=University of Pretoria,c=ZA");
            ldapAuthenticationProviderConfigurer
                    .userSearchFilter("uid={0}")
                    .userSearchBase("")
                    .contextSource(contextSource);
        }
    }
}