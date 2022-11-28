# Spring-security playground

While playing around with Spring boot 3 and trying to implement JWT I kept running into issues. This playground project was my attempt to make it all make sense but I kept running into issues. Putting my upgrade to Spring boot 3 on hold due to security.

## How to use

Project is a simple maven project with no setup required. Just build and start it up.
Then, to experience Spring security in all its glory:

1) Go to [http://localhost:8080](http://localhost:8080), notice that you are not prompted for credentials. That's because we have it configured in the SecurityConfig.java class to have permitAll() access
2) From the returned page there are options to go elsewhere. You can go down the line and see how unsecured still doesn't prompt for credentials
3) As you get to the /secured link, you'll be prompted to login. The users are:
        - user - password is "user" and they can go into secured locations. See the filterChain @Bean in SecurityConfig.java to understand why
        - admin - password is "admin". They can go to secured and admin locations

Other examples shown are:

* what happens when you attempt to access an admin location with the plain user account. There's a customized accessDenied.html page shown
* what happens when you attempt to access a location that doesn't exist
* finally, you can log out

## Issues

I haven't gotten to api security so there may be more. My primary issue is that I cannot unsecure index.html. With Spring Boot 3 (or really Spring Security 6) we move from antMatchers to requestMatchers. This syntax no longer works as it did before:

"""
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/unsecured*/**").permitAll()
                        .requestMatchers("/admin*/**").hasRole("ADMIN")
                        // this is what we used to do in Spring Security 5.8
                        //.antMatchers("/", "/unsecured/**").permitAll()
                        //.antMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()) // this covers ALL other places not mentioned above, requiring
                                                       // for any other locations you must be authenticated
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error/accessDenied.html"));
        return http.build();
}
"""

No matter what I do, the .requestMatchers("/").permitAll() does not work. I am prompted for credentials is why it doesn't work.
The other issue I ran into was that:

"""
.requestMatchers("/unsecured/**").permitAll()
"""

no longer worked. You have to now enter:

"""
.requestMatchers("/unsecured*/**").permitAll()
"""

Notice the entra* before the last slash. Not the end of the world though.
