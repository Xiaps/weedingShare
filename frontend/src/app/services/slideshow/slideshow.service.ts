import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class SlideshowService {
  private stompClient: Client
  public postSubject = new BehaviorSubject<any>(null);

  constructor() {
    this.stompClient = new Client({
      brokerURL: environment.wsUrl, // Connexion WebSocket STOMP
      reconnectDelay: 5000, // Reconnexion automatique après 5s
      heartbeatIncoming: 4000, // Ping de connexion
      heartbeatOutgoing: 4000,
    });

    this.stompClient.onConnect = () => {
      console.log('Connected to WebSocket');
      this.subscribeToUpdates();
    };

    this.stompClient.onWebSocketError = (error) => {
      console.error('WebSocket Error:', error);
    };

    this.stompClient.activate(); // Démarrer la connexion
  }

  private subscribeToUpdates() {
    console.log("Je m'abonne");
    
    this.stompClient.subscribe('/topic/slideshow', (message) => {
      console.log('New Slide:', JSON.parse(message.body));
      this.postSubject.next(JSON.parse(message.body));
    });
  }


  disconnect() {
    if (this.stompClient) {
      this.stompClient.deactivate();
    }
  }

  
}
