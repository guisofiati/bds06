package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{
	
	@Query("SELECT obj FROM Movie obj WHERE "
			+ "(:genreId IS NULL OR obj.genre = :genreId) "
			+ "ORDER BY obj.title")
	Page<Movie> findAllByGenre(Genre genreId, Pageable pageble);
	
	@Query("SELECT obj FROM Review obj INNER JOIN obj.movie movie WHERE movie.id = :id")
	List<Review> findReviewsByMovieId(Long id);
}
