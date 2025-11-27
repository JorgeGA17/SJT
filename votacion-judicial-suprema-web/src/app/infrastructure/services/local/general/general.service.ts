import { Injectable } from '@angular/core';
import { constantes } from 'src/app/constants';
import { CasacionModel } from 'src/app/domain/models/Casacion.model';

@Injectable({
    providedIn: 'root'
})
export class GeneralService {
    public readonly FECHA_SEL = constantes.FECHA_SELECCIONADA;
    public readonly CASACION_SEL = constantes.CASACION_SELECCIONADA;

    constructor() { }

    setFechaSeleccionada(fecha: Date) {
        localStorage.setItem(this.FECHA_SEL,fecha.toISOString());
    }

    getFechaSeleccionada() {
        let fechaSel: string | null = localStorage.getItem(this.FECHA_SEL);
        if (fechaSel) {
            return new Date(fechaSel);
        } else {    
            return null;
        }
    }

    setCasacionSeleccionada(cas : CasacionModel) {
        //console.log("guardando casación actual en local", cas);
        localStorage.setItem(this.CASACION_SEL,JSON.stringify(cas));
    }

    clearCasacionSeleccionada() {
        localStorage.setItem(this.CASACION_SEL,'');
    }

    getCasacionSeleccionada() {
        let casacionLocal: string | null  = localStorage.getItem(this.CASACION_SEL);
        //console.log("recuperando casación actual en local", casacionLocal);
        if (casacionLocal) {
            return JSON.parse(casacionLocal);
        } else {
            return null;
        }
    }

    clear() {
        localStorage.clear();
    }

}
