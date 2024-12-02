package principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Batman {

    private static Scanner scanner = new Scanner(System.in);
    private static List<String> pistasRecolectadas = new ArrayList<>();
    private static int tiempoRestante = 10; // Tiempo en minutos
    private static String estrategiaElegida = "";
    private static boolean gordonAyuda = false;

    public static void main(String[] args) {
        System.out.println("Eres Batman, y has recibido un nuevo caso en Gotham City.");
        System.out.println("Un valioso artefacto ha sido robado del Museo de Gotham y hay múltiples sospechosos.");

        investigarCaso();
    }

    private static void investigarCaso() {
        while (tiempoRestante > 0) {
            mostrarEstado();
            mostrarOpciones();
            int eleccion = scanner.nextInt();

            switch (eleccion) {
                case 1:
                    interrogarSospechoso();
                    break;
                case 2:
                    examinarEscena();
                    break;
                case 3:
                    revisarPistas();
                    break;
                case 4:
                    elegirEstrategia();
                    break;
                case 5:
                    consultarGordon();
                    break;
                case 6:
                    resolverCaso(); // Nueva opción para resolver el caso
                    break;
                default:
                    System.out.println("Opción no válida. Pierdes 1 minuto.");
                    tiempoRestante--;
            }

            // Evento aleatorio
            eventoAleatorio();
        }

        finalizarCaso("el tiempo se agotó y no pudiste resolver el caso.");
    }

    private static void mostrarEstado() {
        System.out.println("\nTiempo restante: " + tiempoRestante + " minutos.");
        System.out.println("Estrategia elegida: " + (estrategiaElegida.isEmpty() ? "Ninguna" : estrategiaElegida));
    }

    private static void mostrarOpciones() {
        System.out.println("¿Qué quieres hacer?");
        System.out.println("1. Interrogar a un sospechoso.");
        System.out.println("2. Examinar la escena del crimen.");
        System.out.println("3. Revisar las pistas recolectadas.");
        System.out.println("4. Elegir una estrategia.");
        System.out.println("5. Consultar a Jim Gordon.");
        System.out.println("6. Resolver el caso."); // Nueva opción
        System.out.print("Selecciona una opción (1-6): ");
    }

    private static void interrogarSospechoso() {
        System.out.println("\n¿A quién deseas interrogar?");
        System.out.println("1. El Joker");
        System.out.println("2. Catwoman");
        System.out.println("3. Penguin");
        System.out.println("4. Riddler");
        System.out.print("Selecciona un número (1-4): ");
        int eleccion = scanner.nextInt();

        switch (eleccion) {
            case 1:
                interrogarJoker();
                break;
            case 2:
                interrogarCatwoman();
                break;
            case 3:
                interrogarPenguin();
                break;
            case 4:
                interrogarRiddler();
                break;
            default:
                System.out.println("Opción no válida. Pierdes 1 minuto.");
                tiempoRestante--;
        }
    }

    private static void examinarEscena() {
        System.out.println("\nExaminas la escena del crimen.");
        if (!pistasRecolectadas.contains("mensaje en clave")) {
            System.out.println("Encuentras un mensaje en clave que podría ser crucial.");
            pistasRecolectadas.add("mensaje en clave");
            System.out.println("Has recolectado la pista: mensaje en clave.");
        } else {
            System.out.println("Ya has examinado esta área y no encuentras más pistas.");
        }
        tiempoRestante--; // Pierdes tiempo al investigar
    }

    private static void revisarPistas() {
        System.out.println("\nPistas recolectadas:");
        if (pistasRecolectadas.isEmpty()) {
            System.out.println("No has recolectado ninguna pista aún.");
        } else {
            for (String pista : pistasRecolectadas) {
                System.out.println("- " + pista);
            }
        }
        tiempoRestante--; // Revisar pistas también cuesta tiempo
    }

    private static void elegirEstrategia() {
        System.out.println("\nElige una estrategia:");
        System.out.println("1. Estrategia de confrontación: Presiona a los sospechosos.");
        System.out.println("2. Estrategia de observación: Mantente en las sombras y escucha.");
        System.out.println("3. Estrategia de investigación: Examina cada pista a fondo.");
        System.out.print("Selecciona un número (1-3): ");
        int eleccion = scanner.nextInt();

        switch (eleccion) {
            case 1:
                estrategiaElegida = "Confrontación";
                System.out.println("Has elegido la estrategia de confrontación.");
                break;
            case 2:
                estrategiaElegida = "Observación";
                System.out.println("Has elegido la estrategia de observación.");
                break;
            case 3:
                estrategiaElegida = "Investigación";
                System.out.println("Has elegido la estrategia de investigación.");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void consultarGordon() {
        System.out.println("\nConsultas a Jim Gordon, tu aliado en la policía de Gotham.");
        System.out.println("Gordon te ofrece su ayuda y te pregunta qué pistas tienes.");
        
        if (pistasRecolectadas.isEmpty()) {
            System.out.println("No tienes pistas que mostrar.");
            tiempoRestante--;
        } else {
            System.out.println("Tienes las siguientes pistas:");
            for (String pista : pistasRecolectadas) {
                System.out.println("- " + pista);
            }
            System.out.println("Gordon sugiere que el mensaje en clave podría ser un indicio de la siguiente acción.");
            gordonAyuda = true;
            tiempoRestante--; // Consultar a Gordon también consume tiempo
        }
    }

    private static void resolverCaso() {
        System.out.println("\nDecides que es hora de resolver el caso.");
        
        // Comprobar si se tienen pistas clave para resolver el caso
        if (pistasRecolectadas.contains("mensaje en clave") && 
            pistasRecolectadas.contains("conexión con el Riddler")) {
            System.out.println("Presentas las pruebas: el mensaje en clave y la conexión con el Riddler.");
            System.out.println("¡Has resuelto el caso! El Riddler fue el culpable del robo.");
        } else {
            System.out.println("No tienes suficientes pruebas para resolver el caso.");
            System.out.println("Necesitas seguir investigando.");
        }
        
        // Finalizar el caso
        tiempoRestante = 0; // Para terminar el juego
    }

    private static void eventoAleatorio() {
        Random random = new Random();
        int evento = random.nextInt(10); // Genera un número entre 0 y 9

        if (evento == 0) { // Un evento positivo
            System.out.println("\n¡Buena noticia! Has recibido una pista adicional de un informante.");
            pistasRecolectadas.add("pista de informante");
            System.out.println("Has recolectado la pista: pista de informante.");
            tiempoRestante++; // Ganar tiempo
        } else if (evento == 1) { // Un evento negativo
            System.out.println("\n¡Mala noticia! Un sospechoso ha sido alertado y se ha ido.");
            tiempoRestante--; // Pierdes tiempo
        } else if (evento == 2 && gordonAyuda) { // Si Gordon ha ayudado
            System.out.println("\nGordon menciona un detalle importante que habías pasado por alto.");
            tiempoRestante++; // Ganar tiempo
        }
    }

    private static void interrogarJoker() {
        System.out.println("\nHas decidido interrogar al Joker.");
        System.out.println("El Joker se ríe y dice que estuvo en un espectáculo de comedia.");
        System.out.println("Presenta un ticket de teatro como prueba.");
        System.out.println("¿Qué harás?");
        System.out.println("1. Verificar el ticket.");
        System.out.println("2. Desafiarlo a que explique el mensaje en clave.");
        System.out.print("Selecciona un número (1-2): ");
        int eleccion = scanner.nextInt();

        if (eleccion == 1) {
            System.out.println("Verificas el ticket y descubres que es legítimo, pero parece que no está en el lugar correcto.");
            tiempoRestante--;
        } else {
            if (pistasRecolectadas.contains("mensaje en clave")) {
                System.out.println("El Joker se ríe y no te da más información. Pero tu pista lo deja nervioso.");
                pistasRecolectadas.add("confusión del Joker");
                System.out.println("Has recolectado una nueva pista: confusión del Joker.");
            } else {
                System.out.println("El Joker se ríe y te dice que nunca lo resolverías. Te deja confundido.");
            }
        }
    }

    private static void interrogarCatwoman() {
        System.out.println("\nHas decidido interrogar a Catwoman.");
        System.out.println("Ella parece nerviosa y dice que estaba robando joyas en un lugar diferente.");
        System.out.println("Le muestras la caja de joyas vacía con las iniciales 'C.S.' y le preguntas.");
        System.out.println("¿Qué harás?");
        System.out.println("1. Aceptar su explicación.");
        System.out.println("2. Insistir en que explique las iniciales.");
        System.out.print("Selecciona un número (1-2): ");
        int eleccion = scanner.nextInt();

        if (eleccion == 1) {
            System.out.println("Aceptas su explicación, pero te deja con más dudas.");
            tiempoRestante--;
        } else {
            System.out.println("Catwoman se enfada y revela que 'C.S.' son las iniciales de un antiguo colega.");
            System.out.println("¡Descubres que podría haber una conexión con el Riddler!");
            pistasRecolectadas.add("conexión con el Riddler");
        }
    }

    private static void interrogarPenguin() {
        System.out.println("\nHas decidido interrogar al Penguin.");
        System.out.println("El Penguin parece calmado, pero se nota su nerviosismo.");
        System.out.println("¿Qué harás?");
        System.out.println("1. Preguntarle sobre su coartada.");
        System.out.println("2. Mencionarle la huella de zapato encontrada.");
        System.out.print("Selecciona un número (1-2): ");
        int eleccion = scanner.nextInt();

        if (eleccion == 1) {
            System.out.println("Penguin dice que estaba ocupado en su club esa noche.");
            tiempoRestante--;
        } else {
            System.out.println("El Penguin se pone nervioso y dice que las huellas son de un zapato común.");
            System.out.println("Pero te das cuenta de que su zapato no parece tan común. ¡Algo no cuadra!");
            pistasRecolectadas.add("sospecha del Penguin");
        }
    }

    private static void interrogarRiddler() {
        System.out.println("\nHas decidido interrogar al Riddler.");
        System.out.println("El Riddler sonríe y te plantea un acertijo: '¿Qué se rompe cuando se dice su nombre?'");
        System.out.println("¿Qué harás?");
        System.out.println("1. Responder 'el silencio'.");
        System.out.println("2. Ignorar el acertijo y preguntarle sobre el artefacto robado.");
        System.out.print("Selecciona un número (1-2): ");
        int eleccion = scanner.nextInt();

        if (eleccion == 1) {
            System.out.println("El Riddler aplaude y dice que eres inteligente, pero eso no lo ayudará.");
            tiempoRestante--;
        } else {
            System.out.println("El Riddler parece sorprendido, pero se niega a responder. Te dice que 'las palabras son para los tontos'.");
            tiempoRestante--;
        }
    }

    private static void finalizarCaso(String mensaje) {
        System.out.println("\nEl caso ha terminado porque " + mensaje);
        System.out.println("Gracias por jugar, Batman. ¡Gotham te necesita!");
    }
}
