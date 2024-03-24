import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MovieService} from "../../../services/movie/movie.service";
import {Movie} from "../../../models/movie/movie.model";

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.css']
})
export class MovieDetailsComponent implements OnInit {
  movie?: Movie;
  id: number;
  hover: boolean[];
  hoverDirectors: boolean[];

  constructor(private movieService: MovieService, private route: ActivatedRoute) {
    this.id = 0;
    this.hover = [];
    this.hoverDirectors = [];
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.movieService.getMovieById(this.id).subscribe(data => {
      this.movie = data;
      console.log(data)
    });
  }

  scrollLeft(): void {
    const container = document.querySelector('.screenshot-wrapper');
    if (container) {
      container.scrollLeft -= 200; // Adjust this value based on the width of your screenshots
    }
  }

  scrollRight(): void {
    const container = document.querySelector('.screenshot-wrapper');
    if (container) {
      container.scrollLeft += 200; // Adjust this value based on the width of your screenshots
    }
  }
}
