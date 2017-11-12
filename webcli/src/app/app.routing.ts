import { AppComponent } from './app.component';
import { HomeComponent } from './component/auth/home/home.component';
import { EntryComponent } from './component/entry/entry.component';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { GuardService } from './services/auth/guard/guard.service';
import { Routes, RouterModule } from '@angular/router';

const appRoutes: Routes = [
    { path: '', component: HomeComponent, canActivate: [GuardService] },
    { path: 'entry', component: EntryComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },

    // otherwise redirect to home
//    { path: '**', redirectTo: '' }
    { path: '**', component: AppComponent }
];

    export const routing = RouterModule.forRoot(appRoutes);
