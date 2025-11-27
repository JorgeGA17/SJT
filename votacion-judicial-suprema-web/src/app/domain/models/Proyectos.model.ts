interface ValidacionProyectoModel{
    codUsuarioValidado: string,
    nValidado:  number,
    observacion: string |null,
    iniciales: string
}
export interface ProyectoModel{
    idProyecto: number,
    numeroRecurso: string,
    fechaProgramacion: string,
    codigoEstadoVotacion: string,
    descripcionEstadoVotacion: string,
    flagDiscordia: string,
    usuarioResponsable: string,
    iniciales: string,
    flagPonente: string,
    idEstadoProyecto: number,
    descripcionEstadoProyecto: string,
    numeroUnico: number,
    numeroIncidente: number,
    numeroSentido: number,
    numeroVotacion: number,
    uuidAlfresco: string,
    extension: string,
    numeroEnvio: number,
    fechaEnvio: string,
    fechaIngreso: string,
    codigoProgramacion: string,
    numeroGrupo: number,
    numeroSecuencia: number,
    numeroConformacion: number,
    validaciones: ValidacionProyectoModel[]
}