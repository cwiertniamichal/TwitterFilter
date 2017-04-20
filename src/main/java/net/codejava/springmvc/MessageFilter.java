package net.codejava.springmvc;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class MessageFilter {
	
	public static List<Status> getUserTimeline(String username){
		List<Status> statuses = null;
		try{
			statuses = TwitterAuthorization.twitter.getUserTimeline(username);
		}catch(TwitterException te){
			te.printStackTrace();
		}
		return statuses;
	}
}
