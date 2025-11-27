package pe.gob.pj.votacion.infraestructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pe.gob.pj.votacion.domain.model.sijsuprema.*;
import pe.gob.pj.votacion.infraestructure.rest.responses.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MaestraMapper {

    //Fallo
    FalloResponse toFalloResponse(Fallo fallo);
    List<FalloResponse> toFallosResponse(List<Fallo> fallos);

    //Sentido
    SentidoResponse toSentidoResponse(Sentido sentido);
    List<SentidoResponse> toSentidosResponse(List<Sentido> sentidos);

    //Colegiado
    ColegiadoResponse toColegiadoResponse(Colegiado colegiado);
    List<ColegiadoResponse> toColegiadosResponse(List<Colegiado> colegiados);

    //Estados-votacion
    EstadoVotacionResponse toEstadoVotacionResponse(EstadoVotacion estadoVotacion);
    List<EstadoVotacionResponse> toEstadosVotacionResponse(List<EstadoVotacion> estadosVotacion);

    //Estados-votacion
    EstadoProyectoResponse toEstadoProyectoResponse(EstadoProyecto estadoProyecto);
    List<EstadoProyectoResponse> toEstadosProyectoResponse(List<EstadoProyecto> estadosProyecto);

}
