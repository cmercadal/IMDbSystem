package cl.camila.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.camila.model.Rating;
import cl.camila.repository.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	RatingRepository ratingRepo;
	
	@Override
	public List<Rating> findAll() {	
		return ratingRepo.findAll();
	}

	@Override
	public Rating findRatingById(Long id) {	
		return ratingRepo.findRatingById(id);
	}

	//FIND LAST 
	@Override
	public Rating findFirstByOrderByIdDesc() {	
		return ratingRepo.findFirstByOrderByIdDesc();
	}

	//METODOS PRUEBA-------
	@Override
	public List<Rating> findAllRatingByShowId(Long id) {
		return ratingRepo.findAllRatingByShowId(id);
	}

	@Override
	public List<Rating> findRatingByShowsId(Long id) {
		return ratingRepo.findRatingByShowsId(id);
	}

	//ADD
	@Override
	public void addRating(Rating rating) {
		ratingRepo.save(rating);		
	}

	@Override
	public String ratingsAverage(Long showId) {
		List<Rating> ratings = findAllRatingByShowId(showId);
		Double suma = 0.0;
		Double ac = 0.0;
		for (Rating rating : ratings) {
			suma += (double) rating.getRating();
			ac +=1;
		}
		
		return String.format("%.2f", suma/ac) ;
	}

}
