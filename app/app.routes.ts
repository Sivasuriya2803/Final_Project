import { Routes } from '@angular/router';
import { UserLogin } from './user-login/user-login';
import { OfficerLogin } from './officer-login/officer-login';
import { Home } from './home/home';
import { DashboardUser } from './dashboard-user/dashboard-user';
import { DashboardOfficer } from './dashboard-officer/dashboard-officer';
import { Register } from './register/register';

export const routes: Routes = [
      { path: '',component:Home },
      {path:'home',component:Home},
  { path: 'user-login', component: UserLogin },
  { path: 'officer-login', component: OfficerLogin },
  { path: 'register', component: Register },
  {path:'dashboard-User',component:DashboardUser},
  {path:'dashboard-Officer',component:DashboardOfficer}
];
