package com.example.train_schedule.model.server;

import android.content.Context;

import com.example.train_schedule.model.objects.Route;
import com.example.train_schedule.model.objects.RouteNode;
import com.example.train_schedule.model.objects.Station;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class DataManager {
    private static final ArrayList<Station> stations = new ArrayList<>();
    private static final ArrayList<Route> routes = new ArrayList<>();

    public static List<Station> getStations() {
        return stations;
    }

    public static List<Route> getRoutes() {
        return routes;
    }

    public static void loadData(Context context) {
        loadStation();
        loadRoutes(context);
    }

    public static void addRoutes(Route route) {
        routes.add(route);
    }


    private static void loadStation() {
        stations.add(new Station("Новозаводская станция"));
        stations.add(new Station("Красный рассвет"));
        stations.add(new Station("Химки"));
        stations.add(new Station("Казанский вокзал"));
        stations.add(new Station("Ленинградский вокзал"));
        stations.add(new Station("Киевский вокзал"));
    }

    public static Station findStation(String name) {
        for (Station st : stations)
            if (Objects.equals(st.getName(), name))
                return st;

        return new Station(name);
    }

    private static void loadRoutes(Context context) {
        try {
            File file = new File(context.getFilesDir(), "routes.txt");
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(";");

            while (scanner.hasNext()) {
                String line = scanner.next().trim();

                if (!line.isEmpty()) {
                    String[] parts = line.split("\\|");

                    String routeName = parts[0].trim();
                    List<RouteNode> nodes = new ArrayList<>();

                    for (int i = 1; i < parts.length; i++) {
                        String nodeParams = parts[i].trim();
                        RouteNode node = new RouteNode(nodeParams);
                        nodes.add(node);
                    }

                    Route route = new Route(routeName, nodes);
                    routes.add(route);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveRoutes(Context context) {
        try {
            // открываем поток для записи в файл
            OutputStream outputStream = context.openFileOutput("routes.txt", Context.MODE_PRIVATE);

            // записываем данные в файл

            for (Route route : routes) {
                String data = route.toString();
                outputStream.write(data.getBytes());
            }

            // закрываем поток
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void replaceRoute(Route route, Route newRoute) {
        int index = routes.indexOf(route);
        if (index != -1) {
            routes.remove(index);
            routes.add(index, newRoute);
        } else {
            routes.add(newRoute);
        }
    }
}
