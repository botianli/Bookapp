import { Component, OnInit } from '@angular/core';
import { FormGroup,FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { BehaviorSubject } from 'rxjs';
import { LoginService } from 'src/app/services/login.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  private userSubject: BehaviorSubject<User>;
  default!:User;

  showError:boolean=false;
  loginForm!:FormGroup;
  constructor(private formbuilder:FormBuilder,private router:Router,private loginService:LoginService) {
    this.default=new User();
    this.default.message='';
    this.default.id='-1';
    this.default.token='';
    this.default.firstName='';
    this.default.lastName='';
    this.userSubject= new BehaviorSubject<User>(this.default);
   }
  user!:User;
  ngOnInit(): void {
    this.loginForm=this.formbuilder.group({
    
      password:['',Validators.minLength(8)],
      email:['',Validators.email]
    })

  }
  public  userValue():  BehaviorSubject<User> {
    return this.userSubject;
}
  onSubmit(){
   

     this.loginService.login(this.loginForm.controls['email'].value,this.loginForm.controls['password'].value).subscribe(
       (data:any)=>{
         if(data===true){
                  this.router.navigateByUrl('/dashboard');

         }else{
          this.showError=true;
         }
       }
     );
  }     
}
