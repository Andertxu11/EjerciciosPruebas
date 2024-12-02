package principal;

public class Ejercicio28 {

	public static void main(String[] args) {

		int i;

		for (i = 1; i != 10; i = i + 1) {
			if (i > 5)
				break;
			System.out.println(i);
		}
		System.out.println("Fin: " + i);
	}

}