package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealService {

    Meal save(int userId, Meal meal);

    boolean delete(int userId, int mealId);

    Meal get(int userId, Integer mealId);

    Collection<Meal> getAll(int userId);
}