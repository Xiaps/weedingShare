
import { Component } from '@angular/core';
import { PostService } from '../../services/post.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-publish',
  templateUrl: './publish.component.html',
  styleUrls: ['./publish.component.scss'],
  imports: [FormsModule]
})
export class PublishComponent {
  selectedImage: File | null = null;
  previewUrl: string | null = null;
  comment: string = '';

  constructor(private postService: PostService) {}

  onFileSelected(event: Event): void {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.selectedImage = fileInput.files[0];

      // Afficher un aperçu de l'image
      const reader = new FileReader();
      reader.onload = () => {
        this.previewUrl = reader.result as string;
      };
      reader.readAsDataURL(this.selectedImage);
    }
  }

  removeImage(): void {
    this.selectedImage = null;
    this.previewUrl = null;
  }

  submitPost(): void {
    if (!this.selectedImage) {
      alert('Veuillez sélectionner une image.');
      return;
    }

    this.postService.addPost(this.selectedImage, this.comment).subscribe({
      next: response => {
        console.log('Post ajouté avec succès', response);
        alert('Post ajouté avec succès !');
        this.selectedImage = null;
        this.previewUrl = null;
        this.comment = '';
      },
      error: error => {
        console.error('Erreur lors de l\'ajout du post', error);
        alert('Erreur lors de l\'ajout du post');
      }
    });
  }
}