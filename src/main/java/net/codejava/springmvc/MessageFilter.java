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
	
	public List<Tweet> getHomeTimeline(int tweetsNum){
		List<Status> statuses = null;
		List<Tweet> tweets = new ArrayList<Tweet>();
		
		Paging p = new Paging();
		p.setCount(tweetsNum);
		
		try{
			statuses = TwitterAuthorization.twitter.getHomeTimeline(p);
		}catch (TwitterException e){
			e.printStackTrace();
		}
		tweets = parseStatusesToTweets(statuses, tweets);
		
		return tweets;
	}
	
	public List<Tweet> filterTweets(String allWords, String exactWords, String anyWords, String noWords, String hashes,  
			String author, String recipient, String mentioned, String dateSince, String dateUntil, int tweetsPerPage,
			int pageNum) {	
		List<Status> statuses = null;
		List<Tweet> tweets = new ArrayList<Tweet>();
		
		String queryString = "";
		
		try{
			if(!allWords.equals(""))
				queryString += allWords + " ";
			
			if(!exactWords.equals(""))
				queryString += "\"" + exactWords + "\" ";
			
			if(!anyWords.equals(""))
				queryString += createOrQuery(anyWords, "");
			
			if(!noWords.equals("")){
				String[] words = noWords.split("\\s+");
				for(String word : words)
					queryString += "-" + word + " ";
			}
			
			if(!hashes.equals(""))
				queryString += createOrQuery(hashes, "#");
			
			if(!author.equals("")) 
				queryString += createOrQuery(author, "from:");

			if(!recipient.equals(""))
				queryString += createOrQuery(recipient, "to:");
			
			if(!mentioned.equals(""))
				queryString += createOrQuery(mentioned, "@");
			
			if(!dateSince.equals("")) 
				queryString += " since:" + dateSince + " until:" + dateUntil;

			// debug 
			System.out.println(queryString);
			
			Query query = new Query(queryString);
			QueryResult result;
			
			// set number of tweets per page, max 100
			query.setCount(tweetsPerPage);
			
			result = TwitterAuthorization.twitter.search(query);
			statuses = result.getTweets();
			tweets = parseStatusesToTweets(statuses, tweets);
			pageNum--;
			
			while(result.hasNext() && pageNum > 0) {
				query = result.nextQuery();
				result = TwitterAuthorization.twitter.search(query);
				statuses = result.getTweets();
				tweets = parseStatusesToTweets(statuses, tweets);
				pageNum--;
			}
			
		} catch(TwitterException exception) {
			exception.printStackTrace();
		}
		return tweets;
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
	
	private String createOrQuery(String words, String prefix){
		String orQuery = "";
		String[] wordsArray = words.split("\\s+");
		if(wordsArray.length == 1)
			orQuery += prefix + wordsArray[0] + " ";
		else{
			int i = 0;
			while(i + 1 < wordsArray.length){
				orQuery += prefix + wordsArray[i] + " OR ";
				i++;
			}
			orQuery += prefix + wordsArray[i] + " ";
		}
		return orQuery;
	}
}
