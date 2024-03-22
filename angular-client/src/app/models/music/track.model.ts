export class Track {
  id?: number;
  artistName?: string;
  spotifyUrl?: string;
  name?: string;
  previewUrl?: string;
  trackNumber?: number;

  constructor(id: number, artistName: string, spotifyUrl: string, name: string, previewUrl: string, trackNumber: number) {
    this.id = id;
    this.artistName = artistName;
    this.spotifyUrl = spotifyUrl;
    this.name = name;
    this.previewUrl = previewUrl;
    this.trackNumber = trackNumber;
  }
}
