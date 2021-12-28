package cl.camila.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.camila.model.Show;
import cl.camila.repository.ShowRepository;

@Service
public class ShowServiceImpl implements ShowService {

	@Autowired
	ShowRepository showRepo;
	
	@Override
	public List<Show> findAll() {
		
		return showRepo.findAll();
	}

	@Override
	public Show findById(Long id) {
		
		Optional<Show> optionalShow = showRepo.findById(id);
		if (optionalShow.isPresent()) {
			return optionalShow.get();
		} else {
			return null;
		}
	}

	@Override
	public void add(Show show) {
		showRepo.save(show);

	}

	@Override
	public void update(Show show) {
		showRepo.save(show);			
					
	}

	@Override
	public void deleteById(Long id) {
		showRepo.deleteById(id);
	}

}
