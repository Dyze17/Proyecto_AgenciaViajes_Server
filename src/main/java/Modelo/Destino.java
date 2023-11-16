package Modelo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destino implements Serializable {
    private String pais;
    private String ciudad;
    private String descripcion;
    private String clima;
    private String imagen;
}