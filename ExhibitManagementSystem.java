import java.time.LocalDate;
import java.util.*;

// Exhibit class to represent individual exhibits
class Exhibit {
    private String id;
    private String name;
    private String description;
    private String category;
    private LocalDate acquisitionDate;
    private String location;
    private String condition;
    private boolean onDisplay;
    private Map<String, String> customAttributes;

    public Exhibit(String id, String name, String description, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.acquisitionDate = LocalDate.now();
        this.condition = "Good";
        this.onDisplay = false;
        this.customAttributes = new HashMap<>();
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public LocalDate getAcquisitionDate() { return acquisitionDate; }
    public String getLocation() { return location; }
    public String getCondition() { return condition; }
    public boolean isOnDisplay() { return onDisplay; }
    
    public void setLocation(String location) { this.location = location; }
    public void setCondition(String condition) { this.condition = condition; }
    public void setOnDisplay(boolean onDisplay) { this.onDisplay = onDisplay; }
    
    public void addCustomAttribute(String key, String value) {
        customAttributes.put(key, value);
    }
    
    public String getCustomAttribute(String key) {
        return customAttributes.get(key);
    }

    @Override
    public String toString() {
        return String.format("Exhibit{id='%s', name='%s', category='%s', location='%s', onDisplay=%b}",
                id, name, category, location, onDisplay);
    }
}

// Exhibition class to group exhibits together
class Exhibition {
    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<Exhibit> exhibits;

    public Exhibition(String id, String name, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.exhibits = new HashSet<>();
    }

    public void addExhibit(Exhibit exhibit) {
        exhibits.add(exhibit);
        exhibit.setOnDisplay(true);
    }

    public void removeExhibit(Exhibit exhibit) {
        exhibits.remove(exhibit);
        exhibit.setOnDisplay(false);
    }

    public Set<Exhibit> getExhibits() {
        return Collections.unmodifiableSet(exhibits);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
}

// Main management system class
public class ExhibitManagementSystem {
    private Map<String, Exhibit> exhibits;
    private Map<String, Exhibition> exhibitions;
    private Map<String, List<Exhibit>> categoryIndex;

    public ExhibitManagementSystem() {
        this.exhibits = new HashMap<>();
        this.exhibitions = new HashMap<>();
        this.categoryIndex = new HashMap<>();
    }

    // Add a new exhibit to the system
    public void addExhibit(Exhibit exhibit) {
        exhibits.put(exhibit.getId(), exhibit);
        categoryIndex
            .computeIfAbsent(exhibit.getCategory(), k -> new ArrayList<>())
            .add(exhibit);
    }

    // Create a new exhibition
    public void createExhibition(Exhibition exhibition) {
        exhibitions.put(exhibition.getId(), exhibition);
    }

    // Add exhibit to exhibition
    public void addExhibitToExhibition(String exhibitId, String exhibitionId) {
        Exhibit exhibit = exhibits.get(exhibitId);
        Exhibition exhibition = exhibitions.get(exhibitionId);
        
        if (exhibit != null && exhibition != null) {
            exhibition.addExhibit(exhibit);
        } else {
            throw new IllegalArgumentException("Exhibit or Exhibition not found");
        }
    }

    // Search exhibits by category
    public List<Exhibit> searchByCategory(String category) {
        return categoryIndex.getOrDefault(category, new ArrayList<>());
    }

    // Search exhibits by condition
    public List<Exhibit> searchByCondition(String condition) {
        return exhibits.values().stream()
                .filter(e -> e.getCondition().equalsIgnoreCase(condition))
                .toList();
    }

    // Get all exhibits currently on display
    public List<Exhibit> getExhibitsOnDisplay() {
        return exhibits.values().stream()
                .filter(Exhibit::isOnDisplay)
                .toList();
    }

    // Update exhibit location
    public void updateExhibitLocation(String exhibitId, String newLocation) {
        Exhibit exhibit = exhibits.get(exhibitId);
        if (exhibit != null) {
            exhibit.setLocation(newLocation);
        }
    }

    // Generate report of all exhibits
    public String generateExhibitReport() {
        StringBuilder report = new StringBuilder("Exhibit Management Report\n");
        report.append("================================\n\n");
        
        exhibits.values().forEach(exhibit -> {
            report.append(String.format("ID: %s\n", exhibit.getId()));
            report.append(String.format("Name: %s\n", exhibit.getName()));
            report.append(String.format("Category: %s\n", exhibit.getCategory()));
            report.append(String.format("Location: %s\n", exhibit.getLocation()));
            report.append(String.format("Condition: %s\n", exhibit.getCondition()));
            report.append(String.format("On Display: %s\n", exhibit.isOnDisplay()));
            report.append("--------------------------------\n");
        });
        
        return report.toString();
    }

    // Example usage
    public static void main(String[] args) {
        ExhibitManagementSystem system = new ExhibitManagementSystem();

        // Create exhibits
        Exhibit exhibit1 = new Exhibit("E001", "Ancient Vase", "Greek ceramic vase", "Ceramics");
        exhibit1.setLocation("Gallery A");
        
        Exhibit exhibit2 = new Exhibit("E002", "Modern Sculpture", "Contemporary metal sculpture", "Sculpture");
        exhibit2.setLocation("Gallery B");

        system.addExhibit(exhibit1);
        system.addExhibit(exhibit2);

        // Create exhibition
        Exhibition exhibition = new Exhibition("EX001", "Ancient Art",
                LocalDate.now(), LocalDate.now().plusMonths(3));
        system.createExhibition(exhibition);

        // Add exhibit to exhibition
        system.addExhibitToExhibition("E001", "EX001");

        // Generate and print report
        System.out.println(system.generateExhibitReport());
    }
}
