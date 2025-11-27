export interface DoscordiaFalloModel{
    codigoUsuario:string,
    flagActivo: string,
    id: string | null
}
export interface FallosVotacionModel{
    numeroSecuencia:string,
    codigoSentido:string,
    codigoFallo:string |null,
    flagDiscordia:string,
    anotacion:string,
    discordias:DoscordiaFalloModel [] 
}
export interface VistaCausaModel{
    codigoSede: string,
    codigoEstado: string,
    codigoOrganoJuris: string,
    fechaIngreso: string,
    fechaProgramacion: string,
    fechaEstado: string,
    codigoUsuarioPonente: string,
    codigoEspecialidad: string,
    numeroSentido: string | null,
    numeroVotacion: string | null,
    numeroGrupoVoto: number,
    numeroSecuenciaVoto: number,
    numeroConformacionVoto: number,
    apuntes: string,
    flagVoto: string,
    codigoUsuario: string,
    codigoArea: string | null,
    abrev: string,
    codigoAudUid: string,
    numeroAudIp: string,
    fallos:FallosVotacionModel[],
    jurisprudencia: any
}