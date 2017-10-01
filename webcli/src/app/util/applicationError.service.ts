import { Injectable } from '@angular/core';

@Injectable()
export class ApplicationError extends Error {

  httpStatus?: number = 404;
  applicationStatus?: number;
  errorMessageTranslationkey: string;
  handled: boolean = false;

  constructor(message?: string) {
    super(message);
    this.name = ApplicationError.name;
    Object.setPrototypeOf(this, ApplicationError.prototype);
  }
}