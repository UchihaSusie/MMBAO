package com.rsa.webapi.common;


import com.rsa.webapi.common.exception.JavasmException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    private static final String keyStr="Sd3IfMhkoG1TXXL3S1r3YxdSujlQFtEDRi7YwnyD5aM=";
    private static final String UID="uid";
    //15天
    private static final Integer expTime = 1000 * 60 * 60 *24 * 15;
    private static final Integer expEmailTime = 1000 * 60 * 10;
    public static String Admin_Token = "user_token";


    public static String generate(Integer uid){
        return generate(uid.toString());
    }

    public static String generate(String uid){
        return generateBase(uid,expTime);
    }

    public static String generateEmail(String uid){
        return generateBase(uid,expEmailTime);
    }

    public static String generateBase(String uid, Integer expire){
        Map<String, Object> map = new HashMap<>();
        map.put(UID,uid);

        Date time = new Date();
        Date expDate = new Date(time.getTime() + expire);

        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyStr));
        String jws = Jwts.builder()
                .setClaims(map)
                .setIssuedAt(time)
                .setExpiration(expDate)
                .signWith(key)
                .compact();
        return jws;
    }


    public static String parse(String token){
        Jws<Claims>jws;
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyStr));
        try {
            jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            Claims body = jws.getBody();
            return body.get(UID, String.class);
        } catch (ExpiredJwtException e) {
            throw new JavasmException(ExceptionEnum.TOKENERROR);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new JavasmException(ExceptionEnum.SIGNERROR);
        } catch (Exception e) {
            throw new JavasmException(ExceptionEnum.SYSTEMERROR);
    }}
    public static void generateKey(){
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String encode = Encoders.BASE64.encode(secretKey.getEncoded());
        System.out.println(encode);
    }

    public static void main(String[] args) {
        JWTUtil.generateKey();
        String token = JWTUtil.generate(2);
        System.out.println(token);
        String parse = JWTUtil.parse(token);
        System.out.println(parse);
    }


}
