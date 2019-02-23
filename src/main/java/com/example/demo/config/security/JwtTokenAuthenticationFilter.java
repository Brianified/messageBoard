package com.example.demo.config.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter 
{
	private final Logger log = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);
	private final JwtConfig jwtConfig;
	
	public JwtTokenAuthenticationFilter(JwtConfig jwtConfig) 
	{
		this.jwtConfig = jwtConfig;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
				// 1. get the authentication header. Tokens are supposed to be passed in the authentication header
				String header = request.getHeader(jwtConfig.getHeader());
				
				// 2. validate the header and check the prefix
				if(header == null || !header.startsWith(jwtConfig.getPrefix())) {
					filterChain.doFilter(request, response);  		// If not valid, go to the next filter.
					if(header == null)
					{
						log.debug("authtication failed header was empty");
					}
					else
					{
						log.debug("header was missing "+jwtConfig.getHeader());
					}
					return;
				}
				
				// 3. Get the token
				String token = header.replace(jwtConfig.getPrefix(), "");
				
				try {	// exceptions might be thrown in creating the claims if for example the token is expired
					
					// 4. Validate the token
					Claims claims = Jwts.parser()
							.setSigningKey(jwtConfig.getSecret().getBytes())
							.parseClaimsJws(token)
							.getBody();
					
					String username = claims.getSubject();
					if(username != null) {
						@SuppressWarnings("unchecked")
						List<String> authorities = (List<String>) claims.get("authorities");
						
						// 5. Create auth object
						// UsernamePasswordAuthenticationToken: A built-in object, used by spring to represent the current authenticated / being authenticated user.
						// It needs a list of authorities, which has type of GrantedAuthority interface, where SimpleGrantedAuthority is an implementation of that interface
						 UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
										 username, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
						 
						 // 6. Authenticate the user
						 // Now, user is authenticated
						 SecurityContextHolder.getContext().setAuthentication(auth);
						 log.debug("user " + username + "is now authenticated");
					}
					
				} catch (Exception e) {
					// In case of failure. Make sure it's clear; so guarantee user won't be authenticated
					SecurityContextHolder.clearContext();
					log.debug("exception thrown while authenticating: " + e.getMessage());
				}
				
				// go to the next filter in the filter chain
				filterChain.doFilter(request, response);

	}

}
