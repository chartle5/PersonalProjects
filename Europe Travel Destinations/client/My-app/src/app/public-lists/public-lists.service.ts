import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PublicListService {
  private apiUrl = '/api/public-lists'; 
  private savedListsApiUrl = '/api/lists';
  private reviewApiUrl = '/api/reviews';

  constructor(private http: HttpClient) {}

  getPublicLists(headers: any) {
    const options = { headers: new HttpHeaders(headers) };
    return this.http.get<any[]>(this.apiUrl, options);
  }

  getSavedLists(token: string) {
    return this.http.get<any[]>('/api/lists', {
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  postPublicList(name: string, destinations: any[], token: string) {
    return this.http.post(this.apiUrl, { name, destinations }, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }



  deletePublicList(id: string, token: string): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.delete(`${this.apiUrl}/${id}`, { headers });
  }

  
  postReview(listId: string, rating: number, comment: string, token: string): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    const body = {
      rating,
      comment,
    };

    console.log('Posting review:', body);
    return this.http.post<any>(`${this.reviewApiUrl}/${listId}/reviews`, body, { headers }); 
  }
}

