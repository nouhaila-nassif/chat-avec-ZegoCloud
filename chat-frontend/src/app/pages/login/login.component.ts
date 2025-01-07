import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'; // Importer FormsModule
import {CommonModule, NgIf} from '@angular/common';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})

export class LoginComponent {
  user = { username: '', password: '' };
  message: string | null = null;

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    this.http.post('/api/chat/login', this.user)
      .subscribe((response: any) => {
        this.message = 'User logged in successfully';
        localStorage.setItem('token', response.token); // Stocker le token
        setTimeout(() => {
          this.router.navigate(['/chat']);
        }, 2000); // Redirection après 2 secondes
      }, error => {
        this.message = 'Error logging in user';
      });
  }
}
