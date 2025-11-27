import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { DocumentosModel, RequestGenerarTablillaModel, TablillaModel, MagistradoModel, RequestEnviarDocumento, RequestValidarDocumento } from 'src/app/domain/models/Casacion.model';
import { CasacionService } from 'src/app/infrastructure/services/remote/casacion/casacion.service';

import { AppVJState } from '../../../../../infrastructure/global-store/vj.reducers';
import { Store } from '@ngrx/store';
import Swal from 'sweetalert2';
import * as actions from '../../../../../infrastructure/global-store/vj.actions';
import { constantes } from 'src/app/constants';
import { JurisprudenciaService } from 'src/app/infrastructure/services/remote/herramientas/jurisprudencia.service';
import { LoginService } from 'src/app/infrastructure/services/remote/autenticacion/login.service';
import { DataProyectoModel, EstadoModel, ProyectoModel } from 'src/app/domain/models/Proyectos.model';
import { ProyectosService } from 'src/app/infrastructure/services/remote/casacion/proyectos.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';


@Component({
  selector: 'app-validar',
  templateUrl: './validar.component.html',
  styleUrls: ['./validar.component.scss']
})
export class ValidarComponent {

  reqEnviarDocumento : RequestEnviarDocumento;
  reqValidarDocumento : RequestValidarDocumento;

  listaProyectos : ProyectoModel[];
  listaRelacionados : ProyectoModel[];
  listaMagistrados: MagistradoModel[];

  reqGenerarTab : RequestGenerarTablillaModel;
  tablillaPro : TablillaModel;

  proyectoSeleccionado: DataProyectoModel;

  usuarioWeb : string = '';
  dialogTab : boolean = false;
  dialogDoc : boolean = false;
  modeValida : boolean = false;

  observacionDoc : string = '';

  documentosEncontrados!: DocumentosModel[];
  documentosAdjuntos!: DocumentosModel[];

  tipoBusquedaJuris: string = '';
  busqJurisprudencia: string = '';

  listaVacia : string[] = [];

  displayModal: boolean = false;
  base64Pdf: string = '';
  pdfSafeUrl: SafeResourceUrl | undefined;

  base64Documento: string = '';

  // file
  selectedFiles?: FileList;
  progress = 0;
  message = '';

  existeTabla: boolean = true;

  private distritoUsuario: string = '';
  private provinciaUsuario: string = '';
  private salaUsuario: string = '';
  nombreSalaUsuario: string = '';
  constructor(private router: Router, private loginService: LoginService, private sanitizer: DomSanitizer, private proyectosService: ProyectosService
    ,private store: Store<AppVJState>, private jurisprudenciaService: JurisprudenciaService, private casacionService: CasacionService){
    this.listaProyectos = [];
    this.listaRelacionados = [];
    this.listaMagistrados = [];

    this.distritoUsuario = this.loginService.getDistrito();
    this.provinciaUsuario = this.loginService.getProvincia();
    this.salaUsuario = this.loginService.getSala();
    this.nombreSalaUsuario = this.loginService.getNombreSala();

    this.reqGenerarTab = {
      distrito: '',
      provincia: '',
      instancia: '',
      visualizaPonente: '',
      nUnico: '',
      nIncidente: '',
      fechaIngreso: '',
      programacion: '',
      grupo: '',
      secuencia: '',
      conformacion: '',
      fechaProgramacion: '',
      discordia: '',
      sentido: '',
      votacion: '',
      codUsuario: ''
    };

    this.tablillaPro = {
      descProceso: '',
      descActoProcesalJuez: '',
      fojasJuez: '',
      descFalloJuez: '',
      descActoProcesalProcedencia: '',
      fojasOrigenProcedencia : '',
      descFalloProcedencia : '',
      magistrado1 : '',
      magistrado1Flag : '',
      magistrado2 : '',
      magistrado2Flag : '',
      magistrado3 : '',
      magistrado3Flag : '',
      magistrado4 : '',
      magistrado4Flag : '',
      magistrado5 : '',
      magistrado5Flag : '',
      magistrados : null,
      impedimento1 : '',
      impedimento1Flag : '',
      impedimento2 : '',
      impedimento2Flag : '',
      impedimento3 : '',
      impedimento3Flag : '',
      impedimento4 : '',
      impedimento4Flag : '',
      impedimento5 : '',
      impedimento5Flag : '',
      materias : null,
      causales : null,      
      tipoabrev : '',
      tipoAudiencia : '',
      especialidad : '',
      apuntes : '',
      recurrentes : [] ,
      jurisprudencias : [],
      existe: false
    };

    this.proyectoSeleccionado = {
      codigoEstadoVotacion: '',
      codigoProgramacion: '',
      estadoProyecto: '',
      estadoVotacion: '',
      extension: '',
      fechaEnvio: '',
      fechaIngreso: '',
      fechaProgramacion: '',
      flagDiscordia: '',
      flagPonente: '',
      idEstadoProyecto: '',
      idProyecto: '',
      iniciales: '',
      numeroConformacion: '',
      numeroEnvio: '',
      numeroGrupo: '',
      numeroIncidente: '',
      numeroRecurso: '',
      numeroSecuencia: '',
      numeroSentido: '',
      numeroUnico: '',
      numeroVotacion: '',
      usuarioResponsable: '',
      uuidAlfresco: ''
    }

    this.reqEnviarDocumento = {
      nroIdProyecto: '',
      nUnico: '',
      nIncidente: '',
      sentido: '',
      votacion: '',
      usuarioResponsable: '',
      codEstado: '',
      nombreDocumento: '',
      file: ''
    }

    this.reqValidarDocumento = {
      nroIdProyecto: '',
      codUsuarioValida: '',
      nValidado: '',
      observacion: ''      
    }

    //this.estadoSeleccionado = { code:'',name:'',activo: ''};
    this.usuarioWeb = '';
    this.listarProyectosPorValidar('2');
  }

  limpiarTablilla(): void {
    this.reqGenerarTab = {
      distrito: '',
      provincia: '',
      instancia: '',
      visualizaPonente: '',
      nUnico: '',
      nIncidente: '',
      fechaIngreso: '',
      programacion: '',
      grupo: '',
      secuencia: '',
      conformacion: '',
      fechaProgramacion: '',
      discordia: '',
      sentido: '',
      votacion: '',
      codUsuario: ''
    };
    
    this.tablillaPro = {
      descProceso: '',
      descActoProcesalJuez: '',
      fojasJuez: '',
      descFalloJuez: '',
      descActoProcesalProcedencia: '',
      fojasOrigenProcedencia : '',
      descFalloProcedencia : '',
      magistrado1 : '',
      magistrado1Flag : '',
      magistrado2 : '',
      magistrado2Flag : '',
      magistrado3 : '',
      magistrado3Flag : '',
      magistrado4 : '',
      magistrado4Flag : '',
      magistrado5 : '',
      magistrado5Flag : '',
      magistrados : null,
      impedimento1 : '',
      impedimento1Flag : '',
      impedimento2 : '',
      impedimento2Flag : '',
      impedimento3 : '',
      impedimento3Flag : '',
      impedimento4 : '',
      impedimento4Flag : '',
      impedimento5 : '',
      impedimento5Flag : '',
      materias : null,
      causales : null,      
      tipoabrev : '',
      tipoAudiencia : '',
      especialidad : '',
      apuntes : '',
      recurrentes : [] ,
      jurisprudencias : [],
      existe: false
    };

    this.listaMagistrados = [];
  }

  // cambiarEstado(): void {
  //   if(this.estadoSeleccionado && this.estadoSeleccionado.code){
  //     this.listarProyectosPorValidar(this.estadoSeleccionado.code);
  //   }
  // }

  listarProyectosPorValidar(codigoEst:string): void {
    this.usuarioWeb = this.loginService.getUsuario().codigoUsuario;
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.existeTabla = false;
    this.proyectosService.listarProyectosPorValidar(this.distritoUsuario,this.provinciaUsuario,this.salaUsuario,this.usuarioWeb,codigoEst, null, null).subscribe({
      next:(response:any)=>{
        if(response.codigo===constantes.RESPONSE_COD_EXITO){
          if(response.data){
            this.listaProyectos = response.data;
          }
          else{
            Swal.fire('¡Atención!', "Listar proyectos: Error de respuesta", 'warning');
          }
        }
        else{
          Swal.fire('¡Atención!', response.descripcion, 'warning');
        }
      },
      complete:()=>{
        this.existeTabla = true;
        this.listaRelacionados = [];
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
      },      
      error:(err)=>{
        this.existeTabla = true;
        this.listaProyectos =  [];
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        Swal.fire('¡Atención!', err, 'warning');
      }
    });      
  }

  cargarDetalle(event : any): void {
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.proyectosService.listarRelacionados(event.data.numeroUnico,event.data.numeroIncidente,event.data.numeroSentido,event.data.numeroVotacion,event.data.usuarioResponsable).subscribe({
      next:(response:any)=>{
        if(response.codigo===constantes.RESPONSE_COD_EXITO){
          if(response.data){
            this.listaRelacionados = response.data;
          }
          else{
            Swal.fire('¡Atención!', "Listar proyectos relacionados: Error de respuesta", 'warning');
            this.store.dispatch(actions.mostrarCargando({ estado: false }));
          }
        }
        else{
          Swal.fire('¡Atención!', response.descripcion, 'warning');
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
        }
      },
      complete:()=>{
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
      },      
      error:(err)=>{
        this.listaRelacionados =  [];
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        Swal.fire('¡Atención!', err, 'warning');
      }
    }); 
  }

  verTablilla(tab : DataProyectoModel) :void{
    //limpiar modelo
    this.limpiarTablilla();
    this.reqGenerarTab.distrito = this.distritoUsuario;
    this.reqGenerarTab.provincia = this.provinciaUsuario;
    this.reqGenerarTab.instancia = this.salaUsuario;
    this.reqGenerarTab.visualizaPonente = 'S';
    this.reqGenerarTab.nUnico = tab.numeroUnico;
    this.reqGenerarTab.nIncidente = tab.numeroIncidente;
    this.reqGenerarTab.fechaIngreso = tab.fechaIngreso;
    this.reqGenerarTab.programacion = tab.codigoProgramacion;
    this.reqGenerarTab.grupo = tab.numeroGrupo;
    this.reqGenerarTab.secuencia = tab.numeroSecuencia;
    this.reqGenerarTab.conformacion = tab.numeroConformacion;
    this.reqGenerarTab.fechaProgramacion = tab.fechaProgramacion;
    this.reqGenerarTab.discordia = tab.flagDiscordia;
    this.reqGenerarTab.sentido = tab.numeroSentido;
    this.reqGenerarTab.votacion = tab.numeroVotacion; 
    this.reqGenerarTab.codUsuario = this.usuarioWeb;
    //hacer request de la tablilla -> asignar a modelo
    this.cargarTablilla();
    //abrir dialogo
    this.dialogTab = true;
  }

  cargarTablilla(): void {
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.casacionService.cargarDatosTablilla({...this.reqGenerarTab}).subscribe({
      next:(response:any)=>{
        if(response.codigo===constantes.RESPONSE_COD_EXITO){
          if(response.data){
            this.tablillaPro.descProceso = response.data.juzgado.xproceso?response.data.juzgado.xproceso.trim():'';
            this.tablillaPro.descActoProcesalJuez = response.data.juzgado.xactoProcesalJuz?response.data.juzgado.xactoProcesalJuz.trim():'';
            this.tablillaPro.fojasJuez = response.data.juzgado.nfojasJuz?response.data.juzgado.nfojasJuz:'';
            this.tablillaPro.descFalloJuez = response.data.juzgado.xfalloJuz?response.data.juzgado.xfalloJuz.trim():'';
            this.tablillaPro.descActoProcesalProcedencia = response.data.juzgado.xactoProcesal?response.data.juzgado.xactoProcesal.trim():'';
            this.tablillaPro.fojasOrigenProcedencia  = response.data.juzgado.nfojas?response.data.juzgado.nfojas:'';
            this.tablillaPro.descFalloProcedencia  = response.data.juzgado.xfallo?response.data.juzgado.xfallo.trim():'';
            this.tablillaPro.tipoabrev  = response.data.juzgado.xabrev?response.data.juzgado.xabrev.trim():'';
            this.tablillaPro.especialidad  = response.data.juzgado.cespecialidad?response.data.juzgado.cespecialidad.trim():'';
            this.tablillaPro.apuntes = response.data.apuntes?response.data.apuntes.trim():'';
            this.listaMagistrados = response.data.magistrados;
            this.llenarMagistrados(this.listaMagistrados);
            // this.listarFallos();
            this.tablillaPro.magistrados = response.data.magistrados || null;
            this.tablillaPro.impedimento1  = response.data.impedimento.xusuario1?response.data.impedimento.xusuario1.trim():'';
            this.tablillaPro.impedimento1Flag  = response.data.impedimento.limpedido1?response.data.impedimento.limpedido1.trim():'';
            this.tablillaPro.impedimento2  = response.data.impedimento.xusuario2?response.data.impedimento.xusuario2.trim():'';
            this.tablillaPro.impedimento2Flag  = response.data.impedimento.limpedido2?response.data.impedimento.limpedido2.trim():'';
            this.tablillaPro.impedimento3  = response.data.impedimento.xusuario3?response.data.impedimento.xusuario3.trim():'';
            this.tablillaPro.impedimento3Flag  = response.data.impedimento.limpedido3?response.data.impedimento.limpedido3.trim():'';
            this.tablillaPro.impedimento4  = response.data.impedimento.xusuario4?response.data.impedimento.xusuario4.trim():'';
            this.tablillaPro.impedimento4Flag  = response.data.impedimento.limpedido4?response.data.impedimento.limpedido4.trim():'';
            this.tablillaPro.impedimento5  = response.data.impedimento.xusuario5?response.data.impedimento.xusuario5.trim():'';
            this.tablillaPro.impedimento5Flag  = response.data.impedimento.limpedido5?response.data.impedimento.limpedido5.trim():'';
            this.tablillaPro.materias  = response.data.materias || null;
            this.tablillaPro.causales  = response.data.causales || null;
            this.tablillaPro.recurrentes = response.data.partes;
            this.tablillaPro.recurrentes.sort((a, b) => (a.lrecurrente === b.lrecurrente) ? 0 : a.lrecurrente === "S" ? -1 : 1);
            //this.tempRecurrentes = response.data.partes;
            this.documentosAdjuntos = response.data.jurisprudencias;
            this.tablillaPro.existe = true;
            this.store.dispatch(actions.mostrarCargando({ estado: false }));
          }
          else{
            this.store.dispatch(actions.mostrarCargando({ estado: false }));
            Swal.fire('¡Atención!', "Cargar casación: Error de respuesta", 'warning');
          }
        }
        else{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', response.descripcion, 'warning');
        }
      },
      error:(err)=>{
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        Swal.fire('¡Atención!', err, 'warning');
      }
    });
  }

  llenarMagistrados(magistrados : MagistradoModel[]): void{
    if(magistrados && magistrados.length > 0){
      switch(magistrados.length-1){
        case 0:
          this.tablillaPro.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaPro.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';         
          break;
        case 1:
          this.tablillaPro.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaPro.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';
          this.tablillaPro.magistrado2  = magistrados[1].nombreUsuario?magistrados[1].nombreUsuario.trim():'';
          this.tablillaPro.magistrado2Flag  = magistrados[1].lponente?magistrados[1].lponente.trim():'';       
          break;
        case 2:
          this.tablillaPro.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaPro.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';
          this.tablillaPro.magistrado2  = magistrados[1].nombreUsuario?magistrados[1].nombreUsuario.trim():'';
          this.tablillaPro.magistrado2Flag  = magistrados[1].lponente?magistrados[1].lponente.trim():'';
          this.tablillaPro.magistrado3  = magistrados[2].nombreUsuario?magistrados[2].nombreUsuario.trim():'';
          this.tablillaPro.magistrado3Flag  = magistrados[2].lponente?magistrados[2].lponente.trim():''; 
          break;
        case 3:
          this.tablillaPro.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaPro.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';
          this.tablillaPro.magistrado2  = magistrados[1].nombreUsuario?magistrados[1].nombreUsuario.trim():'';
          this.tablillaPro.magistrado2Flag  = magistrados[1].lponente?magistrados[1].lponente.trim():'';
          this.tablillaPro.magistrado3  = magistrados[2].nombreUsuario?magistrados[2].nombreUsuario.trim():'';
          this.tablillaPro.magistrado3Flag  = magistrados[2].lponente?magistrados[2].lponente.trim():'';
          this.tablillaPro.magistrado4  = magistrados[3].nombreUsuario?magistrados[3].nombreUsuario.trim():'';
          this.tablillaPro.magistrado4Flag  = magistrados[3].lponente?magistrados[3].lponente.trim():'';  
          break;
        case 4:
          this.tablillaPro.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaPro.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';
          this.tablillaPro.magistrado2  = magistrados[1].nombreUsuario?magistrados[1].nombreUsuario.trim():'';
          this.tablillaPro.magistrado2Flag  = magistrados[1].lponente?magistrados[1].lponente.trim():'';
          this.tablillaPro.magistrado3  = magistrados[2].nombreUsuario?magistrados[2].nombreUsuario.trim():'';
          this.tablillaPro.magistrado3Flag  = magistrados[2].lponente?magistrados[2].lponente.trim():'';
          this.tablillaPro.magistrado4  = magistrados[3].nombreUsuario?magistrados[3].nombreUsuario.trim():'';
          this.tablillaPro.magistrado4Flag  = magistrados[3].lponente?magistrados[3].lponente.trim():'';            
          this.tablillaPro.magistrado5  = magistrados[4].nombreUsuario?magistrados[4].nombreUsuario.trim():'';
          this.tablillaPro.magistrado5Flag  = magistrados[4].lponente?magistrados[4].lponente.trim():'';
          break;
      }
    }
  }

  onDialogHide() {
    this.dialogTab = false;
  }


  validaExt(tipo: string, ext: string): boolean {
    let validaTipo : boolean = false;

    if(tipo.toUpperCase() === ext){
      validaTipo = true;
    }

    return validaTipo;
  }

  onPDFDownload(uuid : string) : void {
    
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    if(uuid){
      this.jurisprudenciaService.visualizarPDF(uuid,'PJ').subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data){
              this.base64Pdf = response.data.data;
            } else{
              Swal.fire('¡Atención!', "visualizar documento jurisprudencia: Error de respuesta", 'warning');
            }
          } else{
            Swal.fire('¡Atención!', response.descripcion, 'warning');
          }
        },
        complete:()=>{
          this.createPdfUrl();
          this.displayModal = true;
          // const byteCharacters = atob(pdfBase64);
          // const byteNumbers = new Array(byteCharacters.length);
          // for (let i = 0; i < byteCharacters.length; i++) {
          //   byteNumbers[i] = byteCharacters.charCodeAt(i);
          // }
          // const byteArray = new Uint8Array(byteNumbers);
          // const blob = new Blob([byteArray], { type: 'application/pdf' });
          // // Crear un enlace de descarga
          // const url = window.URL.createObjectURL(blob);
          // const a = document.createElement('a');
          // a.href = url;
          // a.download = 'document.pdf'; 
          // a.click();
          // // Limpiar el objeto URL para liberar memoria
          // window.URL.revokeObjectURL(url);
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
        },
        error:(err)=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });
    } else {
      this.store.dispatch(actions.mostrarCargando({ estado: false }));
      Swal.fire('¡Atención!', 'Documento no disponible', 'warning');
    }
  }

  onWORDDownload(uuid : string) : void {
    let docBase64 : string = "";
    let tipoDoc: string = "";
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    if(uuid){
      this.jurisprudenciaService.visualizarPDF(uuid,'PJ').subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data){
              docBase64 = response.data.data;
              tipoDoc = response.data.tipo;
            } else{
              this.store.dispatch(actions.mostrarCargando({ estado: false }));
              Swal.fire('¡Atención!', "visualizar documento jurisprudencia: Error de respuesta", 'warning');
            }
          } else{
            this.store.dispatch(actions.mostrarCargando({ estado: false }));
            Swal.fire('¡Atención!', response.descripcion, 'warning');
          }
        },
        complete:()=>{
          if(docBase64){
            // Convertir la cadena Base64 a un Blob
            const byteCharacters = atob(docBase64);
            const byteNumbers = new Array(byteCharacters.length);
            for (let i = 0; i < byteCharacters.length; i++) {
              byteNumbers[i] = byteCharacters.charCodeAt(i);
            }
            const byteArray = new Uint8Array(byteNumbers);
            let blob = null;
            if(tipoDoc && tipoDoc == 'doc'){
              blob = new Blob([byteArray], { type: 'application/msword' });
            } else {
              blob = new Blob([byteArray], { type: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' });
            }

            // Crear un enlace de descarga
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            if(tipoDoc && tipoDoc == 'doc'){
              a.download = 'document.doc';
            } else {
              a.download = 'document.docx';
            }
            a.click();

            // Limpiar el objeto URL para liberar memoria
            window.URL.revokeObjectURL(url);
          }
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
        },
        error:(err)=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });
    } else {
      this.store.dispatch(actions.mostrarCargando({ estado: false }));
      Swal.fire('¡Atención!', 'Documento no disponible', 'warning');
    }
  }

  //VISUALIZAR PDF
  previewDoc(uuid: string, origen: string): void {
    //console.log('visualizar previewDOC' , uuid);
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    if(uuid){
      this.jurisprudenciaService.visualizarPDF(uuid,origen).subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data){
              this.base64Pdf = response.data.data;
            } else{
              this.store.dispatch(actions.mostrarCargando({ estado: false }));
              Swal.fire('¡Atención!', "Visualizar documento: Error de respuesta", 'warning');
            }
          } else{
            this.store.dispatch(actions.mostrarCargando({ estado: false }));
            Swal.fire('¡Atención!', response.descripcion, 'warning');
          }
        },
        complete:()=>{
          this.createPdfUrl();
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          this.displayModal = true;
        },
        error:(err)=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });
    } else {
      this.store.dispatch(actions.mostrarCargando({ estado: false }));
      Swal.fire('¡Atención!', 'Documento no disponible', 'warning');
    }
  }

  getPdfUrl2(): string {
    return this.getSafeUrl('data:application/pdf;base64,' + this.base64Pdf);
  }
  getSafeUrl(base64: string): any {
    return this.sanitizer.bypassSecurityTrustResourceUrl(base64);
  }

  createPdfUrl(): void {
    // Paso 1: Convertir base64 a Blob
    const byteCharacters = atob(this.base64Pdf);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: 'application/pdf' });

    // Paso 2: Crear URL de objeto
    const url = URL.createObjectURL(blob);

    // Paso 3: Sanitizar y asignar URL
    this.pdfSafeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }  

  openDialogValidar(doc : DataProyectoModel): void {
    this.proyectoSeleccionado = {
      codigoEstadoVotacion: '',
      codigoProgramacion: '',
      estadoProyecto: '',
      estadoVotacion: '',
      extension: '',
      fechaEnvio: '',
      fechaIngreso: '',
      fechaProgramacion: '',
      flagDiscordia: '',
      flagPonente: '',
      idEstadoProyecto: '',
      idProyecto: '',
      iniciales: '',
      numeroConformacion: '',
      numeroEnvio: '',
      numeroGrupo: '',
      numeroIncidente: '',
      numeroRecurso: '',
      numeroSecuencia: '',
      numeroSentido: '',
      numeroUnico: '',
      numeroVotacion: '',
      usuarioResponsable: '',
      uuidAlfresco: ''
    };
    this.dialogDoc = true;
    this.proyectoSeleccionado = doc;
    this.modeValida = true;
  }

  openDialogObservar(doc : DataProyectoModel): void {
    this.proyectoSeleccionado = {
      codigoEstadoVotacion: '',
      codigoProgramacion: '',
      estadoProyecto: '',
      estadoVotacion: '',
      extension: '',
      fechaEnvio: '',
      fechaIngreso: '',
      fechaProgramacion: '',
      flagDiscordia: '',
      flagPonente: '',
      idEstadoProyecto: '',
      idProyecto: '',
      iniciales: '',
      numeroConformacion: '',
      numeroEnvio: '',
      numeroGrupo: '',
      numeroIncidente: '',
      numeroRecurso: '',
      numeroSecuencia: '',
      numeroSentido: '',
      numeroUnico: '',
      numeroVotacion: '',
      usuarioResponsable: '',
      uuidAlfresco: ''
    };
    this.observacionDoc = '';
    this.dialogDoc = true;
    this.proyectoSeleccionado = doc;
    this.modeValida = false;
  }  

  onDialogDocHide(): void {
    this.dialogDoc = false;
  }

  validarDocumento(){
    this.reqValidarDocumento.nroIdProyecto = this.proyectoSeleccionado.idProyecto||'';
    this.reqValidarDocumento.codUsuarioValida = this.usuarioWeb||'';
    this.reqValidarDocumento.nValidado = '1';
    this.reqValidarDocumento.observacion = this.observacionDoc||null;

    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.proyectosService.registrarValidacion({...this.reqValidarDocumento}).subscribe({
      next:(response:any)=>{
        if(response.codigo===constantes.RESPONSE_COD_EXITO){
          if(response.data){
            this.store.dispatch(actions.mostrarCargando({ estado: false }));
            Swal.fire('¡Registrado correctamente!', response.descripcion, 'info').then((result) => {
              if (result.isConfirmed || result.isDismissed) {
                this.reload();
              }
            });
          }
          else{
            this.store.dispatch(actions.mostrarCargando({ estado: false }));
            Swal.fire('¡Atención!', "Observar documento: Error de respuesta", 'warning');
          }
        }
        else{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', response.descripcion, 'warning');
        }
      },
      error:(err)=>{
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        Swal.fire('¡Atención!', err, 'warning');
      }
    }); 
  }

  observarDocumento(){
    this.reqValidarDocumento.nroIdProyecto = this.proyectoSeleccionado.idProyecto||'';
    this.reqValidarDocumento.codUsuarioValida = this.usuarioWeb||'';
    this.reqValidarDocumento.nValidado = '2';
    this.reqValidarDocumento.observacion = this.observacionDoc||null;

    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.proyectosService.registrarValidacion({...this.reqValidarDocumento}).subscribe({
      next:(response:any)=>{
        if(response.codigo===constantes.RESPONSE_COD_EXITO){
          if(response.data){
            this.store.dispatch(actions.mostrarCargando({ estado: false }));
            Swal.fire('¡Registrado correctamente!', response.descripcion, 'info').then((result) => {
              if (result.isConfirmed || result.isDismissed) {
                this.reload();
              }
            });
          }
          else{
            this.store.dispatch(actions.mostrarCargando({ estado: false }));
            Swal.fire('¡Atención!', "Observar documento: Error de respuesta", 'warning');
          }
        }
        else{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', response.descripcion, 'warning');
        }
      },
      error:(err)=>{
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        Swal.fire('¡Atención!', err, 'warning');
      }
    }); 
  }

  reload(): void {
    location.reload();
  }

  back() :void {
    this.router.navigate(['/votacion/bandeja']);
  }
}
