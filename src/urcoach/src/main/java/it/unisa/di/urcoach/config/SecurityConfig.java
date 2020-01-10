package it.unisa.di.urcoach.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/css/**", "/js/**").permitAll()
                    .antMatchers("/recruiter/**").hasRole("RECRUITER")
                    .antMatchers("/ordini/**").hasRole("ORDINI")
                    .and()
                .formLogin()
                    .loginPage("/loginAdmin")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    /*
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("Salvatore")
                        .password("ciao")
                        .roles("RECRUITER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
    */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Salvatore").password("{noop}ciao").roles("RECRUITER")
                .and()
                .withUser("Tonia").password("{noop}ciao").roles("ORDINI");

    }
}