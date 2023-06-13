import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    private Map<String, String> contacts;
    private String filePath;

    public AddressBook(String filePath) {
        this.contacts = new HashMap<>();
        this.filePath = filePath;
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String phoneNumber = parts[0].trim();
                    String name = parts[1].trim();
                    contacts.put(phoneNumber, name);
                }
            }
            System.out.println("Contactos cargados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar los contactos: " + e.getMessage());
        }
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                String phoneNumber = entry.getKey();
                String name = entry.getValue();
                writer.write(phoneNumber + "," + name);
                writer.newLine();
            }
            System.out.println("Contactos guardados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los contactos: " + e.getMessage());
        }
    }

    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            String phoneNumber = entry.getKey();
            String name = entry.getValue();
            System.out.println(phoneNumber + " : " + name);
        }
    }

    public void create(String phoneNumber, String name) {
        contacts.put(phoneNumber, name);
        System.out.println("Contacto creado exitosamente.");
    }

    public void delete(String phoneNumber) {
        if (contacts.containsKey(phoneNumber)) {
            contacts.remove(phoneNumber);
            System.out.println("Contacto eliminado exitosamente.");
        } else {
            System.out.println("El contacto no existe.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nAgenda Telefónica");
            System.out.println("1. Mostrar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Guardar cambios");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    list();
                    break;
                case 2:
                    System.out.print("Ingrese el número telefónico: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    String name = scanner.nextLine();
                    create(phoneNumber, name);
                    break;
                case 3:
                    System.out.print("Ingrese el número telefónico del contacto a eliminar: ");
                    String numberToDelete = scanner.nextLine();
                    delete(numberToDelete);
                    break;
                case 4:
                    save();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Gracias por usar la agenda telefonica hasta pronto");
                    break;
                default:
                    System.out.println("Opción inválida. Inténtalo de nuevo.");
                    break;
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook("contacts.csv");
        addressBook.load();
        addressBook.menu();
    }
}
