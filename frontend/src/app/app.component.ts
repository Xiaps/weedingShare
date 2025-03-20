import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'WeedingShare';

  showHeader = true;

  constructor(private router: Router) {
    this.router.events.subscribe(() => {
      this.showHeader = this.router.url !== '/diaporama';
    });
  }
}
