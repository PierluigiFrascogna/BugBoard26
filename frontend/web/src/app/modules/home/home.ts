import { Component } from '@angular/core';
import { Sidebar } from './sidebar/sidebar';
import { Navbar } from './navbar/navbar'


@Component({
  selector: 'app-home',
  imports: [Sidebar, Navbar],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
}
