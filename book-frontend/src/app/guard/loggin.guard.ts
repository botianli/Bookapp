import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { HotToastService } from '@ngneat/hot-toast';
import { Observable } from 'rxjs';
import { LoginService } from '../services/login.service';

@Injectable({
  providedIn: 'root'
})
export class LogginGuard implements CanActivate {
  
  constructor(private loginService:LoginService,private notification:HotToastService,private router:Router){}
  
    canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if(this.loginService.loggedIn()){
        this.notification.error("Already logged in!")
        this.router.navigateByUrl('')
        return false;
      }else{
        return true;
      }
  }
  
}
