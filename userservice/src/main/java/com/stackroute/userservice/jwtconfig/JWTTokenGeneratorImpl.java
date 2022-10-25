package com.stackroute.userservice.jwtconfig;

import com.stackroute.userservice.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTTokenGeneratorImpl implements JWTTokenGenerator{

    /**
     * To get the property values
     */
    @Value("${jwt.secret}")
    private String secret;

    @Value("${app.jwttoken.message}")
    private String message;

    @Override
    public Map<String, String> generateToken(User user) {
        String jwtToken = "";
        //Setting expiration for token to one day
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Date date = Date.from(tomorrow.atStartOfDay(defaultZoneId).toInstant());

        jwtToken = Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date()).setExpiration(date).signWith(SignatureAlgorithm.HS256,secret).compact();

        Map<String,String> jwtTokenMap = new HashMap<>();
        jwtTokenMap.put("token",jwtToken);
        jwtTokenMap.put("message",message);
        jwtTokenMap.put("id",String.valueOf(user.getUserId()));
        jwtTokenMap.put("firstName",user.getFirstName());
        jwtTokenMap.put("lastName",user.getLastName());
        return jwtTokenMap;
    }
}
