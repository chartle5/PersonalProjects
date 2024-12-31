import { Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HeaderComponent } from './header/header.component'; 
import { SearchComponent } from './search/search.component';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { ListsComponent } from './lists/lists.component';
import { PublicListsComponent } from './public-lists/public-lists.component';
import { UpdatePasswordComponent } from './update-password/update-password.component';
import { ManageUsersComponent } from './manage-users/manage-users.component';
import { PrivacyPolicyComponent } from './privacy-policy/privacy-policy.component';
import { DmcaPolicyComponent } from './dmca-policy/dmca-policy.component';
import { AcceptableUsePolicyComponent } from './acceptable-use-policy/acceptable-use-policy.component';

export const routes: Routes = [
            { path: '', component: HomeComponent },
            { path: 'search', component: SearchComponent},
            { path: 'login', component: LoginComponent },
            { path: 'register', component: RegisterComponent },
            { path: 'lists', component: ListsComponent },
            { path: 'public-lists', component: PublicListsComponent},
            { path: 'update-password', component: UpdatePasswordComponent },
            { path: 'manage-users', component: ManageUsersComponent },
            { path: 'privacy-policy', component: PrivacyPolicyComponent },
            { path: 'dmca-policy', component: DmcaPolicyComponent },
            { path: 'acceptable-use-policy', component: AcceptableUsePolicyComponent },
];

