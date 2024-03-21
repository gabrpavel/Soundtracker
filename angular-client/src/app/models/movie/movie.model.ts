// movie.model.ts

export class Movie {
  id: number;
  ruTitle: string;
  enTitle?: string;
  releaseYear: number;
  description: string;
  length: number;
  poster: URL;
  type: any; // Замените на соответствующий тип
  genres: any[]; // Замените на соответствующий тип
  actors: any[]; // Замените на соответствующий тип
  directors: any[]; // Замените на соответствующий тип
  movieScreenshots: any[]; // Замените на соответствующий тип
  album: any; // Замените на соответствующий тип

  constructor(
    id: number,
    ruTitle: string,
    enTitle: string,
    releaseYear: number,
    description: string,
    length: number,
    poster: string,
    type: any,
    genres: any[],
    actors: any[],
    directors: any[],
    movieScreenshots: any[],
    album: any
  ) {
    this.id = id;
    this.ruTitle = ruTitle;
    this.enTitle = enTitle;
    this.releaseYear = releaseYear;
    this.description = description;
    this.length = length;
    this.poster = new URL(poster);
    this.type = type;
    this.genres = genres;
    this.actors = actors;
    this.directors = directors;
    this.movieScreenshots = movieScreenshots;
    this.album = album;
  }
}
