package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.codejava.springmvc.MessageFilter;
import net.codejava.springmvc.Tweet;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

/**
 * This class is made for unit-testing purpose.
 */
public class MessageFilterTest {

	private MessageFilter messageFilter = new MessageFilter();

	@Test
	public void queryAllWordsTest() throws TwitterException {
		Query query1 = new Query("test me ");
		Query query2 = messageFilter.createQuery("test me", "", "", "", "", "", "", "", "", "");
		assertEquals(query1, query2);
	}

	@Test
	public void queryExactWordsTest() throws TwitterException {
		Query query1 = new Query("\"happy hour\" ");
		Query query2 = messageFilter.createQuery("", "happy hour", "", "", "", "", "", "", "", "");
		assertEquals(query1, query2);
	}

	@Test
	public void queryAnyWordsTest() throws TwitterException {
		Query query1 = new Query("happy OR hour ");
		Query query2 = messageFilter.createQuery("", "", "happy hour", "", "", "", "", "", "", "");
		assertEquals(query1, query2);
	}

	@Test
	public void queryNoWordsTest() throws TwitterException {
		Query query1 = new Query("-happy -hour ");
		Query query2 = messageFilter.createQuery("", "", "", "happy hour", "", "", "", "", "", "");
		assertEquals(query1, query2);
	}

	@Test
	public void queryHashesWordsTest() throws TwitterException {
		Query query1 = new Query("#happy OR #hour ");
		Query query2 = messageFilter.createQuery("", "", "", "", "happy hour", "", "", "", "", "");
		assertEquals(query1, query2);
	}

	@Test
	public void queryAuthorTest() throws TwitterException {
		Query query1 = new Query("from:@Gwiazdowski ");
		Query query2 = messageFilter.createQuery("", "", "", "", "", "@Gwiazdowski", "", "", "", "");
		assertEquals(query1, query2);
	}

	@Test
	public void queryRecipientTest() throws TwitterException {
		Query query1 = new Query("to:@Gwiazdowski ");
		Query query2 = messageFilter.createQuery("", "", "", "", "", "", "@Gwiazdowski", "", "", "");
		assertEquals(query1, query2);
	}

	@Test
	public void queryMentionedTest() throws TwitterException {
		Query query1 = new Query("@Gwiazdowski OR @Janusz ");
		Query query2 = messageFilter.createQuery("", "", "", "", "", "", "", "Gwiazdowski Janusz", "", "");
		assertEquals(query1, query2);
	}

	@Test
	public void queryDateTest() throws TwitterException {
		Query query1 = new Query(" since:2015-12-21 until:2016-12-21");
		Query query2 = messageFilter.createQuery("", "", "", "", "", "", "", "", "2015-12-21", "2016-12-21");
		assertEquals(query1, query2);
	}

	@Test
	public void queryAllTest() throws TwitterException {
		Query query1 = new Query("test \"happy\" hour -hello #world from:@Korwin to:@Gwiazdowski "
				+ "@Janusz  since:2015-12-21 until:2016-12-21");
		Query query2 = messageFilter.createQuery("test", "happy", "hour", "hello", "world", "@Korwin", "@Gwiazdowski",
				"Janusz", "2015-12-21", "2016-12-21");
		assertEquals(query1, query2);
	}

	@Test
	public void statusParseTest() throws TwitterException {
		Status status1 = TwitterObjectFactory.createStatus(
				"{" + "\"text\":\"Hello World!\"," + "\"user\":{\"location\":\"location:\"," + "\"name\":\"Author\","
						+ "\"screen_name\":\"Screen Name\"," + "\"listed_count\":3},\"coordinates\":null}");
		Status status2 = TwitterObjectFactory.createStatus(
				"{" + "\"text\":\"Test no.2\"," + "\"user\":{\"location\":\"location:\"," + "\"name\":\"Kowalski\","
						+ "\"screen_name\":\"Kowal\"," + "\"listed_count\":3},\"coordinates\":null}");
		List<Status> statuses = new ArrayList<Status>();
		statuses.add(status1);
		statuses.add(status2);
		List<Tweet> tweets = new ArrayList<Tweet>();
		messageFilter.parseStatusesToTweets(statuses, tweets);
		Tweet tweet1 = new Tweet();
		tweet1.setScreenName("Screen Name");
		tweet1.setMessage("Hello World!");
		tweet1.setAuthor("Author");
		Tweet tweet2 = new Tweet();
		tweet2.setScreenName("Kowal");
		tweet2.setMessage("Test no.2");
		tweet2.setAuthor("Kowalski");
		assertEquals(tweet1.getAuthor(), tweets.get(0).getAuthor());
		assertEquals(tweet1.getMessage(), tweets.get(0).getMessage());
		assertEquals(tweet1.getScreenName(), tweets.get(0).getScreenName());
		assertEquals(tweet2.getAuthor(), tweets.get(1).getAuthor());
		assertEquals(tweet2.getMessage(), tweets.get(1).getMessage());
		assertEquals(tweet2.getScreenName(), tweets.get(1).getScreenName());
	}

}
