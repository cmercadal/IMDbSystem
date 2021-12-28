package cl.camila.dto;

import java.util.List;

import cl.camila.model.Rating;
import cl.camila.model.Show;
import cl.camila.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowDTO {
	
	 private Long id;
	  
	 private String showTitle;
	  
	 private String network;
	 
	 
	 private User creatorShow;
	   
	 private List<Rating> ratings;
	 
	 private String averageRating;
	 
	 
	 
	 public static ShowDTO toDTO(Show show) {
		 
		 ShowDTO showDTO = new ShowDTO();
		 showDTO.setId(show.getId());
		 showDTO.setShowTitle(show.getShowTitle());
		 showDTO.setNetwork(show.getNetwork());
		 showDTO.setCreatorShow(show.getCreatorShow());
		 showDTO.setRatings(show.getRatings());
		
		 return showDTO;
	 }
	
}



