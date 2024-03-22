import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MovieService} from "../../../services/movie/movie.service";
import {Movie} from "../../../models/movie/movie.model";
import {join} from "@angular/compiler-cli";

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.css']
})
export class MovieDetailsComponent implements OnInit {
  movie?: Movie;
  id: number;

  constructor(private movieService: MovieService, private route: ActivatedRoute) {
    this.id = 0;
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.movieService.getMovieById(this.id).subscribe(data => {
      this.movie = data;
      console.log(data)
    });
  }
}
