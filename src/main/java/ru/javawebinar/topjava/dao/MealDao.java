package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MealDao {
    private Connection connection;

    public MealDao() {
        this.connection = DBUtil.getConnection();
    }

    public void addMeal(Meal meal){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into meals(dateTime, description, calories) VALUES (?, ?, ?)");
            preparedStatement.setString(1, meal.getDateTime().toString());
            preparedStatement.setString(2, meal.getDescription());
            preparedStatement.setString(3, String.valueOf(meal.getCalories()));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
