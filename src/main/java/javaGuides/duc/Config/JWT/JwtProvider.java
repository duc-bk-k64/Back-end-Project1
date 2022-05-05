package javaGuides.duc.Config.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class JwtProvider {
	@Value("$(secret_key)")
	private String jwtSecret;
public String generateToken(String userName) {
	Date date = new Date(System.currentTimeMillis()+3600000);
	return Jwts.builder().setSubject(userName).setExpiration(date).signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
}
public boolean validateToken(String token) {
	try {
		Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		return true;
	} catch (Exception e) {
		//System.out.println("invalid token");
	}
	return false;

}

public String getLoginFormToke(String token) {
	Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	
	return claims.getSubject();
}
}
