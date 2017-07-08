package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> filteredMealWithExceedList = new ArrayList<>();
        for (int x = 0; x < mealList.size(); ) {
            final int a = x;
            long count = mealList.stream()
                    .filter(s -> s.getDateTime().toLocalDate().isEqual(mealList.get(a).getDateTime().toLocalDate()))
                    .count();
            if (count == 0) {
                ++x;
                continue;
            }
            int allCalories = mealList.stream().skip(a).limit(count)
                    .map(UserMeal::getCalories)
                    .reduce((s1, s2) -> s1 + s2)
                    .get();
            final boolean exceeded = allCalories > caloriesPerDay;
            mealList.stream().skip(a).limit(count)
                    .filter(s -> s.getDateTime().toLocalTime().isAfter(startTime) && s.getDateTime().toLocalTime().isBefore(endTime))
                    .forEach(s -> filteredMealWithExceedList.add(new UserMealWithExceed(s.getDateTime(),
                            s.getDescription(), s.getCalories(), exceeded)));
            x += count;
        }
        return filteredMealWithExceedList;
    }



}
