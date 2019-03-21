package arango;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to ask input to user.
 * 
 * @author mfontana
 */
public class InputAsker {

    /**
     * Request String
     *
     * @param message - message will be show to user to ask data
     * @return String - String input by user
     */
    public static String askString(String message) {
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
     * Request String
     *
     * @param message - message will be show to user to ask data
     * @return String - String input by user
     */
    public static String askStringOptionalEmpty(String message) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String answer = null;
        do {
            try {
                System.out.println(message);
                answer = br.readLine();
            } catch (IOException ex) {
                System.out.println("Error input / output.");
            }
        } while (answer == null);
        return answer;
    }

    /**
     * Request String, it can only be optionA or optionB
     *
     * @param message - message will be show to user to ask data
     * @param optionA - option a possible
     * @param optionB - option b possible
     * @return String (optionA or optionB) - input by user
     */
    public static String askString(String message, String optionA, String optionB) {
        String answer = "";
        do {
            answer = askString(message);
            if (!answer.equalsIgnoreCase(optionA) && !answer.equalsIgnoreCase(optionB)) {
                System.out.println("Wrong answer! Write " + optionA + " or " + optionB + " please");
            }
        } while (!answer.equalsIgnoreCase(optionA) && !answer.equalsIgnoreCase(optionB));
        return answer;
    }

    /**
     * Request String from a ArrayList of String accepteds
     *
     * @param message - message will be show to user to ask data
     * @param wordsAccepted - List of words accepted in answer
     * @return String - answer of user, the string will be in list of words accepted
     */
    public static String askString(String message, List<String> wordsAccepted) {
        String answer;
        boolean wordOk;
        do {
            for (String word : wordsAccepted) {
                System.out.println(word);
            }
            answer = InputAsker.askString(message);
            wordOk = wordIsOk(answer, wordsAccepted);
            if (!wordOk) {
                System.out.println("Wrong answer!");
            }
        } while (!wordOk);
        return answer;
    }

    /**
     * Returns true if the word exists in wordsAccepted.
     * 
     * @param word - String to find in words accepted
     * @param wordsAccepted - List of words accepted 
     * @return boolean - true if string is in words accepted
     */
    private static boolean wordIsOk(String word, List<String> wordsAccepted) {
        for (String w : wordsAccepted) {
            if (w.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Request int 
     *
     * @param message - message will be show to user to ask data
     * @return int - a integer number
     */
    public static int askInt(String message) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num = 0;
        boolean error;
        do {
            try {
                System.out.println(message);
                num = Integer.parseInt(br.readLine());
                error = false;
            } catch (IOException ex) {
                System.out.println("Error input / output.");
                error = true;
            } catch (NumberFormatException ex) {
                System.out.println("Please, write integer number.");
                error = true;
            }
        } while (error);
        return num;
    }

        /**
     * Request int 
     *
     * @param message - message will be show to user to ask data
     * @return int - a integer number
     */
    public static Integer askIntWhitNull(String message) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num = 0;
        boolean error;
        do {
            try {
                System.out.println(message);
                String n = br.readLine();
                if(n.isEmpty()){
                    return null;
                }
                num = Integer.parseInt(n);
                error = false;
            } catch (IOException ex) {
                System.out.println("Error input / output.");
                error = true;
            } catch (NumberFormatException ex) {
                System.out.println("Please, write integer number.");
                error = true;
            }
        } while (error);
        return num;
    }
    
    /**
     * Request int in the interval between min and max (inclusives)
     *
     * @param message - message will be show to user to ask data
     * @param min - min number accepted
     * @param max - max number accepted
     * @return int - int input by user in the interval between min and max 
     */
    public static int askInt(String message, int min, int max) {
        int num;
        do {
            num = askInt(message);
            if (num < min || num > max) {
                System.out.println("Error, the number must be between " + min + " and " + max);
            }
        } while (num < min || num > max);
        return num;
    }
    
    public static <T> int askElementList(String message, List<T> lista) {
        int num;
        do {
            System.out.println("0 - No esta definido aún");
            for(int i = 0; i < lista.size() ; i++) {
            	System.out.println((i+1) + " - " + lista.get(i));
            }
            num = askInt(message);
            if (num < 0 || num > lista.size()) {
                System.out.println("Error, the number must be between " + 0 + " and " + lista.size());
            }
        } while (num < 0 || num > lista.size());
        return num;
    }

    public static <T> Set<T> generarListaApartirDeListaSinRepetidos(String message, List<T> lista, Class<T> tClass) {
    	Set<T> nuevaLista = new HashSet<T>();
    	
    	int num;
        do {
            System.out.println("0 - Lista hecha");
            for(int i = 0; i < lista.size() ; i++) {
            	System.out.println((i+1) + " - " + lista.get(i));
            }
            num = askInt(message);
            if (num < 0 || num > lista.size()) {
                System.out.println("Error, the number must be between " + 0 + " and " + lista.size());
            }else {
            	if(num != 0) {
            		if(!nuevaLista.add(lista.get(num - 1))) {
            			System.out.println("Ya esta en la lista...");
            		}
            	}
            }
        } while (num != 0 );
    	
        return nuevaLista;
    }
}
