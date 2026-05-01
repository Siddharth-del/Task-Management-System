package com.example.Task.Security;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

     @Autowired
     private JwtUtils jwtUtils;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                System.out.println("AuthToken Called");

        try {
            String jwt=parseJwt(request);
            if(jwt!=null && jwtUtils.validateJwtToken(jwt)){
                System.out.println("Token THERE:"+jwt);
                String username=jwtUtils.getUserIdFromToken(jwt);
                //UserDetails userDetails=userDetailsService.loadUserByUsername(username);
               
                 Claims claims=jwtUtils.getAllClaims(jwt);
                List<String> roles =claims.get("roles",List.class);

                System.out.println("ROLES:"+roles);
                 List<GrantedAuthority> authorities=List.of(); 
                if(roles!=null){
                authorities=roles.stream().map(role ->(GrantedAuthority) new SimpleGrantedAuthority(role)).toList();
                }

                UsernamePasswordAuthenticationToken authetication=
                new UsernamePasswordAuthenticationToken( username,null,authorities);
                            
                    authetication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authetication);
            }  
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request,response);
        
    }
    
    private String parseJwt(HttpServletRequest request){

        String jwt=jwtUtils.getJwtFromHeader(request);
        return jwt;
    }
    

}
