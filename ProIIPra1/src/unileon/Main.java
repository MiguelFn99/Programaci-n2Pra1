package unileon;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	static final int TAMTABLERO = 30;
	static final int TAMFILATABLERO = 30;
	static final int TAMCOLTABLERO = 30;

	public static void main(String[] args) {

		ArrayList<String> lineas = new ArrayList<String>();
		lineas = lectura();

		// Recorro todos los juegos de trenes
		for (int i = 0; i < lineas.size(); i++) {
			// Creo array de un juego y lo guardo
			ArrayList<String> conjuntoTrenes = new ArrayList<String>();
			i = bloqueTrenes(lineas, i, conjuntoTrenes);
			// Juego con ese conjunto de trenes
			juegoConjuntoTrenes(conjuntoTrenes);
		}

	}

	private static int bloqueTrenes(ArrayList<String> lineas, int i, ArrayList<String> conjuntoTrenes) {

		// Si no puede parsear el numero de lineas de trenes salta excepción
		try {
			int numLineas = Integer.parseInt(lineas.get(i));
		} catch (Exception e) {
			conjuntoTrenesErroneo();
		}

		int numLineas = Integer.parseInt(lineas.get(i));
		if (numLineas < 1 || numLineas > 10) {
			conjuntoTrenesErroneo();
		}
		i++;

		// Mete al array las lineas de trenes si hay más líneas que numLineas salta
		// excepción
		try {
			for (int j = 0; j < numLineas; j++) {
				if (lineas.get(i).charAt(0) == 'A' || lineas.get(i).charAt(0) == 'B'
						|| lineas.get(i).charAt(0) == 'I' || lineas.get(i).charAt(0) == 'D') {
					conjuntoTrenes.add(lineas.get(i));
				} else {
					conjuntoTrenesErroneo();
				}

				if (j != numLineas - 1) {
					i++;
				}
			}
		} catch (Exception e) {
			conjuntoTrenesErroneo();
		}

		return i;
	}

	// Salida del programa
	private static void conjuntoTrenesErroneo() {
		System.out.println("Conjunto de trenes incorrecto.");
		System.exit(0);
	}

	private static void juegoConjuntoTrenes(ArrayList<String> conjuntoTrenes) {
		// Creo un array de trenes y lo relleno de trenes
		ArrayList<Tren> trenes = new ArrayList<Tren>();
		for (int i = 0; i < conjuntoTrenes.size(); i++) {
			Tren oTren = new Tren(conjuntoTrenes.get(i), TAMFILATABLERO, TAMCOLTABLERO);
			trenes.add(oTren);  
		}
		// Ordeno los trenes (B, A, I, D)
		trenes = ordenarTrenes(trenes);

		// Creo el tablero
		Tablero oTablero = new Tablero(TAMFILATABLERO, TAMCOLTABLERO);

		// Coloco los trenes en en tablero
		for (int i = 0; i < trenes.size(); i++) {
			oTablero.colocoTren(trenes.get(i));
		}

		// Compruebo que no haya colisiones iniciales
		if (oTablero.comprueboColisionInicial()) {
			conjuntoTrenesErroneo();
		}

		// trenesLongCinco(trenes);

		// Muevo los trenes hasta que no quede ninguno
		while (oTablero.comprueboTablero()) {
			for (int i = 0; i < trenes.size(); i++) {
				oTablero.muevoTren(trenes.get(i), trenes);
			}
			// oTablero.dibujoTablero();
		}

		// Imprimo el resultado final de los trenes
		oTablero.dibujoTablero();

	}

	private static void trenesLongCinco(ArrayList<Tren> trenes) {
		int cont = 0;
		for (int i = 0; i < trenes.size(); i++) {
			if (trenes.get(i).getLongitud() == 5) {
				cont++;
			}
		}
		System.out.println("Hay " + cont + " trenes de longitud 5.");
	}

	private static ArrayList<Tren> ordenarTrenes(ArrayList<Tren> trenes) {

		ArrayList<Tren> trenesOrdenados = new ArrayList<Tren>();

		bucleOrdenar('B', trenesOrdenados, trenes);
		bucleOrdenar('A', trenesOrdenados, trenes);
		bucleOrdenar('I', trenesOrdenados, trenes);
		bucleOrdenar('D', trenesOrdenados, trenes);

		return trenesOrdenados;
	}

	private static void bucleOrdenar(char c, ArrayList<Tren> trenesOrdenados, ArrayList<Tren> trenes) {
		for (int i = 0; i < trenes.size(); i++) {
			if (trenes.get(i).getDireccion() == c) {
				trenesOrdenados.add(trenes.get(i));
			}
		}
	}

	private static ArrayList<String> lectura() {
		Scanner sc = new Scanner(System.in);

		ArrayList<String> lineas = new ArrayList<String>();

		while (sc.hasNext()) {
			String linea = sc.nextLine().trim();
			lineas.add(linea);
		}
		sc.close();

		return lineas;
	}

}
