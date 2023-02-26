package tsjlh.poo2023.integrador2;

import java.util.ArrayList;
import java.util.Objects;
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
	ArrayList<Item> items;

	/*
	 * Para mostrar información encima de los menús. Sirve para errores, avisos e
	 * información obtenida.
	 */
	String flash;

	public App() {
		users = new ArrayList<>();
		scanner = new Scanner(System.in, "UTF-8");
		items = new ArrayList<>();
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

			System.out.printf("""
					%s
					+--------Registro--------+
					| 1. Registrar Usuario   |
					| 2. Registrar Préstamo  |
					| 3. Registrar Material  |
					| 4. Volver...           |
					+------------------------+%n""", flash);

			System.out.print("Ingrese una opción: ");
			try {
				opt = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException exception) {
				continue;
			}

			switch (opt) {
				case 1 -> {
					clearScreen();

					User newUser = new User();
					newUser.scanAttributes(scanner);
					users.add(newUser);

					this.flash = "aviso: Usuario para \"" + newUser.getName() + "\" agreado con éxito.";
				}

				case 3 -> {
					clearScreen();

					Item newItem = new Item();
					newItem.scanAttributes(scanner);
					items.add(newItem);

					this.flash = "aviso: Material para \"" + newItem.getName() + "\" agreado con éxito.";
				}

				case 4 -> this.flash = "";
				default -> this.flash = "error: Opción inválida.";
			}
		} while (opt != 4);
	}

	public void queryEntryMenu() {
		int opt = 0, optUser = 0, optItem = 0;
		final String[] optQuery = new String[3];

		do {
			clearScreen();

			System.out.printf("""
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
					+-------------------------+%n""", flash);

			System.out.print("Ingrese una opción: ");

			try {
				opt = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException exception) {
				continue;
			}

			// TODO: Agrupar usuarios por su rol y mostrarlos en un menú.
			// TODO: Agrupar materiales por su tipo y mostrarlos en un menú.
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

					optQuery[0] = "";

					System.out.print("Ingrese el nombre o código del usuario: ");
					try {
						optQuery[0] = scanner.nextLine().toLowerCase();
					} catch (Exception exception) {
						continue;
					}

					if (Objects.equals(optQuery[0], "")) {
						this.flash = "error: Necesita ingresar un valor para realizar la búsqueda.";
						break;
					}

					Predicate<User> searchByNameOrCode = (user) -> !user.getName().toLowerCase().equals(optQuery[0])
							&& !user.getId().toLowerCase().equals(optQuery[0]);

					ArrayList<User> results = new ArrayList<>(users);
					results.removeIf(searchByNameOrCode);

					if (results.size() > 0) {
						this.flash = results.get(0).toString();
					} else {
						this.flash = "error: Usuario no encontrado.";
					}
				}

				case 5 -> {
					do {
						clearScreen();

						for (int i = 0; i < items.size(); i++) {
							System.out.println(i + ". " + items.get(i).getName());
						}

						System.out.print("Indique el material del que quiere ver información: ");
						try {
							optItem = Integer.parseInt(scanner.nextLine());
						} catch (NumberFormatException exception) {
							continue;
						}

						if (optItem >= 0 && optItem <= items.size()) {
							this.flash = items.get(optItem).toString();
						}
					} while (optItem < 0 || optItem > items.size());
				}

				case 6 -> {
					clearScreen();

					optQuery[1] = "";

					System.out.print("Ingrese el nombre o código del material: ");
					try {
						optQuery[0] = scanner.nextLine().toLowerCase();
					} catch (Exception exception) {
						continue;
					}

					if (Objects.equals(optQuery[0], "")) {
						this.flash = "error: Necesita ingresar un valor para realizar la búsqueda.";
						break;
					}

					Predicate<Item> searchByNameOrCode = (item) -> !item.getName().toLowerCase().equals(optQuery[0])
							&& !item.getId().toLowerCase().equals(optQuery[0]);

					ArrayList<Item> results = new ArrayList<>(items);
					results.removeIf(searchByNameOrCode);

					if (results.size() > 0) {
						this.flash = results.get(0).toString();
					} else {
						this.flash = "error: Material no encontrado.";
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
			System.out.printf("""
					%s
					+-------Menú-------+
					| 1. Registrar     |
					| 2. Consultar     |
					| 3. Eliminar      |
					| 4. Salir         |
					+------------------+%n""", app.flash);

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
