import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/model/book';
import { FavoriteService } from 'src/app/services/favorite.service';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-recomendation',
  templateUrl: './recomendation.component.html',
  styleUrls: ['./recomendation.component.scss']
})
export class RecomendationComponent implements OnInit {

  loaded:boolean=false;
  showLoading:boolean=false;

  localUser=localStorage.getItem('user');
  author!:string;

  user= JSON.parse(localStorage.getItem('user') || '{}');
  // use seachService for now but need recommendation when it is done
  constructor(private favoriteService:FavoriteService,private searchService:SearchService) {
  
   }

   bookData!:Book[];
  ngOnInit(): void {
    this.getAuthor();
    
  }
  getBooks(authorName:string){
    this.searchService.getResult('Author',authorName).subscribe((data:any)=>{
      this.bookData=data;
      this.showLoading=false;
      this.loaded=true;
    })

  }
  getAuthor(){
    this.loaded=false;
    this.showLoading=true;
    
    this.favoriteService.getFavoriteAuthor(this.user.id,this.user.token).subscribe((author:any)=>{
      this.author=author;
      this.getBooks(this.author);
    })
    
  }

}
