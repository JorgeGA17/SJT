import { Injectable } from '@angular/core';
//import * as CryptoJS from 'crypto-js';
import { Observable, from, map, catchError, of, switchMap, throwError } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class CryptoService {

  constructor() { }

  /*
  private readonly secretKey = 'clave_super_secreta';// crearlo de manera segura

  // Cifra cualquier objeto o string y lo devuelve como string
  encrypt(data: any): string {
    const dataString = typeof data === 'string' ? data : JSON.stringify(data);
    return CryptoJS.AES.encrypt(dataString, this.secretKey).toString();
  }

  //Descifra un string encriptado y lo devuelve como objeto o string
  decrypt<T = any>(encryptedData: string): T | null {
    try {
      const bytes = CryptoJS.AES.decrypt(encryptedData, this.secretKey);
      const decrypted = bytes.toString(CryptoJS.enc.Utf8);
      return decrypted ? JSON.parse(decrypted) : null;
    } catch (error) {
      console.error('Error al descifrar datos:', error);
      return null;
    }
  }*/

  private readonly encoder = new TextEncoder();
  private readonly decoder = new TextDecoder();

  // entre 16 a 32 caracteres
  private readonly password = "bqR2KdGgcr4YAemywPlHQu0H4Vh3tg77";
  private readonly salt = this.encoder.encode("GPMG6hX6MhgYXT8qcrCAiylPg0LTcOFN");
  //private password: string | null = null;
  //private salt: Uint8Array | null = null;

  //Establece la clave de cifrado y opcionalmente el salt (debería ser aleatorio por sesión) dinamico
  /*setEncryptionKey(password: string, salt?: string) {
    this.password = password;
    this.salt = salt ? this.encoder.encode(salt) : crypto.getRandomValues(new Uint8Array(16));
  }*/

  private deriveKey(): Observable<CryptoKey> {
    if (!this.password || !this.salt) {
      return throwError(() => new Error('Clave o salt no definidos. Llama primero a setEncryptionKey().'));
    }

    const keyMaterial$ = from(
      window.crypto.subtle.importKey(
        'raw',
        this.encoder.encode(this.password),
        'PBKDF2',
        false,
        ['deriveKey']
      )
    );

    return keyMaterial$.pipe(
      switchMap(keyMaterial =>
        from(
          window.crypto.subtle.deriveKey(
            {
              name: 'PBKDF2',
              salt: this.salt!,
              iterations: 100000,
              hash: 'SHA-256',
            },
            keyMaterial,
            { name: 'AES-GCM', length: 256 },
            false,
            ['encrypt', 'decrypt']
          )
        )
      )
    );
  }

  encrypt(data: any): Observable<string> {
    const encoded = this.encoder.encode(JSON.stringify(data));
    const iv = crypto.getRandomValues(new Uint8Array(12));

    return this.deriveKey().pipe(
      switchMap(key =>
        from(
          window.crypto.subtle.encrypt(
            { name: 'AES-GCM', iv },
            key,
            encoded
          )
        ).pipe(
          map(encrypted => {
            const encryptedArray = new Uint8Array(encrypted);
            const fullData = new Uint8Array(iv.length + encryptedArray.length);
            fullData.set(iv);
            fullData.set(encryptedArray, iv.length);
            return btoa(String.fromCharCode(...fullData));
          })
        )
      )
    );
  }

  decrypt<T = any>(encryptedBase64: string): Observable<T | null> {
    const fullData = Uint8Array.from(atob(encryptedBase64), c => c.charCodeAt(0));
    const iv = fullData.slice(0, 12);
    const data = fullData.slice(12);

    return this.deriveKey().pipe(
      switchMap(key =>
        from(
          window.crypto.subtle.decrypt(
            { name: 'AES-GCM', iv },
            key,
            data
          )
        ).pipe(
          map(decrypted => {
            const decoded = this.decoder.decode(decrypted);
            return JSON.parse(decoded) as T;
          }),
          catchError(error => {
            console.error('Error al descifrar:', error);
            return of(null);
          })
        )
      )
    );
  }
}
