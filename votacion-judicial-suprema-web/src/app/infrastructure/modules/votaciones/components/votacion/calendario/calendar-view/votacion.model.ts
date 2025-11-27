export interface Votacion {
  id: string;
  expediente: string;
  fecha: Date;
  ponente: string;
  titulo: string;
  partes: Parte[];
  contenidoHtml: string;
  itemsDisponibles: string[];
  itemsSeleccionados: string[];
  documentos: Documento[];
}

export interface Parte {
  tipoParte: string;
  nombre: string;
  sentido: string;
  fallo: string;
  discordia: string[];
  efectosSentido: string;
}

export interface Documento {
  id: string;
  nombre: string;
  url: string;
  anotaciones?: string; // JSON string
}

export interface Annotation {
  id: string;
  type: 'highlight' | 'text' | 'draw' | 'image';
  page: number;
  coordinates: AnnotationCoordinates;
  content: string | object;
  color?: string;
  timestamp: string;
}

export interface AnnotationCoordinates {
  x: number;
  y: number;
  width?: number;
  height?: number;
}

export interface AnnotationData {
  documentId: string;
  annotations: Annotation[];
}

export interface CalendarEvent {
  date: Date;
  votaciones: Votacion[];
}
