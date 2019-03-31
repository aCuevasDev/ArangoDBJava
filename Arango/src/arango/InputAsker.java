package arango;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Metodos estaticos para pedir input al usuario.
 * 
 * @author mfontana
 * @author razz97
 * @author acuevas
 * @author movip88
 */
public class InputAsker {

	/**
	 * Pide una string al usuario
	 *
	 * @param message - mensaje a mostrar al usuario.
	 * @return String - String input introducido por el usuario.
	 */
	public static String pedirString(String message) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String answer = "";
		do {
			try {
				System.out.println(message);
				answer = br.readLine();
				if (answer.equals("")) {
					System.out.println("You must write something.");
				}
			} catch (IOException ex) {
				System.out.println("Error input / output.");
			}
		} while (answer.equals(""));
		return answer;
	}

	/**
	 * Pide un entero al usuario.
	 * 
	 * @param texto enunciado a mostrar al usuario.
	 * @return entero introducido.
	 */
	public static int pedirEntero(String texto) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int num = 0;
		boolean error;
		do {
			try {
				System.out.println(texto);
				num = Integer.parseInt(br.readLine());
				error = false;
			} catch (IOException ex) {
				System.out.println("Error de entrada / salida.");
				error = true;
			} catch (NumberFormatException ex) {
				System.out.println("Debes introducir un n�mero entero.");
				error = true;
			}
		} while (error);
		return num;
	}

	/**
	 * Pide un entero al usuario hasta que lo introduce correctamente, comprueba que
	 * este entre un minimo y un maximo pasados como argumentos.
	 * 
	 * @param texto enunciado a mostrar al usuario.
	 * @param min   minimo del entero a introducir.
	 * @param max   maximo del entero a introducir.
	 * @return
	 */
	public static int pedirEntero(String texto, int min, int max) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int num = 0;
		boolean error;
		do {
			try {
				System.out.println(texto);
				num = Integer.parseInt(br.readLine());
				error = num > max || num < min;
				if (error) {
					System.out.println("El numero debe estar entre " + min + " y " + max);
				}
			} catch (IOException ex) {
				System.out.println("Error de entrada / salida.");
				error = true;
			} catch (NumberFormatException ex) {
				System.out.println("Debes introducir un n�mero entero.");
				error = true;
			}
		} while (error);
		return num;
	}

	/**
	 * A partir de una lista devuelve el indice deseado por el usuario.
	 * 
	 * @param <T> 			tipo de elementos en la lista (para evitar el warning de lista sin tipo)
	 * @param pregunta     	enunciado a mostrar al usuario.
	 * @param lista        	lista de elementos a escoger.
	 * @param salirConZero 	boolean de si se puede salir del menu con 0.
	 * @return indice introducido por el usuario.
	 */
	public static <T> int pedirIndice(String pregunta, List<T> lista, boolean salirConZero) {
		for (int i = 0; i < lista.size(); i++) {
			System.out.println((i + 1) + ". " + lista.get(i));
		}
		if (salirConZero) {
			System.out.println("0. Salir.");
		}
		return pedirEntero(pregunta, salirConZero ? 0 : 1, lista.size());
	}

	/**
	 * Pide al usuario que introduzca cualquier input de teclado
	 * @param mensaje a mostrar al usuario.
	 */
	public static void pedirTecla(String mensaje) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean error = false;
		do {
			try {
				System.out.println(mensaje);
				br.readLine();
			} catch (IOException e) {
				System.out.println("Error de entrada / salida.");
				error = true;
			}
		} while (error);
	}
	
	/**
	 * Pregunta al usuario una pregunta binaria.
	 * 
	 * @param text
	 * @return true si responde afirmativamente, false si responde que no.
	 */
	public static boolean pedirSiONo(String text) {
		boolean error = false;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String cadena = "";
		do {
			try {
				System.out.println(text);
				cadena = br.readLine();
				if (cadena.equals(""))
					System.out.println("No puedes dejar este campo en blanco.");

				if (cadena.equalsIgnoreCase("si") || cadena.equalsIgnoreCase("s"))
					return true;
				if (cadena.equalsIgnoreCase("no") || cadena.equalsIgnoreCase("n"))
					return false;
				System.out.println("Debes insertar (s)i o (n)o.");
				error = true;
			} catch (IOException ex) {
				System.out.println("Input / Output error.");
			}
		} while (error);
		return false;
	}

}
