import { AfterViewInit, Component, Input, OnInit, Output, ViewChild , EventEmitter} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Book } from 'src/app/model/book';

@Component({
  selector: 'app-booklist',
  templateUrl: './booklist.component.html',
  styleUrls: ['./booklist.component.scss']
})
export class BooklistComponent implements AfterViewInit {

  dataSource!: MatTableDataSource<Book>;
  @Output() deleteEvent= new EventEmitter<Book>();
  @Input() set data(bookdata:Book[]){
    this.dataSource= new MatTableDataSource<Book>(bookdata);
  };


  constructor(){}
  displayedColumns: string[]=['Title','Author','Subject','Delete'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;

    console.log('Book list loaded')

}
deleteBook(book:Book){

    this.deleteEvent.emit(book);


}

}
