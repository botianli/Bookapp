import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { SignUp } from '../model/signUp';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SignupService {
  signUpurl!:string;

  constructor(private httpClient:HttpClient,private router:Router) { 
    this.signUpurl='http://localhost:'+environment.port+'/user/api/v1/users/signup';

  }
  signup(firstName:string,lastName:string,email:string,password:string):Observable<any>{

    return this.httpClient.post<SignUp>(this.signUpurl,{firstName,lastName,email,password});
  
     }
}
