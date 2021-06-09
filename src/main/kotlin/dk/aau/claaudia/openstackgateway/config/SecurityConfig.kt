package dk.aau.claaudia.openstackgateway.config

//import dk.aau.claaudia.openstackgateway.security.JWTAuthorizationFilter

//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


//@Configuration
//@Profile("prod", "test", "dev", "local")
////class JWTSecurityConfig(val appProperties: AppProperties) : WebSecurityConfigurerAdapter() {
//class JWTSecurityConfig() : WebSecurityConfigurerAdapter() {
//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
//        http.cors().and()//.disable()
//            .csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .authorizeRequests()
//            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//            .antMatchers(
//                "/",
//                "/v3/api-docs",
//                "/v3/api-docs/**",
//                "/swagger-ui.html",
//                "/swagger-ui/**",
//                "/swagger-resources/**"
//            ).permitAll()
//            //.antMatchers("/api", "/api/**").hasAnyRole() // .hasRole("api-user")
//            .anyRequest().authenticated()
//    }
//}

//ALLOW ALL CONFIG
//@Configuration
//@EnableWebSecurity
//class WebSecurityConfig : WebSecurityConfigurerAdapter() {
//    @Autowired
//    @Throws(Exception::class)
//    override fun configure(auth: AuthenticationManagerBuilder) {
//        // …
//    }
//
//    @Throws(Exception::class)
//    override fun configure(httpSecurity: HttpSecurity) {
//        httpSecurity.authorizeRequests().antMatchers("/").permitAll()
//    }
//}

//@Configuration
//@Profile("prod", "test", "dev")
//class JWTSecurityConfig(val appProperties: AppProperties) : WebSecurityConfigurerAdapter() {
//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
//        http.cors().and()//.disable()
//            .csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .authorizeRequests()
//            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//            .antMatchers(
//                "/",
//                "/v3/api-docs",
//                "/v3/api-docs/**",
//                "/swagger-ui.html",
//                "/swagger-ui/**",
//                "/swagger-resources/**"
//            ).permitAll()
//            //.antMatchers("/api", "/api/**").hasAnyRole() // .hasRole("api-user")
//            .anyRequest().authenticated().and()
//            .addFilter(JWTAuthorizationFilter(authenticationManager(), appProperties))
//    }
//}

//@Configuration
//@Profile("local")
//class JWTSecurityConfigLocal(val appProperties: AppProperties) : WebSecurityConfigurerAdapter() {
//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
//        http.cors().and()//.disable()
//            .csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and().authorizeRequests().antMatchers("/**").permitAll();
//    }
//}