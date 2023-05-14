package com.example.train_schedule.model.objects;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Класс, представляющий маршрут.
 */
public class Route implements Serializable {
    private String name;//Название маршрута.
    private List<RouteNode> routeNodes = new ArrayList<>();//Список узлов маршрута.

    /**
     * Создает маршрут с заданным названием и узлами маршрута.
     *
     * @param routeName название маршрута
     * @param nodes     список узлов маршрута
     */
    public Route(String routeName, List<RouteNode> nodes) {
        name = routeName;
        routeNodes = nodes;
    }

    /**
     * Создает пустой маршрут.
     */
    public Route() {
    }

    /**
     * Добавляет узел маршрута в список узлов маршрута.
     * Если количество узлов больше или равно 2, устанавливает имя маршрута.
     *
     * @param routeNode узел маршрута
     */
    public void addNode(RouteNode routeNode) {
        if (routeNodes.size() >= 2) setName();
        routeNodes.add(routeNode);
    }

    /**
     * Возвращает название маршрута.
     * Если название не установлено, возвращает строку "Не установлено".
     *
     * @return название маршрута
     */
    public String getName() {
        return (name == null) ? "Не установлено" : name;
    }

    /**
     * Возвращает время прибытия на последний узел маршрута.
     * Если список узлов маршрута пуст, возвращает null.
     *
     * @return время прибытия на последний узел маршрута
     */
    public Date getArrivalTime() {
        if (routeNodes.size() == 0) return null;
        int index = routeNodes.size() - 1;
        return routeNodes.get(index).getArrivalTime();
    }

    /**
     * Возвращает время отправления с первого узла маршрута.
     * Если список узлов маршрута пуст, возвращает null.
     *
     * @return время отправления с первого узла маршрута
     */
    public Date getDepartureTime() {
        if (routeNodes.size() == 0) return null;
        return routeNodes.get(0).getDepartureTime();
    }

    /**
     * Возвращает время отправления с первого узла маршрута в виде строки в формате "HH:mm dd.MM.yyyy".
     * Если время отправления не установлено, возвращает "-".
     *
     * @return время отправления с первого узла маршрута в виде строки
     */
    public String getDepartureTimeS() {
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.ROOT);
        Date time_departure = getDepartureTime();
        return time_departure == null ? "-" : dt.format(time_departure);
    }

    /**
     * Возвращает время прибытия в формате строки.
     *
     * @return строка, содержащая время прибытия в формате "HH:mm dd.MM.yyyy",
     * если время прибытия задано; если время прибытия не задано, возвращает "-".
     */
    public String getArrivalTimeS() {
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.ROOT);
        Date time_arrival = getArrivalTime();
        return time_arrival == null ? "-" : dt.format(time_arrival);
    }

    /**
     * Возвращает общее время в пути.
     *
     * @return строка, содержащая общее время в пути в формате "x.xx ч.",
     * если время отправления и прибытия заданы; если хотя бы одно из них не задано, возвращает "-".
     */
    public String getAllTime() {
        if (getDepartureTime() == null || getArrivalTime() == null) return "-";
        long time_out = getDepartureTime().getTime();
        long time_in = getArrivalTime().getTime();
        double time_on_move = (time_in - time_out) / 3600000.0;
        return String.format(Locale.ROOT, "%.2f ч.", time_on_move);
    }

    /**
     * Устанавливает название маршрута на основе имени начальной и конечной станций.
     */
    public void setName() {
        if (routeNodes.size() >= 2) {
            String name_start = routeNodes.get(0).getStation().getName();
            String name_end = routeNodes.get(routeNodes.size() - 1).getStation().getName();
            name = name_start + " - " + name_end;
        }
    }

    /**
     * Возвращает список узлов маршрута.
     *
     * @return список узлов маршрута.
     */
    public List<RouteNode> getNodes() {
        return routeNodes;
    }

    /**
     * Заменяет заданный узел маршрута на новый узел.
     *
     * @param routeNode    узел маршрута, который необходимо заменить.
     * @param newRouteNode новый узел маршрута.
     */
    public void replace(RouteNode routeNode, RouteNode newRouteNode) {
        int index = routeNodes.indexOf(routeNode);
        if (index != -1) {
            routeNodes.remove(index);
            routeNodes.add(index, newRouteNode);
        }
    }

    /**
     * Копирует данные о маршруте из заданного маршрута.
     *
     * @param route маршрут, из которого необходимо скопировать данные.
     */
    public void clone(Route route) {
        name = route.name;
        routeNodes = new ArrayList<>(route.routeNodes.size());
        for (RouteNode node : route.routeNodes)
            routeNodes.add(new RouteNode(node));
    }

    /**
     * Возвращает строковое представление маршрута
     */
    @NonNull
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(name);
        res.append("|");
        for (RouteNode rn : routeNodes) {
            res.append(rn.toString());
            res.append("|");
        }
        res.deleteCharAt(res.length() - 1);
        res.append(";");
        return res.toString();
    }
}
