package principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Propiedad {
    String nombre;
    int precio;
    boolean esPropiedad;
    String propietario;
    int grupo; // Grupo al que pertenece
    int viviendas; // Número de viviendas en la propiedad
    boolean hotel; // Indica si hay un hotel
    boolean esEstacion; // Indica si es una estación
    boolean esServicio; // Indica si es un servicio (agua/luz)

    public Propiedad(String nombre, int precio, int grupo, boolean esEstacion, boolean esServicio) {
        this.nombre = nombre;
        this.precio = precio;
        this.esPropiedad = false;
        this.propietario = null;
        this.grupo = grupo;
        this.viviendas = 0;
        this.hotel = false;
        this.esEstacion = esEstacion;
        this.esServicio = esServicio;
    }

    public int calcularAlquiler(Jugador jugador) {
        int alquilerBase = precio / 10; // Alquiler base
        if (hotel) {
            return alquilerBase * 10; // Alquiler con hotel
        }
        if (esEstacion) {
            int estacionesPropias = (int) jugador.estacionesPropietario();
            return 25 * estacionesPropias; // Alquiler por estaciones
        }
        if (esServicio) {
            return (jugador.estacionesPropietario() > 0) ? 4 * jugador.estacionesPropietario() : 0; // Alquiler por servicios
        }
        return alquilerBase + (viviendas * alquilerBase); // Alquiler con viviendas
    }

    @Override
    public String toString() {
        return nombre + " ($" + precio + ") - Propietario: " + (propietario != null ? propietario : "Nadie") +
               ", Viviendas: " + viviendas + ", Hotel: " + (hotel ? "Sí" : "No") +
               (esEstacion ? " - Estación" : "") + (esServicio ? " - Servicio" : "");
    }
}

class Jugador {
    String nombre;
    int dinero;
    int posicion;
    int vueltas; // Contador de vueltas
    int turnosEnCarcel; // Contador de turnos en cárcel

    public Jugador(String nombre, int dinero) {
        this.nombre = nombre;
        this.dinero = dinero;
        this.posicion = 0; // Comienza en la posición 0 (Inicio)
        this.vueltas = 0; // Inicializa el contador de vueltas
        this.turnosEnCarcel = 0; // Inicializa turnos en cárcel
    }

    public void mover(int espacios) {
        if (turnosEnCarcel > 0) {
            turnosEnCarcel--; // Disminuir turnos en cárcel
            System.out.println(nombre + " está en la cárcel. Turnos restantes: " + turnosEnCarcel);
            if (turnosEnCarcel == 0) {
                System.out.println(nombre + " ha salido de la cárcel.");
            }
            return; // No se mueve si está en la cárcel
        }

        int nuevaPosicion = (posicion + espacios) % 32; // Tablero circular de 32 espacios
        if (nuevaPosicion < posicion) {
            vueltas++; // Aumentar el contador de vueltas
            dinero += 100; // Recibir $100 por dar la vuelta
            System.out.println(nombre + " ha dado una vuelta completa y recibe $100!");
        }
        posicion = nuevaPosicion;
    }

    public int estacionesPropietario() {
        int count = 0;
        for (Propiedad p : Monopoly.propiedades) {
            if (p.propietario != null && p.propietario.equals(nombre) && p.esEstacion) {
                count++;
            }
        }
        return count;
    }

    public boolean tieneGrupoCompleto(int grupo) {
        for (Propiedad p : Monopoly.propiedades) {
            if (p.grupo == grupo && (p.propietario == null || !p.propietario.equals(nombre))) {
                return false; // Falta una propiedad del grupo
            }
        }
        return true; // Tiene todas las propiedades del grupo
    }

    @Override
    public String toString() {
        return nombre + " - Dinero: $" + dinero + " - Posición: " + posicion;
    }
}

public class Monopoly {
    static List<Propiedad> propiedades = new ArrayList<>();
    private static final Random random = new Random();
    private static final List<Jugador> jugadores = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de jugadores: ");
        int numJugadores = scanner.nextInt();
        scanner.nextLine(); // Consumir nueva línea

        for (int i = 0; i < numJugadores; i++) {
            System.out.print("Ingrese el nombre del jugador " + (i + 1) + ": ");
            String nombre = scanner.nextLine();
            jugadores.add(new Jugador(nombre, 1500)); // Cada jugador comienza con $1500
        }

        // Crear propiedades con nombres de ciudades de One Piece y asignar grupos
        String[][] nombresPropiedades = {
            {"Pueblo Fuschia", "Pueblo Shells", "Pueblo Naranja", "Pueblo Sirope"},
            {"Isla Konomi", "Loguetown", "Isla Drum", "Alabasta"},
            {"Water 7", "Dressrosa", "Isla Enies Lobby", "Pueblo Sabaody"},
            {"Pueblo de los Gyojin", "Boinn Island", "Isla de las Serpientes", "Isla de los sueños"},
            {"Isla Wano", "Isla Zou", "Isla Punk Hazard", "Isla Dressrosa"},
            {"Isla Totto Land", "Isla Boinn", "Isla de los Caballeros", "Isla de los Piratas"},
            {"Isla de los Soldados", "Isla de los Reyes", "Isla de los Sábados", "Isla de los Dioses"},
            {"Isla de los Monstruos", "Isla de los Cazadores", "Isla de los Fantasmas", "Isla de los Bandidos"}
        };

        int[] precios = {
            100, 120, 140, 160,
            180, 200, 220, 240,
            260, 280, 300, 320,
            340, 360, 380, 400,
            420, 440, 460, 480,
            500, 520, 540, 560,
            580, 600, 620, 640,
            660, 680, 700, 720,
            740, 760, 780, 800
        };

        for (int i = 0; i < nombresPropiedades.length; i++) {
            for (int j = 0; j < nombresPropiedades[i].length; j++) {
                propiedades.add(new Propiedad(nombresPropiedades[i][j], precios[i * 4 + j], i, false, false));
            }
        }

        // Añadir estaciones
        propiedades.add(new Propiedad("Estación de tren 1", 200, -1, true, false));
        propiedades.add(new Propiedad("Estación de tren 2", 200, -1, true, false));
        propiedades.add(new Propiedad("Estación de tren 3", 200, -1, true, false));
        propiedades.add(new Propiedad("Estación de tren 4", 200, -1, true, false));

        // Añadir servicios
        propiedades.add(new Propiedad("Servicio de Agua", 150, -1, false, true));
        propiedades.add(new Propiedad("Servicio de Luz", 150, -1, false, true));

        // Añadir casillas especiales
        propiedades.add(new Propiedad("Inicio", 0, -1, false, false)); // Casilla de Inicio
        propiedades.add(new Propiedad("Cárcel", 0, -1, false, false)); // Casilla de Cárcel
        propiedades.add(new Propiedad("Ir a la Cárcel", 0, -1, false, false)); // Casilla para ir a la cárcel
        propiedades.add(new Propiedad("Parking", 0, -1, false, false)); // Casilla de Parking

        // Añadir casillas de Caja Comunitaria
        propiedades.add(new Propiedad("Caja Comunitaria 1", 0, -1, false, false)); // Caja Comunitaria 1
        propiedades.add(new Propiedad("Caja Comunitaria 2", 0, -1, false, false)); // Caja Comunitaria 2

        // Añadir casillas "?"
        propiedades.add(new Propiedad("?", 0, -1, false, false)); // Casilla "?"
        propiedades.add(new Propiedad("?", 0, -1, false, false)); // Casilla "?"
        propiedades.add(new Propiedad("?", 0, -1, false, false)); // Casilla "?"

        // Últimas casillas
        propiedades.add(new Propiedad("Pagar $100", 0, -1, false, false)); // Pagar $100
        propiedades.add(new Propiedad("Recibir $100", 0, -1, false, false)); // Recibir $100

        boolean juegoActivo = true;
        while (juegoActivo) {
            for (int i = 0; i < jugadores.size(); i++) {
                Jugador jugador = jugadores.get(i);
                System.out.println("\nTurno de " + jugador.nombre);
                System.out.println(jugador);

                // Lanzar el dado
                int tirada = lanzarDado();
                System.out.println("Has sacado un " + tirada);

                // Mover al jugador
                jugador.mover(tirada);
                Propiedad propiedadActual = propiedades.get(jugador.posicion);
                System.out.println("Te has movido a " + propiedadActual);

                // Comprobar efectos de la propiedad
                if (jugador.posicion == 30) { // Ir a la cárcel
                    System.out.println(jugador.nombre + " ha caído en 'Ir a la Cárcel'. Enviándolo a la cárcel por 3 turnos.");
                    jugador.turnosEnCarcel = 3; // Establece turnos en cárcel
                    jugador.posicion = 10; // Mover a la cárcel
                    continue; // No hace nada más en este turno
                } else if (jugador.posicion == 10) { // Cárcel
                    System.out.println(jugador.nombre + " está en la cárcel.");
                    if (tirada == 5) {
                        System.out.println(jugador.nombre + " ha sacado un 5 y sale de la cárcel.");
                        jugador.turnosEnCarcel = 0; // Sale de la cárcel
                    } else {
                        continue; // No hace nada más en este turno
                    }
                } else if (jugador.posicion == 32 || jugador.posicion == 33) { // Casillas de recibir o pagar
                    if (jugador.posicion == 32) {
                        System.out.println(jugador.nombre + " ha caído en 'Pagar $100'.");
                        jugador.dinero -= 100;
                    } else {
                        System.out.println(jugador.nombre + " ha caído en 'Recibir $100'.");
                        jugador.dinero += 100;
                    }
                    continue; // Fin del turno
                } else if (jugador.posicion >= 30) { // Casillas de Caja Comunitaria y "?"
                    if (propiedadActual.nombre.startsWith("Caja Comunitaria")) {
                        int ayuda = random.nextInt(50) + 50; // Suma de $50 a $100
                        System.out.println(jugador.nombre + " ha recibido $ " + ayuda + " de Caja Comunitaria.");
                        jugador.dinero += ayuda;
                    } else if (propiedadActual.nombre.equals("?")) {
                        if (random.nextBoolean()) {
                            int ganancia = random.nextInt(50) + 50; // Beneficio entre $50 a $100
                            System.out.println(jugador.nombre + " ha ganado $ " + ganancia + "!");
                            jugador.dinero += ganancia;
                        } else {
                            int perdida = random.nextInt(50) + 50; // Pérdida entre $50 a $100
                            System.out.println(jugador.nombre + " ha perdido $ " + perdida + "!");
                            jugador.dinero -= perdida;
                        }
                    }
                    continue; // Fin del turno
                }

                // Si la propiedad ya tiene propietario
                if (propiedadActual.esPropiedad) {
                    if (!propiedadActual.propietario.equals(jugador.nombre)) {
                        int alquiler = propiedadActual.calcularAlquiler(jugador);
                        jugador.dinero -= alquiler;
                        System.out.println(jugador.nombre + " debe pagar $" + alquiler + " a " + propiedadActual.propietario);
                        // Si el jugador no tiene suficiente dinero, queda eliminado
                        if (jugador.dinero < 0) {
                            System.out.println(jugador.nombre + " se ha quedado sin dinero. ¡Eliminado del juego!");
                            jugadores.remove(i);
                            i--; // Ajustar el índice al eliminar
                            if (jugadores.size() == 1) {
                                System.out.println(jugadores.get(0).nombre + " es el ganador!");
                                juegoActivo = false;
                                break;
                            }
                        }
                    }
                } else {
                    // Comprar propiedad
                    System.out.print("¿Quieres comprar esta propiedad? (s/n): ");
                    String respuesta = scanner.next();
                    if (respuesta.equalsIgnoreCase("s")) {
                        if (jugador.dinero >= propiedadActual.precio) {
                            jugador.dinero -= propiedadActual.precio;
                            propiedadActual.esPropiedad = true;
                            propiedadActual.propietario = jugador.nombre;
                            System.out.println(jugador.nombre + " ha comprado " + propiedadActual);
                        } else {
                            System.out.println("No tienes suficiente dinero para comprar " + propiedadActual);
                        }
                    }
                }

                // Opción de construir viviendas o un hotel
                if (propiedadActual.propietario != null && propiedadActual.propietario.equals(jugador.nombre)) {
                    if (jugador.tieneGrupoCompleto(propiedadActual.grupo)) {
                        System.out.println("¿Quieres construir viviendas (hasta 4) o un hotel (1)?");
                        System.out.print("¿Cuántas viviendas deseas construir (0-4)? ");
                        int numViviendas = scanner.nextInt();
                        if (numViviendas >= 0 && numViviendas <= 4) {
                            for (int j = 0; j < numViviendas; j++) {
                                if (jugador.dinero >= 50) { // Costo de cada vivienda
                                    jugador.dinero -= 50;
                                    propiedadActual.viviendas++;
                                } else {
                                    System.out.println("No tienes suficiente dinero para construir más viviendas.");
                                    break;
                                }
                            }
                        }

                        // Construir hotel si ya hay 4 viviendas
                        if (propiedadActual.viviendas >= 4 && !propiedadActual.hotel) {
                            System.out.print("¿Quieres construir un hotel? (s/n): ");
                            String construirHotel = scanner.next();
                            if (construirHotel.equalsIgnoreCase("s")) {
                                if (jugador.dinero >= 200) { // Costo del hotel
                                    jugador.dinero -= 200;
                                    propiedadActual.hotel = true;
                                    propiedadActual.viviendas = 0; // No se pueden tener viviendas y un hotel al mismo tiempo
                                    System.out.println(jugador.nombre + " ha construido un hotel en " + propiedadActual);
                                } else {
                                    System.out.println("No tienes suficiente dinero para construir un hotel.");
                                }
                            }
                        }
                    } else {
                        System.out.println("No puedes construir en " + propiedadActual.nombre + " porque no tienes todas las propiedades del grupo.");
                    }
                }

                // Verificar si el jugador se queda sin dinero
                if (jugador.dinero <= 0) {
                    System.out.println(jugador.nombre + " se ha quedado sin dinero. ¡Eliminado del juego!");
                    jugadores.remove(i);
                    i--; // Ajustar el índice al eliminar
                    if (jugadores.size() == 1) {
                        System.out.println(jugadores.get(0).nombre + " es el ganador!");
                        juegoActivo = false;
                        break;
                    }
                }
            }

            // Preguntar si continuar si hay más de un jugador
            if (juegoActivo && jugadores.size() > 1) {
                System.out.print("¿Quieres continuar con la siguiente ronda? (s/n): ");
                String continuarJuego = scanner.next();
                if (continuarJuego.equalsIgnoreCase("n")) {
                    juegoActivo = false;
                }
            }
        }

        scanner.close();
        System.out.println("Fin del juego. ¡Gracias por jugar!");
    }

    private static int lanzarDado() {
        return random.nextInt(6) + 1 + random.nextInt(6) + 1; // Suma de dos dados
    }
}
