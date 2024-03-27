import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MovieService} from "../../../services/movie/movie.service";
import {Movie} from "../../../models/movie/movie.model";
import {MatDialog} from '@angular/material/dialog';
import {ScreenshotDialogComponent} from '../screenshot-dialog/screenshot-dialog.component';

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
  displayedScreenshots: any[];
  screenshotIndex: number;

  constructor(private movieService: MovieService, private route: ActivatedRoute, public dialog: MatDialog) { // Добавлен MatDialog в конструктор
    this.id = 0;
    this.hover = [];
    this.hoverDirectors = [];
    this.displayedScreenshots = [];
    this.screenshotIndex = 0;
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.movieService.getMovieById(this.id).subscribe(data => {
      this.movie = data;
      console.log(data);
      this.updateDisplayedScreenshots();
    });
  }

  updateDisplayedScreenshots(): void {
    if (this.movie && this.movie.movieScreenshots) {
      this.displayedScreenshots = this.movie.movieScreenshots.slice(this.screenshotIndex, this.screenshotIndex + 6);
    }
  }

  scrollLeft(): void {
    if (this.screenshotIndex > 0) {
      this.screenshotIndex -= 3;
      this.updateDisplayedScreenshots();
    }
  }

  scrollRight(): void {
    if (this.movie && this.movie.movieScreenshots && this.screenshotIndex < this.movie.movieScreenshots.length - 6) {
      this.screenshotIndex += 3;
      this.updateDisplayedScreenshots();
    }
  }

  openDialog(screenshot: any): void {
    this.dialog.open(ScreenshotDialogComponent, {
      data: {url: screenshot.url}
    });
  }
}
