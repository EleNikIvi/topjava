package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;
    @Autowired
    ProfileRestController userController;

    public void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId = userController.get().getId();
        Integer mealId = "create".equals(action) ? null : getId(request);
        Meal meal = service.get(userId, mealId);
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/meal.jsp").forward(request, response);
    }

    public void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        Integer id = "create".equals(action) ? null : getId(request);;
        int userId = userController.get().getId();
        Meal meal = new Meal(id, LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        service.save(userId, meal);
        response.sendRedirect("meals");
    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("getAll");
        int userId = userController.get().getId();
        request.setAttribute("meals",
                MealsUtil.getWithExceeded(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int mealId = getId(request);
        int userId = userController.get().getId();
        log.info("Delete {}", mealId);
        service.delete(userId, mealId);
        response.sendRedirect("meals");
    }

    public void getAllByFilter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String fromTime = request.getParameter("fromTime");
        String toTime = request.getParameter("toTime");

        response.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}