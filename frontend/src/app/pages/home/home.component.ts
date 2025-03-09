import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  constructor(private router: Router) {}

  navigateToDiaporama() {
    this.router.navigate(['/diaporama']);  // Remplace par le chemin correct
  }

  navigateToPublish() {
    this.router.navigate(['/publish']);  // Remplace par le chemin correct
  }
}
