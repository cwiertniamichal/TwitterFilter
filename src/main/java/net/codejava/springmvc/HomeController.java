package net.codejava.springmvc;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	public MessageFilter mf;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
        String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("authorizationLink", TwitterAuthorization.getAuthorizationLink());
		
		return new ModelAndView("home", "command", new TwitterAuthorization());
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(Locale locale, Model model, @RequestParam("PIN")String PIN, HttpServletRequest request) {
		if(TwitterAuthorization.login(PIN)){
			mf = new MessageFilter();
			return new ModelAndView("loggedIn", "command", mf);
		}
		else{
			String referer = request.getHeader("Referer");
			return new ModelAndView("redirect:" + referer);
		}
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
