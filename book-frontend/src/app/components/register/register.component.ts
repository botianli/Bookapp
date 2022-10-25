import { Component, OnInit } from '@angular/core';
import { FormGroup,FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SignUp } from 'src/app/model/signUp';
import { SignupService } from 'src/app/services/signup.service';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  buttonDisabled!:boolean;
  registerForm!: FormGroup;
  signedupError:boolean=false;
  constructor(private formbuilder:FormBuilder,private router:Router,private signupService:SignupService) { }

  ngOnInit(): void {
    this.registerForm=this.formbuilder.group({
      firstName:['',Validators.minLength(2)],
  
      lastName:['',Validators.minLength(2)],
      password:['',Validators.minLength(8)],
      email:['',Validators.email]
    })
  }
  onSubmit(){
    this.signupService.signup(this.registerForm.controls['firstName'].value,this.registerForm.controls['lastName'].value,this.registerForm.controls['email'].value,this.registerForm.controls['password'].value).subscribe(
      (user: SignUp) => {
        // store user details and jwt token in local storage to keep user logged in between page refreshes
        this.router.navigateByUrl('/login');
       
    },error=>{
this.signedupError=true;
    }
    )


  }
  get first(): any {
    return this.registerForm.get('firstName');
  }
  get last(): any {
    return this.registerForm.get('lastName');
  }
  get pass():any{
    return this.registerForm.get('password');
  }
  get email():any{
    return this.registerForm.get('email');
  }

}
