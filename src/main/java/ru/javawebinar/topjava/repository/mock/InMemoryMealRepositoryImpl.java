package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        System.out.println("save " + meal.getId());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        Map<Integer, Meal> allUserMeals = repository.putIfAbsent(AuthorizedUser.id(), new ConcurrentHashMap<>());
        System.out.println("save " + meal.getDescription() + " " + allUserMeals);
        if (allUserMeals == null) {
            allUserMeals = repository.get(AuthorizedUser.id());
        }
        allUserMeals.merge(meal.getId(), meal, (mealOld, mealNew) -> mealNew);
        System.out.println(allUserMeals);
        repository.put(AuthorizedUser.id(), allUserMeals);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        if (repository.containsKey(AuthorizedUser.id())) {
            Map<Integer, Meal> userMealMap = repository.get(AuthorizedUser.id());
            if (userMealMap.containsKey(id)) {
                userMealMap.remove(id);
                return true;
            }
        }
        return false;
    }

    @Override
    public Meal get(int id) {
        if (repository.containsKey(AuthorizedUser.id())) {
            Map<Integer, Meal> userMeal = repository.get(AuthorizedUser.id());
            if (userMeal.containsKey(id))
                return userMeal.get(id);
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.get(AuthorizedUser.id()).values();
    }
}
