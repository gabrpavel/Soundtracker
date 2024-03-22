export class MovieDto {
  id: number;
  ruTitle: string;
  enTitle: string;
  type: string;
  releaseYear: string;
  length: number;
  poster: string;
  genres: string[];

  constructor() {
    this.id = 0;
    this.ruTitle = '';
    this.enTitle = '';
    this.type = '';
    this.releaseYear = '';
    this.length = 0;
    this.poster = '';
    this.genres = [];
  }
}
