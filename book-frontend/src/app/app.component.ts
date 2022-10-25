import { Component, HostListener } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent {
  @HostListener("window:onbeforeunload",["$event"])
  clearLocalStorage(){
      localStorage.clear();
  }
  title = 'book-app';
}
