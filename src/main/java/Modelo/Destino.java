package Modelo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destino {
    private String pais;
    private String ciudad;
    private String descripcion;
    private String clima;
    private String imagen;
}