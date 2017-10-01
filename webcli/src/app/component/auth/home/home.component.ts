import { User } from '../../../models/user.model';
import { UserService } from '../../../services/auth/user/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    currentUser: User;
    users: User[] = [];
    loading = false;
    uname: String;

    constructor(private userService: UserService) {
        const cUser = localStorage.getItem('currentUser');
        if (cUser) {
            this.currentUser = JSON.parse(cUser);
         }


    }

    ngOnInit() {
       this.loadAllUsers();
    }

    deleteUser(id: number) {
        this.userService.delete(id).subscribe(() => { this.loadAllUsers(); });
    }

    public loadAllUsers() {
        this.userService.getAllUsers(this.uname).subscribe(
          users => { this.users = users; },
      (err: any) => {
        if (err.error instanceof Error) {
          console.log('Client-side error occured.');
        } else {
          console.log('Server-side error occured.');
        }
      });
        this.loading = false;
    }

}
