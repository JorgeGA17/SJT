import { Injectable } from '@angular/core';
import { jwtDecode } from "jwt-decode";
import { LocalStorageService } from '../../services/local/local-storage.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthValidationService {

  constructor(private localStorageService: LocalStorageService, private router: Router) { 

  }

  tokenJWTEsValido():boolean {
    const token = this.localStorageService.getToken();
    if(!token){
      return false;
    }
    const parts = token.split('.');
    if (parts.length !== 3) return false;

    try {
      const header = JSON.parse(atob(parts[0]));
      const payload = JSON.parse(atob(parts[1]));
      if(typeof header !== 'object' || typeof payload !== 'object'){
        return false;
      }
      const decoded = jwtDecode(token);
      console.log("token decodificado",decoded);
      return true;
      //return typeof header === 'object' && typeof payload === 'object';
    } catch (e) {
      return false;
    }
  }

  goLogin(){
    this.router.navigate(['/autenticacion/login']);
  }
}
