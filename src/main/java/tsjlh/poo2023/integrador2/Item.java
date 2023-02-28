package tsjlh.poo2023.integrador2;

import java.util.Scanner;

public class Item {
	private String name;
	private String id;
	private String category;
	private int quantity;
	private boolean consumable;

	public Item() {
		this.name = "Sin nombre";
		this.id = "Sin ID";
		this.category = "Sin categoría";
		this.quantity = 0;
		this.consumable = false;
	}

	public Item(String name, String id, String category, int quantity, boolean consumable) {
		this.name = name;
		this.id = id;
		this.category = category;
		this.quantity = quantity;
		this.consumable = consumable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isConsumable() {
		return this.consumable;
	}

	public void setConsumable(boolean consumable) {
		this.consumable = consumable;
	}

	public void scanAttributes(Scanner scanner) {
		String lastName = this.name, lastId = this.id, lastCategory = this.category,
				lastQuantity = String.valueOf(this.quantity), lastConsumable = this.consumable ? "S" : "N";

		System.out.printf("Nombre (%s): ", this.name);
		this.name = scanner.nextLine();
		if (this.name.equals("")) {
			this.name = lastName;
		}

		System.out.printf("ID (%s): ", this.id);
		this.id = scanner.nextLine();
		if (this.id.equals("")) {
			this.id = lastId;
		}

		System.out.printf("Categoría (%s): ", this.category);
		this.category = scanner.nextLine();
		if (this.category.equals("")) {
			this.category = lastCategory;
		}

		System.out.printf("Cantidad (%s): ", this.quantity);
		this.quantity = Integer.parseInt(scanner.nextLine());
		if (this.quantity == 0) {
			this.quantity = Integer.parseInt(lastQuantity);
		}

		System.out.printf("¿Es consumible? (S/N) [%s]: ", this.consumable ? "S" : "N");
		this.consumable = scanner.nextLine().equalsIgnoreCase("s");
		if (!this.consumable) {
			this.consumable = lastConsumable.equalsIgnoreCase("s");
		}
	}

	/*
	 * Devuelve una cadena con la informacion de la clase formateada para mostrarse.
	 */
	@Override
	public String toString() {
		return String.format("""
				Nombre: %s
				ID: %s
				Categoría: %s
				Cantidad: %d
				Consumible: %s""", this.name, this.id, this.category, this.quantity, this.consumable ? "Sí" : "No");
	}
}
