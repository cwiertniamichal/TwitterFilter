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

	/**
	 * Getting given number of tweets from timeline and returning it as list.
	 */
	public List<Tweet> getHomeTimeline(int tweetsNum) {
		List<Status> statuses = null;
		List<Tweet> tweets = new ArrayList<Tweet>();

		Paging p = new Paging();
		p.setCount(tweetsNum);

		try {
			statuses = TwitterAuthorization.twitter.getHomeTimeline(p);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		tweets = parseStatusesToTweets(statuses, tweets);

		return tweets;
	}

	/**
	 * Creating query with given filters for later use.
	 */
	public Query createQuery(String allWords, String exactWords, String anyWords, String noWords, String hashes,
			String author, String recipient, String mentioned, String dateSince, String dateUntil) {
		String queryString = "";
		if (!allWords.equals(""))
			queryString += allWords + " ";

		if (!exactWords.equals(""))
			queryString += "\"" + exactWords + "\" ";

		if (!anyWords.equals(""))
			queryString += createOrQuery(anyWords, "");

		if (!noWords.equals("")) {
			String[] words = noWords.split("\\s+");
			for (String word : words)
				queryString += "-" + word + " ";
		}

		if (!hashes.equals(""))
			queryString += createOrQuery(hashes, "#");

		if (!author.equals(""))
			queryString += createOrQuery(author, "from:");

		if (!recipient.equals(""))
			queryString += createOrQuery(recipient, "to:");

		if (!mentioned.equals(""))
			queryString += createOrQuery(mentioned, "@");

		if (!dateSince.equals(""))
			queryString += " since:" + dateSince + " until:" + dateUntil;

		// debug purposes
		System.out.println(queryString);

		Query query = new Query(queryString);
		return query;
	}

	/**
	 * Getting tweets list from timeline which fulfil given parameters as arguments.
	 */
	public List<Tweet> filterTweets(String allWords, String exactWords, String anyWords, String noWords, String hashes,
			String author, String recipient, String mentioned, String dateSince, String dateUntil, int tweetsPerPage,
			int pageNum) {
		List<Status> statuses = null;
		List<Tweet> tweets = new ArrayList<Tweet>();

		Query query = createQuery(allWords, exactWords, anyWords, noWords, hashes, author, recipient, mentioned,
				dateSince, dateUntil);

		try {
			QueryResult result;

			// set number of tweets per page, max 100
			query.setCount(tweetsPerPage);

			result = TwitterAuthorization.twitter.search(query);
			statuses = result.getTweets();
			tweets = parseStatusesToTweets(statuses, tweets);
			pageNum--;

			while (result.hasNext() && pageNum > 0) {
				query = result.nextQuery();
				result = TwitterAuthorization.twitter.search(query);
				statuses = result.getTweets();
				tweets = parseStatusesToTweets(statuses, tweets);
				pageNum--;
			}

		} catch (TwitterException exception) {
			exception.printStackTrace();
		}
		return tweets;
	}

	/**
	 * This method parses list of statuses to list of tweets.
	 */
	public List<Tweet> parseStatusesToTweets(List<Status> statuses, List<Tweet> tweets) {
		for (Status status : statuses) {
			Tweet tweet = new Tweet();
			tweet.setScreenName(status.getUser().getScreenName());
			tweet.setMessage(status.getText());
			tweet.setAuthor(status.getUser().getName());
			tweets.add(tweet);
		}
		return tweets;
	}

	/**
	 * Auxiliary method for parsing purposes.
	 */
	private String createOrQuery(String words, String prefix) {
		String orQuery = "";
		String[] wordsArray = words.split("\\s+");
		if (wordsArray.length == 1)
			orQuery += prefix + wordsArray[0] + " ";
		else {
			int i = 0;
			while (i + 1 < wordsArray.length) {
				orQuery += prefix + wordsArray[i] + " OR ";
				i++;
			}
			orQuery += prefix + wordsArray[i] + " ";
		}
		return orQuery;
	}
}
