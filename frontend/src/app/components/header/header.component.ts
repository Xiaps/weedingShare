import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  isActive = false;

  constructor(protected router: Router) {
    router.events.subscribe(() => this.isActive = false)
  }

  toggleMenu() {
    this.isActive = !this.isActive;
  }
}
