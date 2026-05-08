import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.io.File;
import java.util.List;

public class RecommendationSystem {

    public static void main(String[] args) {

        try {

            // Load data model
            DataModel model = new FileDataModel(new File("data.csv"));

            // Calculate similarity between users
            UserSimilarity similarity =
                    new PearsonCorrelationSimilarity(model);

            // Find nearest 2 users
            UserNeighborhood neighborhood =
                    new NearestNUserNeighborhood(2, similarity, model);

            // Build recommender
            UserBasedRecommender recommender =
                    new GenericUserBasedRecommender(
                            model,
                            neighborhood,
                            similarity
                    );

            // Recommend items for User 1
            List<RecommendedItem> recommendations =
                    recommender.recommend(1, 3);

            System.out.println("Recommendations for User 1:");

            for (RecommendedItem recommendation : recommendations) {

                System.out.println(
                        "Item ID: "
                        + recommendation.getItemID()
                        + " | Predicted Rating: "
                        + recommendation.getValue()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}