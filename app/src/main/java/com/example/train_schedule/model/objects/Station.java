package com.example.train_schedule.model.objects;

import androidx.annotation.NonNull;

/**
 * Класс Station представляет станцию.
 */
public class Station {
    private final String name; //Имя станции.

    /**
     * Создает новый объект станции с заданным именем.
     *
     * @param name имя станции
     */
    public Station(String name) {
        this.name = name;
    }

    /**
     * Возвращает имя станции.
     *
     * @return имя станции
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает строковое представление станции, равное ее имени.
     *
     * @return строковое представление станции
     */
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
