import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class user {
  private apiUrl = 'http://localhost:8080/api/chat'; // Assurez-vous que cette URL correspond à votre backend

  constructor(private http: HttpClient) {}

  // Vérifie si l'utilisateur est connecté
  isAuthenticated(): boolean {
    return !!this.getToken(); // Si un token existe, l'utilisateur est authentifié
  }

  // Méthode pour récupérer le token
  getToken(): string | null {
    try {
      return localStorage.getItem('token');
    } catch (e) {
      console.error('Failed to retrieve token:', e);
      return null;
    }
  }

  login(credentials: { username: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    });
  }
  register(user: { username: string; email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    });
  }

  // Méthode pour enregistrer le token
  saveToken(token: string): void {
    try {
      localStorage.setItem('token', token);
    } catch (e) {
      console.error('Failed to save token:', e);
    }
  }

  logout(): void {
    try {
      localStorage.removeItem('token');
    } catch (e) {
      console.error('Failed to remove token:', e);
    }
  }
}
