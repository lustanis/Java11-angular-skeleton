import { Routes, RouterModule } from '@angular/router';
import {LoginForm} from './security/loginForm/LoginForm.component';
import {AppComponent} from './app.component';
import {AuthGuard} from './security/authGuard';
import {Page1} from './view/page1/Page1.component';
import {Page2} from './view/page2/Page2.component';
import {Home} from './view/Home.component';

const appRoutes: Routes = [
    { path: 'home', component: Home },
    { path: 'login', component: LoginForm },
    // home route protected by auth guard
   // { path: '', component: Home/*, canActivate: [AuthGuard]*/ },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'page1', component: Page1 },
    { path: 'page2', component: Page2 },
    // otherwise redirect to home
   // { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);
