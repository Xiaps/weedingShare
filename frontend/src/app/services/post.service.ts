import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { Post } from '../models/post.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private postEndpoint = '/posts';

  constructor(private http: HttpClient) {}

  addPost(image: File, comment?: string): Observable<any> {
    const formData = new FormData();
    formData.append('image', image);
    if (comment) {
      formData.append('comment', comment);
    }

    return this.http.post(`${environment.apiUrl + this.postEndpoint}`, formData);
  }

  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(environment.apiUrl + this.postEndpoint);
  }

  deletePost(id: string): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl + this.postEndpoint}/${id}`);
  }
}
