import { Injectable } from '@angular/core';

import {
  Http,
  Headers,
  RequestOptions,
  Response
} from '@angular/http';

import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { environment } from '../../../environments/environment';
import { User } from '../../models/user.model';
import { ApplicationError } from '../../util/applicationerror.service';
import { UtilService } from '../../util/util.service';
import { error } from 'util';

/**
* AuthService uses JSON-Web-Token authorization strategy.
* Fetched token and user details are stored in sessionStorage.
*/
@Injectable()
export class AuthService {

  public static readonly SIGNUP_URL = environment.apiUrl + '/api/auth/signup';
  public static readonly LOGIN_URL = environment.apiUrl + '/api/auth/login';
  public static readonly REFRESH_TOKEN_URL = environment.apiUrl + '/api/auth/token/refresh';

  private token: string;
  private username: string;
  private userId: number;

  constructor(
    private http: Http,
    private utilService: UtilService) {

    this.refreshUserData();
  }

  /**
  * Refreshes userId, username and token from sessionStorage
  */
  public refreshUserData(): void {
    const user = sessionStorage.getItem('user');
    if (user) {
      this.saveUserDetails(JSON.parse(user));
    }
  }

  /**
  * Registers new user and saves following token
  * @param username
  * @param email
  * @param password
  */
  public signUp(user: User): Observable<{}> {

    return this.http.post(AuthService.SIGNUP_URL, user, this.generateOptions())
      .map((res: Response) => {
        this.saveToken(res);
        this.saveUserDetails(JSON.parse(sessionStorage.getItem('user')));
        console.log(res.status);
      }).catch(err => {
        return this.utilService.hanldeHttpError(err, 'user');
      });
  }

  /**
  * Fetches and saves token for given user
  * @param username
  * @param password
  */
  public login(username: string, password: string): Observable<{}> {

    const requestParam = {
      username: username,
      password: password
    };

    const options = this.generateOptions();

    const body = JSON.stringify(requestParam);
    return this.http
        .post(AuthService.LOGIN_URL, body, options)
        .map(this.extractData)
        .catch(err => {
         return this.utilService.hanldeHttpError(err, 'user');
        });
}

  /**
  * Removes token and user details from sessionStorage and service's variables
  */
  public logout(): void {
    sessionStorage.removeItem('user');
    localStorage.removeItem('currentUser');
    this.token = null;
    this.username = null;
    this.userId = null;
  }

  /**
  * Refreshes token for the user with given token
  * @param token - which should be refreshed
  */
  public refreshToken(token: string): Observable<{}> {
    const requestParam = { token: this.token };

    return this.http.post(AuthService.REFRESH_TOKEN_URL, requestParam, this.generateOptions())
      .map((res: Response) => {
         this.saveToken(res);
      }).catch(err => {
        throw Error(err.json().message);
      });
  }

  /**
  * Checks if user is authorized
  * @return true is user authorized (there is token in sessionStorage) else false
  */
  public isAuthorized(): boolean {
    return Boolean(this.token);
  }

  /**
  * @return username if exists
  */
  public getUsername(): string {
    return this.username;
  }

  /**
  * @return userId if exists
  */
  public getUserId(): number {
    return this.userId;
  }

  /**
  * @return token if exists
  */
  public getToken(): string {
    return this.token;
  }

  // Saves user details with token into sessionStorage as user item
  private saveToken(res: Response): void {
    const response = res.json() && res.json().token;
    if (response) {
      const token = response;
      const claims = this.getTokenClaims(token);
      claims.token = token;
      sessionStorage.setItem('user', JSON.stringify(claims));
    } else {
      throw Error(res.json());
    }
  }

  // Saves user details into service properties
  private saveUserDetails(user): void {
    this.token = user.token || '';
    this.username = user.sub || '';
    this.userId = user.id || 0;
  }

  // Retrieves user details from token
  private getTokenClaims(token: string): any {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64));
  }

  // Generates Headers
  private generateOptions(): RequestOptions {

    const headers = new Headers({'Content-Type': 'application/json'});

    return new RequestOptions({ headers: headers });
  }

    private extractData(res: Response) {
      const body = res.json();
        if (body && body.token) {

            return body.token;
      }

       return {};

    }

}
