package tsjlh.poo2023.integrador2;

// import java.util.ArrayList;
import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    // final ArrayList<User> users = new ArrayList<>();
    final Scanner scanner = new Scanner(System.in);

    int opcion = 0;

    // Limpia la pantalla
    System.out.print("\033[H\033[2J");

    System.out.println("Bienvenido al sistema de almacenamiento del TSJ, UA: La Huerta.");

    do {
      /*
       * Muestra un menú de opciones. Las opciones son:
       * 1. Registro
       * 2. Consulta
       * 3. Salir
       */
      System.out.println("""
          +-------Menú-------+
          | 1. Registro      |
          | 2. Consulta      |
          | 3. Salir         |
          +------------------+
          """);

      System.out.print("Ingrese una opción: ");
      opcion = scanner.nextInt();

      // Funcionalidad de menú
      switch (opcion) {
        case 1 -> Menu.add(scanner);
        case 2 -> Menu.query(scanner);
        case 3 -> System.out.println("Saliendo...");

        default -> System.out.println("\033[31merror:\033[0m Opción inválida.");
      }
    } while (opcion != 3);

    scanner.close();
  }
}
