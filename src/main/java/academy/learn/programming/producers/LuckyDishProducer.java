package academy.learn.programming.producers;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import java.util.List;

public class LuckyDishProducer {

    @Produces
    public List<String> getLuckyDish() {
        System.err.println("LuckyDishProducer::getLuckyDish");
        return List.of(
                "Ampesi",
                "Tuo Zaafi",
                "Banku",
                "Fufu",
                "Red Red",
                "Gari Foto",
                "Ebunu Ebunu",
                "Fante Fante",
                "Mpotompoto");
    }

    public void dispose(@Disposes List<String> dishes) {
        System.err.println("LuckyDishProducer::dispose");
        dishes = null;
    }
}
