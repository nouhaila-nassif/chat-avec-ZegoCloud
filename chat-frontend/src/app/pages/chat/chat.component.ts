import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule
  ],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  users: string[] = [];

  constructor(private router: Router) {}

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
    function getUrlParams(url: string): { [key: string]: string } {
      let urlStr = url.split('?')[1];
      const urlSearchParams = new URLSearchParams(urlStr);
      const result: { [key: string]: string } = {};
      urlSearchParams.forEach((value, key) => {
        result[key] = value;
      });
      return result;
    }

    const roomID = getUrlParams(window.location.href)['roomID'] || (Math.floor(Math.random() * 10000) + "");
    const userID = Math.floor(Math.random() * 10000) + "";
    const userName = "userName" + userID;
    const appID = 1739124010; // Remplacez par votre appID
    const serverSecret = '8db7d82f916a521ccdca4661016bce29'; // Remplacez par votre serverSecret
    const kitToken = (window as any).ZegoUIKitPrebuilt.generateKitTokenForTest(appID, serverSecret, roomID, userID, userName);

    const zp = (window as any).ZegoUIKitPrebuilt.create(kitToken);
    zp.joinRoom({
      container: document.querySelector("#root"),
      sharedLinks: [{
        name: 'Personal link',
        url: window.location.protocol + '//' + window.location.host + window.location.pathname + '?roomID=' + roomID,
      }],
      scenario: {
        mode: (window as any).ZegoUIKitPrebuilt.VideoConference,
      },
      turnOnMicrophoneWhenJoining: true,
      turnOnCameraWhenJoining: true,
      showMyCameraToggleButton: true,
      showMyMicrophoneToggleButton: true,
      showAudioVideoSettingsButton: true,
      showScreenSharingButton: true,
      showTextChat: true,
      showUserList: true,
      maxUsers: 2,
      layout: "Auto",
      showLayoutButton: false,
      onUserJoin: (user: any) => {
        this.users.push(user.userName);
      },
      onUserLeave: (user: any) => {
        this.users = this.users.filter(u => u !== user.userName);
      }
    });
  }

  logout(): void {
    localStorage.removeItem('token'); // Supprimer le jeton de session
    this.router.navigate(['/login']); // Rediriger vers la page de connexion
  }
}
