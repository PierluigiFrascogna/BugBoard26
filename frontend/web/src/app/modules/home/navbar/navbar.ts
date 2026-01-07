import { Component, computed, inject } from '@angular/core';
import { RouterLink } from "@angular/router";
import { AuthStore } from '../../../core/auth/auth-store';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  private readonly authStore = inject(AuthStore)
  //readonly isAdmin = computed(() => this.authStore.role()==="admin");
  readonly isAdmin = computed(() => true);
}
