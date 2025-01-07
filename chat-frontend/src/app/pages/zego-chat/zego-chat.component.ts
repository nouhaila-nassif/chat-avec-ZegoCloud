import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {FormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-zego-chat',
  standalone: true,
  templateUrl: './zego-chat.component.html',
  imports: [
    FormsModule,
    NgIf,
    NgForOf
  ],
  styleUrls: ['./zego-chat.component.css']
})
export class ZegoChatComponent implements OnInit {
  users: string[] = []; // Liste des utilisateurs connectés
  messages: string[] = []; // Liste des messages envoyés
  messageInput: string = ''; // Entrée du message à envoyer
  roomID: string = 'room123'; // ID de la salle de chat
  userID: string = 'user' + Math.floor(Math.random() * 10000); // ID utilisateur unique
  userName: string = 'user' + this.userID; // Nom utilisateur

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.loadScript('https://unpkg.com/@zegocloud/zego-uikit-prebuilt/zego-uikit-prebuilt.js', () => {
      this.initializeChat();
    });
  }

  loadScript(src: string, callback: () => void): void {
    const script = document.createElement('script');
    script.src = src;
    script.onload = callback;
    document.head.appendChild(script);
  }

  initializeChat(): void {
    const appID = 1739124010; // Remplacez par votre AppID Zego
    const serverSecret = '8efff54c767a41e3ab12ebfe05b10578'; // Remplacez par votre ServerSecret Zego

    // Générer un token de connexion pour rejoindre la salle de chat
    const kitToken = (window as any).ZegoUIKitPrebuilt.generateKitTokenForTest(appID, serverSecret, this.roomID, this.userID, this.userName);

    // Créer et rejoindre la salle de chat
    const zp = (window as any).ZegoUIKitPrebuilt.create(kitToken);
    zp.joinRoom({
      container: document.querySelector("#root"), // Container HTML pour afficher la salle
      sharedLinks: [{
        name: 'Personal link',
        url: window.location.protocol + '//' + window.location.host + window.location.pathname + '?roomID=' + this.roomID,
      }],
      scenario: {
        mode: (window as any).ZegoUIKitPrebuilt.ChatRoom, // Mode Chat uniquement (sans vidéo ni audio)
      },
      // Désactiver complètement la vidéo et l'audio
      turnOnMicrophoneWhenJoining: false,
      turnOnCameraWhenJoining: false,
      showMyCameraToggleButton: false,
      showMyMicrophoneToggleButton: false,
      showAudioVideoSettingsButton: false,
      showScreenSharingButton: false,
      showTextChat: true, // Activer le chat texte
      showUserList: true,
      maxUsers: 10, // Limite de participants (en fonction de vos besoins)
      layout: "Auto",
      showLayoutButton: false,
      onUserJoin: (user: any) => {
        this.users.push(user.userName); // Ajouter les utilisateurs à la liste
      },
      onUserLeave: (user: any) => {
        this.users = this.users.filter(u => u !== user.userName); // Retirer un utilisateur qui quitte
      },
      onTextMessageReceived: (message: any) => {
        this.messages.push(message.text); // Ajouter les messages reçus à la liste
      }
    });
  }

  sendMessage(): void {
    if (this.messageInput.trim()) {
      const message = this.messageInput.trim();
      this.messages.push(message); // Ajouter le message envoyé à la liste
      (window as any).ZegoUIKitPrebuilt.sendTextMessage(message); // Envoi du message texte via Zego
      this.messageInput = ''; // Réinitialiser l'entrée du message
    }
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
