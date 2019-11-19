package com.lois.utils;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/11.
 */
public class JwtUtil {
    public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoi5YiY5b-X5rSLIiwiaWF0IjoxNTcxNDY4MzU2LCJleHAiOjE1NzE0NzE5NTZ9.M2q8d4CqyDMTPh7iiYTCMrlNqaGW9Y919Zk29QS6Dq0";
        Claims claims = jwtUtil.parseJWT(token);
        System.out.println(claims.getId());
    }
    @Test
    public void test1(){
        String[] s =  "http://127.0.0.1:8080/images/setting/30AC069C9A5346698E93A681A95C18F9-photo_cxXszp.jpg".split("/");
        System.out.println(s[s.length-1]);
    }

    private String key = "lois";

    private long ttl = 3600000*24;//一个小时

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生成JWT
     *
     * @param id
     * @param subject
     * @return
     */
    public String createJWT(String id, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key);
        if (ttl > 0) {
            builder.setExpiration( new Date( nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr){
        return  Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

}
