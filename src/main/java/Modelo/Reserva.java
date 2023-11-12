package Modelo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {
    private String fechaSolicitud;
    private String fechaViaje;
    private String idCliente;
    private short numPersonas;
    private PaqueteTuristico paqueteTuristico;
}