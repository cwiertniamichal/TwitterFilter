package net.codejava.springmvc;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

public class MessageFilter {
	public List<String> filters;
	
	public List<Tweet> getUserTimeline(String author, String keyWords, String dateSince, 
			String dateUntil, int pagesNum ) {	
		List<Status> statuses = null;
		List<Tweet> tweets = new ArrayList<Tweet>();
		Paging paging = new Paging(1, pagesNum);
		String queryString = "";
		
		try{
			if(filters.contains("all")) {
				statuses = TwitterAuthorization.twitter.getHomeTimeline(paging);
				//tweets = parseStatusesToTweets(statuses);
			}
			if(filters.contains("key-words")) {			
				queryString += "\"" + keyWords + "\"";
				//Query q = new Query(keyWords);
				//q.setCount(pagesNum);
				
				//statuses = TwitterAuthorization.twitter.search(q).getTweets();
				//tweets = parseStatusesToTweets(statuses);
				/*
				 * if statuses.hasNext()
				 * {
				 * 	query = result.nextQuery();
				 *	result = twitter.search(query);
				 * }
				 */
				
			}
			if(filters.contains("author")) {
				queryString += " from:" + author ;
				//Query q = new Query("from:" + author);
				//q.setCount(pagesNum);
				
				//statuses = TwitterAuthorization.twitter.search(q).getTweets();
				
				//tweets = parseStatusesToTweets(statuses);
			}
			if(filters.contains("date")) {
				queryString += " since:" + dateSince + " until:" + dateUntil;
				//Query q = new Query("");
				//q.setCount(pagesNum);
				//q.setSince(dateSince);
				//q.setUntil(dateUntil);
				//statuses = TwitterAuthorization.twitter.search(q).getTweets();
				//tweets = parseStatusesToTweets(statuses);
			}
			System.out.println(queryString);
			Query query = new Query(queryString);
			QueryResult result;
			query.setCount(pagesNum);
			result = TwitterAuthorization.twitter.search(query);
			statuses = result.getTweets();
			tweets = parseStatusesToTweets(statuses, tweets);
			while(result.hasNext()) {
				query = result.nextQuery();
				result = TwitterAuthorization.twitter.search(query);
				statuses = result.getTweets();
				tweets = parseStatusesToTweets(statuses, tweets);
			}
		} catch(TwitterException exception) {
			exception.printStackTrace();
		}
		return tweets;
	}
	
	public List<String> getFilters() {
		return this.filters;
	}
	
	
	public void setFilters(List<String> filters) {
		this.filters = filters;
	}
	
	public List<Tweet> parseStatusesToTweets(List<Status> statuses, List<Tweet> tweets) {
		for(Status status : statuses){
			Tweet tweet = new Tweet();
			tweet.setScreenName(status.getUser().getScreenName());
			tweet.setMessage(status.getText());
			tweet.setAuthor(status.getUser().getName());
			tweets.add(tweet);
		}
		return tweets;
	}
}
