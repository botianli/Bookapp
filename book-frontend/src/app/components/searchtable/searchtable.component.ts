import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import { Router } from '@angular/router';
import { HotToastService } from '@ngneat/hot-toast';
import { Book } from 'src/app/model/book';
import { FavoriteService } from 'src/app/services/favorite.service';
import { LoginService } from 'src/app/services/login.service';
@Component({
  selector: 'app-searchtable',
  templateUrl: './searchtable.component.html',
  styleUrls: ['./searchtable.component.scss']
})
export class SearchtableComponent implements AfterViewInit {

  dataSource!: MatTableDataSource<Book>;
  @Input() set data(bookdata:Book[]){
    this.dataSource= new MatTableDataSource<Book>(bookdata);
  };

  loggedin!:boolean;
  constructor(private loginService:LoginService,private favoriteService:FavoriteService,private router:Router,private toastService:HotToastService){}
  displayedColumns: string[]=['Title','Author','Subject','Add'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.loggedin=this.loginService.loggedIn();
    console.log('use loggedin : '+this.loggedin)

}
success(){
  this.toastService.success("Book added!");
}
fail(){
  this.toastService.error("Book already there");
}
addBook(book:Book){
  if(!this.loginService.loggedIn()){
   this.toastService.error("Please login first!")
  }else{
    const localUser=localStorage.getItem('user');
    const user= JSON.parse(localStorage.getItem('user') || '{}');
    var id:number = +user.id;
    this.favoriteService.addBook(id,book,user.token).subscribe((book:Book)=>{
      this.success();

    },error=>{
     this.fail();
    })
    }

}

}
