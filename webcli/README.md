# Webcli2

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 1.4.3.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `-prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).

## start webcli https
ng serve --ssl 1 --ssl-key certificate/key.pem --ssl-cert certificate/cert.pem
- environment.ts changed to https on port 8443
- to gen certificates
> openssl genrsa -out key.pem 1024
> openssl req -newkey rsa:1024 -new -key key.pem -out csr.pem
> openssl x509 -req -days 9999 -in csr.pem -signkey key.pem -out cert.pem

## start websrv https
keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650
- keeps same port for client:4200
- updated application.yml with:
server:
  port: 8443
  ssl:
    key-store: keystore.p12
    key-store-password: ******
    keyStoreType: PKCS12
    
## install sass and compass for styling 
gem install ruby
gem install sass --no-ri --no-rdoc
gem install compass --no-ri --no-rdoc 

## make webcli / angular using scss
ng set defaults.styleExt scss
