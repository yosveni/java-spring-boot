package com.linkapital.core.configuration;

import com.linkapital.core.services.security.jwt.JwtConfigurer;
import com.linkapital.core.services.security.jwt.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String CLIENT = "CLIENT";
    private static final String PARTNER = "PARTNER";
    private static final String BACKOFFICE = "BACKOFFICE";
    private static final String SECURITY = "SECURITY";
    private static final String ENTREPRENEUR = "ENTREPRENEUR";

    private final JwtProvider jwtProvider;

    public SecurityConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**",
                        "/security/user/register**/**",
                        "/security/user/send_code/**",
                        "/security/user/confirm_code/**",
                        "/security/user/client/reset_password",
                        "/authorization/owner/**").permitAll()
                .antMatchers(HttpMethod.GET, "/whatsapp/**",
                        "/authorization_question/**").permitAll()
                .antMatchers(HttpMethod.GET, "/bank_nomenclature/offer/**",
                        "/commission/user/**",
                        "/interview/**").authenticated()
                .antMatchers(HttpMethod.GET, "/authorization/**/**",
                        "/company/transfer/**").hasAnyRole(BACKOFFICE)
                .antMatchers("/offer/client/indicative/select").hasAnyRole(CLIENT, PARTNER, ENTREPRENEUR)
                .antMatchers("/offer/client/**").hasAnyRole(PARTNER, ENTREPRENEUR)
                .antMatchers(HttpMethod.GET, "/bank_nomenclature/**",
                        "/partner_bank/**",
                        "/security/user/active",
                        "/company/financial_strength/**",
                        "/company/credit_information/**",
                        "/company/generate_document/**",
                        "/company/bank_documents/**",
                        "/company/score_analysis/**",
                        "/offer/**",
                        "/security/user/identification/**",
                        "/storage/generate_document/**",
                        "/storage/upload/payment_ticket/**").hasAnyRole(BACKOFFICE, SECURITY)
                .antMatchers(HttpMethod.DELETE, "/authorization/**").hasAnyRole(BACKOFFICE, SECURITY)
                .antMatchers("/company/reset_owner/**",
                        "/company/maintenance",
                        "/partner_bank/**",
                        "/commission/**",
                        "/authorization_question/**",
                        "/whatsapp/**").hasAnyRole(SECURITY)
                .antMatchers("/company/by_cnpj/**",
                        "/comment/offer").hasAnyRole(BACKOFFICE, PARTNER, ENTREPRENEUR, SECURITY)
                .antMatchers("/company/**",
                        "/storage/**",
                        "/security/user",
                        "/security/user/update_password",
                        "/security/user/update_email/**",
                        "/security/user/profile",
                        "/security/user/intensity/**",
                        "/reputation/**",
                        "/identification/**",
                        "/authorization/init/**",
                        "/property_guarantee/**",
                        "/comment/**",
                        "/interview/answer").authenticated()
                .antMatchers("/security/user/identification/**",
                        "/information/**",
                        "/interview/**",
                        "/bank_nomenclature/**").hasAnyRole(BACKOFFICE, SECURITY)
                .antMatchers("/security/**").hasRole(SECURITY)
                .antMatchers("/websocket/**").permitAll()
                .antMatchers().hasAnyRole(BACKOFFICE, SECURITY)
                .antMatchers(HttpMethod.DELETE, "/**").hasRole(SECURITY)
                .and()
                .apply(new JwtConfigurer(jwtProvider));
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(singletonList("*"));
        configuration.setAllowedMethods(asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
