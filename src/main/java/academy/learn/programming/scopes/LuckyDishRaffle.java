package academy.learn.programming.scopes;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Random;

@Named
public class LuckyDishRaffle {

    private final List<String> luckyDishes;

    @Inject
    public LuckyDishRaffle(List<String> luckyDishes) {
        this.luckyDishes = luckyDishes;
    }

    private final Random random = new Random();

    public String getYourLuckyDish() {
        return luckyDishes.get(random.nextInt(8));
    }
}
