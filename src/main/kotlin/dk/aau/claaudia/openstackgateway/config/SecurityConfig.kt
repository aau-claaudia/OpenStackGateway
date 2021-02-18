package dk.aau.claaudia.openstackgateway.config

import dk.aau.claaudia.openstackgateway.security.JWTAuthorizationFilter
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy


@Configuration
@Profile("prod", "test", "dev")
class JWTSecurityConfig(val appProperties: AppProperties) : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors().and()//.disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers(
                "/",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/swagger-resources/**"
            ).permitAll()
            //.antMatchers("/api", "/api/**").hasAnyRole() // .hasRole("api-user")
            .anyRequest().authenticated().and()
            .addFilter(JWTAuthorizationFilter(authenticationManager(), appProperties))
    }
}

@Configuration
@Profile("local")
class JWTSecurityConfigLocal(val appProperties: AppProperties) : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors().and()//.disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests().antMatchers("/**").permitAll();
    }
}