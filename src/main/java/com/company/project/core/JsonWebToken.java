package com.company.project.core;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JsonWebToken {

    private static final String JWT_ID = "jwt";
    private final static String JWT_SECRET = "wirechen";
    private static final int JWT_TTL = 60*60*1000;  //millisecond

    /**
     * 由字符串生成加密key
     * @return
     */
    private SecretKey generalKey(){
        String stringKey = JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     * @param subject JSON字符串
     * @return
     * @throws Exception
     */
    public String createJWT(String subject) throws Exception {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + JWT_TTL);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                        .setId(JWT_ID)
                        .setIssuedAt(now)
                        .setSubject(subject)
                        .setExpiration(exp)
                        .signWith(SignatureAlgorithm.HS256, key);
        return builder.compact();
    }

    /**
     * 解密jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt)
                    .getBody();
        return claims;
    }


}
