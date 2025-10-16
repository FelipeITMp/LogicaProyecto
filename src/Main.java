import Manejadores.*;
import Memoria.UsuarioMem;
import Enums.Rol;
import Enums.EstadoCita;
import Enums.Genero;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UsuarioMem memoriaUsuario = new UsuarioMem();
        AuthService autenticacion = new AuthService();
        CitaService cita = new CitaService();
        HistoriaService historia = new HistoriaService();
        NotaService nota = new NotaService();

        Scanner ent = new Scanner(System.in);
        AuthService.LoginResult sesion = null;

        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        boolean bandera = true;
        do {
            System.out.println("\nmenu principal:");
            System.out.println("1. registrar paciente");
            System.out.println("2. registrar doctor");
            System.out.println("3. login");
            System.out.println("4. crear cita (paciente)");
            System.out.println("5. ver agenda doctor");
            System.out.println("6. ver citas paciente");
            System.out.println("7. confirmar cita");
            System.out.println("8. cancelar cita (paciente)");
            System.out.println("9. agregar nota historia");
            System.out.println("10. ver historia");
            System.out.println("11. agregar nota simple");
            System.out.println("12. ver notas simples");
            System.out.println("13. logout");
            System.out.println("0. salir");
            System.out.print("opcion: ");

            int opcion = Integer.parseInt(ent.nextLine());

            try{
                switch (opcion) {
                    case 1:
                        System.out.print("Username: ");
                        String username = ent.nextLine();

                        System.out.print("Password: ");
                        String password = ent.nextLine();

                        System.out.print("Cédula: ");
                        String cedula = ent.nextLine();

                        System.out.print("Nombres: ");
                        String nombres = ent.nextLine();

                        System.out.print("Apellidos: ");
                        String apellidos = ent.nextLine();

                        System.out.print("Correo: ");
                        String correo = ent.nextLine();

                        System.out.print("Teléfono: ");
                        String telefono = ent.nextLine();

                        System.out.println("Seleccione género: 1) Masculino  2) Femenino  3) Otro");
                        int opciong = Integer.parseInt(ent.nextLine());
                        Genero genero = switch(opciong) {
                            case 1 -> Genero.MASCULINO;
                            case 2 -> Genero.FEMENINO;
                            default -> Genero.OTRO;
                        };

                        System.out.print("Dirección: ");
                        String direccion = ent.nextLine();

                        System.out.print("Fecha de nacimiento (yyyy-MM-dd): ");
                        LocalDate fecha_nacimiento = LocalDate.parse(ent.nextLine());

                        int id = memoriaUsuario.crear(username,password,Rol.Paciente,cedula,nombres,apellidos,correo,telefono,genero,direccion,fecha_nacimiento,"","","");
                        System.out.println("paciente creado con id " + id);
                        break;
                    case 2:
                        System.out.print("Username: ");
                        username = ent.nextLine();

                        System.out.print("Password: ");
                        password = ent.nextLine();

                        System.out.print("Cédula: ");
                        cedula = ent.nextLine();

                        System.out.print("Nombres: ");
                        nombres = ent.nextLine();

                        System.out.print("Apellidos: ");
                        apellidos = ent.nextLine();

                        System.out.print("Correo: ");
                        correo = ent.nextLine();

                        System.out.print("Teléfono: ");
                        telefono = ent.nextLine();

                        System.out.println("Seleccione género: 1) Masculino  2) Femenino  3) Otro");
                        opciong = Integer.parseInt(ent.nextLine());
                        genero = switch(opciong) {
                            case 1 -> Genero.MASCULINO;
                            case 2 -> Genero.FEMENINO;
                            default -> Genero.OTRO;
                        };

                        System.out.print("Horario: ");
                        String horario = ent.nextLine();

                        System.out.print("Sede: ");
                        String sede = ent.nextLine();

                        System.out.print("Especialidad: ");
                        String especialidad = ent.nextLine();

                        id = memoriaUsuario.crear(username,password,Rol.Doctor,cedula,nombres,apellidos,correo,telefono,genero,"",null,horario,sede,especialidad);
                        System.out.println("Doctor creado con id: " + id);
                        break;
                    case 3:
                        System.out.print("username: ");
                        username = ent.nextLine();
                        System.out.print("password: ");
                        password = ent.nextLine();
                        sesion = autenticacion.login(username,password);
                        System.out.println("login ok -> usuario " + sesion.user().username + " rol " + sesion.user().rol);
                        break;
                    case 4:
                        if ((sesion == null) || (sesion.pacienteId() == null)) {
                            System.out.println("debes hacer login como paciente");
                            break;
                        }
                        System.out.print("id doctor: ");
                        int idDoc = Integer.parseInt(ent.nextLine());
                        LocalDate fecha = pedirFecha(ent);
                        LocalTime hora = pedirHora(ent);
                        LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

                        int citaId = cita.crearPorPaciente(sesion.pacienteId(), idDoc, fecha, fechaHora, "sin observacion");
                        System.out.println("cita creada con id " + citaId);
                        break;
                    case 5:
                        System.out.print("id doctor: ");
                        idDoc = Integer.parseInt(ent.nextLine());
                        fecha = pedirFecha(ent);
                        var agenda = cita.agendaDoctor(idDoc, fecha);
                        if (agenda.isEmpty()) {
                            System.out.println("no hay citas para esa fecha");
                        } else {
                            for (var c : agenda) {
                                System.out.println("cita " + c.id + " hora " + c.hora.toLocalTime().format(formatoHora) + " estado " + c.estado);
                            }
                        }
                        break;
                    case 6:
                        if (sesion == null || sesion.pacienteId() == null) {
                            System.out.println("debes hacer login como paciente");
                            break;
                        }
                        System.out.println("desde:");
                        LocalDate desde = pedirFecha(ent);
                        System.out.println("hasta:");
                        LocalDate hasta = pedirFecha(ent);
                        var citas = cita.citasPaciente(sesion.pacienteId(), desde, hasta);
                        if (citas.isEmpty()) {
                            System.out.println("no hay citas en ese rango");
                        } else {
                            for (var c : citas) {
                                System.out.println("cita " + c.id + " fecha " + c.fecha + " hora " + c.hora.toLocalTime().format(formatoHora) + " estado " + c.estado);
                            }
                        }
                        break;
                    case 7:
                        System.out.print("id cita: ");
                        id = Integer.parseInt(ent.nextLine());
                        cita.actualizarEstado(id, EstadoCita.Confirmada);
                        System.out.println("cita " + id + " confirmada");
                        break;
                    case 8:
                        if (sesion == null || sesion.pacienteId() == null) {
                            System.out.println("debes hacer login como paciente");
                            break;
                        }
                        System.out.print("id cita: ");
                        id = Integer.parseInt(ent.nextLine());
                        cita.cancelarPorPaciente(id, sesion.pacienteId());
                        System.out.println("cita " + id + " cancelada");
                        break;
                    case 9:
                        if (sesion == null || sesion.pacienteId() == null) {
                            System.out.println("debes hacer login como paciente");
                            break;
                        }
                        int historiaId = historia.obtenerOCrearHistoria(sesion.pacienteId());
                        System.out.print("alergias: ");
                        String alergias = ent.nextLine();
                        System.out.print("medicamentos: ");
                        String meds = ent.nextLine();
                        System.out.print("motivo consulta: ");
                        String motivo = ent.nextLine();
                        System.out.print("recomendaciones: ");
                        String recom = ent.nextLine();
                        historia.agregarNota(historiaId, alergias, meds, motivo, recom);
                        System.out.println("nota clinica agregada");
                        break;
                    case 10:
                        if (sesion == null || sesion.pacienteId() == null) {
                            System.out.println("debes hacer login como paciente");
                            break;
                        }
                        historiaId = historia.obtenerOCrearHistoria(sesion.pacienteId());
                        var notash = historia.listarNotas(historiaId);
                        if (notash.isEmpty()) {
                            System.out.println("historia vacia");
                        } else {
                            for (var n : notash) {
                                System.out.println("nota " + n.getId() + " motivo: " + n.getMotivoConsulta());
                            }
                        }
                        break;
                    case 11:
                        if (sesion == null || sesion.pacienteId() == null) {
                            System.out.println("debes hacer login como paciente");
                            break;
                        }
                        int notaId = nota.ObtenerOCrearNota(sesion.pacienteId());
                        System.out.print("texto nota: ");
                        String txt = ent.nextLine();
                        nota.agregarNota(notaId, txt);
                        System.out.println("nota agregada");
                        break;
                    case 12:
                        if (sesion == null || sesion.pacienteId() == null) {
                            System.out.println("debes hacer login como paciente");
                            break;
                        }
                        notaId = nota.ObtenerOCrearNota(sesion.pacienteId());
                        var notasn = nota.listarNotas(notaId);
                        if (notasn.isEmpty()) {
                            System.out.println("sin notas");
                        } else {
                            for (var n : notasn) {
                                System.out.println("nota " + n.getId() + ": " + n.getTexto());
                            }
                        }
                        break;
                    case 13:
                        if (sesion != null) {
                            autenticacion.logout(sesion.sesionId());
                            System.out.println("logout ok");
                            sesion = null;
                        } else {
                            System.out.println("no hay sesion activa");
                        }
                        break;
                    case 0:
                        bandera = false;
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }

            } catch (Exception e) {
                System.out.println("Error: "+ e.getMessage());
            }

        } while (bandera);
    }

    private static LocalDate pedirFecha(Scanner sc) {
        LocalDate hoy = LocalDate.now();
        int anio, mes, dia;

        while (true) {
            System.out.print("anio: ");
            anio = sc.nextInt();
            if (anio < hoy.getYear()) {
                System.out.println("anio no valido, debe ser >= " + hoy.getYear());
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("mes: ");
            mes = sc.nextInt();
            if (mes < 1 || mes > 12) {
                System.out.println("mes no valido");
                continue;
            }
            if (anio == hoy.getYear() && mes < hoy.getMonthValue()) {
                System.out.println("mes no valido para este anio");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("dia: ");
            dia = sc.nextInt();
            sc.nextLine();
            if (dia < 1 || dia > 31) {
                System.out.println("dia no valido");
                continue;
            }
            if (anio == hoy.getYear() && mes == hoy.getMonthValue() && dia < hoy.getDayOfMonth()) {
                System.out.println("dia no valido para este mes y anio");
                continue;
            }
            break;
        }

        return LocalDate.of(anio, mes, dia);
    }

    private static LocalTime pedirHora(Scanner sc) {
        while (true) {
            System.out.print("hora (ejemplo 10:00): ");
            String horaStr = sc.nextLine();
            try {
                return LocalTime.parse(horaStr);
            } catch (Exception e) {
                System.out.println("formato invalido, use HH:MM en 24h");
            }
        }
    }
}
