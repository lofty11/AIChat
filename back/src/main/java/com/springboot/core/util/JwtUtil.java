package com.springboot.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.springboot.core.model.UserToken;
import com.springboot.core.model.dto.TokenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JwtUtil {

    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // 秘钥
    static final String SECRET = "Role-Privilege-Token";
    // 签名是有谁生成
    static final String ISSUSER = "AIChat";
    // 签名的主题
    static final String SUBJECT = "this is a token";
    // 签名的观众
    static final String AUDIENCE = "MINIAPP";
    // Request中的变量名
    public static final String LOGIN_TOKEN_KEY = "authorization";

    private static Set<String> blacklist = new HashSet<>();

    /**
     * 创建用户Token
     * @param userId 用户id
     * @return token
     */
    public static String createToken(Long userId, String userName, Integer userLevel, int expireTime) {
        logger.debug("createToken:");
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            Map<String, Object> map = new HashMap<String, Object>();
            Date nowDate = new Date();
            Date expireDate = getAfterDate(nowDate, 0, 0, 0, 0, 0, expireTime);
            map.put("alg", "HS256");
            map.put("typ", "JWT");
            String tokenId = Common.genSeqNum(1);
            StringBuilder message = new StringBuilder().append("createToken: ").append("userId = ")
                    .append(userId).append(" userName=").append(userName).append(" tokenId:").append(tokenId);
            logger.debug(message.toString());
            String token = JWT.create()
                    // 设置头部信息 Header
                    .withHeader(map)
                    // 设置 载荷 Payload
                    .withClaim("userId", userId)
                    .withClaim("tokenId",tokenId)
                    .withClaim("userName",userName)
                    .withClaim("userLevel",userLevel)
                    .withIssuer(ISSUSER)
                    .withSubject(SUBJECT)
                    .withAudience(AUDIENCE)
                    // 生成签名的时间
                    .withIssuedAt(nowDate)
                    // 签名过期的时间
                    .withExpiresAt(expireDate)
                    // 签名 Signature
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * 获得UserId和DepartId
     * @param token
     * @return UserAndDepart
     *    modifiedBy Ming Qiu 2020/11/3 23:09
     */
    public static UserToken verifyTokenAndGetClaims(String token) {
        if (token == null || token.isEmpty()||isBlacklisted(token)) {
            return null;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUSER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            Claim claimUserId = claims.get("userId");
            Claim claimUserName = claims.get("userName");
            Claim claimUserLevel = claims.get("userLevel");
            Claim expireTime=claims.get("exp");
            return new UserToken(claimUserId.asLong(),claimUserName.asString() ,expireTime.asDate(),claimUserLevel.asInt());
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public static void addToBlacklist(String token) {
        blacklist.add(token);
    }

    public static boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
    public static Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        if (date == null) {
            date = new Date();
        }

        Calendar cal = new GregorianCalendar();

        cal.setTime(date);
        if (year != 0) {
            cal.add(Calendar.YEAR, year);
        }
        if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }
        if (day != 0) {
            cal.add(Calendar.DATE, day);
        }
        if (hour != 0) {
            cal.add(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != 0) {
            cal.add(Calendar.MINUTE, minute);
        }
        if (second != 0) {
            cal.add(Calendar.SECOND, second);
        }
        return cal.getTime();
    }
}

