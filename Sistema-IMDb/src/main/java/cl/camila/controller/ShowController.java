package cl.camila.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import cl.camila.dto.ShowDTO;
import cl.camila.model.Rating;
import cl.camila.model.Show;
import cl.camila.model.User;
import cl.camila.service.RatingService;
import cl.camila.service.ShowService;
import cl.camila.service.UserService;

@Controller
@RequestMapping("/shows")
public class ShowController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ShowService showService;
	@Autowired
	private RatingService ratingService;

// ADD RATING
	@PostMapping(value = "/{id}/add")
	public RedirectView addRating(@Valid @ModelAttribute("addRating") Rating rating, BindingResult result,
			@PathVariable("id") Long id, Principal principal) {
		if (result.hasErrors()) {
			return new RedirectView("/shows/"+id);
		} else {
			String email = principal.getName(); 
			User currentUser = userService.findUserByEmail(email);
			Show currentShow = showService.findById(id);
			Rating ratingDb = ratingService.findFirstByOrderByIdDesc();
			
			if (ratingDb != null) {
				rating.setId(ratingDb.getId()+1);
			}
			
			rating.setUser(currentUser);
			rating.setShowId(currentShow.getId());
			
			ratingService.addRating(rating);
			currentShow.setRatings(ratingService.findAllRatingByShowId(currentShow.getId()));
			showService.update(currentShow);
			
			//Codigo prueba------------------------
//			currentShow.setRatings(ratingService.findAllRatings());
//			List<Rating> listRating = new ArrayList<Rating>();
//					listRating.add(ratingService.findFirstByOrderByIdDesc());
//			currentShow.setRatings(listRating);
			
			return new RedirectView("/shows/"+id);
		}
	}

// READ ONE
	@GetMapping(value = "/{id}")
	public ModelAndView getShow(@PathVariable("id") Long id, Model model, Principal principal) {
		Show show = showService.findById(id);
		System.out.println(show);
		User creatorShow = show.getCreatorShow();
		String email = principal.getName();
		User currentUser = userService.findUserByEmail(email);
		List<Rating> showRatings = show.getRatings();
		Rating newRating = new Rating();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("addRating", newRating);
		modelAndView.addObject("showRatings", showRatings);
		modelAndView.addObject("currentUser", currentUser);
		modelAndView.addObject("show", show);
		modelAndView.addObject("creatorShow", creatorShow);
		
		modelAndView.setViewName("show");
		
		return modelAndView;
	}

// READ ALL
	@GetMapping("")
	public ModelAndView homePage(Principal principal, Model model) {
		String email = principal.getName();
		User currentUser = userService.findUserByEmail(email);
		List<Show> allShows = showService.findAll();
		
		List<ShowDTO> showsDTO = new ArrayList<ShowDTO>();
		
		for (Show showT : allShows) {
			ShowDTO showDTO = ShowDTO.toDTO(showT);
			showDTO.setAverageRating(ratingService.ratingsAverage(showT.getId()));
			showsDTO.add(showDTO);
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("allShows", showsDTO);
		modelAndView.addObject("currentUser", currentUser);
		modelAndView.setViewName("home");
		
		
		return modelAndView;
	}

// CREATE NEW
	@GetMapping(value = "/new")
	public ModelAndView newShow(Model model) {
		Show newShow = new Show();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("newShow", newShow);
		modelAndView.setViewName("new");
		
		return modelAndView;
	}

//CREATE SAVE
	@PostMapping(value = "/create")
	public RedirectView createShow(@ModelAttribute("newShow") @Valid Show show, BindingResult result, Principal principal) {
		if (result.hasErrors()) {
			return new RedirectView("new");
		} else {
			String email = principal.getName();
			User creatorShow = userService.findUserByEmail(email);
			show.setCreatorShow(creatorShow);
			showService.add(show);
			return new RedirectView("/shows");
		}
	}

//EDIT
	@GetMapping(value = "/{id}/edit")
	public ModelAndView editShow(@PathVariable("id") Long id, Model model, @ModelAttribute("errors") String errors) {
		Show editShow = showService.findById(id);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("editShow", editShow);
		modelAndView.setViewName("edit");
		return modelAndView;
	}

// UPDATE
	@PostMapping(value = "/{id}/edit")
	public RedirectView updateShow(@PathVariable("id") Long id, @Valid @ModelAttribute("editShow") Show editedShow,
			BindingResult result, Model model, Principal principal, HttpSession session) {
		String email = principal.getName();
		Show show = showService.findById(id);
		User creatorShow = userService.findUserByEmail(email);
		if (result.hasErrors()) {
			session.setAttribute("id", show.getId());
			return new RedirectView("/shows/createError");
		} else {
			editedShow.setCreatorShow(creatorShow);
			showService.update(editedShow);
			return new RedirectView("/shows/");
		}
	}

	@RequestMapping("/createError")
	public String flashMessages(RedirectAttributes redirectAttributes, HttpSession session) {
		redirectAttributes.addFlashAttribute("errors", "Title and Network must be present");
		return "redirect:/shows/" + session.getAttribute("id") + "/edit";
	}

// DELETE / DESTROY
	@GetMapping(value = "/{id}/delete")
	public RedirectView deleteShow(@PathVariable("id") Long id) {
		showService.deleteById(id);
		return new RedirectView("/shows/");
	}
}

