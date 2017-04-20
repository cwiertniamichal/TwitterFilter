package net.codejava.springmvc;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class MessageFilter {
	public List<String> filters;
	
	public List<Tweet> getUserTimeline(String author, String keyWords, String dateSince, 
			String dateUntil ){	
		List<Status> statuses = null;
		List<Tweet> tweets = null;
		//String query = "";
		try{
			if(filters.contains("all")){
				System.out.println("HERE");
				statuses = TwitterAuthorization.twitter.getHomeTimeline();
				tweets = parseStatusesToTweets(statuses);
			}
			if(filters.contains("author")){
				//query += "source:" + author;
				statuses = TwitterAuthorization.twitter.getUserTimeline(author);
				tweets = parseStatusesToTweets(statuses);
			}
			if(filters.contains("key-words")){
				//query += keyWords;
				Query q = new Query(keyWords);
				statuses = TwitterAuthorization.twitter.search(q).getTweets();
				tweets = parseStatusesToTweets(statuses);
			}
			//Query q = new Query(query);
			if(filters.contains("date")) {
				Query q = new Query("");
				q.setSince(dateSince);
				q.setUntil(dateUntil);
				statuses = TwitterAuthorization.twitter.search(q).getTweets();
				tweets = parseStatusesToTweets(statuses);
			}
			//statuses = TwitterAuthorization.twitter.search(q).getTweets();
			//tweets = parseStatusesToTweets(statuses);
		}catch(TwitterException te){
			te.printStackTrace();
		}
		return tweets;
	}
	
	public List<String> getFilters(){
		return this.filters;
	}
	
	
	public void setFilters(List<String> filters){
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
