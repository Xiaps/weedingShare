import { Routes } from '@angular/router';

// Importation des composants de pages
import { HomeComponent } from './pages/home/home.component';  // Assure-toi que le chemin est correct
import { DiaporamaComponent } from './pages/diaporama/diaporama.component';  // Assure-toi que le chemin est correct
import { PublishComponent } from './pages/publish/publish.component';  // Assure-toi que le chemin est correct
import { AdminComponent } from './pages/admin/admin.component';  // Assure-toi que le chemin est correct
import { PostsComponent } from './pages/posts/posts.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },  // Page d'accueil
  { path: 'diaporama', component: DiaporamaComponent },  // Page diaporama
  { path: 'publish', component: PublishComponent },  // Page publication
  { path: 'posts', component: PostsComponent },  // Page publication
  { path: 'admin', component: AdminComponent },  // Page administration
];
