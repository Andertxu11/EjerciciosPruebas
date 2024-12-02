package principal;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Calculadora {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		double num1 = 0, num2 = 0, resultado;
		String operacion;
		
		boolean continuar = true;
		List<String> historial = new ArrayList<>();

		System.out.println("Bienvenido a la calculadora!");

		while (continuar) {
			try {
				System.out.println("\nSelecciona una operación:");
				System.out.println("1. Sumar (+)");
				System.out.println("2. Restar (-)");
				System.out.println("3. Multiplicar (*)");
				System.out.println("4. Dividir (/)");
				System.out.println("5. Potencia (^)");
				System.out.println("6. Raíz cuadrada (√)");
				System.out.println("7. Porcentaje (%)");
				System.out.println("8. Seno (sin)");
				System.out.println("9. Coseno (cos)");
				System.out.println("10. Tangente (tan)");
				System.out.println("11. Logaritmo en base 10 (log10)");
				System.out.println("12. Logaritmo natural (ln)");
				System.out.println("13. Módulo (%)");
				System.out.println("14. Ver historial");
				System.out.println("15. Salir");
				System.out.print("Opción: ");
				operacion = scanner.next();

				if (operacion.equals("15")) {
					continuar = false;
					System.out.println("Gracias por usar la calculadora. ¡Adiós!");
					break;
				}

				// Para la mayoría de las operaciones
				if (!operacion.equals("6") && !operacion.equals("8") && !operacion.equals("9")
						&& !operacion.equals("10") && !operacion.equals("11") && !operacion.equals("12")) {
					System.out.print("Introduce el primer número: ");
					num1 = scanner.nextDouble();
					System.out.print("Introduce el segundo número: ");
					num2 = scanner.nextDouble();
				} else if (operacion.equals("6")) { // Raíz cuadrada
					System.out.print("Introduce el número: ");
					num1 = scanner.nextDouble();
				}

				switch (operacion) {
				case "1": // Suma
					resultado = num1 + num2;
					System.out.println("Resultado: " + resultado);
					historial.add(num1 + " + " + num2 + " = " + resultado);
					break;
				case "2": // Resta
					resultado = num1 - num2;
					System.out.println("Resultado: " + resultado);
					historial.add(num1 + " - " + num2 + " = " + resultado);
					break;
				case "3": // Multiplicación
					resultado = num1 * num2;
					System.out.println("Resultado: " + resultado);
					historial.add(num1 + " * " + num2 + " = " + resultado);
					break;
				case "4": // División
					if (num2 != 0) {
						resultado = num1 / num2;
						System.out.println("Resultado: " + resultado);
						historial.add(num1 + " / " + num2 + " = " + resultado);
					} else {
						System.out.println("Error: División por cero no permitida.");
					}
					break;
				case "5": // Potencia
					resultado = Math.pow(num1, num2);
					System.out.println("Resultado: " + resultado);
					historial.add(num1 + " ^ " + num2 + " = " + resultado);
					break;
				case "6": // Raíz cuadrada
					if (num1 >= 0) {
						resultado = Math.sqrt(num1);
						System.out.println("Resultado: " + resultado);
						historial.add("√" + num1 + " = " + resultado);
					} else {
						System.out.println("Error: No se puede calcular la raíz cuadrada de un número negativo.");
					}
					break;
				case "7": // Porcentaje
					resultado = (num1 * num2) / 100;
					System.out.println("El " + num2 + "% de " + num1 + " es: " + resultado);
					historial.add(num2 + "% de " + num1 + " = " + resultado);
					break;
				case "8": // Seno
					resultado = Math.sin(Math.toRadians(num1));
					System.out.println("Resultado: " + resultado);
					historial.add("sin(" + num1 + ") = " + resultado);
					break;
				case "9": // Coseno
					resultado = Math.cos(Math.toRadians(num1));
					System.out.println("Resultado: " + resultado);
					historial.add("cos(" + num1 + ") = " + resultado);
					break;
				case "10": // Tangente
					resultado = Math.tan(Math.toRadians(num1));
					System.out.println("Resultado: " + resultado);
					historial.add("tan(" + num1 + ") = " + resultado);
					break;
				case "11": // Logaritmo base 10
					if (num1 > 0) {
						resultado = Math.log10(num1);
						System.out.println("Resultado: " + resultado);
						historial.add("log10(" + num1 + ") = " + resultado);
					} else {
						System.out.println("Error: Logaritmo de números no positivos no permitido.");
					}
					break;
				case "12": // Logaritmo natural
					if (num1 > 0) {
						resultado = Math.log(num1);
						System.out.println("Resultado: " + resultado);
						historial.add("ln(" + num1 + ") = " + resultado);
					} else {
						System.out.println("Error: Logaritmo de números no positivos no permitido.");
					}
					break;
				case "13": // Módulo
					resultado = num1 % num2;
					System.out.println("Resultado: " + resultado);
					historial.add(num1 + " % " + num2 + " = " + resultado);
					break;
				case "14": // Ver historial
					System.out.println("Historial de cálculos:");
					for (String entry : historial) {
						System.out.println(entry);
					}
					break;
				default:
					System.out.println("Operación no válida.");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: Entrada no válida. Por favor, ingresa un número.");
				scanner.next(); // Limpiar el buffer
			} catch (Exception e) {
				System.out.println("Ocurrió un error: " + e.getMessage());
			}
		}

		scanner.close();
	}
}
