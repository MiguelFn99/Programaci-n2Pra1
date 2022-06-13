package unileon;

import java.util.ArrayList;

public class Tablero {

	private char tablero[][];

	// Constructor Tablero
	public Tablero(int tamFil, int tamCol) {
		this.tablero = new char[tamFil][tamCol];
		inicializoTablero();
	}

	// Inicializo tablero con puntos
	private void inicializoTablero() {
		for (int fila = 0; fila < tablero.length; fila++) {
			for (int col = 0; col < tablero[0].length; col++) {
				tablero[fila][col] = '.';
			}
		}
	}

	// Impresión del tablero
	public void dibujoTablero() {
		System.out.println("   0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 2 2 2 2 2 2 2 2 2 2");
		System.out.println("   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9");
		for (int fila = 0; fila < tablero.length; fila++) {
			if (tablero.length - fila - 1 < 10) {
				System.out.print("0");
			}
			System.out.print(tablero.length - fila - 1 + " ");
			for (int col = 0; col < tablero[fila].length; col++) {
				if (col == tablero[fila].length - 1) {
					System.out.print(tablero[fila][col]);
				} else {
					System.out.print(tablero[fila][col] + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	// Coloco tren en el tablero
	public void colocoTren(Tren tren) {

		int fil = tren.getFilaTablero();
		int col = tren.getColumna();
		char dir = tren.getDireccion();
		for (int i = 0; i < tren.getLongitud(); i++) {
			switch (dir) {
			case 'A':
				if (tablero[fil][col] == '.') {
					tablero[fil][col] = 'A';
				} else {
					tablero[fil][col] = 'X';
				}
				fil++;
				break;
			case 'B':
				if (tablero[fil][col] == '.') {
					tablero[fil][col] = 'B';
				} else {
					tablero[fil][col] = 'X';
				}
				fil--;
				break;
			case 'I':
				if (tablero[fil][col] == '.') {
					tablero[fil][col] = 'I';
				} else {
					tablero[fil][col] = 'X';
				}
				col++;
				break;
			case 'D':
				if (tablero[fil][col] == '.') {
					tablero[fil][col] = 'D';
				} else {
					tablero[fil][col] = 'X';
				}
				col--;
				break;
			}
		}
	}

	// Muevo los trenes en el tablero
	public void muevoTren(Tren tren, ArrayList<Tren> trenes) {

		char casilla = comprueboCasillaOcupada(tren);

		// Si la cabeza es una X
		if (tablero[tren.getFilaTablero()][tren.getColumna()] == 'X') {
			borroTren(tren);
			tren.muevoTren('X');
			colocoTren(tren);
		} else {
			// Dependiendo de la casilla que va a ocupar
			switch (casilla) {
			case '.':
				borroTren(tren);
				tren.muevoTren(casilla);
				colocoTren(tren);
				break;
			case 'E':
				borroTren(tren);
				tren.muevoTren(casilla);
				colocoTren(tren);
				break;
			case 'X':
				borroTren(tren);
				tren.muevoTren(casilla);
				colocoTren(tren);
				break;
			default:
				borroTren(tren);
				tren.muevoTren(casilla);
				tablero[tren.getFilaTablero()][tren.getColumna()] = 'X';
				colocoTren(tren);
				buscoTrenColision(casilla, trenes, tren.getFilaTablero(), tren.getColumna());
				break;
			}
		}

	}

	private void buscoTrenColision(char casilla, ArrayList<Tren> trenes, int fila, int col) {
		for (int i = 0; i < trenes.size(); i++) {
			if (trenes.get(i).getDireccion() == casilla) {
				if (trenes.get(i).trenColisionado(fila, col)) {
					Tren trenAux = trenes.get(i).cortoTren(fila, col);
					if (trenAux != null) {
						trenes.add(i, trenAux);// Lo añado detrás del que he cortado
						break;
					}
				}
			}
		}
	}

	// Borro tren del tablero
	private void borroTren(Tren tren) {

		int fil = tren.getFilaTablero();
		int col = tren.getColumna();
		char dir = tren.getDireccion();

		for (int i = 0; i < tren.getLongitud(); i++) {
			switch (dir) {
			case 'A':
				if (tablero[fil][col] != 'X') {
					tablero[fil][col] = '.';
				}
				fil++;
				break;
			case 'B':
				if (tablero[fil][col] != 'X') {
					tablero[fil][col] = '.';
				}
				fil--;
				break;
			case 'I':
				if (tablero[fil][col] != 'X') {
					tablero[fil][col] = '.';
				}
				col++;
				break;
			case 'D':
				if (tablero[fil][col] != 'X') {
					tablero[fil][col] = '.';
				}
				col--;
				break;
			default:
				break;
			}

		}

	}

	// Compruebo la casilla que va a ocupar un tren que caracter tiene
	private char comprueboCasillaOcupada(Tren tren) {
		char direccion = tren.getDireccion();
		int fila = tren.getFilaTablero();
		int col = tren.getColumna();
		int lonFil = tablero.length;
		int lonCol = tablero[0].length;

		if ((direccion == 'A') && (fila - 1 >= 0))
			return tablero[fila - 1][col];
		else if ((direccion == 'B') && (fila + 1 < lonFil))
			return tablero[fila + 1][col];
		else if ((direccion == 'D') && (col + 1 < lonCol))
			return tablero[fila][col + 1];
		else if ((direccion == 'I') && (col - 1 >= 0))
			return tablero[fila][col - 1];
		else
			return 'E';
	}

	// Indica si el tablero está vacío y no tiene trenes
	public boolean comprueboTablero() {
		for (int fila = 0; fila < tablero.length; fila++) {
			for (int col = 0; col < tablero[fila].length; col++) {
				if (tablero[fila][col] == 'A' || tablero[fila][col] == 'B' || tablero[fila][col] == 'D'
						|| tablero[fila][col] == 'I')
					return true;
			}
		}
		return false;
	}

	public boolean comprueboColisionInicial() {
		for (int fila = 0; fila < tablero.length; fila++) {
			for (int col = 0; col < tablero[fila].length; col++) {
				if (tablero[fila][col] == 'X') {
					return true;
				}
			}
		}
		return false;
	}

}
