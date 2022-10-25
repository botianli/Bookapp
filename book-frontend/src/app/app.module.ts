import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import { MainlayoutComponent } from './layout/mainlayout/mainlayout.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FullLayoutComponent } from './layout/full-layout/full-layout.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import {MatDividerModule} from '@angular/material/divider';
import {MatListModule} from '@angular/material/list';
import { FavoriteComponent } from './components/favorite/favorite.component';
import { RecomendationComponent } from './components/recomendation/recomendation.component';
import { SearchComponent } from './components/search/search.component';
import {MatFormFieldModule} from '@angular/material/form-field'
import {MatInputModule} from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import {MatSelectModule} from '@angular/material/select';
import { HttpClientModule } from '@angular/common/http';
import { ErrorComponent } from './components/error/error.component';
import { LoginService } from './services/login.service';
import { SignupService } from './services/signup.service';
import { SearchService } from './services/search.service';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatTableModule} from '@angular/material/table';
import { SearchtableComponent } from './components/searchtable/searchtable.component';
import { BooklistComponent } from './components/booklist/booklist.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AuthorComponent } from './components/author/author.component';
import { FavoriteService } from './services/favorite.service';
import { AuthorlistComponent } from './components/authorlist/authorlist.component';
import { HotToastModule } from '@ngneat/hot-toast';


@NgModule({
  declarations: [
    AppComponent,
    MainlayoutComponent,
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    FullLayoutComponent,
    LoginComponent,
    RegisterComponent,
    FavoriteComponent,
    RecomendationComponent,
    SearchComponent,
    ErrorComponent,
    SearchtableComponent,
    BooklistComponent,
    ProfileComponent,
    AuthorComponent,
    AuthorlistComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatButtonModule,
    FlexLayoutModule,
    MatSidenavModule,MatPaginatorModule,MatTableModule,
    MatProgressSpinnerModule,
    MatToolbarModule,FormsModule,
    MatIconModule,MatInputModule,
    MatCardModule,MatDividerModule,MatListModule,MatFormFieldModule,ReactiveFormsModule,MatSelectModule, HotToastModule.forRoot()
  ],
  providers: [LoginService,SignupService,SearchService,FavoriteService],
  bootstrap: [AppComponent]
})
export class AppModule { }
