package net.codejava.springmvc;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.Status;

@Controller
public class LoggedInController {
	
	@RequestMapping(value = "/login/getUserTimeline", method = RequestMethod.POST)
	public String login(Locale locale, Model model, @RequestParam("username")String username) {
		List<Status> statuses = MessageFilter.getUserTimeline(username);
		List<String> messages = new ArrayList();
		for(Status status : statuses){
			messages.add(status.getText());
		}
		model.addAttribute("statuses", messages);
		
		return "loggedIn";
	}
}
