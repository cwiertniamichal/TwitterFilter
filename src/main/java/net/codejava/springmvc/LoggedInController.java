package net.codejava.springmvc;

import java.util.List;
import java.util.Locale;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoggedInController {

	/**
	 * Returning new view with given number of tweets from home timeline. Tweets
	 * are displayed one after another under command menu.
	 */
	@RequestMapping(value = "/login/getHomeTimeline", method = RequestMethod.POST)
	public ModelAndView login(Locale locale, Model model, @RequestParam("tweets-num") int tweetsNum,
			@ModelAttribute("SpringWeb") MessageFilter messageFilter) {
		List<Tweet> tweets = messageFilter.getHomeTimeline(tweetsNum);
		model.addAttribute("tweets", tweets);
		return new ModelAndView("loggedIn", "command", messageFilter);
	}

	/**
	 * Returning new view with filtered tweets. Tweets are displayed one after
	 * another under command menu.
	 */
	@RequestMapping(value = "/login/filterTweets", method = RequestMethod.POST)
	public ModelAndView login(Locale locale, Model model, @NotNull @RequestParam("all-words") String allWords,
			@RequestParam("exact-words") String exactWords, @RequestParam("any-words") String anyWords,
			@RequestParam("no-words") String noWords, @RequestParam("hashes") String hashes,
			@RequestParam("author") String author, @RequestParam("recipient") String recipient,
			@RequestParam("mentioned") String mentioned, @RequestParam("date-since") String dateSince,
			@RequestParam("date-until") String dateUntil, @RequestParam("tweets-per-page") int tweetsPerPage,
			@NotNull @Min(1) @RequestParam("pages-num") int pagesNum,
			@ModelAttribute("SpringWeb") MessageFilter messageFilter) {
		List<Tweet> tweets = messageFilter.filterTweets(allWords, exactWords, anyWords, noWords, hashes, author,
				recipient, mentioned, dateSince, dateUntil, tweetsPerPage, pagesNum);
		model.addAttribute("tweets", tweets);
		return new ModelAndView("loggedIn", "command", messageFilter);
	}

}
