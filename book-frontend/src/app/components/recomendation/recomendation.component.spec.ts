import {  HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FavoriteService } from 'src/app/services/favorite.service';
import { SearchService } from 'src/app/services/search.service';

import { RecomendationComponent } from './recomendation.component';

describe('RecomendationComponent', () => {
  let component: RecomendationComponent;
  let fixture: ComponentFixture<RecomendationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecomendationComponent ],
      imports:[HttpClientModule],
      providers:[FavoriteService,SearchService]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecomendationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
