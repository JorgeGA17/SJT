package pe.gob.pj.votacion.infraestructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import pe.gob.pj.votacion.domain.model.sijsuprema.Registro;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.DiscordiaCommand;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.FalloCommand;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.JurisprudenciaCommand;
import pe.gob.pj.votacion.domain.model.sijsuprema.command.RegistrarVotoCommand;
import pe.gob.pj.votacion.infraestructure.rest.requests.DiscordiaRequest;
import pe.gob.pj.votacion.infraestructure.rest.requests.FalloRequest;
import pe.gob.pj.votacion.infraestructure.rest.requests.JurisprudenciaRequest;
import pe.gob.pj.votacion.infraestructure.rest.requests.RegistrarVotoRequest;
import pe.gob.pj.votacion.infraestructure.rest.responses.RegistroResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = FechaMapperHelper.class)
public interface VotoMapper {

    RegistroResponse toRegistroVotoResponse(
            Registro registro);

    @Mapping(target = "codigoSede", source = "request.codigoSede")
    @Mapping(target = "codigoEstado", source = "request.codigoEstado")
    @Mapping(target = "codigoOrganoJuris", source = "request.codigoOrganoJuris")
    @Mapping(target = "fechaIngreso", source = "request.fechaIngreso", qualifiedByName = "stringToZonedDateTime")
    @Mapping(target = "fechaProgramacion", source = "request.fechaProgramacion", qualifiedByName = "stringToZonedDateTime")
    @Mapping(target = "fechaEstado", source = "request.fechaEstado", qualifiedByName = "stringToZonedDateTime")
    @Mapping(target = "codigoUsuarioPonente", source = "request.codigoUsuarioPonente")
    @Mapping(target = "codigoEspecialidad", source = "request.codigoEspecialidad")
    @Mapping(target = "numeroSentido", source = "request.numeroSentido")
    @Mapping(target = "numeroVotacion", source = "request.numeroVotacion")
    @Mapping(target = "numeroGrupoVoto", source = "request.numeroGrupoVoto")
    @Mapping(target = "numeroSecuenciaVoto", source = "request.numeroSecuenciaVoto")
    @Mapping(target = "numeroConformacionVoto", source = "request.numeroConformacionVoto")
    @Mapping(target = "apuntes", source = "request.apuntes")
    @Mapping(target = "flagVoto", source = "request.flagVoto")
    @Mapping(target = "codigoUsuario", source = "request.codigoUsuario")
    @Mapping(target = "codigoArea", source = "request.codigoArea")
    @Mapping(target = "abrev", source = "request.abrev")
    @Mapping(target = "codigoAudUid", source = "request.codigoAudUid")
    @Mapping(target = "numeroAudIp", source = "request.numeroAudIp")
    @Mapping(target = "fallos", source = "request.fallos")
    @Mapping(target = "jurisprudencias", source = "request.jurisprudencias")
    RegistrarVotoCommand toRegistrarVotoCommand(
            String codigoDistrito, String codigoProvincia, String codigoInstancia,
            String codigoProgramacion, String numeroUnico, String numeroIncidente,
            RegistrarVotoRequest request);

    FalloCommand falloRequestToFalloCommand(FalloRequest request);

    JurisprudenciaCommand jurisprudenciaRequestToJurisprudenciaCommand(JurisprudenciaRequest request);

    DiscordiaCommand discordiaRequestToDiscordiaCommand(DiscordiaRequest request);

}
