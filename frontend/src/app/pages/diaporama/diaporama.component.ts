import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { SlideshowService } from '../../services/slideshow/slideshow.service';
import { CommonModule } from '@angular/common';
import { environment } from '../../../environments/environment.development';

@Component({
  selector: 'app-diaporama',
  imports: [CommonModule],
  templateUrl: './diaporama.component.html',
  styleUrl: './diaporama.component.scss'
})
export class DiaporamaComponent implements OnInit {
  currentPost: any;
  nextPost: any;
  fadeOut = false;
  nextImage = new Image(); // Objet pour précharger l’image
  environment = environment

  constructor(private slideshowService: SlideshowService) {}

  ngOnInit(): void {
    this.slideshowService.postSubject.subscribe(posts => {
      if (posts) {
        this.nextPost = posts.nextPost;
        this.preloadNextImage(environment.serverUrl + "/" + this.nextPost.imageUrl); // Préchargement
        this.fadeOut = true;
        setTimeout(() => {
          this.currentPost = this.nextPost; // Changer l’image après l’animation
          this.fadeOut = false;
        }, 1000);
      }
    });
  }

  preloadNextImage(imageUrl: string) {
    this.nextImage.src = imageUrl;
  }

  onImageLoad() {
    console.log("Image affichée !");
  }
}