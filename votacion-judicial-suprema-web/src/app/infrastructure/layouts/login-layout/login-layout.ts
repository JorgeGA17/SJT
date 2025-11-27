import { Component, OnInit, ChangeDetectorRef, HostListener } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { CommonModule, Location } from '@angular/common'
import { AppVjState } from '../../global-store/vj.reducers';
import * as actions from '../../global-store/vj.actions';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { AuthService } from '../../services/remote/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login-layout',
   imports: [RouterOutlet, MatButtonModule, MatIconModule, CommonModule],
  templateUrl: './login-layout.html',
  styleUrl: './login-layout.scss'
})
export class LoginLayout implements OnInit{
  opcultarBack =  true;
  brandId = 0;
  cargarBrandIdSubs: Subscription = new Subscription();

  screenWidth: number = 0;
  isMobile = false;
   constructor(private location: Location,
    private store: Store<AppVjState>,
    private cdr: ChangeDetectorRef,
    private authService: AuthService){
   }
   ngOnInit(): void {
    this.screenWidth = window.innerWidth;
    this.checkScreenWidth();
   }

   goBack():void{
    this.location.back()
  }
  
  @HostListener('window:resize', ['$event'])
  onResize(event: Event): void {
    this.screenWidth = window.innerWidth;
    this.checkScreenWidth();
  }

  checkScreenWidth(): void {
    if (this.screenWidth > 768) {
      this.isMobile = false;
    } else {
      this.isMobile = true;
    }
  }
}

