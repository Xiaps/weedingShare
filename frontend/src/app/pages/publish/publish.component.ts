import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-publish',
  templateUrl: './publish.component.html',
  styleUrls: ['./publish.component.scss'],
  imports: [FormsModule]
})
export class PublishComponent {
  public selectedImage: string | ArrayBuffer | null = null;
  public comment: string = '';

  // Fonction pour gérer la sélection d'une image
  onImageSelect(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.selectedImage = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  // Fonction pour supprimer l'image sélectionnée
  onDeleteImage() {
    this.selectedImage = null;
  }

  // Fonction pour publier le post
  onPublish() {
    console.log('Post published with comment:', this.comment);
    // Ici, tu peux ajouter la logique pour envoyer le post à ton serveur
  }
}
