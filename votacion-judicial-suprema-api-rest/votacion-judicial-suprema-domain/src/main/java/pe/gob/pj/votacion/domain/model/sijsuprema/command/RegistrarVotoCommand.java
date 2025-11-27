package pe.gob.pj.votacion.domain.model.sijsuprema.command;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Accessors(fluent = true)
@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrarVotoCommand {

    String codigoDistrito;
    String codigoProvincia;
    String codigoInstancia;
    String codigoProgramacion;
    BigDecimal numeroUnico;
    Integer numeroIncidente;
    String codigoSede;
    String codigoEstado;
    String codigoOrganoJuris;
    ZonedDateTime fechaIngreso;
    ZonedDateTime fechaProgramacion;
    ZonedDateTime fechaEstado;
    String codigoUsuarioPonente;
    String codigoEspecialidad;
    Integer numeroSentido;
    Integer numeroVotacion;
    Integer numeroGrupoVoto;
    Integer numeroSecuenciaVoto;
    Integer numeroConformacionVoto;
    String apuntes;
    String flagVoto;
    String codigoUsuario;
    String codigoArea;
    String abrev;
    String codigoAudUid;
    String numeroAudIp;
    List<FalloCommand> fallos;
    List<JurisprudenciaCommand> jurisprudencias;

}
