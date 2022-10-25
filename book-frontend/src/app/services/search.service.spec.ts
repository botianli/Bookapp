import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { environment } from 'src/environments/environment';
import { Book } from '../model/book';

import { SearchService } from './search.service';

describe('SearchService', () => {
  let service: SearchService;
let httpMock:HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule],
      providers:[SearchService]
    });
    service = TestBed.inject(SearchService);
    httpMock= TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should create getResult',()=>{
    expect(service.getResult).toBeTruthy();
  })
  it('should call client and result search result'),()=>{

    let booklist:Book[]=[{ title:"thi",
    author:"string",
    subject:"string",
    cover:"null",
    isbn!:"string"}]


    service.getResult('title','input').subscribe((data:any)=>{
      expect(data.length).toEqual(1)
    })
    let req = httpMock.expectOne('http://localhost:'+environment.port+'/search/api/v1/search');
    expect(req.request.method).toBe('GET');
    req.flush(booklist);

  }
});
