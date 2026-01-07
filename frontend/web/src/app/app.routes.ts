import { Routes } from '@angular/router';
import { Login } from './core/auth/login/login';
import { Home } from './modules/home/home';
import { Profile } from './modules/profile/profile';
import { authGuard } from './core/auth/guards/auth-guard';
import { noAuthGuard } from './core/auth/guards/no-auth-guard';
import { Manage } from './modules/manage/manage';


export const routes: Routes = [
    
    { path: 'login', component: Login, canActivate: [noAuthGuard] },

    {
        path: '',
        canActivate: [authGuard],
        children: [
            { path: '', redirectTo: 'home', pathMatch: 'full' },
            { path: 'home', component: Home },
            { path: 'profile', component: Profile },
            { path: 'manage', component: Manage}
        ]
    },
    
    { path: '**', redirectTo: '' },
    
];
