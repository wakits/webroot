import { Injectable } from '@angular/core';
import { Router, RouterStateSnapshot, ActivatedRouteSnapshot } from '@angular/router';

@Injectable()
export class GuardService {

  constructor(private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

        console.log('up in');

        if (localStorage.getItem('currentUser')) {
            // logged in so return true
            return true;
        }
      console.log('up after');

        // not logged in so redirect to login page with the return url
        this.router.navigate(['/entry'], { queryParams: { returnUrl: state.url }});
        return false;
    }

}
