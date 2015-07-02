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
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;


@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll() //Allows people to access our resources
                .antMatchers("/nav/*").permitAll() //This allows us to navigate all pages without having to login. This may need some customization.
                .antMatchers("/login").permitAll() //This allows us to navigate all pages without having to login. This may need some customization.
                .antMatchers("/").permitAll() //This allows us to navigate all pages without having to login. This may need some customization.
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")

                .permitAll();
    }

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