package com.devsuperior.movieflix.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer // esta anotaçao vai processar para que esta classe implemente a funcionalidade
						// do oauth2
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${cors.origins}")
	private String corsOrigins;

	@Autowired
	private Environment env;

	@Autowired
	private JwtTokenStore tokenStore;

	private static final String[] PUBLIC = { "/oauth/token", "/h2-console/**" };

	private static final String[] VISITOR_OR_MEMBER = { "/genres/**"};

	private static final String[] MEMBER = { "/reviews/**" };

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore); // com esta linha o server é capaz de decodificar o token recebido para
											// valida-lo
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// H2
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable(); // o H2 requer que sejam desabilitados os frames
		}

		// rotas liberadas
		http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll() // qualquer url listada no PUBLIC
		.antMatchers(VISITOR_OR_MEMBER).hasAnyRole("MEMBER", "VISITOR")
		.antMatchers(MEMBER).hasRole("MEMBER")
		.anyRequest().authenticated(); // qualquer outra rota deve estar logado

		http.cors().configurationSource(corsConfigurationSource());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		String[] origins = corsOrigins.split(",");

		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOriginPatterns(Arrays.asList(origins));
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	@Bean
	FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
				new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
