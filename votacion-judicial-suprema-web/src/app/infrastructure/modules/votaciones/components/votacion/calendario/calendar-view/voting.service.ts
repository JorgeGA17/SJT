import { Injectable, signal, computed } from '@angular/core';
import { Votacion } from './votacion.model';

@Injectable({
  providedIn: 'root'
})
export class VotingService {
  // Signals para estado reactivo
  private votacionesSignal = signal<Votacion[]>(this.generateMockData());
  private selectedVotacionIdSignal = signal<string | null>(null);

  // Exponer señales como readonly
  readonly votaciones = this.votacionesSignal.asReadonly();
  readonly selectedVotacionId = this.selectedVotacionIdSignal.asReadonly();

  // Computed signal para la votación seleccionada
  readonly selectedVotacion = computed(() => {
    const id = this.selectedVotacionIdSignal();
    return id ? this.votacionesSignal().find(v => v.id === id) : null;
  });

  constructor() {}

  // Obtener votaciones por fecha
  getVotacionesByDate(date: Date): Votacion[] {
    return this.votacionesSignal().filter(v =>
      this.isSameDay(v.fecha, date)
    );
  }

  // Obtener votaciones por rango de fechas
  getVotacionesByDateRange(startDate: Date, endDate: Date): Votacion[] {
    return this.votacionesSignal().filter(v =>
      v.fecha >= startDate && v.fecha <= endDate
    );
  }

  // Obtener votación por ID
  getVotacionById(id: string): Votacion | undefined {
    return this.votacionesSignal().find(v => v.id === id);
  }

  // Seleccionar votación
  selectVotacion(id: string): void {
    this.selectedVotacionIdSignal.set(id);
  }

  // Actualizar votación
  updateVotacion(votacion: Votacion): void {
    this.votacionesSignal.update(votaciones =>
      votaciones.map(v => v.id === votacion.id ? votacion : v)
    );
  }

  // Crear nueva votación
  createVotacion(votacion: Votacion): void {
    this.votacionesSignal.update(votaciones => [...votaciones, votacion]);
  }

  // Eliminar votación
  deleteVotacion(id: string): void {
    this.votacionesSignal.update(votaciones =>
      votaciones.filter(v => v.id !== id)
    );
  }

  // Utilidad: comparar fechas (mismo día)
  private isSameDay(date1: Date, date2: Date): boolean {
    return date1.getFullYear() === date2.getFullYear() &&
           date1.getMonth() === date2.getMonth() &&
           date1.getDate() === date2.getDate();
  }

  // Generar datos de ejemplo (20+ votaciones)
  private generateMockData(): Votacion[] {
    const votaciones: Votacion[] = [];
    const ponentes = [
      'Magistrado Juan Pérez González',
      'Magistrada María López Rodríguez',
      'Magistrado Carlos Sánchez Martínez',
      'Magistrada Ana García Fernández',
      'Magistrado Luis Martínez Jiménez'
    ];

    const tiposExpediente = ['AM', 'ADR', 'RA', 'RE', 'RD'];
    const today = new Date();

    // Generar 25 votaciones distribuidas en diferentes fechas
    for (let i = 0; i < 25; i++) {
      const dayOffset = Math.floor(Math.random() * 30) - 15; // De -15 a +15 días
      const fecha = new Date(today);
      fecha.setDate(today.getDate() + dayOffset);

      const expedienteNum = String(i + 1).padStart(4, '0');
      const tipoExp = tiposExpediente[Math.floor(Math.random() * tiposExpediente.length)];

      votaciones.push({
        id: `VOT-${i + 1}`,
        expediente: `${tipoExp} ${expedienteNum}/2024`,
        fecha: fecha,
        ponente: ponentes[Math.floor(Math.random() * ponentes.length)],
        titulo: `Votación sobre ${tipoExp} ${expedienteNum}/2024 - ${this.generateTituloAleatorio()}`,
        partes: this.generatePartes(),
        contenidoHtml: `<h2>Análisis del caso ${tipoExp} ${expedienteNum}/2024</h2><p>Este es el contenido HTML del análisis legal para la votación...</p>`,
        itemsDisponibles: [
          'Jurisprudencia relevante A',
          'Jurisprudencia relevante B',
          'Doctrina aplicable C',
          'Precedente judicial D',
          'Normativa E'
        ],
        itemsSeleccionados: [
          'Jurisprudencia relevante A',
          'Precedente judicial D'
        ],
        documentos: this.generateDocumentos(i + 1)
      });
    }

    return votaciones.sort((a, b) => a.fecha.getTime() - b.fecha.getTime());
  }

  private generateTituloAleatorio(): string {
    const titulos = [
      'Recurso de amparo contra acto de autoridad',
      'Acción de inconstitucionalidad',
      'Controversia constitucional',
      'Recurso de revisión administrativa',
      'Apelación en materia civil'
    ];
    return titulos[Math.floor(Math.random() * titulos.length)];
  }

  private generatePartes(): any[] {
    return [
      {
        tipoParte: 'Demandante',
        nombre: 'Juan Carlos Rodríguez Pérez',
        sentido: 'Favorable',
        fallo: 'Procedente',
        discordia: [],
        efectosSentido: 'Se ordena la restitución de derechos y el pago de daños y perjuicios conforme a derecho.'
      },
      {
        tipoParte: 'Demandado',
        nombre: 'Secretaría de Hacienda y Crédito Público',
        sentido: 'Desfavorable',
        fallo: 'Improcedente',
        discordia: ['Magistrado Carlos Sánchez'],
        efectosSentido: 'Se confirma la resolución impugnada en todas sus partes.'
      },
      {
        tipoParte: 'Tercero Interesado',
        nombre: 'Instituto Nacional Electoral',
        sentido: 'Neutral',
        fallo: 'Sin materia',
        discordia: [],
        efectosSentido: 'No aplica.'
      }
    ];
  }

  private generateDocumentos(votacionNum: number): any[] {
    return [
      {
        id: `DOC-${votacionNum}-1`,
        nombre: `Sentencia_Principal_${votacionNum}.pdf`,
        url: 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf',
        anotaciones: undefined
      },
      {
        id: `DOC-${votacionNum}-2`,
        nombre: `Anexo_Tecnico_${votacionNum}.pdf`,
        url: 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf',
        anotaciones: undefined
      },
      {
        id: `DOC-${votacionNum}-3`,
        nombre: `Jurisprudencia_Aplicable_${votacionNum}.pdf`,
        url: 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf',
        anotaciones: undefined
      }
    ];
  }
}
