import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HotToastService } from '@ngneat/hot-toast';
import { FavoriteService } from 'src/app/services/favorite.service';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.scss']
})
export class AuthorComponent implements OnInit {

  loaded:boolean=false;
  showLoading:boolean=false;
  localUser=localStorage.getItem('user');
  user= JSON.parse(localStorage.getItem('user') || '{}');
  constructor(private favoriteService:FavoriteService,private notification:HotToastService,private route:Router) { }
  authorData!:string[];
  ngOnInit(): void {
    this.getBook()
  }

  getBook(){
    this.loaded=false;
    this.showLoading=true;
    this.favoriteService.getAuthors(this.user.id,this.user.token).subscribe((data:any)=>{
      this.authorData=data;
      this.showLoading=false;
      this.loaded=true;
    },error=>{
      if(error.status==404){
        this.notification.error("Please add a book first");
        this.route.navigateByUrl('')
        
      }else{
        this.route.navigateByUrl('/error')
      }
      
    })
 
    
  }
}
