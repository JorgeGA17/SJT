import { Injectable } from '@angular/core';

export interface OpcionesSimplificacion {
  preservarEstructura?: boolean;
  eliminarComentarios?: boolean;
  normalizarEspacios?: boolean;
  maxBrConsecutivos?: number;
}

@Injectable({
  providedIn: 'root'
})
export class SimplificadorHTML {

  /**
   * Simplifica HTML eliminando etiquetas vacías, spans sin atributos y optimizando párrafos
   * @param html - String HTML a simplificar
   * @returns HTML simplificado
   */
  public simplificarHTML(html: string): string {
    // Validación de entrada
    if (!html || typeof html !== 'string') {
      return '';
    }

    const container = document.createElement('div');
    container.innerHTML = html.trim();

    // 1. Eliminar etiquetas vacías recursivamente
    this.eliminarEtiquetasVacias(container);

    // 2. Eliminar <span> sin atributos recursivamente
    this.eliminarSpansSinAtributos(container);

    // 3. Optimizar párrafos consecutivos (nueva lógica mejorada)
    this.optimizarParrafos(container);

    // 4. Limpiar whitespace innecesario
    this.limpiarWhitespace(container);

    return container.innerHTML;
  }

  /**
   * Versión avanzada con opciones personalizables
   * @param html - String HTML a simplificar
   * @param opciones - Opciones de configuración
   * @returns HTML simplificado según opciones
   */
  public simplificarHTMLAvanzado(html: string, opciones: OpcionesSimplificacion = {}): string {
    const {
      preservarEstructura = true,
      eliminarComentarios = true,
      normalizarEspacios = true,
      maxBrConsecutivos = 2
    } = opciones;

    let resultado = this.simplificarHTML(html);

    if (eliminarComentarios) {
      resultado = resultado.replace(/<!--[\s\S]*?-->/g, '');
    }

    if (!preservarEstructura) {
      // Convertir todos los párrafos a texto plano con <br>
      resultado = resultado.replace(/<p[^>]*>/g, '').replace(/<\/p>/g, '<br>');
    }

    // Limitar <br> consecutivos
    if (maxBrConsecutivos > 0) {
      const brPattern = new RegExp(`(<br\\s*\/?>){${maxBrConsecutivos + 1},}`, 'gi');
      const replacement = '<br>'.repeat(maxBrConsecutivos);
      resultado = resultado.replace(brPattern, replacement);
    }

    if (normalizarEspacios) {
      resultado = resultado.replace(/\s+/g, ' ').trim();
    }

    return resultado;
  }

  /**
   * Elimina etiquetas HTML vacías de forma recursiva
   * @param container - Elemento contenedor
   */
  private eliminarEtiquetasVacias(container: Element): void {
    // Etiquetas que pueden estar vacías por diseño (self-closing o funcionales)
    const etiquetasPermitidas = new Set([
      'BR', 'HR', 'IMG', 'INPUT', 'META', 'LINK', 'AREA', 'BASE', 'COL',
      'EMBED', 'KEYGEN', 'PARAM', 'SOURCE', 'TRACK', 'WBR'
    ]);

    const elementos = Array.from(container.querySelectorAll('*'));
    
    // Procesar desde los elementos más profundos hacia arriba
    elementos.reverse().forEach(elemento => {
      const tagName = elemento.tagName.toUpperCase();
      
      // Saltar etiquetas que pueden estar vacías
      if (etiquetasPermitidas.has(tagName)) {
        return;
      }

      // Verificar si el elemento está realmente vacío
      const textoLimpio = elemento.textContent?.trim() || '';
      const tieneHijosValidos = Array.from(elemento.children).some(hijo => 
        etiquetasPermitidas.has(hijo.tagName.toUpperCase())
      );

      if (!textoLimpio && !tieneHijosValidos) {
        elemento.remove();
      }
    });
  }

  /**
   * Elimina spans sin atributos preservando su contenido
   * @param container - Elemento contenedor
   */
  private eliminarSpansSinAtributos(container: Element): void {
    // Procesar spans desde los más profundos hacia arriba
    const spans = Array.from(container.querySelectorAll('span')).reverse();
    
    spans.forEach(span => {
      if (span.getAttributeNames().length === 0) {
        // Verificar si el span tiene contenido
        if (span.textContent?.trim()) {
          // Reemplazar con nodos de texto, preservando estructura
          const fragment = document.createDocumentFragment();
          while (span.firstChild) {
            fragment.appendChild(span.firstChild);
          }
          span.replaceWith(fragment);
        } else {
          // Eliminar span vacío
          span.remove();
        }
      }
    });
  }

  /**
   * Optimiza párrafos consecutivos con lógica mejorada
   * @param container - Elemento contenedor
   */
  private optimizarParrafos(container: Element): void {
    const childNodes = Array.from(container.childNodes);
    const newContainer = document.createDocumentFragment();
    
    let grupoParrafos: HTMLElement[] = [];
    
    const procesarGrupo = () => {
      if (grupoParrafos.length === 0) return;
      
      if (grupoParrafos.length === 1) {
        // Un solo párrafo, mantenerlo como está
        newContainer.appendChild(grupoParrafos[0].cloneNode(true));
      } else {
        // Múltiples párrafos, optimizar
        const contenidoOptimizado = this.optimizarGrupoParrafos(grupoParrafos);
        if (contenidoOptimizado.trim()) {
          const newP = document.createElement('p');
          newP.innerHTML = contenidoOptimizado;
          newContainer.appendChild(newP);
        }
      }
      grupoParrafos = [];
    };

    childNodes.forEach(node => {
      if (node.nodeType === Node.ELEMENT_NODE) {
        const element = node as HTMLElement;
        
        if (element.tagName === 'P' && element.getAttributeNames().length === 0) {
          grupoParrafos.push(element);
        } else {
          // Elemento diferente o párrafo con atributos
          procesarGrupo();
          newContainer.appendChild(node.cloneNode(true));
        }
      } else if (node.nodeType === Node.TEXT_NODE) {
        // Preservar nodos de texto
        const texto = node.textContent?.trim();
        if (texto) {
          procesarGrupo();
          newContainer.appendChild(node.cloneNode(true));
        }
      } else {
        // Otros tipos de nodos
        procesarGrupo();
        newContainer.appendChild(node.cloneNode(true));
      }
    });

    procesarGrupo();
    
    // Reemplazar el contenido del container
    container.innerHTML = '';
    container.appendChild(newContainer);
  }

  /**
   * Optimiza un grupo de párrafos consecutivos sin atributos
   * @param parrafos - Array de párrafos a optimizar
   * @returns Contenido HTML optimizado
   */
  private optimizarGrupoParrafos(parrafos: HTMLElement[]): string {
    const contenidos: string[] = [];
    
    parrafos.forEach((parrafo, index) => {
      const contenido = parrafo.innerHTML.trim();
      
      // Verificar si el párrafo solo contiene <br>
      const soloBr = /^<br\s*\/?>$/i.test(contenido);
      
      if (soloBr) {
        // Si es solo <br>, decidir si agregarlo basado en el contexto
        const prevContenido = contenidos[contenidos.length - 1];
        const nextParrafo = parrafos[index + 1];
        const nextContenido = nextParrafo ? nextParrafo.innerHTML.trim() : '';
        
        // Solo agregar <br> si hay contenido antes y después
        if (prevContenido && nextContenido && 
            !prevContenido.endsWith('<br>') && 
            !/^<br\s*\/?>$/i.test(nextContenido)) {
          contenidos.push('<br>');
        }
      } else if (contenido) {
        // Contenido real, agregarlo
        contenidos.push(contenido);
      }
    });
    
    return contenidos.join('<br>');
  }

  /**
   * Limpia espacios en blanco innecesarios
   * @param container - Elemento contenedor
   */
  private limpiarWhitespace(container: Element): void {
    const walker = document.createTreeWalker(
      container,
      NodeFilter.SHOW_TEXT,
      null
    );

    const nodosTexto: Text[] = [];
    let node;
    
    while (node = walker.nextNode()) {
      nodosTexto.push(node as Text);
    }

    nodosTexto.forEach(nodoTexto => {
      // Normalizar espacios en blanco múltiples
      const textoLimpio = nodoTexto.textContent?.replace(/\s+/g, ' ') || '';
      
      // Solo mantener el nodo si tiene contenido significativo
      if (textoLimpio.trim()) {
        nodoTexto.textContent = textoLimpio;
      } else {
        // Verificar si estamos entre elementos block
        const parent = nodoTexto.parentElement;
        const isWhitespaceBetweenBlocks = parent && 
          this.isBlockElement(nodoTexto.previousSibling) &&
          this.isBlockElement(nodoTexto.nextSibling);
        
        if (isWhitespaceBetweenBlocks) {
          nodoTexto.remove();
        }
      }
    });
  }

  /**
   * Verifica si un nodo es un elemento de bloque
   * @param node - Nodo a verificar
   * @returns true si es elemento de bloque
   */
  private isBlockElement(node: Node | null): boolean {
    if (!node || node.nodeType !== Node.ELEMENT_NODE) {
      return false;
    }
    
    const blockElements = new Set([
      'DIV', 'P', 'H1', 'H2', 'H3', 'H4', 'H5', 'H6', 'BLOCKQUOTE',
      'PRE', 'UL', 'OL', 'LI', 'HR', 'TABLE', 'THEAD', 'TBODY', 'TR', 'TD', 'TH'
    ]);
    
    return blockElements.has((node as Element).tagName.toUpperCase());
  }

  /**
   * Valida si el HTML es válido básicamente
   * @param html - String HTML a validar
   * @returns true si es válido
   */
  public validarHTML(html: string): boolean {
    if (!html || typeof html !== 'string') {
      return false;
    }
    
    try {
      const container = document.createElement('div');
      container.innerHTML = html;
      return container.innerHTML.length > 0;
    } catch (error) {
      return false;
    }
  }

  /**
   * Obtiene estadísticas del HTML simplificado
   * @param htmlOriginal - HTML original
   * @param htmlSimplificado - HTML simplificado
   * @returns Objeto con estadísticas
   */
  public obtenerEstadisticas(htmlOriginal: string, htmlSimplificado: string): {
    longitudOriginal: number;
    longitudSimplificada: number;
    porcentajeReduccion: number;
    elementosEliminados: number;
  } {
    const containerOriginal = document.createElement('div');
    const containerSimplificado = document.createElement('div');
    
    containerOriginal.innerHTML = htmlOriginal;
    containerSimplificado.innerHTML = htmlSimplificado;
    
    const elementosOriginales = containerOriginal.querySelectorAll('*').length;
    const elementosSimplificados = containerSimplificado.querySelectorAll('*').length;
    
    const longitudOriginal = htmlOriginal.length;
    const longitudSimplificada = htmlSimplificado.length;
    const porcentajeReduccion = Math.round(((longitudOriginal - longitudSimplificada) / longitudOriginal) * 100);
    
    return {
      longitudOriginal,
      longitudSimplificada,
      porcentajeReduccion,
      elementosEliminados: elementosOriginales - elementosSimplificados
    };
  }

  /**
   * Método adicional para limpiar <br> excesivos después de la optimización
   * @param html - HTML a limpiar
   * @param maxConsecutivos - Máximo número de <br> consecutivos permitidos
   * @returns HTML limpiado
   */
  public limpiarBrExcesivos(html: string, maxConsecutivos: number = 2): string {
    if (!html) return '';
    
    // Limpiar <br> consecutivos excesivos
    const brPattern = new RegExp(`(<br\\s*\/?>){${maxConsecutivos + 1},}`, 'gi');
    const replacement = '<br>'.repeat(maxConsecutivos);
    
    return html.replace(brPattern, replacement);
  }
}