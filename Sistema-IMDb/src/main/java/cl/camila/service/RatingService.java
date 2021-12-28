package cl.camila.service;

import java.util.List;

import cl.camila.model.Rating;

public interface RatingService {

	List<Rating> findAll();
	Rating findRatingById(Long id);
	Rating findFirstByOrderByIdDesc();
	List<Rating> findAllRatingByShowId(Long id);
	List<Rating> findRatingByShowsId(Long id);
	void addRating(Rating rating);
	
	public String ratingsAverage(Long Id);
	
}
