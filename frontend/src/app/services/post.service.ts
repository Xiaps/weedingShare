import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiUrl = 'http://localhost:8080/api/posts';

  constructor(private http: HttpClient) {}

  addPost(image: File, comment?: string): Observable<any> {
    const formData = new FormData();
    formData.append('image', image);
    if (comment) {
      formData.append('comment', comment);
    }

    return this.http.post(`${this.apiUrl}/add`, formData);
  }
}
