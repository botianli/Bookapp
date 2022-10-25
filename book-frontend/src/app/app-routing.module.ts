import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthorComponent } from './components/author/author.component';
import { ErrorComponent } from './components/error/error.component';
import { FavoriteComponent } from './components/favorite/favorite.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/profile/profile.component';
import { RecomendationComponent } from './components/recomendation/recomendation.component';
import { RegisterComponent } from './components/register/register.component';
import { SearchComponent } from './components/search/search.component';
import { AuthGuard } from './guard/auth.guard';
import { LogginGuard } from './guard/loggin.guard';
import { FullLayoutComponent } from './layout/full-layout/full-layout.component';
import { MainlayoutComponent } from './layout/mainlayout/mainlayout.component';
const routes: Routes = [
  {
    path:'',
    component: MainlayoutComponent,
    children:[{
      path:'',
      component:SearchComponent
    },{
      path:'favorite',
      component:FavoriteComponent,canActivate: [AuthGuard]
    },{
      path:'recommendation',
      component:RecomendationComponent,canActivate: [AuthGuard]
    }, {
      path:'author',
      component:AuthorComponent,canActivate: [AuthGuard]
    },{
      path:'search',
      component:SearchComponent,canActivate: []
    },{
      path:'profile',
      component:ProfileComponent
    },{
      path:'error',
      component: ErrorComponent
    },]
  },
  {
    path:'',
    component:FullLayoutComponent,
    children:[{
      path:'login',
      component:LoginComponent,canActivate:[LogginGuard]
    },
    {
      path:'register',
      component: RegisterComponent
    },
    ]
  }
  ,{ path: '**', redirectTo: '' }
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
