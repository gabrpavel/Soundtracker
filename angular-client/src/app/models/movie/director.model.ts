export class Director {
  id?: number;
  ruName?: string;
  enName?: string;
  photo?: string;

  constructor(id: number, ruName: string, enName: string, photo: string) {
    this.id = id;
    this.ruName = ruName;
    this.enName = enName;
    this.photo = photo;
  }
}
