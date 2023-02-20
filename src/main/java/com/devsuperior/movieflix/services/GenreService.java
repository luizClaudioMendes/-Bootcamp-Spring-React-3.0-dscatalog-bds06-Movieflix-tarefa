package com.devsuperior.movieflix.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.repositories.GenreRepository;

@Service
public class GenreService {
	
	@Autowired
	private GenreRepository repository;

	public List<GenreDTO> findAll() {
		List<Genre> list = repository.findAll();
		List<GenreDTO> result = new ArrayList<GenreDTO>();
		
		for (Genre genre : list) {
			result.add(new GenreDTO(genre));
		}
		
		
		return result;
	}

}
