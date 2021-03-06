package net.codejava.springmvc;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	private MessageFilter messageFilter;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) {

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);
		model.addAttribute("authorizationLink", TwitterAuthorization.getAuthorizationLink());

		return new ModelAndView("home", "command", new TwitterAuthorization());
	}

	/**
	 * Simply selects the login view to render by returning its name.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(Locale locale, Model model, @RequestParam("PIN") String PIN, HttpServletRequest request) {
		if (TwitterAuthorization.login(PIN)) {
			messageFilter = new MessageFilter();
			MessageFilter.tweets = null;
			return new ModelAndView("loggedIn", "command", messageFilter);
		} else {
			String referer = request.getHeader("Referer");
			return new ModelAndView("redirect:" + referer);
		}
	}
}
