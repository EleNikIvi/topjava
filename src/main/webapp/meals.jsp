<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
        .wrapper{
            display: flex;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Подсчет калорий</a></h3>
    <h2>Моя еда</h2>
    <form action="meals?action=filter">
        <input type="hidden" name="action" value="filter">
        <div class="wrapper">
            <div>От даты</div>
            <div><input type="date" name="fromDate" id="fromDate"></div>
            <div>От времени</div>
            <div><input type="time" id="fromTime"></div>
        </div>
        <div class="wrapper">
            <div>До даты</div>
            <div><input type="date" id="toDate"></div>
            <div>До времени</div>
            <div><input type="time" id="toTime"></div>
        </div>
        <button type="submit">Search</button>
    </form>
    <a href="meals?action=create">Добавить</a>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
