import java.util.ArrayList;
import java.util.Scanner;

// Exhibit class
class Exhibit {
    private String name;
    private String type;
    private String location;
    private String description;

    public Exhibit(String name, String type, String location, String description) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.description = description;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Exhibit{" +
               "Name='" + name + '\'' +
               ", Type='" + type + '\'' +
               ", Location='" + location + '\'' +
               ", Description='" + description + '\'' +
               '}';
    }
}

// ExhibitManager class
class ExhibitManager {
    private ArrayList<Exhibit> exhibits = new ArrayList<>();

    // Add exhibit
    public void addExhibit(Exhibit exhibit) {
        exhibits.add(exhibit);
        System.out.println("Exhibit added successfully.");
    }

    // Display exhibits
    public void viewExhibits() {
        if (exhibits.isEmpty()) {
            System.out.println("No exhibits available.");
        } else {
            for (Exhibit exhibit : exhibits) {
                System.out.println(exhibit);
            }
        }
    }

    // Delete exhibit
    public void deleteExhibit(String name) {
        exhibits.removeIf(exhibit -> exhibit.getName().equalsIgnoreCase(name));
        System.out.println("Exhibit deleted if found.");
    }
}

public class ExhibitManagementSystem {
    public static void main(String[] args) {
        ExhibitManager manager = new ExhibitManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Exhibit Management System ---");
            System.out.println("1. Add Exhibit");
            System.out.println("2. View Exhibits");
            System.out.println("3. Delete Exhibit");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Exhibit Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Exhibit Type: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter Exhibit Location: ");
                    String location = scanner.nextLine();
                    System.out.print("Enter Exhibit Description: ");
                    String description = scanner.nextLine();

                    Exhibit exhibit = new Exhibit(name, type, location, description);
                    manager.addExhibit(exhibit);
                    break;

                case 2:
                    manager.viewExhibits();
                    break;

                case 3:
                    System.out.print("Enter the name of the exhibit to delete: ");
                    String exhibitName = scanner.nextLine();
                    manager.deleteExhibit(exhibitName);
                    break;

                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
