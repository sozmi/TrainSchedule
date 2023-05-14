package com.example.train_schedule.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.train_schedule.R;
import com.example.train_schedule.model.objects.Route;
import com.example.train_schedule.model.objects.RouteNode;
import com.example.train_schedule.model.objects.Station;
import com.example.train_schedule.model.server.CustomTextWatcher;
import com.example.train_schedule.model.server.DataManager;
import com.santalu.maskara.widget.MaskEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Фрагмент для добавления или редактирования узла маршрута.
 */
public class RouteNodeFragment extends Fragment {

    private TextView time; // текстовое поле для отображения времени стоянки
    private Spinner station; // выпадающий список со станциями
    private final RouteNode newRouteNode = new RouteNode(); // новый узел маршрута, создаваемый при редактировании/создании узла
    private RouteNode routeNode; // Исходный узел маршрута, переданный в аргументах фрагмента.
    private Route route; // маршрут, к которому относится узел
    private boolean isAdd = false, isErrorArr = false, isErrorDep = false; // флаги для проверки ошибок

    /**
     * Создание представления фрагмента.
     *
     * @return View, представляющее фрагмент.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // создание представления фрагмента
        View view = inflater.inflate(R.layout.fragment_route_node, container, false);

        // получение ссылок на элементы представления
        final Button btnSave = view.findViewById(R.id.route_node_btn_save);
        final Button btnCancel = view.findViewById(R.id.route_node_btn_cancel);
        final MaskEditText timeout = view.findViewById(R.id.route_node_tbx_time_out);
        final MaskEditText timein = view.findViewById(R.id.route_node_time_in);
        station = view.findViewById(R.id.route_node_station);
        time = view.findViewById(R.id.route_node_time);

        // создание адаптера для выпадающего списка
        ArrayAdapter<Station> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, DataManager.getStations());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        station.setAdapter(adapter);

        // получение параметров фрагмента
        Bundle bundle = getArguments();
        if (bundle != null) {
            route = (Route) bundle.getSerializable("route");
            routeNode = (RouteNode) bundle.getSerializable("routeNode");
            if (routeNode != null) {
                timeout.setText(routeNode.getDepartureTimeS());
                timein.setText(routeNode.getArrivalTimeS());
                time.setText(routeNode.getTimeParking());
                newRouteNode.clone(routeNode);
            } else {
                isAdd = true;// узел маршрута добавляется, а не редактируется
            }
        }

        // установка обработчиков событий
        btnSave.setOnClickListener(v -> onClickButtonSave());
        btnCancel.setOnClickListener(v -> onClickButtonCancel());
        timeout.addTextChangedListener(new CustomTextWatcher(this::SetTimeDep, this::UpdateTime));
        timein.addTextChangedListener(new CustomTextWatcher(this::SetTimeArr, this::UpdateTime));

        return view;
    }

    /**
     * Обновляет текстовое поле с временем стоянки
     */
    private void UpdateTime() {
        time.setText(newRouteNode.getTimeParking());
    }

    /**
     * Заполнение поля времени отправления
     */
    private void SetTimeDep(String s) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.ROOT);
        df.setLenient(false);
        try {
            newRouteNode.setTimeDeparture(df.parse(s));
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Некорректная дата: \"" + s + "\"", Toast.LENGTH_SHORT).show();
            isErrorDep = true;
            return;
        }
        isErrorDep = false;
    }

    /**
     * Заполнение поля времени отправления
     */
    private void SetTimeArr(String s) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.ROOT);
        df.setLenient(false);
        try {
            newRouteNode.setTimeArrival(df.parse(s));
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Некорректная дата: \"" + s + "\"", Toast.LENGTH_SHORT).show();
            isErrorArr = true;
            return;
        }
        isErrorArr = false;
    }

    /**
     * Обработчик нажатия кнопки "Сохранить".
     */
    private void onClickButtonSave() {
        if (isErrorDep) {
            Toast.makeText(getContext(), "Исправьте дату отправления", Toast.LENGTH_LONG).show();
            return;
        }

        if (isErrorArr) {
            Toast.makeText(getContext(), "Исправьте дату прибытия", Toast.LENGTH_LONG).show();
            return;
        }

        newRouteNode.setStation((Station) station.getSelectedItem());
        if (isAdd) route.addNode(newRouteNode);
        else route.replace(routeNode, newRouteNode);
        route.setName();

        //открытие предыдущего фрагмента
        openRouteFragment();
    }

    /**
     * Обработчик нажатия кнопки "Отмена".
     */
    private void onClickButtonCancel() {
        //открытие предыдущего фрагмента
        openRouteFragment();
    }

    private void openRouteFragment() {
        //открытие предыдущего фрагмента
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putSerializable("route", route);
        Fragment fragment = new RouteFragment();
        fragment.setArguments(bundle);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_fragment_view, fragment);
        ft.commit();
    }
}