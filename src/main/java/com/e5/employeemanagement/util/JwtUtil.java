package com.e5.employeemanagement.util;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import com.e5.employeemanagement.helper.AuthenticateException;
import com.e5.employeemanagement.helper.EmployeeManagementException;

/**
 * <p>
 * It is Util class to JWT - related operations.
 * </p>
 */
public class JwtUtil {
    private static final SecretKey KEY;
    //Token validity is 20 minutes
    private static final long TOKEN_VALIDITY = 60 * 60 * 2000;

    static {
        KeyGenerator keyGen;
        try {
            keyGen = KeyGenerator.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new EmployeeManagementException("Issue with server");
        }
        SecretKey secretKey = keyGen.generateKey();
        KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Base64.getEncoder().encodeToString(secretKey.getEncoded())));
    }

    /**
     * <p>
     * It is method to generate the Token using claims and username.
     * </p>
     *
     * @param subject denotes the Username in the form email.
     * @return {@link String} it return the JWT Token based on claims.
     */
    public static String generateToken(String subject) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .and()
                .signWith(KEY)
                .compact();
    }

    /**
     * <p>
     * It is method to extract the username from jwt token.
     * </p>
     *
     * @param jwtToken denotes the JWT Token to extract the username.
     * @return extracted username from jwt token.
     */
    public static String extractUserName(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    /**
     * <p>
     * It is method to extract Claim from the jwt Token using Function.
     * </p>
     *
     * @param jwtToken denotes the JWT Token for extract the claims.
     * @param claimResolver it is denoting the claim function.
     * @return {@link T} it return the generic type.
     */
    private static  <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    /**
     * <p>
     * It is method to extract All Claims using the jwt token.
     * </p>
     *
     * @param jwtToken denotes the JWT Token for extract all claims.
     * @return {@link Claims} if the all claims successfully extract.
     * @throws AuthenticateException if the token is invalid.
     */
    private static Claims extractAllClaims(String jwtToken) {
        try {
            return Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload();
        } catch (Exception e) {
            throw new AuthenticateException("Token is invalid");
        }
    }

    /**
     * <p>
     * It is method to Validate the Token using JWT Token and use details.
     * </p>
     *
     * @param jwtToken denotes the JWT Token to validate token.
     * @param userDetails it contains the username and password to validate token.
     * @return true if the token doesn't expire and the username is match otherwise false.
     */
    public static boolean validateToken(String jwtToken, UserDetails userDetails) {
        final String username = extractUserName(jwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    /**
     * <p>
     * It is method to find the Token is expired or not using JWT Token.
     * </p>
     *
     * @param jwtToken denotes the JWT Token to validate token
     * @return false if the token doesn't expire.
     * @throws AuthenticateException if the JWT Token is expired.
     */
    private static boolean isTokenExpired(String jwtToken) {
        if (extractClaim(jwtToken, Claims::getExpiration).before(new Date())) {
            throw new AuthenticateException("Token is expired");
        }
        return false;
    }
}
