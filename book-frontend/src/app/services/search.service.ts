import { Injectable } from '@angular/core';
import { Book } from '../model/book';
import { HttpParams, HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  searchUrl='http://localhost:'+environment.port+'/search/api/v1/search';
  bookSeed!:Book[]
  constructor(private client:HttpClient) { 
 
  }

  getResult(option:string,input:string){
    const params = new HttpParams()
   .set('searchOption', option)
   .set('searchInput', input);
   return this.client.get(this.searchUrl,{params});

  }
}
