import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {CommonModule, NgIf} from '@angular/common';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    NgIf
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  user = { username: '', password: '' };
  message: string | null = null;

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    this.http.post('/api/chat/register', this.user)
      .subscribe(response => {
        this.message = 'User registered successfully';
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000); // Redirection après 2 secondes
      }, error => {
        this.message = 'Error registering user';
      });
  }
}
