package com.trkgrn_theomer.cheapspring.api.interceptor;


import com.trkgrn_theomer.cheapspring.api.exception.ExpiredJwtExc;
import com.trkgrn_theomer.cheapspring.api.exception.NullPointerExc;
import com.trkgrn_theomer.cheapspring.api.model.concretes.Token;
import com.trkgrn_theomer.cheapspring.api.service.TokenService;
import com.trkgrn_theomer.cheapspring.api.service.userdetail.CustomUserDetails;
import com.trkgrn_theomer.cheapspring.api.service.userdetail.UserDetailService;
import com.trkgrn_theomer.cheapspring.config.jwt.service.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    private final TokenService tokenService;

    private final JwtUtil jwtUtil;

    private final UserDetailService userDetailsService;

    @Value("${jwt.refresh.expire.hours}")
    Long expireHours;

    public AuthInterceptor(TokenService tokenService, JwtUtil jwtUtil, UserDetailService userDetailsService) {
        this.tokenService = tokenService;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getHeader("Authorization") != null) {
            String jwt = request.getHeader("Authorization").substring(7);
            String username = "";
            Token tokenObj;

            try {
                username = jwtUtil.extractUsername(jwt);
                tokenObj = tokenService.findTokenByUsername(username);
            } catch (NullPointerException e) {
                throw new NullPointerExc("Oturum süresi sona erdi.");
            } catch (ExpiredJwtException e) {
                throw new ExpiredJwtExc("Oturun süresi sona erdi.");
            }
            System.out.println("Hi");
            if (jwt.equals(tokenObj.getJwt())) { // request headerından gelen token rediste bulunuyor mu?
                System.out.println("kalansüre: "+jwtUtil.tokenExpiredHours(jwt)+" expr:"+expireHours);
                if(jwtUtil.tokenExpiredHours(jwt) < 6L){ // token'ın kalan süresi 6 saatten az ise
                    //Refresh Token
                    final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    final String refreshJwt = jwtUtil.generateToken(userDetails,expireHours);
                    // rediste kaydet
                    tokenService.save(new Token(username,refreshJwt),expireHours);

                    System.out.println("kalansüre: "+jwtUtil.tokenExpiredHours(jwt)+" expr:"+expireHours);

                    log.info("Token yenilendi");

                    response.addHeader("refreshToken",refreshJwt);
                    response.addHeader("Access-Control-Expose-Headers", "refreshToken");
                    response.addCookie(new Cookie("test","sago"));
                }
            }
            else{
                response.setStatus(401);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request,response,handler,modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
