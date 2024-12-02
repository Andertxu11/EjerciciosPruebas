package principal;

import java.util.Scanner;

public class EjercicoKk {
	
		 public static boolean esBisiesto(int anio) {
		        if ((anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0)) {
		            return true;
		        }
		        return false;
		    }

		    public static void main(String[] args) {
		        Scanner scanner = new Scanner(System.in);

		        System.out.println("Introduce el número promedio de veces que vas al baño por día:");
		        int vecesPorDia = scanner.nextInt();

		        System.out.println("Introduce el año actual:");
		        int anio = scanner.nextInt();

		        int diasEnElAnio = esBisiesto(anio) ? 366 : 365;

		        int totalVecesPorAnio = vecesPorDia * diasEnElAnio;

		        System.out.println("Este año, irás al baño aproximadamente " + totalVecesPorAnio + " veces.");

		        if (totalVecesPorAnio < 1000) {
		            System.out.println("¡Parece que vas muy pocas veces al baño!");
		        } else if (totalVecesPorAnio >= 1000 && totalVecesPorAnio <= 3000) {
		            System.out.println("Vas un número normal de veces al baño.");
		        } else {
		            System.out.println("¡Vas demasiadas veces al baño! Podrías querer consultar a un médico.");
		        }

		        scanner.close();
		    }
		}