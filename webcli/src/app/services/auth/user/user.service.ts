import { environment } from '../../../../environments/environment';
import { User } from '../../../models/user.model';
import { LoggerService } from '../../../util/logger.service';
import { AuthService } from '../auth.service';
import { Injectable } from '@angular/core';
import { Jsonp, Http, RequestOptions } from '@angular/http';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class UserService {

  private static  LIST_USERS_ENDPOINT = environment.apiUrl + '/api/auth/user/';
  private static  SIGNUP_ENDPOINT = environment.apiUrl + '/api/auth/signup';

  private user: User = null;

  constructor(private http: HttpClient,
    private logger: LoggerService,
    private authService: AuthService) { }

  public getAllUsers(filter: String): Observable<User[]> {
    const endPoint = UserService.LIST_USERS_ENDPOINT + 'list?name=' + filter;

     return this.http.get<User[]> (endPoint).
       map((res: User[]) => res,
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          console.log('Client-side error occured.');
        } else {
          console.log('Server-side error occured.');
        }
      });

  }

   private getById(id: number) {
      const endPoint = UserService.LIST_USERS_ENDPOINT + 'list/' + id;
        return this.http.get(endPoint + id)
          .map((response: Response) => response.json());
    }

    public getUserByToken(token: String): Observable<User> {
      const endPoint = UserService.LIST_USERS_ENDPOINT;

      console.log('start http get call for getting user');
       return this.http.get<User>(endPoint + '?token=' + token)
          .map((result: User) => result,
            (err: HttpErrorResponse) => {
              if (err.error instanceof Error) {
                console.log('Client-side error occured.');
              } else {
                console.log('Server-side error occured.');
              }
            });
    }

    public signup(user: User) {
      console.log(user);
      return this.authService.signUp(user);
//      const endPoint = UserService.SIGNUP_ENDPOINT;
//        return this.http.post(endPoint, user)
//          .map((response: Response) => response);
    }

    update(user: User) {
//        return this.http.put('/api/users/' + user.id, user, this.jwt()).map((response: Response) => response.json());
    }

// is not working, misses mapping on serve side
    delete(id: number) {
      console.log('user: ' + id);
      return this.http.delete(UserService.LIST_USERS_ENDPOINT + 'delete', this.jwt).map((response: Response) => response.json());
    }

    // private helper methods
    private jwt() {
        // create authorization header with jwt token
        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.token) {
            const headers = new Headers({ 'Authorization': 'Bearer ' + currentUser.token });
            return new RequestOptions(headers);
        }
    }
}
