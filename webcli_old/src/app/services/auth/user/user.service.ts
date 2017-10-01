import { environment } from '../../../../environments/environment';
import { User } from '../../../models/user.model';
import { LoggerService } from '../../../util/logger.service';
import { Injectable } from '@angular/core';
import { Jsonp, Http, RequestOptions } from '@angular/http';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class UserService {

  private static  LIST_USERS_ENDPOINT = environment.apiUrl + '/api/auth/user/';

  private users: User[] = [];

  constructor(private http: HttpClient,
    private logger: LoggerService) { }

  public getAllUsers(filter: string): User[] {
    const endPoint = UserService.LIST_USERS_ENDPOINT + 'list/' + filter;

     this.http.get<User[]> (endPoint)
      .subscribe(objects => { this.users = objects; },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          console.log('Client-side error occured.');
        } else {
          console.log('Server-side error occured.');
        }
      });

    return this.users;

  }

   private getById(id: number) {
      const endPoint = UserService.LIST_USERS_ENDPOINT + 'list/' + id;
        return this.http.get(endPoint + id)
          .map((response: Response) => response.json());
    }

    public create(user: User) {
      const endPoint = UserService.LIST_USERS_ENDPOINT + 'create';
        return this.http.post(endPoint, user).map((response: Response) => response.json());
    }

    update(user: User) {
//        return this.http.put('/api/users/' + user.id, user, this.jwt()).map((response: Response) => response.json());
    }

    delete(id: number) {
//        return this.http.delete('/api/users/' + id, this.jwt()).map((response: Response) => response.json());
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
