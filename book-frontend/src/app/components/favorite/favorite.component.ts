import { i18nMetaToJSDoc } from '@angular/compiler/src/render3/view/i18n/meta';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HotToastService } from '@ngneat/hot-toast';
import { Book } from 'src/app/model/book';
import { FavoriteService } from 'src/app/services/favorite.service';

@Component({
  selector: 'app-favorite',
  templateUrl: './favorite.component.html',
  styleUrls: ['./favorite.component.scss']
})
export class FavoriteComponent implements OnInit {
  loaded:boolean=false;
  localUser=localStorage.getItem('user');
  user= JSON.parse(localStorage.getItem('user') || '{}');
  constructor(private favoriteService:FavoriteService,private router:Router,private toastService:HotToastService) { }
  bookData!:Book[];
  showLoading:boolean=false;
  ngOnInit(): void {

    this.getBook();
  }

  getBook(){
    this.showLoading=true;
   
    this.favoriteService.getBooks(this.user.id,this.user.token).subscribe((data:any)=>{
      this.bookData=data;
      this.showLoading=false;
      this.loaded=true;
     

    },error=>{
      if(error.status==404){
        this.toastService.error("Please add a book first")
        this.router.navigateByUrl('')
      }else{
        this.router.navigateByUrl('/error')
      }
     
    }

    )
  }
  deleteBook(book:Book){
    this.favoriteService.deleteBook(this.user.id,book.isbn,this.user.token)
    .subscribe(
      (data:any)=>{
        this.toastService.success("Book deleted")
        this.bookData=data

},error=>{
  this.toastService.error("Book fail to delete")
}
    );
  }

}
