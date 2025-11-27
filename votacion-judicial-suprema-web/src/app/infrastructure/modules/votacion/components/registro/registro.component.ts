import { Component, OnInit, OnDestroy , ChangeDetectorRef, HostListener} from '@angular/core';
import { Router } from '@angular/router';

import { CasacionModel, DocumentosModel, FalloModel, SentidoModel, PartesModel, RequestGenerarTablillaModel, TablillaModel, RecurrenteModel, MagistradoModel, RequestGrabarVoto, FalloElegidoModel, DiscordiasElegidasModel, PDFAdjuntoModel, RequestRelacionadosModel, CasacionRelacionadaModel, DocumentoDigitalModel, RequestListarDocumentosDigModel, RequestVerDocumentosDigModel } from 'src/app/domain/models/Casacion.model';
import { CasacionService } from 'src/app/infrastructure/services/remote/casacion/casacion.service';

import { AppVJState } from '../../../../../infrastructure/global-store/vj.reducers';
import { Store } from '@ngrx/store';
import Swal from 'sweetalert2';
import * as actions from '../../../../../infrastructure/global-store/vj.actions';
import { constantes } from 'src/app/constants';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { DatePipe } from '@angular/common';
import { JurisprudenciaService } from 'src/app/infrastructure/services/remote/herramientas/jurisprudencia.service';
import { LoginService } from 'src/app/infrastructure/services/remote/autenticacion/login.service';
import { GeneralService } from 'src/app/infrastructure/services/local/general/general.service';
import { SimplificadorHTML } from './SimplificadorHTML ';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.scss']
})
export class RegistroComponent implements OnInit, OnDestroy {
  customBackgroundColors = [
    '#fff699', '#fee8c3', '#ffd0a2', '#fbaea6',
    '#e9ed98', '#b6eea7', '#a6f6d9', '#b7e7f3',
    '#a1c4fc', '#d5bcfd', '#febcfa', '#ffb7cb',
    '#bcaea1', '#d5bf9a', '#dddddd', '#66747f'
  ];

  configuracionEditor = {
    toolbar: [
      // Grupo 1: Formato básico
      //['bold', 'italic', 'underline', 'strike'],
      ['bold', 'italic', 'underline'],
      
      // Grupo 2: Colores personalizados
      [{ 'color': [] }],
      [{ 'background': this.customBackgroundColors }],
      /*]
      // Grupo 3: Otros formatos
      [{ 'font': [] }],
      [{ 'size': ['small', false, 'large', 'huge'] }],
      // Grupo 4: Párrafos y listas
      [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
      ['blockquote', 'code-block'],
      [{ 'list': 'ordered'}, { 'list': 'bullet' }],
      
      // Grupo 5: Alineación e indentación
      [{ 'script': 'sub'}, { 'script': 'super' }],
      [{ 'indent': '-1'}, { 'indent': '+1' }],
      [{ 'align': [] }],
      */
      // Grupo 6: Media y limpieza
      //['link', 'image'],
      ['clean']

    ]
  };

  mostrarCuria = false;

  usuarioWeb : string = "";
  dateSeleccionada :Date = new Date(); 
  fechaSel : string = "";

  flagVoto: boolean = false;
  fechaFormateada : string = "";
  partes!: PartesModel[];
  listaSentidos: SentidoModel[] | undefined = [];
  listaFallosUsuario: FalloModel[] = [];
  listaAlVoto: FalloModel[] = [];

  partesSec!: PartesModel[];
  listaSentidosSec: SentidoModel[] | undefined = [];
  listaFallosUsuarioSec: FalloModel[] = [];

  documentosEncontrados!: DocumentosModel[];
  documentosAdjuntos: DocumentosModel[];
  documentosAdjTemp: DocumentosModel[];

  documentosAdjuntosSec: DocumentosModel[];
  documentosEncontradosSec!: DocumentosModel[];
  documentosAdjTempSec: DocumentosModel[];

  listaMagistrados: MagistradoModel[];
  magistradoSeleccionado: MagistradoModel[]=[];
  recurrenteSeleccionado: RecurrenteModel;
  recurrenteSeleccionadoSec: RecurrenteModel;
  listaMagSinPonente: MagistradoModel[];
  seleccionDisc: MagistradoModel[]=[];

  listaMagistradosSec: MagistradoModel[];

  casacionSeleccionada : CasacionModel|null;
  casacionAlter : CasacionModel|null = null;
  listaCasaciones : CasacionModel[];
  listaCasacionesRelacionadas : CasacionRelacionadaModel[] = [];
  listaDocumentosDigitales : DocumentoDigitalModel[] = [];

  tipoBusquedaJuris!: string;
  busqJurisprudencia!: string;
  
  reqGenerarTab : RequestGenerarTablillaModel;
  tablillaData !: TablillaModel;
  tablillaDataSec : TablillaModel;
  tempRecurrentes : RecurrenteModel[] = [];
  reqGrabarVotacion : RequestGrabarVoto;
  ponenteActual : string = '';
  descJurisprudencia : string = '';

  remainingText: number = 1500;
  remainingTextRec: number = 1500;
  validaDiscordia: boolean = false;

  reqGenTabSecundaria : RequestGenerarTablillaModel;
  dialogTab : boolean = false;

  flagVotoSec: boolean = false;
  busqJurisprudenciaSec: string = '';
  ponenteActualSec : string = '';
  tempRecurrentesSec : RecurrenteModel[] = [];
  listaVacia : string[] = [];

  displayDialogDiscordias: boolean = false;
  flagBtnSiguiente: boolean = true;
  flagBtnAnterior: boolean = true;

  tempApuntes : string = '';
  tempAnotaciones: RecurrenteModel[] = [];
  tempAdjuntos: DocumentosModel[];

  // file
  selectedFiles?: FileList;
  currentFile?: File;
  progress: number = 0;
  message: string = '';
  preview: string = '';

  displayModal: boolean = false;
  base64Pdf: string = '';
  pdfSafeUrl: SafeResourceUrl | undefined;

  textLEstado: string = '';

  // PERMISOS SEGUN USUARIO
  ponenteMode: boolean = false;
  magistradoInMode: boolean = false;
  jurisprudenciaMode: boolean = false;

  private temporizadorI: any;
  private inactivityTimeoutId: any;
  private inactivityThreshold: number = 20000;
  private isUserInactive: boolean = false;

  private distritoUsuario: string = '';
  private provinciaUsuario: string = '';
  private salaUsuario: string = '';
  nombreSalaUsuario: string = '';

  constructor(private router: Router, private store: Store<AppVJState>,
    private casacionService: CasacionService, private sanitizer: DomSanitizer, private jurisprudenciaService: JurisprudenciaService,
    private loginService: LoginService, private generalService: GeneralService, private datePipe : DatePipe, private cdr: ChangeDetectorRef, private simplificadorHTML: SimplificadorHTML) {
    this.listaAlVoto = [{cfallo: '1727', xfallo: 'AL VOTO'}];  

    this.listaCasaciones = []
    
    this.usuarioWeb = this.loginService.getUsuario().codigoUsuario;
    this.distritoUsuario = this.loginService.getDistrito();
    this.provinciaUsuario = this.loginService.getProvincia();
    this.salaUsuario = this.loginService.getSala();
    this.nombreSalaUsuario = this.loginService.getNombreSala();

    this.dateSeleccionada = this.generalService.getFechaSeleccionada()||new Date();
    this.casacionSeleccionada = JSON.parse(JSON.stringify(this.generalService.getCasacionSeleccionada()))||null; 

    this.tablillaData = {
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

    this.tablillaDataSec = {
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

    // this.tablillaPro = {
    //   descProceso: '',
    //   descActoProcesalJuez: '',
    //   fojasJuez: '',
    //   descFalloJuez: '',
    //   descActoProcesalProcedencia: '',
    //   fojasOrigenProcedencia : '',
    //   descFalloProcedencia : '',
    //   magistrado1 : '',
    //   magistrado1Flag : '',
    //   magistrado2 : '',
    //   magistrado2Flag : '',
    //   magistrado3 : '',
    //   magistrado3Flag : '',
    //   magistrado4 : '',
    //   magistrado4Flag : '',
    //   magistrado5 : '',
    //   magistrado5Flag : '',
    //   magistrados : null,
    //   impedimento1 : '',
    //   impedimento1Flag : '',
    //   impedimento2 : '',
    //   impedimento2Flag : '',
    //   impedimento3 : '',
    //   impedimento3Flag : '',
    //   impedimento4 : '',
    //   impedimento4Flag : '',
    //   impedimento5 : '',
    //   impedimento5Flag : '',
    //   materias : null,
    //   causales : null,      
    //   tipoabrev : '',
    //   tipoAudiencia : '',
    //   especialidad : '',
    //   apuntes : '',
    //   recurrentes : [] ,
    //   jurisprudencias : [],
    //   existe: false
    // };    

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

    this.reqGenTabSecundaria = {
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

    this.recurrenteSeleccionado = {
      nombreParte : '',
      nsecuencia : 0,
      lrecurrente : '',
      ltipoParte : '',
      sentido : null,
      fallo: null,
      ldiscordia : '',
      discordias : null,
      xobservacion : '',
      xabreviatura : '',
      desParte : '',
      lpublicado : ''
    }

    this.recurrenteSeleccionadoSec = {
      nombreParte : '',
      nsecuencia : 0,
      lrecurrente : '',
      ltipoParte : '',
      sentido : null,
      fallo: null,
      ldiscordia : '',
      discordias : null,
      xobservacion : '',
      xabreviatura : '',
      desParte : '',
      lpublicado : ''
    }    

    this.reqGrabarVotacion = {
      distrito: '',
      provincia: '',
      instancia: '',
      codEstado : '',
      codProgramacion :'',
      nUnico : '',
      nIncidente : '',
      fechaIngreso : '',
      fechaProgramacion : '',
      sentido : '',
      votacion : '',
      idRegistroOrigen : '',
      fallos : null,
      jurisprudencia : null,
      apuntes : '',
      voto: '',
      codigoUsuario: '',
      fechaEstado: '',
      codEspecialidad: '',
      numeroGrupo: '',
      numeroSecuencia: '',
      numeroConformacion: '',
      ponente: '',
      codigoArea: '',
      abrev: ''
    }    
    this.listaMagistrados = [];
    this.listaMagistradosSec = [];
    this.listaMagSinPonente = [];
    this.partes = [];
    this.partesSec = [];
    this.listaSentidos = [];
    this.listaFallosUsuario = [];
    this.documentosEncontrados = [];
    this.documentosAdjuntos = [];
    this.documentosAdjuntosSec = [];
    this.tempAdjuntos = [];
    this.documentosAdjTemp = [];
    this.documentosAdjTempSec = [];
    this.tipoBusquedaJuris = 'PJ';
    this.descJurisprudencia = 'Nro. Casación (Ejm. 2030-2016)';
  }

  ngOnInit(): void {
    this.listarSentidos();
    this.listarCasaciones(this.datePipe.transform(this.dateSeleccionada, 'yyyy-MM-dd') || '', JSON.parse(JSON.stringify(this.casacionSeleccionada)));
    this.fechaFormateada = this.formatearFechaCompleta();

    this.temporizadorI = setInterval(() => {
      this.exeAutoguardado();
    }, 60000); 

    this.resetInactivityTimer();

    this.validarCuria();
  }

  validarCuria(){
    const salasStr:string = environment.codidosSalaCuria;
    const salasCuria = salasStr.split(',').map(n => n.trim());
    this.mostrarCuria = salasCuria.includes(this.salaUsuario);
  }

  // Escucha de eventos de interacción
  @HostListener('document:mousemove', ['$event'])
  @HostListener('document:keydown', ['$event'])
  @HostListener('document:input', ['$event'])
  onUserActivity(): void {
    this.resetInactivityTimer(); // Reinicia el temporizador cada vez que detecta actividad
  }  

  resetInactivityTimer(): void {
    if (this.inactivityTimeoutId) {
      clearTimeout(this.inactivityTimeoutId);
    }
    this.isUserInactive = false; // Usuario no está inactivo mientras se detecta actividad
    this.inactivityTimeoutId = setTimeout(() => {
      this.isUserInactive = true; // Marca al usuario como inactivo si pasa el tiempo límite sin actividad
    }, this.inactivityThreshold);
  }

  loadCasaciones(): void {
    this.refreshPage();
    this.refreshPage();
  }

  refreshPage(){
    this.casacionSeleccionada = {
      nro: 0,
      codEstado :  '',
      estado :  '',
      codProgramacion:  '',
      codDistrito:  '',
      codProvincia:  '',
      codInstancia:  '',
      nombreInstancia :  '',
      numeroProgramacion: 0,
      numeroGrupo:  '',
      numeroSecuencia :  '',
      numeroConformacion:  '',
      numeroUnico :  '',
      numeroIncidente:  '',
      fechaIngreso:  '',
      fechaProgramacion:  '',
      numeroRecurso :  '',
      codMotivoIngreso:  '',
      nombreMotivoIngreso :  '',
      codProcedencia:  '',
      procedencia :  '',
      flagDiscordia:  '',
      numeroDirimente: 0,
      numeroOrden: 0,
      fechaEstado :  '',
      codigoArea :  '',
      numeroSentido :  '',
      numeroVotacion :  '',
      codIdRegistro: ''
    }
    this.listarCasaciones(this.datePipe.transform(this.dateSeleccionada, 'yyyy-MM-dd') || '', JSON.parse(JSON.stringify(this.generalService.getCasacionSeleccionada()))||null);
  }

  limpiarTablilla(): void {
    this.flagVoto = false;
    this.busqJurisprudencia = '';
    this.ponenteActual = '';
    this.tempRecurrentes = [];

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

    this.tablillaData = {
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

    this.limpiarRecurrenteSeleccionado();

    this.partes = [];
    this.listaSentidos = [];
    this.listaFallosUsuario = [];
    this.listarSentidos();

    this.documentosEncontrados = [];
    this.documentosAdjuntos = [];
    this.documentosAdjTemp = [];
  }

    limpiarTablillaSecundaria(): void {
    this.flagVotoSec = false;
    this.busqJurisprudenciaSec = '';
    this.ponenteActualSec = '';
    this.tempRecurrentesSec = [];

    this.reqGenTabSecundaria = {
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

    this.tablillaDataSec = {
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

    this.limpiarRecurrenteSeleccionadoSec();

    this.partesSec = [];
    this.listaSentidosSec = [];
    this.listaFallosUsuarioSec = [];
    this.listarSentidosSec();

    this.documentosEncontradosSec = [];
    this.documentosAdjuntosSec = [];
    this.documentosAdjTempSec = [];
  }


  limpiarRecurrenteSeleccionado():void{
    this.recurrenteSeleccionado = {
      nombreParte : '',
      nsecuencia : 0,
      lrecurrente : '',
      ltipoParte : '',
      sentido : null,
      fallo: null,
      ldiscordia : '',
      discordias : null,
      xobservacion : '',
      xabreviatura : '',
      desParte : '',
      lpublicado : ''
    }
  }

  limpiarRecurrenteSeleccionadoSec():void{
    this.recurrenteSeleccionadoSec = {
      nombreParte : '',
      nsecuencia : 0,
      lrecurrente : '',
      ltipoParte : '',
      sentido : null,
      fallo: null,
      ldiscordia : '',
      discordias : null,
      xobservacion : '',
      xabreviatura : '',
      desParte : '',
      lpublicado : ''
    }
  }  

  limpiarRqVoto():void{
    this.reqGrabarVotacion = {
      distrito: '',
      provincia: '',
      instancia: '',
      codEstado : '',
      codProgramacion :'',
      nUnico : '',
      nIncidente : '',
      fechaIngreso : '',
      fechaProgramacion : '',
      sentido : '',
      votacion : '',
      idRegistroOrigen : '',
      fallos : null,
      jurisprudencia : null,
      apuntes : '',
      voto: '',
      codigoUsuario: '',
      fechaEstado: '',
      codEspecialidad: '',
      numeroGrupo: '',
      numeroSecuencia: '',
      numeroConformacion: '',
      ponente: '',
      codigoArea: '',
      abrev: ''      
    }
  }  

  eventSelectCasacion(event: any|null): void{
    if(event){
      this.store.dispatch(actions.mostrarCargando({ estado: true }));
      this.limpiarTablilla();
      this.casacionSeleccionada = {...event};
      if(this.casacionSeleccionada && this.casacionSeleccionada.nro != null){
        this.comprobarLimiteLista({...this.casacionSeleccionada});
        this.reqGenerarTab.distrito = this.casacionSeleccionada.codDistrito;
        this.reqGenerarTab.provincia = this.casacionSeleccionada.codProvincia;
        this.reqGenerarTab.instancia = this.casacionSeleccionada.codInstancia;
        this.reqGenerarTab.visualizaPonente = 'S';
        this.reqGenerarTab.nUnico = this.casacionSeleccionada.numeroUnico;
        this.reqGenerarTab.nIncidente = this.casacionSeleccionada.numeroIncidente;
        this.reqGenerarTab.fechaIngreso = this.casacionSeleccionada.fechaIngreso;
        this.reqGenerarTab.programacion = this.casacionSeleccionada.codProgramacion;
        this.reqGenerarTab.grupo = this.casacionSeleccionada.numeroGrupo;
        this.reqGenerarTab.secuencia = this.casacionSeleccionada.numeroSecuencia;
        this.reqGenerarTab.conformacion = this.casacionSeleccionada.numeroConformacion;
        this.reqGenerarTab.fechaProgramacion = this.casacionSeleccionada.fechaProgramacion;
        this.reqGenerarTab.discordia = this.casacionSeleccionada.flagDiscordia;
        this.reqGenerarTab.sentido = this.casacionSeleccionada.numeroSentido;
        this.reqGenerarTab.votacion = this.casacionSeleccionada.numeroVotacion; 
        this.reqGenerarTab.codUsuario = this.usuarioWeb;
        this.cargarCasacionSeleccionada();
        this.generalService.setCasacionSeleccionada({...this.casacionSeleccionada});
        this.listarCasacionesRelacionados(this.casacionSeleccionada.codDistrito, this.casacionSeleccionada.numeroUnico,this.casacionSeleccionada.numeroIncidente);
        this.listarDocumentosDigitales( this.casacionSeleccionada.numeroUnico,this.casacionSeleccionada.numeroIncidente)
      }
    }
  }

  cargarCasacionSeleccionada(): void {
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.casacionService.cargarDatosTablilla({...this.reqGenerarTab}).subscribe({
      next:(response:any)=>{
        if(response.codigo===constantes.RESPONSE_COD_EXITO){
          if(response.data){
            this.tablillaData.descProceso = response.data.juzgado.xproceso?response.data.juzgado.xproceso.trim():'';
            this.tablillaData.descActoProcesalJuez = response.data.juzgado.xactoProcesalJuz?response.data.juzgado.xactoProcesalJuz.trim():'';
            this.tablillaData.fojasJuez = response.data.juzgado.nfojasJuz?response.data.juzgado.nfojasJuz:'';
            this.tablillaData.descFalloJuez = response.data.juzgado.xfalloJuz?response.data.juzgado.xfalloJuz.trim():'';
            this.tablillaData.descActoProcesalProcedencia = response.data.juzgado.xactoProcesal?response.data.juzgado.xactoProcesal.trim():'';
            this.tablillaData.fojasOrigenProcedencia  = response.data.juzgado.nfojas?response.data.juzgado.nfojas:'';
            this.tablillaData.descFalloProcedencia  = response.data.juzgado.xfallo?response.data.juzgado.xfallo.trim():'';
            this.tablillaData.tipoabrev  = response.data.juzgado.xabrev?response.data.juzgado.xabrev.trim():'';
            this.tablillaData.tipoAudiencia  = response.data.juzgado.xdescripcion?response.data.juzgado.xdescripcion.trim():'';
            this.tablillaData.especialidad  = response.data.juzgado.cespecialidad?response.data.juzgado.cespecialidad.trim():'';
            this.tablillaData.apuntes = response.data.apuntes?response.data.apuntes.trim():'';
            this.tempApuntes = this.tablillaData.apuntes;
            this.reloadContador(this.tablillaData.apuntes);
            this.listaMagistrados = response.data.magistrados;
            this.llenarMagistrados(this.listaMagistrados);
            this.listarFallos();
            this.tablillaData.magistrados = response.data.magistrados || null;
            this.tablillaData.impedimento1  = response.data.impedimento.xusuario1?response.data.impedimento.xusuario1.trim():'';
            this.tablillaData.impedimento1Flag  = response.data.impedimento.limpedido1?response.data.impedimento.limpedido1.trim():'';
            this.tablillaData.impedimento2  = response.data.impedimento.xusuario2?response.data.impedimento.xusuario2.trim():'';
            this.tablillaData.impedimento2Flag  = response.data.impedimento.limpedido2?response.data.impedimento.limpedido2.trim():'';
            this.tablillaData.impedimento3  = response.data.impedimento.xusuario3?response.data.impedimento.xusuario3.trim():'';
            this.tablillaData.impedimento3Flag  = response.data.impedimento.limpedido3?response.data.impedimento.limpedido3.trim():'';
            this.tablillaData.impedimento4  = response.data.impedimento.xusuario4?response.data.impedimento.xusuario4.trim():'';
            this.tablillaData.impedimento4Flag  = response.data.impedimento.limpedido4?response.data.impedimento.limpedido4.trim():'';
            this.tablillaData.impedimento5  = response.data.impedimento.xusuario5?response.data.impedimento.xusuario5.trim():'';
            this.tablillaData.impedimento5Flag  = response.data.impedimento.limpedido5?response.data.impedimento.limpedido5.trim():'';
            this.tablillaData.materias  = response.data.materias || null;
            this.tablillaData.causales  = response.data.causales || null;
            this.tempRecurrentes =  JSON.parse(JSON.stringify(response.data.partes));
            this.tempAnotaciones = JSON.parse(JSON.stringify(response.data.partes));
            this.documentosAdjuntos = response.data.jurisprudencias;
            this.tempAdjuntos = [...this.documentosAdjuntos];
            this.documentosAdjTemp = [...this.documentosAdjuntos];
            this.tablillaData.existe = true;
            this.completarlistaMagSP();
            this.comprobarPermisos();
          }
          else{
            Swal.fire('¡Atención!', "Cargar casación: Error de respuesta", 'warning');
          }
        }
        else{
          Swal.fire('¡Atención!', response.descripcion, 'warning');
        }
      },
      complete:() => {
        this.validarAlVoto();
        this.cdr.detectChanges();
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
      },
      error:(err)=>{
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        Swal.fire('¡Atención!', err, 'warning');
      }
    });
  }

  validarAlVoto(){
    if(this.casacionSeleccionada && this.casacionSeleccionada.codEstado == '495'){
      this.flagVoto = true;
      this.cambiarAlVoto();
    }
  }

  llenarMagistrados(magistrados : MagistradoModel[]): void{
    if(magistrados && magistrados.length > 0){
      switch(magistrados.length-1){
        case 0:
          this.tablillaData.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaData.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';         
          break;
        case 1:
          this.tablillaData.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaData.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';
          this.tablillaData.magistrado2  = magistrados[1].nombreUsuario?magistrados[1].nombreUsuario.trim():'';
          this.tablillaData.magistrado2Flag  = magistrados[1].lponente?magistrados[1].lponente.trim():'';       
          break;
        case 2:
          this.tablillaData.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaData.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';
          this.tablillaData.magistrado2  = magistrados[1].nombreUsuario?magistrados[1].nombreUsuario.trim():'';
          this.tablillaData.magistrado2Flag  = magistrados[1].lponente?magistrados[1].lponente.trim():'';
          this.tablillaData.magistrado3  = magistrados[2].nombreUsuario?magistrados[2].nombreUsuario.trim():'';
          this.tablillaData.magistrado3Flag  = magistrados[2].lponente?magistrados[2].lponente.trim():''; 
          break;
        case 3:
          this.tablillaData.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaData.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';
          this.tablillaData.magistrado2  = magistrados[1].nombreUsuario?magistrados[1].nombreUsuario.trim():'';
          this.tablillaData.magistrado2Flag  = magistrados[1].lponente?magistrados[1].lponente.trim():'';
          this.tablillaData.magistrado3  = magistrados[2].nombreUsuario?magistrados[2].nombreUsuario.trim():'';
          this.tablillaData.magistrado3Flag  = magistrados[2].lponente?magistrados[2].lponente.trim():'';
          this.tablillaData.magistrado4  = magistrados[3].nombreUsuario?magistrados[3].nombreUsuario.trim():'';
          this.tablillaData.magistrado4Flag  = magistrados[3].lponente?magistrados[3].lponente.trim():'';  
          break;
        case 4:
          this.tablillaData.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaData.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';
          this.tablillaData.magistrado2  = magistrados[1].nombreUsuario?magistrados[1].nombreUsuario.trim():'';
          this.tablillaData.magistrado2Flag  = magistrados[1].lponente?magistrados[1].lponente.trim():'';
          this.tablillaData.magistrado3  = magistrados[2].nombreUsuario?magistrados[2].nombreUsuario.trim():'';
          this.tablillaData.magistrado3Flag  = magistrados[2].lponente?magistrados[2].lponente.trim():'';
          this.tablillaData.magistrado4  = magistrados[3].nombreUsuario?magistrados[3].nombreUsuario.trim():'';
          this.tablillaData.magistrado4Flag  = magistrados[3].lponente?magistrados[3].lponente.trim():'';            
          this.tablillaData.magistrado5  = magistrados[4].nombreUsuario?magistrados[4].nombreUsuario.trim():'';
          this.tablillaData.magistrado5Flag  = magistrados[4].lponente?magistrados[4].lponente.trim():'';
          break;
      }
    }
  }

  llenarMagistradosSec(magistrados : MagistradoModel[]): void{
    if(magistrados && magistrados.length > 0){
      switch(magistrados.length-1){
        case 0:
          this.tablillaDataSec.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';         
          break;
        case 1:
          this.tablillaDataSec.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';
          this.tablillaDataSec.magistrado2  = magistrados[1].nombreUsuario?magistrados[1].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado2Flag  = magistrados[1].lponente?magistrados[1].lponente.trim():'';       
          break;
        case 2:
          this.tablillaDataSec.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';
          this.tablillaDataSec.magistrado2  = magistrados[1].nombreUsuario?magistrados[1].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado2Flag  = magistrados[1].lponente?magistrados[1].lponente.trim():'';
          this.tablillaDataSec.magistrado3  = magistrados[2].nombreUsuario?magistrados[2].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado3Flag  = magistrados[2].lponente?magistrados[2].lponente.trim():''; 
          break;
        case 3:
          this.tablillaDataSec.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';
          this.tablillaDataSec.magistrado2  = magistrados[1].nombreUsuario?magistrados[1].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado2Flag  = magistrados[1].lponente?magistrados[1].lponente.trim():'';
          this.tablillaDataSec.magistrado3  = magistrados[2].nombreUsuario?magistrados[2].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado3Flag  = magistrados[2].lponente?magistrados[2].lponente.trim():'';
          this.tablillaDataSec.magistrado4  = magistrados[3].nombreUsuario?magistrados[3].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado4Flag  = magistrados[3].lponente?magistrados[3].lponente.trim():'';  
          break;
        case 4:
          this.tablillaDataSec.magistrado1  = magistrados[0].nombreUsuario?magistrados[0].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado1Flag  = magistrados[0].lponente?magistrados[0].lponente.trim():'';
          this.tablillaDataSec.magistrado2  = magistrados[1].nombreUsuario?magistrados[1].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado2Flag  = magistrados[1].lponente?magistrados[1].lponente.trim():'';
          this.tablillaDataSec.magistrado3  = magistrados[2].nombreUsuario?magistrados[2].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado3Flag  = magistrados[2].lponente?magistrados[2].lponente.trim():'';
          this.tablillaDataSec.magistrado4  = magistrados[3].nombreUsuario?magistrados[3].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado4Flag  = magistrados[3].lponente?magistrados[3].lponente.trim():'';            
          this.tablillaDataSec.magistrado5  = magistrados[4].nombreUsuario?magistrados[4].nombreUsuario.trim():'';
          this.tablillaDataSec.magistrado5Flag  = magistrados[4].lponente?magistrados[4].lponente.trim():'';
          break;
      }
    }
  }  

  listarSentidos(): void {
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.casacionService.listarSentidos(this.distritoUsuario,this.provinciaUsuario,this.salaUsuario).subscribe({
      next:(response:any)=>{
        if(response.codigo===constantes.RESPONSE_COD_EXITO){
          if(response.data){
            this.listaSentidos = response.data;
          }
          else{
            this.store.dispatch(actions.mostrarCargando({ estado: false }));
            Swal.fire('¡Atención!', "Listar Sentidos: Error de respuesta", 'warning');
          }
        }
        else{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', response.descripcion, 'warning');
        }
      },
      complete:()=>{
        
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
      },
      error:(err)=>{
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        Swal.fire('¡Atención!', err, 'warning');
      }
    });
  }

  listarSentidosSec(): void {
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.casacionService.listarSentidos(this.distritoUsuario,this.provinciaUsuario,this.salaUsuario).subscribe({
      next:(response:any)=>{
        if(response.codigo===constantes.RESPONSE_COD_EXITO){
          if(response.data){
            this.listaSentidosSec = response.data;
          }
          else{
            this.store.dispatch(actions.mostrarCargando({ estado: false }));
            Swal.fire('¡Atención!', "Listar Sentidos: Error de respuesta", 'warning');
          }
        }
        else{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', response.descripcion, 'warning');
        }
      },
      complete:()=>{
        
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
      },
      error:(err)=>{
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        Swal.fire('¡Atención!', err, 'warning');
      }
    });
  }  

  listarFallos(): void {
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.casacionService.listarFallos(this.tablillaData.especialidad, this.tablillaData.tipoabrev, this.salaUsuario).subscribe({
      next:(response:any)=>{
        if(response.codigo===constantes.RESPONSE_COD_EXITO){
          if(response.data){
            this.listaFallosUsuario = [];
            for( let fallo of response.data){
              if(fallo.cfallo.toString()!=='1727'){
                this.listaFallosUsuario.push(fallo);
              }
            }
          }
          else{
            this.store.dispatch(actions.mostrarCargando({ estado: false }));
            Swal.fire('¡Atención!', "Listar Fallos: Error de respuesta", 'warning');
          }
        }
        else{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', response.descripcion, 'warning');
        }
      },
      complete:()=>{
        this.recargarTablaRecurrentes();
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
      },
      error:(err)=>{
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        Swal.fire('¡Atención!', err, 'warning');
      }
    });
  }  

  recargarTablaRecurrentes():void{
    this.tablillaData.recurrentes = this.tempRecurrentes;
    this.tablillaData.recurrentes.sort((a, b) => (a.lrecurrente === b.lrecurrente) ? 0 : a.lrecurrente === "S" ? -1 : 1);
  }

  //JURISPRUDENCIA
  buscarJud():void{
    //const casacionRegex = /^[0-9]+-\d{4}$/;
    if(this.busqJurisprudencia && this.busqJurisprudencia.length > 3 ){
      this.store.dispatch(actions.mostrarCargando({ estado: true }));
      this.jurisprudenciaService.buscarJurisprudencia(this.busqJurisprudencia,this.tipoBusquedaJuris).subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data.length>0){
              this.documentosEncontrados = [];
              response.data.forEach((element: { recurso: string; uuid: string; origen: string}) => {
                this.documentosEncontrados.push(new DocumentosModel(null,element.recurso , element.uuid, element.origen));
              });
            } else{
              this.store.dispatch(actions.mostrarCargando({ estado: false }));
              Swal.fire('¡Atención!', "No se encontraron resultados", 'warning');
            }
          }
          else{
            this.store.dispatch(actions.mostrarCargando({ estado: false }));
            Swal.fire('¡Atención!', response.descripcion, 'warning');
          }
        },
        complete:()=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
        },
        error:(err)=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });
    } else {
      Swal.fire('¡Atención!', "Ingrese contenido a buscar, mayor a 3 caracteres", 'info');
    }
  }

  //VISUALIZAR PDF
  previewDoc(uuid: string, origen: string): void {
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    if(uuid){
      this.jurisprudenciaService.visualizarPDF(uuid,origen).subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data){
              this.base64Pdf = response.data.data;
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

  //VISUALIZAR DOCUMENTO DIG
  verDoc(doc: DocumentoDigitalModel): void {
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    if(doc.ndocumento){
      var req: RequestVerDocumentosDigModel = { nUnico: doc.nunico, nIncidente: doc.nincidente, nDocumento: doc.ndocumento };
      //this.jurisprudenciaService.visualizarPDF(uuid,origen).subscribe({
        this.casacionService.verDocumentoDigital(req).subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data){
              this.base64Pdf = response.data.contenido;
            } else{
              this.store.dispatch(actions.mostrarCargando({ estado: false }));
              Swal.fire('¡Atención!', "visualizar documento digital: Error de respuesta", 'warning');
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

  grabar(): void {
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.fillVotacion();
    if(this.reqGrabarVotacion.apuntes &&this.reqGrabarVotacion.apuntes?.length > 1500){
      this.store.dispatch(actions.mostrarCargando({ estado: false }));
      Swal.fire('¡Atención!', "La longitud de los apuntes debe ser menor a 1500 caracteres.",);
      return;
    }
    if(this.validarRecurrentes(this.reqGrabarVotacion)){
      this.casacionService.grabarVoto({...this.reqGrabarVotacion}).subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data){
              Swal.fire('¡Registrado correctamente!', response.descripcion, 'info');
            } else{
              Swal.fire('¡Atención!', "Grabar votación: Error de respuesta", 'warning');
            }
          }
          else{
            Swal.fire('¡Atención!', response.descripcion, 'warning');
          }
        },
        complete:()=>{
          this.loadCasaciones();
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
        },        
        error:(err)=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });
    } else {
      this.store.dispatch(actions.mostrarCargando({ estado: false }));
      Swal.fire('¡Atención!', "Completar datos de los recurrentes correctamente", 'warning');
    }
  }

  fillVotacion(): void {
    if(this.casacionSeleccionada){
      this.reqGrabarVotacion.distrito = this.distritoUsuario;
      this.reqGrabarVotacion.provincia = this.provinciaUsuario;
      this.reqGrabarVotacion.instancia = this.salaUsuario;
      this.reqGrabarVotacion.codEstado = this.casacionSeleccionada.codEstado;
      this.reqGrabarVotacion.codProgramacion =  this.casacionSeleccionada.codProgramacion;
      this.reqGrabarVotacion.nUnico =  this.casacionSeleccionada.numeroUnico;
      this.reqGrabarVotacion.nIncidente =  this.casacionSeleccionada.numeroIncidente;
      this.reqGrabarVotacion.fechaIngreso =  this.casacionSeleccionada.fechaIngreso;
      this.reqGrabarVotacion.fechaProgramacion =  this.casacionSeleccionada.fechaProgramacion;
      this.reqGrabarVotacion.sentido =  this.casacionSeleccionada.numeroSentido;
      this.reqGrabarVotacion.votacion =  this.casacionSeleccionada.numeroVotacion;
      this.reqGrabarVotacion.idRegistroOrigen =  this.casacionSeleccionada.codIdRegistro;
      this.fillFallos();
      this.reqGrabarVotacion.jurisprudencia = this.consolidarAdjuntos(this.documentosAdjTemp, this.documentosAdjuntos);
      //this.reqGrabarVotacion.apuntes = this.tablillaData.apuntes;
      this.tablillaData.apuntes = this.tablillaData.apuntes.trim();
      console.log("html original ",this.tablillaData.apuntes);
      //if(this.tablillaData.apuntes.length > 1500){
      //  this.tablillaData.apuntes = this.simplificarHTML(this.tablillaData.apuntes);
      this.tablillaData.apuntes = this.simplificadorHTML.simplificarHTML(this.tablillaData.apuntes);
      this.tablillaData.apuntes = this.simplificadorHTML.limpiarBrExcesivos(this.tablillaData.apuntes, 2);
      console.log("html simplificado ",this.tablillaData.apuntes);
      //}
      this.reqGrabarVotacion.apuntes = this.tablillaData.apuntes;
      this.reqGrabarVotacion.voto= this.flagVoto?'S':'N';
      this.reqGrabarVotacion.codigoUsuario= this.usuarioWeb;
      this.reqGrabarVotacion.fechaEstado=  this.casacionSeleccionada.fechaEstado;
      this.reqGrabarVotacion.codEspecialidad= this.tablillaData.especialidad;
      this.reqGrabarVotacion.numeroGrupo=   this.casacionSeleccionada.numeroGrupo;
      this.reqGrabarVotacion.numeroSecuencia= this.casacionSeleccionada.numeroSecuencia;
      this.reqGrabarVotacion.numeroConformacion=  this.casacionSeleccionada.numeroConformacion;
      this.reqGrabarVotacion.ponente = this.ponenteActual;
      this.reqGrabarVotacion.codigoArea = this.casacionSeleccionada.codigoArea;
      this.reqGrabarVotacion.abrev = this.tablillaData.tipoabrev;
    } else {
      Swal.fire('¡Atención!', 'No existe casación seleccionada', 'warning');
    }
  }

  fillFallos(): void {
    this.reqGrabarVotacion.fallos = [];
    for( let rec of this.tablillaData.recurrentes){
      if(rec.lrecurrente==="S"){
        let falloElegido : FalloElegidoModel = {
          secuencia : '', 
          codigoSentido: null, 
          codigoFallo: null, 
          discordia: '',
          discordias: null,
          anotacion: ''
        };
        falloElegido.secuencia = rec.nsecuencia?rec.nsecuencia.toString():'';
        falloElegido.codigoSentido = rec.sentido?rec.sentido.csentido:null;
        falloElegido.codigoFallo = rec.fallo&&rec.fallo.cfallo.length>0?rec.fallo.cfallo:null;
        falloElegido.anotacion = rec.xobservacion?rec.xobservacion:'';
        falloElegido.discordia = rec.ldiscordia?rec.ldiscordia:'';

        const matchingItem = this.tempAnotaciones.find(item1 => rec.nsecuencia === item1.nsecuencia);
        falloElegido.discordias = this.consolidarDiscordias(matchingItem!.discordias||[], rec.discordias||[]);        

        this.reqGrabarVotacion.fallos.push(falloElegido);
      }
    }
  }

  simplificarHTML(html: string): string {
    const container = document.createElement('div');
    container.innerHTML = html;
  
    // 1. Eliminar <span> sin atributos
    container.querySelectorAll('span').forEach(span => {
      if (span.getAttributeNames().length === 0) {
        const textNode = document.createTextNode(span.textContent || '');
        span.replaceWith(textNode);
      }
    });
  
    // 2. Agrupar <p> sin atributos con <br> entre ellos
    const nodes = Array.from(container.childNodes);
    const newContainer = document.createElement('div');
  
    let buffer: string[] = [];
  
    function flushBuffer() {
      if (buffer.length > 0) {
        const newP = document.createElement('p');
        newP.innerHTML = buffer.join('<br>');
        newContainer.appendChild(newP);
        buffer = [];
      }
    }
  
    for (const node of nodes) {
      if (node.nodeType === Node.ELEMENT_NODE && (node as HTMLElement).tagName === 'P') {
        const p = node as HTMLParagraphElement;
        const isStyled = p.getAttributeNames().length > 0;
  
        if (!isStyled) {
          buffer.push(p.innerHTML.trim());
        } else {
          flushBuffer();
          newContainer.appendChild(p.cloneNode(true));
        }
      } else {
        flushBuffer();
        newContainer.appendChild(node.cloneNode(true));
      }
    }
  
    flushBuffer();
  
    return newContainer.innerHTML;
  }
  consolidarAdjuntos(listaA : DocumentosModel[], listaB : DocumentosModel[]): PDFAdjuntoModel[]{
    const result: PDFAdjuntoModel[] = [];
    const mapA = new Map<number, DocumentosModel>();
    //Agregar todos los elementos de A al mapa
    listaA.forEach(item => mapA.set(item.id||0, { ...item }));
    //Recorrer B y comparar con el mapa A
    listaB.forEach(item => {
      if (mapA.has(item.id||-1)) {
        // Si el elemento está en A, se añade con activo: S
        result.push({  source: item.nombreEntidad, recurso: item.recurso, uuid: item.uuid, activo: 'S', id: item.id});
        mapA.delete(item.id||-1); // Eliminar para no procesar dos veces
      } else {
        // Si el elemento no está en A, se añade con activo: S
        result.push({ source: item.nombreEntidad, recurso: item.recurso, uuid: item.uuid, activo: 'S', id: item.id });
      }
    });
    //Añadir los elementos restantes de A que no están en B, con activo : N
    mapA.forEach(item => result.push({ source: item.nombreEntidad, recurso: item.recurso, uuid: item.uuid, activo: 'N', id: item.id }));
    return result||null;
  }

  consolidarDiscordias(listaA : any[]|[], listaB : any[]|[]): DiscordiasElegidasModel[]{
    let result: DiscordiasElegidasModel[] = [];
    let mapA = new Map<string, any>();
    //Agregar todos los elementos de A al mapa
    listaA.forEach(item => mapA.set(item.codigoUsuarioVocal||'', {...item} ));
    //Recorrer B y comparar con el mapa A
    listaB.forEach(item => {
      if (mapA.has(item.codigoUsuarioVocal)) {
        // Si el elemento está en A, se añade con activo: S
        let itemMap = mapA.get(item.codigoUsuarioVocal);
        result.push({ codigoUsuario : itemMap?itemMap.codigoUsuarioVocal:'', activo : "S", id : itemMap?itemMap.id.toString():''});
        
        mapA.delete(item.codigoUsuarioVocal); // Eliminar para no procesar dos veces
      } else {
        // Si el elemento no está en A, se añade con activo: S
        result.push({ codigoUsuario : item.codigoUsuarioVocal, activo : "S", id : ''});
      }
    });
    //Añadir los elementos restantes de A que no están en B, con activo : N
    mapA.forEach(item => result.push({ codigoUsuario : item.codigoUsuarioVocal, activo : "N", id : item.id.toString()}));
    return result||null;
  }

  cambiarAlVoto(): void {
    //this.listaFallosUsuario = [];
    if(this.flagVoto){
      //this.listaFallosUsuario.push({xfallo: 'AL VOTO' , cfallo: '1727'});
      this.asignarSentidoRecurrentes("01");
      //this.asignarFalloAlVoto();
      this.refrescarDiscordia();
    } else {
      //this.listarSentidos();
      this.asignarSentidoRecurrentes("00");
    }
  }

  //Asignar los recurrentes activos con un sentido
  asignarSentidoRecurrentes(codigo : string): void {
    for(let parte of this.tablillaData.recurrentes){
      parte.sentido = null;
      parte.fallo = null;
      if(parte.lrecurrente =='S'){
        parte.sentido = this.listaSentidos!.find(p => p.csentido === codigo) || null;
        parte.ldiscordia = "N";
        //limpiar multiselect
      }
    }
  }

  //Asignar los recurrentes activos con un fallo
  asignarFalloRecurrentes(codigo : string): void {
    for(let parte of this.tablillaData.recurrentes){
      if(parte.lrecurrente =='S'){
        parte.fallo = this.listaFallosUsuario.find(p => p.cfallo.toString() === codigo) || null;
        parte.ldiscordia = "N";
        //limpiar multiselect
      }
    }
  }

  //Asignar los recurrentes activos con un fallo
  asignarFalloAlVoto(): void{
    let falloV : FalloModel = {xfallo: 'AL VOTO', cfallo: '1727'};
    for(let parte of this.tablillaData.recurrentes){
      if(parte.lrecurrente == 'S'){
        parte.ldiscordia = "N";
        parte.fallo = falloV;
      }
    }
  }

  refrescarDiscordia(): void {
    for(let parte of this.tablillaData.recurrentes){
      if(parte.lrecurrente ==='S'){
        parte.discordias = [];
      }
    }
  }

  seleccionarSentido(parte : RecurrenteModel) : void {
    if(parte.sentido && parte.sentido.csentido === '03' ){
      parte.ldiscordia = "S";   
      let falloVacio : FalloModel = {xfallo: '', cfallo: ''}
      parte.fallo = falloVacio;
      parte.discordias = null;
    } else {
      parte.ldiscordia = "N";
      parte.discordias = null;
    }
  }

  validarRecurrentes(requestGrabar: any) : boolean {
    let changed = false;
    if(this.ponenteMode){
      if(requestGrabar.fallos && requestGrabar.fallos.length > 0) {
        //En caso haya votacion para todos los recurrentes, comprobamos si cumplen
        for( let obj  of requestGrabar.fallos ){
          //Recorre en busqueda de algún cambio
          if (obj.codigoSentido || obj.codigoFallo || obj.discordia=='S'){
            changed = true;
          }
          //existen discordias sin magistrados seleccionados
          if(obj.discordia==='S' && !obj.discordias){
            return false;
          }
        }
        if (changed) {
          for (let obj2 of requestGrabar.fallos) {
            if (obj2.discordia !== 'S' && (!obj2.codigoSentido || !obj2.codigoFallo)) {
              return false;
            }
          }
        }
        return true;
      } else {
        //no existen votacion registrados
        return false;
      }
    } else {
      //no es ponente, votacion de recurrentes esta bloqueado por lo que no debe validar
      return true;
    }
  }

  back(): void {
    this.router.navigate(['/votacion/listado']);
  }

  openDialog(parte : RecurrenteModel) {
    //console.log('boton discordia :' , {...parte.discordias});
    this.seleccionDisc=[];
    this.limpiarRecurrenteSeleccionado();
    this.validaDiscordia = false;
    this.recurrenteSeleccionado = parte; 
    this.onDialogHide();
    // let arrayD : any = this.recurrenteSeleccionado.discordias;
    // if (this.recurrenteSeleccionado && this.recurrenteSeleccionado.discordias && this.recurrenteSeleccionado.discordias.length > 0) {
    //   console.log('boton discordia :' , arrayD);
    //   for (let objdis of arrayD) {
    //     this.seleccionDisc.push({codigoUsuarioVocal : objdis.codigoUsuarioVocal, nombreUsuario : objdis.nombreUsuario, iniciales : objdis.iniciales, lponente: objdis.flagPonente, id: '' });
    //   }
    // }
    this.displayDialogDiscordias = true;
  }

  onDialogHide() {
    if(this.validaDiscordia) {
      const index = this.tablillaData.recurrentes.findIndex(i => i.nsecuencia === this.recurrenteSeleccionado.nsecuencia);
      if (index !== -1) {
        this.tablillaData.recurrentes[index] = { ...this.recurrenteSeleccionado };
      }
      this.displayDialogDiscordias = false;
    } else {
      const index = this.tablillaData.recurrentes.findIndex(i => i.nsecuencia === this.recurrenteSeleccionado.nsecuencia);
      if (index !== -1) {
        this.tablillaData.recurrentes[index].discordias = [];
      }
      this.recurrenteSeleccionado.discordias = [];
      this.displayDialogDiscordias = false;
    }
  }

  onOptionsSelected() {
    const index = this.tablillaData.recurrentes.findIndex(i => i.nsecuencia === this.recurrenteSeleccionado.nsecuencia);
    if (index !== -1) {
      this.tablillaData.recurrentes[index] = { ...this.recurrenteSeleccionado };
    }
    this.displayDialogDiscordias = false;
  }

  validarDiscordias(){
    this.validaDiscordia = false;
    if(this.recurrenteSeleccionado.discordias && this.recurrenteSeleccionado.discordias.length>1) {
      this.validaDiscordia = true;
    }
  }

  avanzarCasacion(): void{
    if(this.casacionSeleccionada)
    { 
      let indexActual = this.casacionSeleccionada.nro;
      const index = this.listaCasaciones.findIndex(i => i.nro == indexActual);
      this.eventSelectCasacion(this.listaCasaciones[index+1]);
    } else {
      Swal.fire('¡Atención!', "No existe casación seleccionada", 'warning');
    }
  }

  retrocederCasacion(): void{
    if(this.casacionSeleccionada)
    { 
      let indexActual = this.casacionSeleccionada.nro;    
      const index = this.listaCasaciones.findIndex(i => i.nro == indexActual);
      this.eventSelectCasacion(this.listaCasaciones[index-1]);
    } else {
      Swal.fire('¡Atención!', "No existe casación seleccionada", 'warning');
    }
  }

  asignarCasacion(casActual : any|null): void{
    if(casActual && casActual.nro != null){
      let indexActual = casActual.numeroRecurso;
      const index = this.listaCasaciones.findIndex(i => i.numeroRecurso == indexActual);
      let casacionS = JSON.parse(JSON.stringify(this.listaCasaciones[index]));
      this.refrescarCasacion({...casacionS});
      
    } else {
      this.casacionSeleccionada = this.listaCasaciones[0];
      this.eventSelectCasacion(this.casacionSeleccionada);
    }    
    //his.eventSelectCasacion(this.casacionSeleccionada);
  } 

  refrescarCasacion(cas :CasacionModel): void {
    this.casacionSeleccionada = {...cas};
    this.eventSelectCasacion({...this.casacionSeleccionada});
  }


  comprobarLimiteLista(casSeleccionada: CasacionModel): void{
    const index = this.listaCasaciones.findIndex(i => i.nro === casSeleccionada.nro);
    if(index === this.listaCasaciones.length - 1){
      this.flagBtnSiguiente = false;
    } else{
      this.flagBtnSiguiente = true;
    }
    if(index === 0){
      this.flagBtnAnterior = false;
    } else{
      this.flagBtnAnterior = true;
    }
  }

  comprobarPermisos(): void {
    this.magistradoInMode = false;
    this.ponenteMode = false;
    this.jurisprudenciaMode = false;
    for (let magistrado of this.listaMagistrados) {
      if (this.usuarioWeb == magistrado.codigoUsuarioVocal) {
        this.magistradoInMode = true;
        // if(!this.comprobarEstadoPublicado()){
          if (magistrado.lponente == 'S') {
            this.ponenteActual = magistrado.codigoUsuarioVocal;
            this.ponenteMode = true;
          }
        // } 
        // else if (magistrado.lponente == 'S') {
        //     this.ponenteActual = magistrado.codigoUsuarioVocal;
        //     this.jurisprudenciaMode = true;
        // }
      } else {
        if (magistrado.lponente == 'S') {
          this.ponenteActual = magistrado.codigoUsuarioVocal;
        }
      }
    }
    
  }

  // comprobarEstadoPublicado(): boolean {
  //   if(this.casacionSeleccionada && this.casacionSeleccionada.flagPlublicado == 'S'){
  //     return true;
  //   } else {
  //     return false;
  //   }
  // }

  reloadContador(txtActual : string) : void {
    this.remainingText = 1500 - txtActual.length;
  }

  valueChange(value : string) : void {
    this.remainingText = 1500 - value.length;
  }

  showOverlay(txtActual : string) : void {
    this.remainingTextRec = 1500 - txtActual.length;
  }

  changeOverlay(value : string) : void {
    this.remainingTextRec = 1500 - value.length;
  }

  listarCasaciones(fecha: string, casacionActual : any|null): void {
    if(fecha){
      this.store.dispatch(actions.mostrarCargando({ estado: true }));
      this.casacionService.listarCasaciones(fecha,this.distritoUsuario,this.provinciaUsuario,this.salaUsuario).subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data){
              this.listaCasaciones = response.data;
            }
            else{
              this.router.navigate(['/votacion/listado']);
              Swal.fire('¡Atención!', "Listar Fallos: Error de respuesta", 'warning');
            }
          } else{
            this.router.navigate(['/votacion/listado']);
            Swal.fire('¡Atención!', response.descripcion, 'warning');
          }
        },
        complete:()=>{
          this.asignarCasacion(casacionActual);
        },      
        error:(err)=>{
          this.listaCasaciones =  [];
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });      
    } else {
      Swal.fire('Atención!', 'Fecha no válida', 'info');
      this.router.navigate(['/votacion/listado']);
    }
  } 

  listarCasacionesRelacionados(distrito :string|null, numeroUnico:string|null, nincidente:string|null): void {

    var req: RequestRelacionadosModel = { distrito: distrito, nUnico: numeroUnico, nIncidente: nincidente };

      this.store.dispatch(actions.mostrarCargando({ estado: true }));
      this.casacionService.listarCasacionesRelacionados(req).subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data){
              this.listaCasacionesRelacionadas = response.data;
            }
          }
        },
        complete:()=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
        },      
        error:(err)=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });      
  } 

  listarDocumentosDigitales(numeroUnico:string|null, nincidente:string|null): void {

    var req: RequestListarDocumentosDigModel = { nUnico: numeroUnico, nIncidente: nincidente };

      this.store.dispatch(actions.mostrarCargando({ estado: true }));
      this.casacionService.listarDocumentosDigitales(req).subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data){
              this.listaDocumentosDigitales = response.data;
            }
          }
        },
        complete:()=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
        },      
        error:(err)=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });      
  } 


  verTablilla(tab : CasacionRelacionadaModel) :void{
    //limpiar modelo
    this.limpiarTablillaSecundaria();
    this.reqGenTabSecundaria.distrito = this.distritoUsuario;
    this.reqGenTabSecundaria.provincia = this.provinciaUsuario;
    this.reqGenTabSecundaria.instancia = this.salaUsuario;
    this.reqGenTabSecundaria.visualizaPonente = 'S';
    this.reqGenTabSecundaria.nUnico = tab.numeroUnico;
    this.reqGenTabSecundaria.nIncidente = tab.numeroIncidente;
    this.reqGenTabSecundaria.fechaIngreso = tab.fechaIngreso;
    this.reqGenTabSecundaria.programacion = tab.codProgramacion;
    this.reqGenTabSecundaria.grupo = tab.grupo;
    this.reqGenTabSecundaria.secuencia = tab.secuencia;
    this.reqGenTabSecundaria.conformacion = tab.conformacion;
    this.reqGenTabSecundaria.fechaProgramacion = tab.fechaProgramacion;
    this.reqGenTabSecundaria.discordia = tab.discordia;
    this.reqGenTabSecundaria.sentido = tab.sentido;
    this.reqGenTabSecundaria.votacion = tab.votacion;
    this.reqGenTabSecundaria.codUsuario = this.usuarioWeb;
    //hacer request de la tablilla -> asignar a modelo
    this.cargarTablilla();
    //abrir dialogo
    this.dialogTab = true;
  }

  onDialogVerTablillaHide() {
    this.dialogTab = false;
  }

  cargarTablilla(): void {
    this.store.dispatch(actions.mostrarCargando({ estado: true }));
    this.casacionService.cargarDatosTablilla({...this.reqGenTabSecundaria}).subscribe({
      next:(response:any)=>{
        if(response.codigo===constantes.RESPONSE_COD_EXITO){
          if(response.data){
            this.tablillaDataSec.descProceso = response.data.juzgado.xproceso?response.data.juzgado.xproceso.trim():'';
            this.tablillaDataSec.descActoProcesalJuez = response.data.juzgado.xactoProcesalJuz?response.data.juzgado.xactoProcesalJuz.trim():'';
            this.tablillaDataSec.fojasJuez = response.data.juzgado.nfojasJuz?response.data.juzgado.nfojasJuz:'';
            this.tablillaDataSec.descFalloJuez = response.data.juzgado.xfalloJuz?response.data.juzgado.xfalloJuz.trim():'';
            this.tablillaDataSec.descActoProcesalProcedencia = response.data.juzgado.xactoProcesal?response.data.juzgado.xactoProcesal.trim():'';
            this.tablillaDataSec.fojasOrigenProcedencia  = response.data.juzgado.nfojas?response.data.juzgado.nfojas:'';
            this.tablillaDataSec.descFalloProcedencia  = response.data.juzgado.xfallo?response.data.juzgado.xfallo.trim():'';
            this.tablillaDataSec.tipoabrev  = response.data.juzgado.xabrev?response.data.juzgado.xabrev.trim():'';
            this.tablillaDataSec.especialidad  = response.data.juzgado.cespecialidad?response.data.juzgado.cespecialidad.trim():'';
            this.tablillaDataSec.apuntes = response.data.apuntes?response.data.apuntes.trim():'';
            this.listaMagistradosSec = response.data.magistrados;
            this.llenarMagistradosSec(this.listaMagistradosSec);
            // this.listarFallos();
            this.tablillaDataSec.magistrados = response.data.magistrados || null;
            this.tablillaDataSec.impedimento1  = response.data.impedimento.xusuario1?response.data.impedimento.xusuario1.trim():'';
            this.tablillaDataSec.impedimento1Flag  = response.data.impedimento.limpedido1?response.data.impedimento.limpedido1.trim():'';
            this.tablillaDataSec.impedimento2  = response.data.impedimento.xusuario2?response.data.impedimento.xusuario2.trim():'';
            this.tablillaDataSec.impedimento2Flag  = response.data.impedimento.limpedido2?response.data.impedimento.limpedido2.trim():'';
            this.tablillaDataSec.impedimento3  = response.data.impedimento.xusuario3?response.data.impedimento.xusuario3.trim():'';
            this.tablillaDataSec.impedimento3Flag  = response.data.impedimento.limpedido3?response.data.impedimento.limpedido3.trim():'';
            this.tablillaDataSec.impedimento4  = response.data.impedimento.xusuario4?response.data.impedimento.xusuario4.trim():'';
            this.tablillaDataSec.impedimento4Flag  = response.data.impedimento.limpedido4?response.data.impedimento.limpedido4.trim():'';
            this.tablillaDataSec.impedimento5  = response.data.impedimento.xusuario5?response.data.impedimento.xusuario5.trim():'';
            this.tablillaDataSec.impedimento5Flag  = response.data.impedimento.limpedido5?response.data.impedimento.limpedido5.trim():'';
            this.tablillaDataSec.materias  = response.data.materias || null;
            this.tablillaDataSec.causales  = response.data.causales || null;
            this.tablillaDataSec.recurrentes = response.data.partes;
            this.tablillaDataSec.recurrentes.sort((a, b) => (a.lrecurrente === b.lrecurrente) ? 0 : a.lrecurrente === "S" ? -1 : 1);
            //this.tempRecurrentes = response.data.partes;
            this.documentosAdjuntosSec = response.data.jurisprudencias;
            this.tablillaDataSec.existe = true;
            
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

  completarlistaMagSP(): void {
    this.listaMagSinPonente = [];
    if(this.tablillaData.magistrados){
      let arrayMag : any = JSON.parse(JSON.stringify(this.tablillaData.magistrados));
      for( let mag of arrayMag){
        if(mag.lponente !== 'S'){
          let model : MagistradoModel = {
            codigoUsuarioVocal : mag.codigoUsuarioVocal,
            nombreUsuario : mag.nombreUsuario,
            iniciales : mag.iniciales,
            lponente: mag.lponente,
            id : ''
          }
          this.listaMagSinPonente.push(model);
        }
      }
    }
  }

  //actualizar la fecha elegida
  formatearFechaCompleta(): string {
    const dayOfWeek = this.getDayOfWeek(this.dateSeleccionada).toUpperCase();
    const day = this.datePipe.transform(this.dateSeleccionada, 'dd', 'es-ES');
    const month = this.datePipe.transform(this.dateSeleccionada, 'MMMM', 'es-ES');
    const year = this.datePipe.transform(this.dateSeleccionada, 'yyyy', 'es-ES');
    return `${dayOfWeek} ${day} DE ${month?.toUpperCase()} DEL ${year}`;
  }

  private getDayOfWeek(date: Date): string {
    const options: Intl.DateTimeFormatOptions = { weekday: 'long' };
    return new Intl.DateTimeFormat('es-ES', options).format(date);
  }

  actualizarDesc() {
    if(this.tipoBusquedaJuris=='PJ'){
      this.descJurisprudencia = 'Nro. Casación (Ejm. 2030-2016)';
    } else if(this.tipoBusquedaJuris=='TC') {
      this.descJurisprudencia = 'Número STC (Ejm. 5705-2008)';
    } else if(this.tipoBusquedaJuris=='TF') {
      this.descJurisprudencia = 'Número RTF (Ejm. 03112-A-2013)';
    }
  }

  exeAutoguardado() {
    
    if(this.existenCambios() && this.isUserInactive){
      this.store.dispatch(actions.mostrarCargando({ estado: true }));
      this.fillVotacion();
      if(this.reqGrabarVotacion.apuntes &&this.reqGrabarVotacion.apuntes?.length > 1500){
        this.store.dispatch(actions.mostrarCargando({ estado: false }));
        Swal.fire('¡Atención!', "La longitud de los apuntes debe ser menor a 1500 caracteres.",);
        return;
      }
      this.casacionService.autoguardarVoto({...this.reqGrabarVotacion}).subscribe({
        next:(response:any)=>{
          if(response.codigo===constantes.RESPONSE_COD_EXITO){
            if(response.data){
              this.tempApuntes = JSON.parse(JSON.stringify(this.tablillaData.apuntes)); 
              this.tempAdjuntos = JSON.parse(JSON.stringify(this.documentosAdjuntos)); 
              this.tempAnotaciones = JSON.parse(JSON.stringify(this.tablillaData.recurrentes)); 
            }
          }
          else{
            Swal.fire('¡Atención!', response.descripcion, 'warning');
          }
        },
        complete:()=>{
          //this.loadCasaciones();
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
        },        
        error:(err)=>{
          this.store.dispatch(actions.mostrarCargando({ estado: false }));
          Swal.fire('¡Atención!', err, 'warning');
        }
      });
    } else {
      //console.log('no hubo cambios');
    }
  }

  arraysOfObjectsAreEqualUnordered(arr1: any[], arr2: any[]): boolean {
    if (arr1.length !== arr2.length) {
      return false;
    }
    const objectMap = new Map<string, number>();
    arr1.forEach(obj => {
      const key = JSON.stringify(obj);
      objectMap.set(key, (objectMap.get(key) || 0) + 1);
    });
    for (const obj of arr2) {
      const key = JSON.stringify(obj);
      if (!objectMap.has(key) || objectMap.get(key) === 0) {
        return false;
      }
      objectMap.set(key, objectMap.get(key)! - 1);
    }
    return Array.from(objectMap.values()).every(count => count === 0);
  }

  existenCambios(): boolean{
    if(this.tempApuntes !== this.tablillaData.apuntes){
      return true;
    }
    if(!this.arraysOfObjectsAreEqualUnordered([...this.tempAdjuntos],[...this.documentosAdjuntos])){
      return true;
    }
    if(!this.arraysOfObjectsAreEqualUnordered(this.tempAnotaciones,this.tablillaData.recurrentes)){
      return true;
    }

    return false;
  }

  ngOnDestroy(): void {
    if (this.temporizadorI) {
      clearInterval(this.temporizadorI);
    }
    if (this.inactivityTimeoutId) {
      clearTimeout(this.inactivityTimeoutId);
    }
  }
}