import {Track} from "./track.model";

export class Album {
  id?: number;
  artistName?: string;
  spotifyUrl?: string;
  cover?: string;
  name?: string;
  totalTracks?: number;
  tracks?: Track[];


  constructor(id: number, artistName: string, spotifyUrl: string, cover: string, name: string, totalTracks: number, tracks: Track[]) {
    this.id = id;
    this.artistName = artistName;
    this.spotifyUrl = spotifyUrl;
    this.cover = cover;
    this.name = name;
    this.totalTracks = totalTracks;
    this.tracks = tracks;
  }
}
