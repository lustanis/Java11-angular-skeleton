import { Routes, RouterModule } from '@angular/router';
import {LoginForm} from './security/loginForm/LoginForm.component';
import {AppComponent} from './app.component';
import {AuthGuard} from './security/authGuard';



const appRoutes: Routes = [
    { path: 'login', component: LoginForm },

    // home route protected by auth guard
    { path: '', component: AppComponent, canActivate: [AuthGuard] },

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);
