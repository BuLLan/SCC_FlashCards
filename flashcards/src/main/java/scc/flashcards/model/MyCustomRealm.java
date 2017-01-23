package scc.flashcards.model;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;

public class MyCustomRealm extends JdbcRealm {
	  @Override
	  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	    // identify account to log to
	    UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
	    final String username = userPassToken.getUsername();

	    if (username == null) {
	      System.out.println("Username is null.");
	      return null;
	    }

	    // read password hash and salt from db
	    final User user = UserDAO.getUserByEmail(username);

	    if (user == null) {
	      System.out.println("No account found for user [" + username + "]");
	      return null;
	    }

	    // return salted credentials
	    SaltedAuthenticationInfo info = new MySaltedAuthentificationInfo(username, user.getPassword(), user.getSalt());

	    return info;
	  }
	}