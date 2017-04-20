package net.codejava.springmvc;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class MessageFilter {
	public String[] filters;
	
	public List<Tweet> getUserTimeline(String query){
		for(int i = 0; i < filters.length; i++){
			System.out.println(filters[i]);
		}
		List<Status> statuses = null;
		List<Tweet> tweets = null;
		try{
			if(filters[0].equals("author")){
				System.out.println("here");
				statuses = TwitterAuthorization.twitter.getUserTimeline(query);
				tweets = parseStatusesToTweets(statuses);
			}
			else if(filters[0].equals("key-words")){
				Query q = new Query(query);
				statuses = TwitterAuthorization.twitter.search(q).getTweets();
				tweets = parseStatusesToTweets(statuses);
			}
			else {
			
			}
		}catch(TwitterException te){
			te.printStackTrace();
		}
		return tweets;
	}
	
	public String[] getFilters(){
		return this.filters;
	}
	
	
	public void setFilters(String[] filters){
		this.filters = filters;
	}
	
	public List<Tweet> parseStatusesToTweets(List<Status> statuses){
		List<Tweet> tweets = new ArrayList();
		for(Status status : statuses){
			Tweet tweet = new Tweet();
			tweet.setAuthor(status.getUser().getScreenName());
			tweet.setMessage(status.getText());
			tweets.add(tweet);
		}
		return tweets;
	}
}
