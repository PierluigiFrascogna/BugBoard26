import { Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { Home } from './home/home';
import { Profile } from './profile/profile';
import { authGuard } from './auth/auth-guard';
import { noAuthGuard } from './auth/no-auth-guard';

export const routes: Routes = [
    
    { path: 'login', component: Login, canActivate: [noAuthGuard] },

    {
        path: '',
        canActivate: [authGuard],
        children: [
            { path: '', component: Home },
            { path: 'profile', component: Profile },
        ]
    },
    
    { path: '**', redirectTo: '' },
    
];
