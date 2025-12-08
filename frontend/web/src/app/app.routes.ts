import { Routes } from '@angular/router';
import { Login } from './core/auth/login/login';
import { Home } from './modules/home/home';
import { Profile } from './modules/profile/profile';
import { authGuard } from './core/auth/auth-guard';
import { noAuthGuard } from './core/auth/no-auth-guard';


export const routes: Routes = [
    
    { path: 'login', component: Login, canActivate: [noAuthGuard] },
    
    {
        path: '',
        canActivate: [authGuard],
        children: [
            { path: 'home', component: Home },
            { path: 'profile', component: Profile },
        ]
    },
    
    { path: '**', redirectTo: 'login' },
    
];
