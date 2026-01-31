import java.util.*;
public class RecommendationSystem {

    // Sample user-item ratings
    static Map<Integer, Map<String, Integer>> data = new HashMap<>();

    public static void main(String[] args) {

        // Sample data
        data.put(1, Map.of("Mobile", 5, "Laptop", 3, "Headphones", 2));
        data.put(2, Map.of("Mobile", 4, "Laptop", 2, "Camera", 5));
        data.put(3, Map.of("Mobile", 2, "Headphones", 5, "Camera", 4));
        data.put(4, Map.of("Laptop", 4, "Headphones", 3, "Camera", 2));

        int targetUser = 1;

        String recommendation = recommendProduct(targetUser);
        System.out.println("Recommended product for User " + targetUser + ": " + recommendation);
    }

    // Recommend product not rated by target user
    public static String recommendProduct(int userId) {
        Map<String, Integer> targetRatings = data.get(userId);

        Map<String, Integer> score = new HashMap<>();

        for (int otherUser : data.keySet()) {
            if (otherUser == userId) continue;

            Map<String, Integer> otherRatings = data.get(otherUser);

            // Find common items
            int similarity = 0;
            for (String item : targetRatings.keySet()) {
                if (otherRatings.containsKey(item)) {
                    similarity += 1; // simple similarity count
                }
            }

            // Recommend items other user liked
            for (String item : otherRatings.keySet()) {
                if (!targetRatings.containsKey(item)) {
                    score.put(item, score.getOrDefault(item, 0) + similarity * otherRatings.get(item));
                }
            }
        }

        // Find best item
        String bestItem = null;
        int maxScore = -1;

        for (String item : score.keySet()) {
            if (score.get(item) > maxScore) {
                maxScore = score.get(item);
                bestItem = item;
            }
        }

        return bestItem;
    }
}
