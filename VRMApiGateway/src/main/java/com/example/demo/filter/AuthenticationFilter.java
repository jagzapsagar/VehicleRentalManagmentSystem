package com.example.demo.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;


import com.example.demo.util.JwtUtil;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

	@Autowired
	private RestTemplate template;
	@Autowired
	private JwtUtil jwtUtil;

	public AuthenticationFilter() {
		super(Config.class);
	}
	
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            
            

            // If the route is secured
            if (validator.isSecured.test(request)) {

                // Check for Authorization Header
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                System.out.println("---------Token received: " + authHeader);
                String token = null;

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7); // remove "Bearer "
                    System.out.println("-----------substring(7)"+token);
                }

                try {
                    // REST call to AUTH service
                    //template.getForObject("http://EMSECURITYSERVICE/auth/validate?token=" + token, String.class);
                	 System.out.println("📡 Calling security service to validate token..."+token);
                	 //http://localhost:8088/auth/validate?token=
                	 System.out.println("http://localhost:8085/auth/validate?token="+ token);
                	 try {
                		 template.getForObject("http://localhost:8085/auth/validate?token=" + token, String.class);
                	 }catch(Exception e){
                		 System.out.println("Catching exception: "+e.getMessage());
                	 }
                    
                    System.out.println("✅ Token is valid, proceeding to target service...");
                } catch (Exception e) {
                    System.out.println("Invalid access...!");
                    throw new RuntimeException("Unauthorized access to application");
                }
            }

            return chain.filter(exchange);
        });
    }

	public static class Config {

	}
}
