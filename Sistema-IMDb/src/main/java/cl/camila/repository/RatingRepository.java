package cl.camila.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cl.camila.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
	
	List<Rating> findAll();
	Rating findRatingById(Long id);
	Rating findFirstByOrderByIdDesc();
	List<Rating> findAllRatingByShowId(Long id);
	List<Rating> findRatingByShowsId(Long id);
	
	@Query(value ="SELECT AVG(r.ratings) FROM ratings r WHERE show_id = ?1", nativeQuery=true)
	Double ratingsAverage(Long id);

}
