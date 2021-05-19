package com.farzan.chat.config;


import com.farzan.chat.service.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// spring will use it as a source of configuration for the IoC context
@Configuration
// lets spring know that this class specifically is configuring spring security
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // configures spring authentication manager
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // used it to tell spring to use OUR authentication service to check user logins
        auth.authenticationProvider(this.authenticationService);
    }

    /*
     this is the real meet of spring security:
     - defines how spring receives authorization requests,
     - which pages it requires authorization to access
     - and how it handle logouts

     it does this all through the usage of a complex DSL(domain specific language) that uses method chaining
     to reduce the code required to set all of those conditions up
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // these pages should be accessible to all users without authorization
        http.authorizeRequests()
                .antMatchers("/signup", "/css/**", "/js/**","/h2-console/**").permitAll()
                .anyRequest().authenticated();

        http.csrf().ignoringAntMatchers("/h2-console/**");
        http.headers().frameOptions().sameOrigin();


        // use /login to login and make it accessible for anyone
        http.formLogin()
                .loginPage("/login")
                .permitAll();

        // define the default redirect on successful login to our home url
        http.formLogin()
                .defaultSuccessUrl("/chat", true);
    }
}
