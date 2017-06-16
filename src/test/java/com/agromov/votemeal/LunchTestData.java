package com.agromov.votemeal;

import com.agromov.votemeal.matchers.ModelMatcher;
import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.util.LunchBuilder;

import java.util.Objects;

import static com.agromov.votemeal.RestaurantTestData.*;

/**
 * Created by A.Gromov on 31.05.2017.
 */
public class LunchTestData
{
    public static final ModelMatcher<Lunch> MATCHER = ModelMatcher.of(Lunch.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.isEnabled(), actual.isEnabled())
                    )
    );

    public static final int NO_RESULT = 0;

    public static final Lunch GRILLE_GURME = new LunchBuilder()
            .withId(10)
            .withName("Гриль Гурмэ")
            .withPrice(150)
            .withDescription("Рубленая говядина с добавлением свинины, обжаренная на гриле. Острый перец. Маринованые огурцы. Хрустящая булочка")
            .withRestaurant(MCDONALDS)
            .build();

    public static final Lunch CHEESEBURGER = new LunchBuilder()
            .withId(11)
            .withName("Чизбургер Фреш")
            .withPrice(120)
            .withDescription("Сочная котлета из 100% говядины, сыр со вкусом бекона и перца. Ломтик свежего помидора салат и острый соус. Макчикен на регулярной булочке")
            .withRestaurant(MCDONALDS)
            .build();

    public static final Lunch SMOKED_POTATO = new LunchBuilder()
            .withId(12)
            .withName("Картошка с копченостями")
            .withPrice(125)
            .withDescription("Энергерическая ценность на 100г: 116,16 Ккал")
            .withRestaurant(POTATO)
            .build();

    public static final Lunch OKROSHKA = new LunchBuilder()
            .withId(13)
            .withName("Окрошка на квасе")
            .withPrice(170)
            .withDescription("Энергерическая ценность на 100г: 48 Ккал")
            .withRestaurant(POTATO)
            .build();

    public static final Lunch ESPRESSO = new LunchBuilder()
            .withId(14)
            .withName("Эспрессо 30мл")
            .withPrice(114)
            .withDescription("Подается с бокалом воды")
            .withRestaurant(CHOCO)
            .build();

    public static final Lunch CAPUCHINO = new LunchBuilder()
            .withId(15)
            .withName("Капучиино \"Карамель\" 32мл")
            .withPrice(269)
            .withDescription("С карамельным топпингом и ванильным сиропом")
            .withRestaurant(CHOCO)
            .build();

    public static final Lunch BORSCH = new LunchBuilder()
            .withId(16)
            .withName("Первое")
            .withPrice(100)
            .withDescription("Борщ со сметаной. Подается с чесночными гренками")
            .withRestaurant(BENJAMIN)
            .build();

    public static final Lunch PUREE = new LunchBuilder()
            .withId(17)
            .withName("Второе")
            .withPrice(170)
            .withDescription("Куриная отбивня в кляре. Картофельное пюре")
            .withRestaurant(BENJAMIN)
            .build();

    public static final Lunch ALPEN_SUB = new LunchBuilder()
            .withId(18)
            .withName("Альпийский Саб")
            .withPrice(150)
            .withDescription("Сочные колбаски с пряностями. Листья свежего салата с ломтиками помидоров и огурцов. Свежеиспеченый хлеб")
            .withRestaurant(SUBWAY)
            .build();

    public static final Lunch SUBWAY_CLUB = new LunchBuilder()
            .withId(19)
            .withName("Сабвэй Клаб")
            .withPrice(120)
            .withDescription("Соблазнительное сочетание ростбифа, ветчины и индейки, свежие овощи по Вашему выбору на свежевыпеченном хлебе")
            .withRestaurant(SUBWAY)
            .build();

    public static Lunch getCreated()
    {
        return new LunchBuilder()
                .withName("Burger")
                .withPrice(150)
                .build();
    }

    public static Lunch getUpdated()
    {
        return new LunchBuilder(GRILLE_GURME)
                .withPrice(400)
                .build();
    }
}
