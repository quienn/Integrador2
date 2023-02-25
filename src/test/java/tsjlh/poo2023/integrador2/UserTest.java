package tsjlh.poo2023.integrador2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {
  private User user = new User();

  @Test
  public void testScanAttributes() {
    String input = """
        Martín Aguilar
        18
        220111144
        estudiante""";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    Scanner scanner = new Scanner(System.in);
    user.scanAttributes(scanner);

    assertEquals("Martín Aguilar", user.getName());
    assertEquals(18, user.getAge());
    assertEquals("220111144", user.getId());
    assertEquals("estudiante", user.getRole());
  }

  @Test
  public void testAttributes() {
    user = new User("Martín Aguilar", 18, "220111144", "estudiante");

    assertEquals("Martín Aguilar", user.getName());
    assertEquals(18, user.getAge());
    assertEquals("220111144", user.getId());
    assertEquals("estudiante", user.getRole());
  }
}
