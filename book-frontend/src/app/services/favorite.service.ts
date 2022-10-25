import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from '../model/book';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FavoriteService {

  constructor(private httpClient:HttpClient) { }
  
  favoriteUrl:string='http://localhost:'+environment.port+'/favorite/api/v1/books/';
  bookList!:Book[];
  addBook(id:number,book:Book,token:string):Observable<any>{
    console.log('id is:'+id);
    const headers= new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })
    const url=this.favoriteUrl+id;
 
    return this.httpClient.post(url,book,{ headers: headers })
  }
  getBooks(id:number,token:string){
    const headers= new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    })
    const url=this.favoriteUrl+id;
    return this.httpClient.get<Book[]>(url,{ headers: headers })
 
  }
  deleteBook(id:number,isbn:string,token:string){

    const url=this.favoriteUrl+id+'/'+isbn;
    const headers= new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    })
    return this.httpClient.delete(url,{headers})
    
  }
  getAuthors(id:number,token:string){
    const headers= new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    })
    return this.httpClient.get(this.favoriteUrl+id+'/authors',{headers});

  }
  getFavoriteAuthor(id:number,token:string){
    const headers= new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      })
    return this.httpClient.get(this.favoriteUrl+id+'/author',{headers,responseType:'text'},)
  }
}
