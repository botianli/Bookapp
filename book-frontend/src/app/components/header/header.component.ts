import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { User } from 'src/app/model/user';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  
  @Output() toggleSideBarForMe: EventEmitter<any> = new EventEmitter();

  user!:User;
  constructor(private LoginService:LoginService) { }

  ngOnInit(): void {
    this.LoginService.userSubject.subscribe((user:User)=>{
      this.user=user;
    })
  }
  logout(){
    this.LoginService.logout();
    console.log("logging out")
  }
  toggleSideBar() {
    this.toggleSideBarForMe.emit();
    setTimeout(() => {
      window.dispatchEvent(
        new Event('resize')
      );
    }, 300);
  }
  

}
