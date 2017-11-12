import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';


@Injectable()
export class UtilService {

  constructor() { }

  public hanldeHttpError (erro: any, subject: string): Observable<any> {
    const errMsg = (erro.message) ? erro.message :
            erro.status ? `${erro.status} - ${erro.statusText}` : 'Server error';
    console.log(erro.status);
    switch (erro.status) {
      case 500: {
        return Observable.throw('Operation was not able to be completed');
      }
      case 404: {
        return Observable.throw(subject + ' not found.');
      }
      default: {
        return Observable.throw(errMsg);
      }
    }
  }

}
