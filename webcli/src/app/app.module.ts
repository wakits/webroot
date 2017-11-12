import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule, BaseRequestOptions } from '@angular/http';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

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
import { LoggerService } from './util/logger.service';
import { UtilService } from './util/util.service';
import { EntryComponent } from './component/entry/entry.component';
import { AlertModule } from 'ngx-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    AlertComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    EntryComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    HttpClientModule,
    routing,
    AlertModule.forRoot()
  ],
  providers: [
    AuthService,
    GuardService,
    AlertService,
    UserService,
    LoggerService,
    BaseRequestOptions,
    UtilService
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
