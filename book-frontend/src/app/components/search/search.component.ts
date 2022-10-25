import { AfterViewInit, Component, OnInit,ViewChild } from '@angular/core';
import { Book } from 'src/app/model/book';
import { FormControl } from '@angular/forms';
import { SearchService } from 'src/app/services/search.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements AfterViewInit {

  input = new FormControl('');
  options!:string[];
  loaded:boolean=false;

  showNotfound:boolean=false;
  showLoading:boolean=false;
  bookData!:Book[];
  selectedOption= new FormControl('Title');
  constructor(private searchService:SearchService,private route:Router) {

   }
  ngAfterViewInit(): void {

  }

  ngOnInit(): void {
    this.options=[
      "Title","Author"
    ]
  }
  passData(){

  }
  onSubmit(): void{
    this.loaded=false;
    this.showLoading=true;
    this.showNotfound=false;
    // mocking fetching data from service
    // this.bookData=this.searchService.getResult();
    this.searchService.getResult(this.selectedOption.value,this.input.value).subscribe((data:any)=>
    {
      this.bookData=data;
      this.showLoading=false;

      if(data.length==0){
        this.showNotfound=true;
        this.loaded=false;

      }else{
        this.loaded=true;
        this.showNotfound=false;

      }
    },error=>{
      this.showNotfound=true;
      this.loaded=false;
      this.showLoading=false;
      this.route.navigateByUrl('/error')
    })

  }

}
