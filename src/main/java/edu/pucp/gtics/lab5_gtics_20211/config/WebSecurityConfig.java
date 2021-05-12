package edu.pucp.gtics.lab5_gtics_20211.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/user/signIn")
                .loginProcessingUrl("/processLogin")
                .usernameParameter("correo")
                .passwordParameter("password")
                .defaultSuccessUrl("/user/signInRedirect",true);
        http.authorizeRequests()
                .antMatchers("/juegos/lista","/juegos/**").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/juegos/guardar","/juegos/borrar", "/juegos/editar","/juegos/nuevo","/plataformas/**").hasAnyAuthority("ADMIN")

                .anyRequest().permitAll();


        http.logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

        System.out.println("configure");
    }

    @Autowired
    DataSource datasource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        System.out.println("configure 2");
        auth.jdbcAuthentication()
                .dataSource(datasource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("select correo, password, enabled FROM usuarios WHERE correo = ?")
                .authoritiesByUsernameQuery("select u.correo, u.autorizacion from usuarios u " +
                        "where u.correo = ? and u.enabled = 1");
    }

}