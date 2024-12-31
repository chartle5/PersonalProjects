import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HeaderComponent } from './header/header.component';
import { ListsComponent } from './lists/lists.component';

import { routes } from './app.routes';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { SearchComponent } from './search/search.component';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { PublicListsComponent } from './public-lists/public-lists.component';
import { UpdatePasswordComponent } from './update-password/update-password.component';
import { ManageUsersComponent } from './manage-users/manage-users.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SearchComponent,
    ListsComponent,
    HeaderComponent,
    HomeComponent,
    RegisterComponent,
    PublicListsComponent,
    UpdatePasswordComponent,
    ManageUsersComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes),
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
