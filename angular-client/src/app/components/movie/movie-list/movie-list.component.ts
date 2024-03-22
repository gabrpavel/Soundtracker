// movie-list.component.ts
import {Component, OnInit} from '@angular/core';
import {MovieService} from "../../../services/movie/movie.service";
import {MovieDto} from "../../../models/movie/movie-dto.model";

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {
  movies?: MovieDto[];
  currentMovie: {} = <MovieDto>{};
  currentIndex = -1;
  title = '';

  constructor(private movieService: MovieService) {
  }

  ngOnInit(): void {
    this.retrieveMovies();
  }

  retrieveMovies(): void {
    this.movieService.getAllMoviesDto().subscribe({
      next: (data) => {
        this.movies = data;
        console.log('Movies:', this.movies);
      },
      error: (e) => console.error(e)
    });
  }

  refreshList(): void {
    this.retrieveMovies();
    this.currentMovie = {};
    this.currentIndex = -1;
  }

  setActiveMovie(movie: MovieDto, index: number): void {
    this.currentMovie = movie;
    this.currentIndex = index;
  }
}
