package Modelo;

import lombok.*;
import Enum.EstadoReserva;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {
    private LocalDate fechaSolicitud;
    private LocalDate fechaViaje;
    private String idCliente;
    private short numPersonas;
    private PaqueteTuristico paqueteTuristico;
    private Cliente cliente;
    private GuiaTuristico guia; //opcional
    private EstadoReserva estado;
}