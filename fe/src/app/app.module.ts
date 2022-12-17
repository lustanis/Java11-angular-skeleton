import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LoginForm} from './security/loginForm/LoginForm.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {routing} from './app.routes';
import {FormsModule} from '@angular/forms';
import {Page1} from './view/page1/Page1.component';
import {Page2} from './view/page2/Page2.component';
import {Home} from './view/Home.component';
import {JwtInterceptor} from './security/JwtInterceptor';

@NgModule({
  declarations: [
    AppComponent,
    LoginForm,
    Page1,
    Page2,
    Home
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    routing
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: JwtInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
