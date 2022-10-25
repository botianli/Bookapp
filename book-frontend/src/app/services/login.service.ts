import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { User } from '../model/user';


@Injectable({
  providedIn: 'root'
})
export class LoginService {
  signInurl!:string;
  userSubject: BehaviorSubject<User>;
  // loginBoolean:BehaviorSubject<boolean>;
  default:User;
  

  constructor(private httpClient:HttpClient,private router:Router) {
    this.signInurl='http://localhost:'+environment.port+'/user/api/v1/users/login';
      this.default=new User();
      this.default.message='';
      this.default.id='-1';
      this.default.token='';
      this.default.firstName='';
      this.default.lastName='';
    if(localStorage.getItem('user')==''){
    
      this.userSubject= new BehaviorSubject<User>(this.default);
      // this.loginBoolean=new BehaviorSubject<boolean>(false);
      localStorage.setItem('user','');
      localStorage.setItem('loggedin','-1');
    }else{
     const user= JSON.parse(localStorage.getItem('user') || '{}');
     this.userSubject=new BehaviorSubject<User>(user);
    }
    // this.default=new User();
    // this.default.message='';
    // this.default.id='-1';
    // this.default.token='';
    // this.default.firstName='';
    // this.default.lastName='';
    // this.userSubject= new BehaviorSubject<User>(this.default);
    // // this.loginBoolean=new BehaviorSubject<boolean>(false);
    // localStorage.setItem('user','');
    // localStorage.setItem('loggedin','-1');
   }
   
 
  login(email:string, password:string) {
    const result = new Subject<boolean>();
       
    this.httpClient.post<User>(this.signInurl, { email, password}).subscribe((user: User) => {
      // store user details and jwt token in local storage to keep user logged in between page refreshes
      localStorage.setItem('user', JSON.stringify(user));
      localStorage.setItem("loggedin",'1');
      this.userSubject.next(user);
      result.next(true);
      result.complete();
      // this.router.navigateByUrl('/dashboard');
      

     
  },error=>{
    // this.router.navigateByUrl('/register');
      result.next(false);
      result.complete();


  });;
  return result.asObservable();
  }
  loggedIn():boolean{
    if(localStorage.getItem('loggedin')=='-1'){
      return false
    }
    else{
      return true;

    }
  }
downloadImage(id:string,token:string){
  const headers= new HttpHeaders({
    'Authorization': `Bearer ${token}`
  })
  let downUrl = 'http://localhost:'+environment.port+'/user/api/v1/users/image/'+id;
  return this.httpClient.get(downUrl,{headers, responseType: "blob" })
}
uploadImage(id:string,data:FormData,token:string) {
  const headers= new HttpHeaders({
    'Authorization': `Bearer ${token}`
  })
   let uploadUrl = 'http://localhost:'+environment.port+'/user/api/v1/users/image/'+id;


    return this.httpClient.put(uploadUrl, data,{headers});
  }
  
  logout(){
    localStorage.setItem('user','');
    this.userSubject.next(this.default);
    this.router.navigateByUrl('/dashboard');
    localStorage.setItem("loggedin",'-1');


  }
}
