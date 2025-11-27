package pe.gob.pj.votacion.domain.model.sijsuprema.command;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;


import java.util.List;

@Accessors(fluent = true)
@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FalloCommand {

    Integer numeroSecuencia;
    String codigoSentido;
    Integer codigoFallo;
    String flagDiscordia;
    String anotacion;
    List<DiscordiaCommand> discordias;
}
