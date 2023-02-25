package tsjlh.poo2023.integrador2;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
  Scanner scanner;

  ArrayList<User> users;
  // ArrayList<Item> items;

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

            users.forEach((user) -> {
              System.out.println(users.indexOf(user) + ". " + user.getName());
            });

            System.out.print("Indique el usuario del que quiere ver información: ");
            try {
              optUser = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException exception) {
              continue;
            }

            // FIXME: Esta parte no funciona aunque la opción rebase el límite.
            if (optUser >= 0 && optUser <= users.size()) {
              this.flash = users.get(optUser).toString();
            }
          } while (optUser < 0 || optUser > users.size());
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
