package tsjlh.poo2023.integrador2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Loan {
	private Item item;
	private User lender;
	private User borrower;
	private boolean returned;

	private LocalDate lendDate;
	private LocalDate returnDate;

	public Loan() {
		lendDate = null;
		item = null;
		lender = null;
		borrower = null;
		returned = false;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public User getLender() {
		return lender;
	}

	public void setLender(User lender) {
		this.lender = lender;
	}

	public User getBorrower() {
		return borrower;
	}

	public void setBorrower(User borrower) {
		this.borrower = borrower;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	public LocalDate getLendDate() {
		return lendDate;
	}

	public void setLendDate(LocalDate lendDate) {
		this.lendDate = lendDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public void scanAttributes(Scanner scanner,
			ArrayList<Item> availableItems,
			ArrayList<User> registeredUsers,
			String flash) {
		String borrowerId = "", lenderId = "", itemId = "", dateAnswer = "";

		for (int i = 0; i < registeredUsers.size(); i++) {
			System.out.println(
					i + ". + " + registeredUsers.get(i).getName() + " - " + registeredUsers.get(i).getId());
		}

		System.out.print("Indique su usuario a través de su código o nombre: ");
		lenderId = scanner.nextLine();
		for (User user : registeredUsers) {
			if (user.getId().equals(lenderId) || user.getName().equals(lenderId)) {
				this.lender = user;
				break;
			}
		}

		if (this.lender == null || !this.lender.getRole().toLowerCase().equals("almacenista")) {
			System.out.println("No se encontró el usuario prestador o el usuario no es almacenista.");
			flash = "No se encontró el usuario prestador o el usuario no es almacenista.";
		} else {
			System.out.print("Indique el usuario que desea prestar a través de su código o nombre: ");
			borrowerId = scanner.nextLine();

			for (User user : registeredUsers) {
				if (user.getId().equals(borrowerId) || user.getName().equals(borrowerId)) {
					this.borrower = user;
					break;
				}
			}
			if (this.borrower == null) {
				flash = "No se encontró el usuario prestatario.";
			} else {
				for (int i = 0; i < availableItems.size(); i++) {
					System.out.println(
							i + ". + " + availableItems.get(i).getName() + " - " + availableItems.get(i).getId());
				}

				System.out.print("Indique el material que desea prestar a través de su código o nombre: ");

				itemId = scanner.nextLine();
				for (Item item : availableItems) {
					if (item.getId().equals(itemId) || item.getName().equals(itemId)) {
						this.item = item;
						break;
					}
				}

				if (this.item == null) {
					flash = "No se encontró el material.";
				} else {
					System.out.print("¿Desea indicar la fecha de prestamo? (S/N): ");
					dateAnswer = scanner.nextLine();
					if (dateAnswer.toLowerCase().equals("s")) {
						System.out.print("Ingrese la fecha de prestamo (dd-MM-aaaa): ");
						dateAnswer = scanner.nextLine();
						try {
							this.lendDate = LocalDate.parse(dateAnswer,
									DateTimeFormatter.ofPattern("dd-MM-yyyy"));
						} catch (Exception e) {
							flash = "La fecha ingresada no es válida.";
						}
					} else {
						this.lendDate = LocalDate.now();
					}

					// Se puede devolver?
					if (!this.item.isConsumable()) {
						System.out.print("¿Desea indicar la fecha de devolucion? (S/N): ");
						dateAnswer = scanner.nextLine();
						if (dateAnswer.toLowerCase().equals("s")) {
							System.out.print("Ingrese la fecha de devolucion (dd-mm-aaaa): ");
							dateAnswer = scanner.nextLine();

							try {
								this.returnDate = LocalDate.parse(dateAnswer,
										DateTimeFormatter.ofPattern("dd-MM-yyyy"));
							} catch (Exception e) {
								flash = "La fecha ingresada no es válida.";
							}
						} else {
							this.returnDate = null;
						}
					} else {
						this.returnDate = null;
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		return String.format("""
				Material prestado: %s
				Fecha de prestamo: %s
				%s
				Usuario prestador: %s
				Usuario prestatario: %s
				Devuelto: %s%n""",
				this.item.getName(),
				this.lendDate.toString(),
				(this.returnDate != null ? "Fecha de devolucion: " + this.returnDate.toString() : ""),
				this.lender.getName(),
				this.borrower.getName());
	}
}
