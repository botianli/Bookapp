import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LoginService } from './login.service';

describe('LoginService', () => {
  let service: LoginService;
  let httpMock: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule,RouterTestingModule]
    });
    service = TestBed.inject(LoginService);
    httpMock= TestBed.get(HttpTestingController);

  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should create login method',()=>{
    expect(service.login).toBeTruthy();
  })
  it('should create logout method',()=>{
    expect(service.logout).toBeTruthy();
  })
  it('should create uploadImage method',()=>{
    expect(service.uploadImage).toBeTruthy();
  })
  it('should create download method',()=>{
    expect(service.downloadImage).toBeTruthy();
  })
  it('should create loggedIn method',()=>{
    expect(service.loggedIn).toBeTruthy();
  })
  it('should return true for loggedIn method',()=>{
    expect(service.loggedIn()).toEqual(true)
  
    spyOn(service,'loggedIn').and.returnValue(true)

  })
});
