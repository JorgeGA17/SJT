package pe.gob.pj.votacion.infraestructure.db.sijsuprema.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pe.gob.pj.votacion.infraestructure.common.utils.EsquemaConstants;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "sede", schema = EsquemaConstants.DBO)
public class SedeEntity extends AuditoriaSupremaEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "c_sede", length = 4, nullable = false)
  String codigoSede;

  @Column(name = "x_desc_sede", length = 60)
  String descripcionSede;

  @Column(name = "l_activo", length = 1)
  String activo;

  @Column(name = "c_codigo_app", length = 2)
  String codigoApp;

  @Column(name = "c_sede_prin", length = 4)
  String sedePrincipal;

  @Column(name = "c_sede_corte", length = 4)
  String sedeCorte;

  @Column(name = "l_produccion", length = 1)
  String produccion;

  @Column(name = "x_direccion", length = 150)
  String direccion;

  @Column(name = "latitud_sede", length = 20)
  String latitud;

  @Column(name = "longitud_sede", length = 20)
  String longitud;

  @Column(name = "c_provincia", length = 4)
  String codigoProvincia;

  @Column(name = "l_indicador", length = 1)
  String indicador;

  @Column(name = "l_mesa_web", length = 1)
  String mesaWeb;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "c_distrito", referencedColumnName = "c_distrito")
  DistritoJudicialEntity distritoJudicialEntity;

}
