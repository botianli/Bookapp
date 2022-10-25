import { Component, OnInit } from '@angular/core';

import { User } from 'src/app/model/user';
import { LoginService } from 'src/app/services/login.service';
@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  message!:any;
  loaded:boolean=false;
  user!:User;
  constructor(private loginService:LoginService) {
   

 }

 ngOnInit(): void {
   this.loginService.userSubject.subscribe((user:User)=>{
     this.user=user;
   })
    
  }
  

}
