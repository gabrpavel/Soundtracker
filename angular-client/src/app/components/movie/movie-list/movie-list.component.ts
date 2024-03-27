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
  movies: MovieDto[] = [];
  currentMovie: {} = <MovieDto>{};
  currentIndex = -1;
  title = '';
  filteredMovies: MovieDto[] = [];
  private _searchTerm = '';

  get searchTerm(): string {
    return this._searchTerm;
  }

  set searchTerm(value: string) {
    this._searchTerm = value;
    this.filteredMovies = this.filterMovies(this._searchTerm);
  }

  constructor(private movieService: MovieService) {
  }

  ngOnInit(): void {
    this.retrieveMovies();
  }

  retrieveMovies(): void {
    this.movieService.getAllMoviesDto().subscribe({
      next: (data) => {
        this.movies = data;
        this.filteredMovies = [...this.movies]; // Изначально отображаем все фильмы
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

  filterMovies(searchTerm: string) {
    if(!searchTerm) {
      return this.movies;
    }

    return this.movies.filter(movie => movie.ruTitle.toLowerCase().includes(searchTerm.toLowerCase()));
  }
}
