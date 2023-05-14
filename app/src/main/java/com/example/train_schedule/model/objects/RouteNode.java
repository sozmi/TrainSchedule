package com.example.train_schedule.model.objects;

import androidx.annotation.NonNull;

import com.example.train_schedule.model.server.DataManager;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Класс, описывающий узел маршрута. Каждый узел содержит информацию о станции,
 * времени прибытия и времени отправления.
 */
public class RouteNode implements Serializable {
    private Station m_station;// Станция, к которой относится данный узел.
    private Date time_arrival; // Время прибытия на станцию.
    private Date time_departure;// Время отправления со станции.

    /**
     * Создает новый экземпляр класса RouteNode.
     */
    public RouteNode() {
    }

    /**
     * Создает новый экземпляр класса RouteNode на основе уже существующего.
     *
     * @param node узел маршрута, на основе которого создается новый узел
     */
    public RouteNode(RouteNode node) {
        this.m_station = node.m_station;
        this.time_arrival = node.time_arrival;
        this.time_departure = node.time_departure;
    }

    /**
     * Создает новый экземпляр класса RouteNode на основе строки с параметрами.
     *
     * @param nodeParams строка, содержащая параметры узла маршрута в формате "название станции#время прибытия#время отправления"
     */
    public RouteNode(String nodeParams) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.ROOT);

        String[] par = nodeParams.split("#");
        this.m_station = DataManager.findStation(par[0]);
        try {
            if (!Objects.equals(par[1], "-"))
                this.time_arrival = df.parse(par[1]);
            if (!Objects.equals(par[2], "-"))
                this.time_departure = df.parse(par[2]);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает время, на которое запланирована стоянка на станции.
     *
     * @return строка, содержащая время стоянки на станции в формате "чч.мм ч."
     */
    public String getTimeParking() {
        if (time_departure == null || time_arrival == null) return "-";
        double time_parking = (time_departure.getTime() - time_arrival.getTime()) / 3600000.0;
        return String.format(Locale.ROOT, "%.2f ч.", time_parking);
    }

    /**
     * Возвращает объект станции, к которой относится данный узел.
     *
     * @return объект станции
     */
    public Station getStation() {
        return m_station;
    }

    /**
     * Устанавливает объект станции, к которой относится данный узел.
     *
     * @param station_ объект станции
     */
    public void setStation(Station station_) {
        this.m_station = station_;
    }

    /**
     * Устанавливает время прибытия на станцию.
     *
     * @param time_arrival время прибытия на станцию
     */
    public void setTimeArrival(Date time_arrival) {
        this.time_arrival = time_arrival;
    }

    /**
     * Устанавливает время отправления узла маршрута.
     *
     * @param time_departure время отправления
     */
    public void setTimeDeparture(Date time_departure) {
        this.time_departure = time_departure;
    }

    /**
     * Возвращает название станции узла маршрута.
     *
     * @return название станции
     */
    public String getName() {
        return m_station.getName();
    }

    /**
     * Возвращает время отправления узла маршрута.
     *
     * @return время отправления
     */
    public Date getDepartureTime() {
        return time_departure;
    }

    /**
     * Возвращает строковое представление времени отправления узла маршрута в формате "HH:mm dd.MM.yyyy".
     * Если время отправления не задано, возвращает "-".
     *
     * @return строковое представление времени отправления
     */
    public String getDepartureTimeS() {
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.ROOT);
        return time_departure == null ? "-" : dt.format(time_departure);
    }

    /**
     * Возвращает строковое представление времени прибытия узла маршрута в формате "HH:mm dd.MM.yyyy".
     * Если время прибытия не задано, возвращает "-".
     *
     * @return строковое представление времени прибытия
     */
    public String getArrivalTimeS() {
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.ROOT);
        return time_arrival == null ? "-" : dt.format(time_arrival);
    }

    /**
     * Возвращает время прибытия узла маршрута.
     *
     * @return время прибытия
     */
    public Date getArrivalTime() {
        return time_arrival;
    }

    /**
     * Копирует данные из другого узла маршрута.
     *
     * @param routeNode узел маршрута, из которого нужно скопировать данные
     */
    public void clone(RouteNode routeNode) {
        m_station = routeNode.m_station;
        time_arrival = routeNode.time_arrival;
        time_departure = routeNode.time_departure;
    }

    /**
     * Возвращает строковое представление узла маршрута в формате "название станции#время прибытия#время отправления".
     * Если время прибытия или отправления не задано, возвращает "-" в соответствующем поле.
     *
     * @return строковое представление узла маршрута
     */
    @NonNull
    @Override
    public String toString() {
        return m_station.getName() + "#" + getArrivalTimeS() + "#" + getDepartureTimeS();
    }
}
