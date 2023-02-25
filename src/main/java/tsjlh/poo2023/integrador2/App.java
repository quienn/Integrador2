package tsjlh.poo2023.integrador2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

public class App {
  /*
   * Scanner usado en toda la aplicación para pedir información al usuario.
   */
  Scanner scanner;

  /*
   * Arreglo que contiene todos los usuarios del sistema.
   */
  ArrayList<User> users;

  /*
   * Arreglo que contiene todos los materiales del sistema.
   */
  // ArrayList<Item> items;

  /*
   * Para mostrar información encima de los menús. Sirve para errores, avisos e
   * información obtenida.
   */
  String flash;

  public App() {
    users = new ArrayList<>();
    scanner = new Scanner(System.in);
    // items = new ArrayList<>();
    flash = "";
  }

  // Utilidades //

  public void clearScreen() {
    System.out.print("\033[H\033[2J");
  }

  // Menús //

  public void insertEntryMenu() {
    int opt = 0;

    do {
      clearScreen();

      System.out.println("""
          %s
          +--------Registro--------+
          | 1. Registrar Usuario   |
          | 2. Registrar Préstamo  |
          | 3. Registrar Material  |
          | 4. Volver...           |
          +------------------------+""".formatted(flash));

      System.out.print("Ingrese una opción: ");
      opt = Integer.parseInt(scanner.nextLine());

      switch (opt) {
        case 1 -> {
          clearScreen();

          User newUser = new User();
          newUser.scanAttributes(scanner);

          users.add(newUser);
          this.flash = "aviso: Usuario para \"" + newUser.getName() + "\" agreado con éxito.";
        }

        case 4 -> this.flash = "";
        default -> this.flash = "error: Opción incorrecta.";
      }
    } while (opt != 4);
  }

  public void queryEntryMenu() {
    int opt = 0, optUser = 0;
    final String optUserQuery[] = new String[1];

    do {
      clearScreen();

      System.out.println("""
          %s
          +--------Consulta---------+
          | 1. Mostrar Usuarios     |
          | 2. Buscar Usuario...    |
          +-------------------------+
          | 3. Mostrar Préstamos    |
          | 4. Buscar Préstamos...  |
          +-------------------------|
          | 5. Mostrar Materiales   |
          | 6. Buscar Materiales... |
          +-------------------------+
          | 7. Volver...            |
          +-------------------------+""".formatted(flash));

      System.out.print("Ingrese una opción: ");

      try {
        opt = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException exception) {
        continue;
      }

      switch (opt) {
        case 1 -> {
          do {
            clearScreen();

            for (int i = 0; i < users.size(); i++) {
              System.out.println(i + ". " + users.get(i).getName());
            }

            System.out.print("Indique el usuario del que quiere ver información: ");
            try {
              optUser = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException exception) {
              continue;
            }

            if (optUser >= 0 && optUser <= users.size()) {
              this.flash = users.get(optUser).toString();
            }
          } while (optUser < 0 || optUser > users.size());
        }

        case 2 -> {
          clearScreen();

          optUserQuery[0] = "";

          System.out.print("Ingrese el nombre o código del usuario: ");
          try {
            optUserQuery[0] = scanner.nextLine();
          } catch (Exception exception) {
            continue;
          }

          if (optUserQuery[0] == "") {
            this.flash = "error: Necesita ingresar un valor para realizar la búsqueda.";
            break;
          }

          Predicate<User> searchByNameOrCode = (user) -> !user.getName().equals(optUserQuery[0])
              && !user.getId().equals(optUserQuery[0]);

          ArrayList<User> results = new ArrayList<>(users);
          results.removeIf(searchByNameOrCode);

          if (results.size() > 0) {
            this.flash = results.get(0).toString();
          } else {
            this.flash = "error: Usuario no encontrado.";
          }
        }

        case 7 -> this.flash = "";
        default -> this.flash = "error: Opción incorrecta";
      }
    } while (opt != 7);
  }

  public void deleteEntryMenu() {
  }

  public static void main(String[] args) {
    App app = new App();

    int opt = 0;

    do {
      app.clearScreen();
      System.out.println("""
          %s
          +-------Menú-------+
          | 1. Registrar     |
          | 2. Consultar     |
          | 3. Eliminar      |
          | 4. Salir         |
          +------------------+""".formatted(app.flash));

      System.out.print("Ingrese una opción: ");

      try {
        opt = Integer.parseInt(app.scanner.nextLine());
      } catch (NumberFormatException exception) {
        continue;
      }

      switch (opt) {
        case 1 -> app.insertEntryMenu();
        case 2 -> app.queryEntryMenu();
        case 3 -> app.deleteEntryMenu();
        case 4 -> System.out.println("Saliendo...");
        default -> app.flash = "error: Opción inválida.";
      }
    } while (opt != 4);

    app.scanner.close();
  }
}
