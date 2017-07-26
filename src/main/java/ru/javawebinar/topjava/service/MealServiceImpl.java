package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(int userId, Meal meal) {
        try {
            return repository.save(userId, meal);
        } catch (NullPointerException e){
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public boolean delete(int userId, int mealId) {
        try {
            return repository.delete(userId, mealId);
        } catch (NullPointerException e){
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public Meal get(int userId, Integer mealId) {
        try {
            return mealId == null ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                    repository.get(userId, mealId);
        } catch (NullPointerException e){
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        try {
            return repository.getAll(userId);
        } catch (NullPointerException e){
            throw new NotFoundException(e.getMessage());
        }
    }
}