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
	 * Arreglo que contiene todos los préstamos en el sistema.
	 */
	ArrayList<Loan> loans;

	/*
	 * Para mostrar información encima de los menús. Sirve para errores, avisos e
	 * información obtenida.
	 */
	String flash;

	public App() {
		users = new ArrayList<>();
		scanner = new Scanner(System.in, "UTF-8");
		items = new ArrayList<>();
		loans = new ArrayList<>();
		flash = "";
	}

	/*
	 * Limpia la pantalla.
	 */
	public void clearScreen() {
		System.out.print("\033[H\033[2J");
	}

	/*
	 * Método de busqueda genérico. Recibe un arreglo y un predicado. Devuelve un
	 * arreglo con los elementos que no cumplan con el predicado.
	 */
	static <T> ArrayList<T> searchFromList(ArrayList<T> list, Predicate<T> search) {
		ArrayList<T> results = new ArrayList<>(list);
		results.removeIf(search);
		return results;
	}

	/*
	 * Busca un usuario por su nombre o código.
	 */
	public User searchUser(String nameOrCode) {
		return searchFromList(users,
				user -> !user.getName().contains(nameOrCode) && !user.getId().contains(nameOrCode)).get(0);
	}

	/*
	 * Busca un material por su nombre o código.
	 */
	public Item searchItem(String nameOrCode) {
		return searchFromList(items,
				item -> !item.getName().contains(nameOrCode) && !item.getId().contains(nameOrCode)).get(0);
	}

	/*
	 * Busca materiales por su categoría.
	 */
	public ArrayList<Item> searchItemByCategory(String category) {
		return searchFromList(items, item -> !item.getCategory().contains(category));
	}

	/*
	 * Busca préstamos por su código.
	 */

	public Loan searchLoan(String code) {
		return searchFromList(loans, loan -> !loan.getItem().getId().contains(code)).get(0);
	}

	/*
	 * Menú de registros.
	 */
	public void insertEntryMenu() {
		int opt = 0;
		User newUser;
		Item newItem;
		Loan newLoan;

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

				switch (opt) {
					case 1 -> {
						clearScreen();

						newUser = new User();
						newUser.scanAttributes(scanner);
						users.add(newUser);

						this.flash = "aviso: Usuario para \"" + newUser.getName() + "\" agreado con éxito.";
					}

					case 2 -> {
						clearScreen();

						newLoan = new Loan();
						newLoan.scanAttributes(scanner, items, users, this.flash);

						if (newLoan.getItem() == null) {
							this.flash = "error: Hubo un error mientras se registraba el préstamo.";
						} else {
							if (newLoan.getItem().getQuantity() > 0) {
								newLoan.getItem().setQuantity(newLoan.getItem().getQuantity() - 1);
							}

							this.flash = "aviso: Préstamo para \"" + newLoan.getItem().getName()
									+ "\" agreado con éxito.";
						}
					}

					case 3 -> {
						clearScreen();

						newItem = new Item();
						newItem.scanAttributes(scanner);
						items.add(newItem);

						this.flash = "aviso: Material para \"" + newItem.getName() + "\" agreado con éxito.";
					}

					case 4 -> this.flash = "";
					default -> this.flash = "error: Opción inválida.";
				}
			} catch (NumberFormatException exception) {
				this.flash = "error: Ingrese una opción del menú.";
			}
		} while (opt != 4);
	}

	public void queryEntryMenu() {
		int opt = 0, optUser = 0, optItem = 0;
		String optQuery = "";
		User userResult;
		Item itemResult;
		Loan loanResult;

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
								if (optUser >= 0 && optUser <= users.size()) {
									this.flash = users.get(optUser).toString();
								}
							} catch (NumberFormatException exception) {
								this.flash = "error: Ingrese un número.";
							}
						} while (optUser < 0 || optUser > users.size());
					}

					case 2 -> {
						clearScreen();

						System.out.print("Ingrese el nombre o código del usuario: ");
						try {
							optQuery = scanner.nextLine().toLowerCase();

							if (Objects.equals(optQuery, "")) {
								this.flash = "error: Necesita ingresar un valor para realizar la búsqueda.";
							} else {
								userResult = searchUser(optQuery);

								if (userResult != null) {
									this.flash = userResult.toString();
								} else {
									this.flash = "error: Usuario no encontrado.";
								}
							}
						} catch (Exception exception) {
							this.flash = "error: Ingrese un valor válido.";
						}

					}

					case 3 -> {
						do {
							clearScreen();

							for (int i = 0; i < loans.size(); i++) {
								System.out.println(i + ". " + loans.get(i).getItem().getName());
							}

							System.out.print("Indique el préstamo del que quiere ver información: ");
							try {
								optUser = Integer.parseInt(scanner.nextLine());

								if (optUser >= 0 && optUser <= loans.size()) {
									this.flash = loans.get(optUser).toString();
								}
							} catch (NumberFormatException exception) {
								this.flash = "error: Ingrese un número.";
							}
						} while (optUser < 0 || optUser > loans.size());
					}

					case 4 -> {
						clearScreen();

						System.out.print("Ingrese el nombre o código del materia del préstamo: ");
						try {
							optQuery = scanner.nextLine().toLowerCase();

							if (Objects.equals(optQuery, "")) {
								this.flash = "error: Necesita ingresar un valor para realizar la búsqueda.";
							} else {
								loanResult = searchLoan(optQuery);

								if (loanResult != null) {
									this.flash = loanResult.toString();
								} else {
									this.flash = "error: Préstamo no encontrado.";
								}
							}
						} catch (Exception exception) {
							this.flash = "error: Ingrese un valor válido.";
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

								if (optItem >= 0 && optItem <= items.size()) {
									this.flash = items.get(optItem).toString();
								}
							} catch (NumberFormatException exception) {
								this.flash = "error: Ingrese un número.";
							}

						} while (optItem < 0 || optItem > items.size());
					}

					case 6 -> {
						clearScreen();

						optQuery = "";

						System.out.print("Ingrese el nombre o código del material: ");
						try {
							optQuery = scanner.nextLine().toLowerCase();

							if (!Objects.equals(optQuery, "")) {
								itemResult = searchItem(optQuery);
								if (itemResult != null) {
									this.flash = itemResult.toString();
								} else {
									this.flash = "error: Material no encontrado.";
								}
							} else {
								this.flash = "error: Necesita ingresar un valor para realizar la búsqueda.";
							}
						} catch (Exception exception) {
							this.flash = "error: Ingrese un valor válido.";
						}
					}

					case 7 -> this.flash = "";
					default -> this.flash = "error: Opción incorrecta";
				}
			} catch (NumberFormatException exception) {
				this.flash = "error: Ingrese un número.";
			}
		} while (opt != 7);
	}

	public void modifyEntryMenu() {
		int opt = 0;
		String optQuery = "";
		User userResult;
		Item itemResult;
		Loan loanResult;

		do {
			clearScreen();

			System.out.printf("""
					%s
					+--------Modificar---------+
					| 1. Modificar Usuario...  |
					+--------------------------+
					| 2. Modificar Préstamo... |
					+--------------------------+
					| 3. Modificar Material... |
					+--------------------------+
					| 4. Volver...             |
					+--------------------------+%n""", flash);

			System.out.print("Ingrese una opción: ");

			try {
				opt = Integer.parseInt(scanner.nextLine());

				switch (opt) {
					case 1 -> {
						clearScreen();

						for (int i = 0; i < users.size(); i++) {
							System.out.println(i + ". " + users.get(i).getName() + " - " + users.get(i).getId());
						}

						System.out.print("Ingrese el nombre o código del usuario: ");
						try {
							optQuery = scanner.nextLine().toLowerCase();

							if (Objects.equals(optQuery, "")) {
								this.flash = "error: Necesita ingresar un valor para realizar la búsqueda.";
							} else {
								userResult = searchUser(optQuery);

								if (userResult != null) {
									userResult.scanAttributes(scanner);
									this.flash = "aviso: Usuario modificado con éxito.";
								} else {
									this.flash = "error: Usuario no encontrado.";
								}
							}
						} catch (Exception exception) {
							this.flash = "error: Ingrese un valor válido.";
						}
					}

					case 2 -> {
						clearScreen();

						for (int i = 0; i < loans.size(); i++) {
							System.out.println(i + ". " + loans.get(i).getItem().getName());
						}

						System.out.print("Ingrese el nombre o código del material: ");
						try {
							optQuery = scanner.nextLine().toLowerCase();

							if (Objects.equals(optQuery, "")) {
								this.flash = "error: Necesita ingresar un valor para realizar la búsqueda.";
							} else {
								loanResult = searchLoan(optQuery);

								if (loanResult != null) {
									loanResult.scanAttributes(scanner, items, users, this.flash);
									this.flash = "aviso: Préstamo modificado con éxito.";
								} else {
									this.flash = "error: Préstamo no encontrado.";
								}
							}
						} catch (Exception exception) {
							this.flash = "error: Ingrese un valor válido.";
						}
					}

					case 3 -> {
						clearScreen();

						for (int i = 0; i < items.size(); i++) {
							System.out.println(i + ". " + items.get(i).getName());
						}

						System.out.print("Ingrese el nombre o código del material: ");
						try {
							optQuery = scanner.nextLine().toLowerCase();

							if (Objects.equals(optQuery, "")) {
								this.flash = "error: Necesita ingresar un valor para realizar la búsqueda.";
							} else {
								itemResult = searchItem(optQuery);

								if (itemResult != null) {
									itemResult.scanAttributes(scanner);
									this.flash = "aviso: Material modificado con éxito.";
								} else {
									this.flash = "error: Material no encontrado.";
								}
							}
						} catch (Exception exception) {
							this.flash = "error: Ingrese un valor válido.";
						}
					}

					case 4 -> this.flash = "";
					default -> this.flash = "error: Opción incorrecta";
				}
			} catch (NumberFormatException exception) {
				this.flash = "error: Ingrese un número.";
			}
		} while (opt != 4);
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
					| 3. Modificar     |
					| 4. Salir         |
					+------------------+%n""", app.flash);

			System.out.print("Ingrese una opción: ");

			try {
				opt = Integer.parseInt(app.scanner.nextLine());

				switch (opt) {
					case 1 -> app.insertEntryMenu();
					case 2 -> app.queryEntryMenu();
					case 3 -> app.modifyEntryMenu();
					case 4 -> System.out.println("Saliendo...");
					default -> app.flash = "error: Opción inválida.";
				}
			} catch (NumberFormatException exception) {
				app.flash = "error: Ingrese una opción del menú.";
			}
		} while (opt != 4);

		app.scanner.close();
	}
}
