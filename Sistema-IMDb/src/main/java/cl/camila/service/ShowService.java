package cl.camila.service;

import java.util.List;
import java.util.Optional;

import cl.camila.model.Show;


public interface ShowService {
	
	public List<Show> findAll();
	public Show findById(Long id);
	public void add(Show show);
	public void update(Show show);
	public void deleteById(Long id);
	
	
	

}
