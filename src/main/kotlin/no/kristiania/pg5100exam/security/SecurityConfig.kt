package no.kristiania.pg5100exam.security

import no.kristiania.pg5100exam.service.UserService
import no.kristiania.pg5100exam.service.filter.CustomAuthenticationFilter
import no.kristiania.pg5100exam.service.filter.CustomAuthorizationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfig(@Autowired private val userService: UserService, @Autowired private val passwordEncoder: BCryptPasswordEncoder): WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        val authenticationFilter = CustomAuthenticationFilter(authenticationManagerBean())
        authenticationFilter.setFilterProcessesUrl("/api/authentication")
        http.csrf().disable()
        http.sessionManagement().disable()
        http.authorizeHttpRequests()
            .antMatchers("/api/authentication").permitAll()
            .antMatchers("/api/user/register").permitAll()
            .antMatchers("/api/user/**").hasAnyAuthority("ADMIN", "USER")
            .antMatchers("/api/authority/**").hasAuthority("ADMIN")
            .and().formLogin()
        http.authorizeHttpRequests().anyRequest().authenticated()
        http.addFilter(authenticationFilter)
        http.addFilterBefore(CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}