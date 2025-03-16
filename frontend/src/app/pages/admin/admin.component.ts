import { Component } from '@angular/core';
import { Post } from '../../models/post.model';
import { PostService } from '../../services/post.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin',
  imports: [CommonModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent {
  posts: Post[] = [];
  loading = true;
  errorMessage = '';

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(): void {
    this.loading = true;
    this.postService.getPosts().subscribe({
      next: (data) => {
        this.posts = data;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors du chargement des posts.';
        console.error(error);
        this.loading = false;
      }
    });
  }

  deletePost(id: string): void {
    if (confirm('Voulez-vous vraiment supprimer ce post ?')) {
      this.postService.deletePost(id).subscribe({
        next: () => {
          this.posts = this.posts.filter(post => post.id !== id);
        },
        error: (error) => {
          alert('Erreur lors de la suppression du post.');
          console.error(error);
        }
      });
    }
  }
}
