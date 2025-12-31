import { Component } from '@angular/core';
import { Sidebar } from './sidebar/sidebar';
import { Navbar } from './navbar/navbar'
import { ContentView } from "./content-view/content-view";


@Component({
  selector: 'app-home',
  imports: [Sidebar, Navbar, ContentView],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  isSidebarOpen=true;

  onSidebarToggle(open:boolean){
    this.isSidebarOpen=open;
  }
}
