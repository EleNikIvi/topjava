package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private static AtomicInteger counter = new AtomicInteger();

    {
        MealsUtil.MEALS.forEach(m -> save(1, m));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        System.out.println("save " + meal.getId());
        System.out.println(counter);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        System.out.println("save " + meal.getId());
        Map<Integer, Meal> allUserMeals = repository.putIfAbsent(userId, new ConcurrentHashMap<>());
        System.out.println("save " + meal.getDescription() + " " + allUserMeals);
        if (allUserMeals == null) {
            allUserMeals = repository.get(userId);
        }
        Meal mergeMeal = allUserMeals.merge(meal.getId(), meal, (mealOld, mealNew) -> mealNew);
        System.out.println(allUserMeals);
        System.out.println(repository.keySet());
        repository.put(userId, allUserMeals);
        return mergeMeal;
    }

    @Override
    public boolean delete(int userId, int id) throws NullPointerException {
        repository.get(userId).remove(id);
        return true;
    }

    @Override
    public Meal get(int userId, int id) throws NullPointerException{
        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) throws NullPointerException{
        return repository.get(userId).values();
    }
}
