import { TestBed } from '@angular/core/testing';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { environment } from 'src/environments/environment';
import { FavoriteService } from './favorite.service';
import { Book } from '../model/book';
let httpClientSpy: { get: jasmine.Spy };
describe('FavoriteService', () => {
  let service: FavoriteService;
  let httpMock: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule],
      providers:[FavoriteService]
    });
    service = TestBed.inject(FavoriteService);
    httpMock= TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should have addBook method',()=>{
    expect(service.addBook).toBeTruthy()
  })
  it('should have deleteBook method',()=>{
    expect(service.deleteBook).toBeTruthy()
  })
  it('should have getBooks method',()=>{
    expect(service.getBooks).toBeTruthy()
  })
  it('should have getAuthors method',()=>{
    expect(service.getAuthors).toBeTruthy()
  })
  it('should have getFavoriteAuthors method',()=>{
    expect(service.getFavoriteAuthor).toBeTruthy()
  })
  it('should addBook to user and return a booklist',()=>{
    
    let booklist:Book[]=[{ title:"thi",
      author:"string",
      subject:"string",
      cover:"null",
      isbn!:"string"}]
      
      service.addBook(1,new Book(),'token').subscribe(data=>{
        expect(data.length).toEqual(1);
        expect(data[0].author).toEqual('string')

      })
      let req= httpMock.expectOne('http://localhost:'+environment.port+'/favorite/api/v1/books/1');
      expect(req.request.method).toBe('POST');
      req.flush(booklist);
  })
  it('should deleteBook for user and return a booklist',()=>{

    service.deleteBook(1,'1','token').subscribe((data:any) =>{
      expect(data.length).toEqual(0)
    })

    let req=httpMock.expectOne('http://localhost:'+environment.port+'/favorite/api/v1/books/1/1');
    expect(req.request.method).toBe('DELETE');
    req.flush([]);
  })
  it('should getBook then return a booklist',()=>{
    let booklist:Book[]=[{ title:"thi",
    author:"string",
    subject:"string",
    cover:"null",
    isbn!:"string"},{
      title:'r',author:'e',subject:'d',cover:'null',isbn:'d'
    }]
    
    service.getBooks(1,'token').subscribe((data:any)=>{
      expect(data.length).toEqual(2);
    })
    let req=httpMock.expectOne('http://localhost:'+environment.port+'/favorite/api/v1/books/1');
    expect(req.request.method).toBe('GET');
    req.flush(booklist);

  })
  it('should getAuthor then return a authorList',()=>{
    let author:string[]=["this","that"]
    
    service.getAuthors(1,'token').subscribe((data:any)=>{
      expect(data.length).toEqual(2);
    })
    let req=httpMock.expectOne('http://localhost:'+environment.port+'/favorite/api/v1/books/1/authors');
    expect(req.request.method).toBe('GET');
    req.flush(author);

  })
  it('should getFavoriteAuthor then return a single author',()=>{
    let author:string="michael"
    
    service.getFavoriteAuthor(1,'token').subscribe((data:any)=>{
      expect(data).toEqual("michael");
    })
    let req=httpMock.expectOne('http://localhost:'+environment.port+'/favorite/api/v1/books/1/author');
    expect(req.request.method).toBe('GET');
    req.flush(author);

  })
    
});
