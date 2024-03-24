// movie.model.ts

import {MovieType} from "./movie-type.model";
import {Genre} from "./genre.model";
import {Actor} from "./actor.model";
import {Director} from "./director.model";
import {MovieScreenshot} from "./movie-screenshot.model";
import {Album} from "../music/album.model";

export class Movie {
  id: number;
  ruTitle: string;
  enTitle: string;
  releaseYear: number;
  description: string;
  length: number;
  poster: string;
  type: MovieType;
  genres: Genre[];
  actors: Actor[];
  directors: Director[];
  movieScreenshots: MovieScreenshot[];
  album?: Album;

  constructor(
    id: number,
    ruTitle: string,
    enTitle: string,
    releaseYear: number,
    description: string,
    length: number,
    poster: string,
    type: MovieType,
    genres: Genre[],
    actors: Actor[],
    directors: Director[],
    movieScreenshots: MovieScreenshot[],
    album: Album
  ) {
    this.id = id;
    this.ruTitle = ruTitle;
    this.enTitle = enTitle;
    this.releaseYear = releaseYear;
    this.description = description;
    this.length = length;
    this.poster = poster;
    this.type = type;
    this.genres = genres;
    this.actors = actors;
    this.directors = directors;
    this.movieScreenshots = movieScreenshots;
    this.album = album;
  }
}
