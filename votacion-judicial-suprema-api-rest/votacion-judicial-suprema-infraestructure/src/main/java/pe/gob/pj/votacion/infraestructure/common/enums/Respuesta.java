package pe.gob.pj.votacion.infraestructure.common.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@RequiredArgsConstructor
public enum Respuesta {


  VALIDACION_COLEGIADO(-1, "AVISO: Para realizar la Votacion por favor conformar el Colegiado en el SIJ SUPREMO"),
  VALIDACION_PONENTE(-1, "AVISO: Para realizar la Votacion por favor configurar el Ponente en el SIJ SUPREMO"),
  INCREMENTAR_SECUENCIA(-1, "No se pudo incrementar la secuencia tras condición de carrera."),
  INSERTAR_SECUENCIA(-1, "Error insertando la secuencia (secuencia_documento)"),
  CONTAR_SENTIDOS(-1, "Error leyendo cantidad de sentidos (expediente_sentido)."),
  ACTUALIZAR_ULTIMO(-1, "Error actualizando l_ultimo=N en expediente_sentido."),
  INSERTAR_SENIDO(-1, "Error insertando nuevo sentido en expediente_sentido."),
  ACTUALIZAR_AUDIENCIA(-1, "Error actualizando programa_audiencia_sala."),
  ACTUALIZAR_CONFORMACION_GRUPO(-1, "Error actualizando conformacion_grupo."),
  ACTUALIZAR_INSTANCIA_EXPEDIENTE(-1, "Error actualizando instancia_expediente."),
  ACTUALIZAR_EXPEDIENTE_SENTIDO(-1, "Error actualizando l_ultimo=N en expediente_sentido_votacion."),
  INSERTAR_EXPEDIENTE_SENTIDO_VOTACION(-1, "Error insertando expediente_sentido_votacion."),
  USUARIO_NO_PROGRAMADO(-1, "El usuario que registra apuntes no pertenece al colegiado de la programación."),
  EXPEDIENTE_ULTIMO_NO_ACTUALIZADO(-1, "Error actualizando l_ultimo = N en expediente_votacion."),
  EXPEDIENTE_VOCAL_NO_ACTUALIZADO(-1, "Error actualizando expediente_vocal."),
  EXPEDIENTE_VOTACION_NO_ACTUALIZADO(-1, "Error actualizando expediente_votacion."),
  EXPEDIENTE_VOCAL_NO_INSERTADO(-1, "Error insertando expediente_vocal."),
  EXPEDIENTE_VOTACION_NO_INSERTADO(-1, "Error insertando expediente_votacion."),
  VOCAL_NO_PROGRAMADO(-1, "No existen vocales activos para la programación indicada."),
  PARTE_NO_RECURRENTE(-1, "Validación: la parte no es recurrente o no cumple filtros de negocio."),
  PADRE_NO_INSERTADO(-1, "Error al insertar padre en parte_votacion."),
  EXPEDIENTE_SENTIDO_NO_EXISTE(-1, "Validación FK: no existe expediente_sentido para el n_sentido indicado."),
  CODIGO_SENTIDO_NO_EXISTE(-1, "Validación FK: c_sentido no existe en sentido_fallo."),
  EXPEDIENTE_SENTIDO_VOTACION_NO_EXISTE(-1, "Validación FK: no existe expediente_sentido_votacion (cabecera) para n_votacion indicado."),
  EXPEDIENTE_SENTIDO_NO_ACTUALIZADO(-1, "Error actualizando l_ultimo=N en expediente_sentido."),
  EXPEDIENTE_VOTACION_PARTE_ULTIMO_NO_ACTUALIZADO(-1, "Error actualizando l_ultimo=N en expediente_votacion_parte (otras votaciones)."),
  EXPEDIENTE_VOTACION_PARTE_NO_INSERTADO(-1, "Error al insertar expediente_votacion_parte."),
  EXPEDIENTE_VOTACION_PARTE_NO_ACTUALIZADO(-1, "Error al actualizar expediente_votacion_parte."),
  VOTO_DISCORDIA_NO_ACTUALIZADO(-1, "Error al actualizar MovVotoDiscordia."),
  VOTO_DISCORDIA_NO_INSERTADO(-1, "Error al registrar/actualizar MovVotoDiscordia."),
  PARAMETROS_NULOS(-1, "Parámetros requeridos nulos (n_unico, n_incidente, x_uuid)."),
  VOTO_JURISPRUDENCIA_NO_ACTUALIZADO(-1, "Error al Actualizar MovVotoJurisp."),
  VOTO_JURISPRUDENCIA_NO_ACTUALIZADO_POST_INSERT(-1, "Error en reintento de UPDATE tras conflicto de INSERT."),
  VOTO_JURISPRUDENCIA_NO_ACTUALIZADO_NO_INSERTADO(-1, "No se pudo completar el upsert (sin filas afectadas)."),


  VALIDACION_EXITOSA(1, "Validación satisfactoria"),
  REGISTRO_EXITOSO(1, "Registro satisfactorio");

  Integer codigoRespuesta;
  String descripcionRespuesta;

}
