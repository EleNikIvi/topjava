package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final List<Meal> MEALS_USER = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static final List<Meal> MEALS_ADMIN = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500)
    );

    public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                    )
    );

}
