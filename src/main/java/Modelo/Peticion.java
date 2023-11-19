package Modelo;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Peticion implements Serializable {
    String mensaje;
    Object objeto;
}