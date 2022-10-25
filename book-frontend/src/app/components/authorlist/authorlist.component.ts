import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-authorlist',
  templateUrl: './authorlist.component.html',
  styleUrls: ['./authorlist.component.scss']
})
export class AuthorlistComponent implements AfterViewInit {

  constructor() { }
  dataSource!: MatTableDataSource<string>;
 
  displayedColumns: string[]=['Name'];
  
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  @Input() set data(authordata:string[]){
    this.dataSource= new MatTableDataSource<string>(authordata);
  };

  ngAfterViewInit()  {
    this.dataSource.paginator = this.paginator;
  }

}
