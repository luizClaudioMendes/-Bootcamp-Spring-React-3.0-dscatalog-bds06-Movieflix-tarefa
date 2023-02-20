package com.devsuperior.movieflix.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository repository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public ReviewDTO save(ReviewDTO reviewDTO) {
		Movie movie = movieRepository.getOne(reviewDTO.getMovieId());
		User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()); 
		return new ReviewDTO(repository.save(new Review(reviewDTO, user, movie)));
	}

	public List<ReviewDTO> findByMovieId(Long id) {
		Movie movie = movieRepository.getOne(id);
		List<Review> list = repository.findByMovie(movie);
		
		List<ReviewDTO> result = new ArrayList<ReviewDTO>();
		
		for (Review review : list) {
			result.add(new ReviewDTO(review));
		}
		
		
		return result;
	}

}
