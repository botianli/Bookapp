import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HotToastModule, HotToastService } from '@ngneat/hot-toast';
import { of } from 'rxjs';
import { FavoriteService } from 'src/app/services/favorite.service';

import { AuthorComponent } from './author.component';

describe('AuthorComponent', () => {
  let component: AuthorComponent;
  let fixture: ComponentFixture<AuthorComponent>;
  let favoriteService:FavoriteService;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorComponent ],
      imports:[RouterTestingModule,HttpClientModule],
      providers:[FavoriteService,HotToastService]
    })
    .compileComponents();
    favoriteService=TestBed.get(FavoriteService);
    
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
