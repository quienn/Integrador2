package tsjlh.poo2023.integrador2;

import java.util.Scanner;

public class User {
  private String name;
  private int age;
  private String id;
  private String role;

  public User() {
    this.name = "Sin nombre";
    this.age = 0;
    this.id = "Sin ID";
    this.role = "Sin rol";
  }

  public User(String name, int age, String id, String role) {
    this.name = name;
    this.age = age;
    this.id = id;
    this.role = role;
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

  /*
   * Devuelve una cadena con la informacion de la clase formateada para mostrarse.
   */
  @Override
  public String toString() {
    return """
        Nombre: %s
        Código: %s
        Edad: %d
        role: %s
        """.formatted(name, id, age, role);
  }

  /*
   * Pide al usuario informacion para así anexarla al objeto.
   */
  public void scanAttributes(Scanner scanner) {
    System.out.print("Ingrese el nombre: ");
    name = scanner.nextLine();

    // Pequeño Hack para
    System.out.print("Ingrese la edad: ");
    age = Integer.parseInt(scanner.nextLine());

    System.out.print("Ingrese el código: ");
    id = scanner.nextLine();

    System.out.print("Ingrese el rol: ");
    role = scanner.nextLine();
  }
}
