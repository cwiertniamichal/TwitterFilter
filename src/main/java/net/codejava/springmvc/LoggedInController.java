package net.codejava.springmvc;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.Status;

@Controller
public class LoggedInController {
	
	@RequestMapping(value = "/login/getUserTimeline", method = RequestMethod.POST)
	public ModelAndView login(Locale locale, Model model, @RequestParam("author")String author,
			@RequestParam("key-words")String keyWords, @RequestParam("date-since")String dateSince,
			@RequestParam("date-until")String dateUntil, @ModelAttribute("SpringWeb")MessageFilter mf) {
		List<Tweet> tweets = mf.getUserTimeline(author, keyWords, dateSince, dateUntil);
		model.addAttribute("tweets", tweets);
		System.out.println(tweets);
		return new ModelAndView("loggedIn", "command", mf);
	}
	
	@ModelAttribute("filterList")
	public List<String> getFilterList(){
		List<String> filterList = new ArrayList<String>();
		filterList.add("author");
		filterList.add("date");
		filterList.add("key-words");
		filterList.add("all");
		return filterList;
	}

}
