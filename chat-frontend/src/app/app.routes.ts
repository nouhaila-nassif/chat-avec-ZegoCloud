import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ChatComponent } from './pages/chat/chat.component';
import { ZegoChatComponent } from './pages/zego-chat/zego-chat.component'; // Assurez-vous que le chemin est correct
import { AuthGuard } from './auth.guard';

export const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'chat', component: ChatComponent, canActivate: [AuthGuard] }, // Vous pouvez aussi protéger la route si nécessaire
  { path: 'zego-chat', component: ZegoChatComponent, canActivate: [AuthGuard] }, // Ajoutez la route pour ZegoChat
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
