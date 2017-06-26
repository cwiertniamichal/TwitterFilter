package net.codejava.springmvc;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAuthorization {
	public String PIN = "";
	public static Twitter twitter;
	public static RequestToken requestToken;
	public static TwitterFactory twitterFactory;
	
	public String getPIN() {
		return this.PIN;
	}
	
	public void setPIN(String PIN) {
		this.PIN = PIN;
	}
	
	public static String getAuthorizationLink() {
		String authorizationLink = "";
		ConfigurationBuilder cb = new ConfigurationBuilder();
        
        //the following is set without access token - desktop client
        cb.setDebugEnabled(true)
        	.setOAuthConsumerKey("Pi2f4UQkPSgSfk2k7SVEbfrm6")
        	.setOAuthConsumerSecret("Bs1GMEEjj79A8zATTLWIPfQzoryAkCvLzTALYsLPgG4gg7d291");
		
        twitterFactory = new TwitterFactory(cb.build());
        twitter = twitterFactory.getInstance();
             
        try {
        	// get request token.
            // this will throw IllegalStateException if access token is already available
            // this is oob, desktop client version
            requestToken = twitter.getOAuthRequestToken(); 
            authorizationLink = requestToken.getAuthorizationURL();
        } catch (TwitterException exception) {
        	if (exception.getStatusCode() == 401) {
        		System.out.println("Unable to get the access token.");
            } else {
                exception.printStackTrace();
            }
        }
        return authorizationLink;
	}
	
	public static boolean login(String PIN) {
		AccessToken accessToken = null;
		try {
			while(accessToken == null) {
				System.out.println(PIN);
				accessToken = twitter.getOAuthAccessToken(requestToken, PIN);
			}
			} catch (TwitterException exception) {
            if (exception.getStatusCode() == 401) {
                System.out.println("Unable to get the access token.");
                return false;
            } else {
                exception.printStackTrace();
                return false;
            }
        }
		return true;
	}
	
}
