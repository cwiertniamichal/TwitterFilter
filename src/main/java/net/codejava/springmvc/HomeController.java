package net.codejava.springmvc;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.conf.Configuration;

 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	Twitter twitter;
	RequestToken requestToken;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String authorizationLink = "";
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
        ConfigurationBuilder cb = new ConfigurationBuilder();
        
        //the following is set without accesstoken- desktop client
        cb.setDebugEnabled(true)
        	.setOAuthConsumerKey("Pi2f4UQkPSgSfk2k7SVEbfrm6")
        	.setOAuthConsumerSecret("Bs1GMEEjj79A8zATTLWIPfQzoryAkCvLzTALYsLPgG4gg7d291");
		
        
        TwitterFactory tf = new TwitterFactory(cb.build());
         twitter = tf.getInstance();
             
        try {
        	// get request token.
            // this will throw IllegalStateException if access token is already available
            // this is oob, desktop client version
            requestToken = twitter.getOAuthRequestToken(); 
            authorizationLink = requestToken.getAuthorizationURL();
        }catch (TwitterException te) {
        	if (401 == te.getStatusCode()) {
        		System.out.println("Unable to get the access token.");
            } else {
                te.printStackTrace();
            }
        }
        
        String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("authorizationLink", authorizationLink);
		
		return new ModelAndView("home", "command", new HelloSpringMVC());
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Locale locale, Model model, @ModelAttribute("SpringWeb")HelloSpringMVC PIN) {
		AccessToken accessToken = null;
		try{
			while(accessToken == null){
				System.out.println(PIN.PIN);
				accessToken = twitter.getOAuthAccessToken(requestToken, PIN.PIN);
			}
			}catch (TwitterException te) {
            if (401 == te.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                te.printStackTrace();
            }
        }
		
		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query("");
		String res="";
		try{
		QueryResult result = twitter.search(query);
		
		for (Status status : result.getTweets()) {
			System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			res = "@" + status.getUser().getScreenName() + ":" + status.getText(); 
		}
		}catch(Exception e){}
		model.addAttribute("res", res);
		return "loggedIn";
	}

	
}
