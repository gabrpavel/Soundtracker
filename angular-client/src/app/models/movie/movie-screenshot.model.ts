export class MovieScreenshot {
  id?: number;
  url?: string;
  height?: number;
  width?: number;

  constructor(id: number, url: string, height: number, width: number) {
    this.id = id;
    this.url = url;
    this.height = height;
    this.width = width;
  }
}
