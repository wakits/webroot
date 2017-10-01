import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule, BaseRequestOptions } from '@angular/http';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { routing } from './app.routing';
import { AuthService } from './services/auth/auth.service';
import { AlertComponent } from './component/alert/alert.component';
import { HomeComponent } from './component/auth/home/home.component';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { AlertService } from './services/alert/alert.service';
import { GuardService } from './services/auth/guard/guard.service';
import { UserService } from './services/auth/user/user.service';


@NgModule({
  declarations: [
    AppComponent,
    AlertComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routing,
    RouterModule.forRoot([
      {path: '**/*', component: AppComponent},
    ])
  ],
  providers: [
    AuthService,
    GuardService,
    AlertService,
    UserService,
    BaseRequestOptions
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
