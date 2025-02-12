import java.util.*;

// Class to represent an item with features
class Item {
    private String id;
    private Map<String, Double> features;

    public Item(String id, Map<String, Double> features) {
        this.id = id;
        this.features = features;
    }

    public String getId() {
        return id;
    }

    public Map<String, Double> getFeatures() {
        return features;
    }
}

// Class to represent a recommendation with score
class Recommendation implements Comparable<Recommendation> {
    private String itemId;
    private double score;

    public Recommendation(String itemId, double score) {
        this.itemId = itemId;
        this.score = score;
    }

    public String getItemId() {
        return itemId;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int compareTo(Recommendation other) {
        return Double.compare(other.score, this.score); // Sort in descending order
    }
}

// Main recommendation system class
public class RecommendationSystem {
    private Map<String, Item> items;
    private Map<String, Map<String, Double>> userPreferences;

    public RecommendationSystem() {
        this.items = new HashMap<>();
        this.userPreferences = new HashMap<>();
    }

    // Add an item to the system
    public void addItem(String itemId, Map<String, Double> features) {
        items.put(itemId, new Item(itemId, features));
    }

    // Update user preferences
    public void updateUserPreferences(String userId, Map<String, Double> preferences) {
        userPreferences.put(userId, preferences);
    }

    // Calculate similarity between user preferences and item features
    private double calculateSimilarity(Map<String, Double> userPreferences, Map<String, Double> itemFeatures) {
        double score = 0.0;
        int matchCount = 0;

        for (Map.Entry<String, Double> pref : userPreferences.entrySet()) {
            String feature = pref.getKey();
            if (itemFeatures.containsKey(feature)) {
                score += pref.getValue() * itemFeatures.get(feature);
                matchCount++;
            }
        }

        return matchCount > 0 ? score / matchCount : 0.0;
    }

    // Get recommendations for a user
    public List<Recommendation> getRecommendations(String userId, int limit) {
        Map<String, Double> userPrefs = userPreferences.get(userId);
        if (userPrefs == null) {
            throw new IllegalArgumentException("User preferences not found for: " + userId);
        }

        PriorityQueue<Recommendation> recommendations = new PriorityQueue<>();

        // Calculate similarity scores for all items
        for (Item item : items.values()) {
            double similarity = calculateSimilarity(userPrefs, item.getFeatures());
            recommendations.add(new Recommendation(item.getId(), similarity));
        }

        // Get top N recommendations
        List<Recommendation> topRecommendations = new ArrayList<>();
        int count = 0;
        while (!recommendations.isEmpty() && count < limit) {
            topRecommendations.add(recommendations.poll());
            count++;
        }

        return topRecommendations;
    }

    // Example usage
    public static void main(String[] args) {
        RecommendationSystem recommender = new RecommendationSystem();

        // Add sample items (e.g., movies)
        Map<String, Double> movie1Features = new HashMap<>();
        movie1Features.put("action", 0.8);
        movie1Features.put("adventure", 0.6);
        movie1Features.put("romance", 0.2);
        recommender.addItem("movie1", movie1Features);

        Map<String, Double> movie2Features = new HashMap<>();
        movie2Features.put("action", 0.3);
        movie2Features.put("adventure", 0.2);
        movie2Features.put("romance", 0.9);
        recommender.addItem("movie2", movie2Features);

        // Set user preferences
        Map<String, Double> userPreferences = new HashMap<>();
        userPreferences.put("action", 0.9);
        userPreferences.put("adventure", 0.7);
        userPreferences.put("romance", 0.3);
        recommender.updateUserPreferences("user1", userPreferences);

        // Get recommendations
        List<Recommendation> recommendations = recommender.getRecommendations("user1", 2);
        
        // Print recommendations
        for (Recommendation rec : recommendations) {
            System.out.printf("Item: %s, Score: %.2f%n", rec.getItemId(), rec.getScore());
        }
    }
}
