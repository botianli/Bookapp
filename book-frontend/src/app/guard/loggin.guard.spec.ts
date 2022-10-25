import {  HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HotToastService } from '@ngneat/hot-toast';
import { LoginService } from '../services/login.service';

import { LogginGuard } from './loggin.guard';

describe('LogginGuard', () => {
  let guard: LogginGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientModule,RouterTestingModule],
      providers:[LoginService,HotToastService]
    });
    guard = TestBed.inject(LogginGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
