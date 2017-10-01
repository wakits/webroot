export class User {

  constructor(
    public id: number,
    public firstname: string,
    public lastname: string,
    public username: string,
    public email: string,
    public role?: string
  ) {}

  toString() {
    return JSON.stringify(this);
  }

}
