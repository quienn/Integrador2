package tsjlh.poo2023.integrador2;

import java.util.Scanner;

public class User {
	private String name;
	private int age;
	private String id;
	private String role;
	private String phone;

	public User() {
		this.name = "Sin nombre";
		this.age = 0;
		this.id = "Sin ID";
		this.role = "Sin rol";
		this.phone = "Sin número de teléfono";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/*
	 * Devuelve una cadena con la informacion de la clase formateada para mostrarse.
	 */
	@Override
	public String toString() {
		return """
				Nombre: %s
				Código: %s
				Edad: %d
				Rol: %s
				Número de teléfono: %s
				""".formatted(name, id, age, role, phone);
	}

	/*
	 * Pide al usuario informacion para así anexarla al objeto.
	 */
	public void scanAttributes(Scanner scanner) {
		String lastName = this.name, lastId = this.id, lastRole = this.role, lastPhone = this.phone,
				lastAge = String.valueOf(this.age);

		System.out.printf("Ingrese el nombre (%s): ", this.name);
		name = scanner.nextLine();
		if (name.equals("")) {
			name = lastName;
		}

		System.out.printf("Ingrese la edad (%s): ", this.age);
		age = Integer.parseInt(scanner.nextLine());
		if (age == 0) {
			age = Integer.parseInt(lastAge);
		}

		System.out.printf("Ingrese el código (%s): ", this.id);
		id = scanner.nextLine();
		if (id.equals("")) {
			id = lastId;
		}

		System.out.printf("Ingrese el rol (%s): ", this.role);
		role = scanner.nextLine();
		if (role.equals("")) {
			role = lastRole;
		}

		System.out.printf("Ingrese el número de teléfono (%s): ", this.phone);
		phone = scanner.nextLine();
		if (phone.equals("")) {
			phone = lastPhone;
		}
	}
}
