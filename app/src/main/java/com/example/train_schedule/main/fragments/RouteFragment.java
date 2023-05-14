package com.example.train_schedule.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.train_schedule.R;
import com.example.train_schedule.main.adapters.RouteViewAdapter;
import com.example.train_schedule.model.objects.Route;
import com.example.train_schedule.model.server.DataManager;

import java.util.Objects;

/**
 * Фрагмент, отображающий список станций и позволяющий создавать/редактировать маршрут.
 */
public class RouteFragment extends Fragment {
    RecyclerView mRouteNodeView; // Отображение списка станций на маршруте.
    Route route; // Исходный маршрут, переданный в аргументах фрагмента.
    final Route newRoute = new Route(); // Новый маршрут, создаваемый при редактировании/создании маршрута.
    private boolean isAdd = false; // Флаг, указывающий на то, создается новый маршрут или редактируется существующий.

    /**
     * Создание представления фрагмента.
     *
     * @return View, представляющее фрагмент.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // создание представления фрагмента
        View view = inflater.inflate(R.layout.fragment_route_list, container, false);

        // получение ссылок на элементы представления
        mRouteNodeView = view.findViewById(R.id.route_list_station);
        final TextView name = view.findViewById(R.id.route_list_name);
        final TextView time = view.findViewById(R.id.route_list_time);
        final Button btnSave = view.findViewById(R.id.route_list_btn_save);
        final Button btnCancel = view.findViewById(R.id.route_list_btn_cancel);
        final Button btnAddNode = view.findViewById(R.id.route_list_btn_add_node);


        Bundle bundle = getArguments();
        if (bundle != null) {
            route = (Route) bundle.getSerializable("route");
            newRoute.clone(route);
            name.setText(newRoute.getName());
            time.setText(newRoute.getAllTime());
            RouteViewAdapter childItemAdapter = new RouteViewAdapter(newRoute);
            mRouteNodeView.setAdapter(childItemAdapter);

        } else {
            route = new Route();
            isAdd = true;
        }

        // установка обработчиков событий
        btnSave.setOnClickListener(v -> onClickButtonSave());
        btnAddNode.setOnClickListener(v -> onClickButtonAddNode());
        btnCancel.setOnClickListener(v -> onClickButtonCancel());

        return view;
    }

    /**
     * Обработчик нажатия кнопки "Сохранить маршрут".
     */
    private void onClickButtonSave() {
        if (mRouteNodeView.getAdapter() == null || Objects.requireNonNull(mRouteNodeView.getAdapter()).getItemCount() < 2) {
            Toast.makeText(getActivity(), "Ошибка. Маршрут должен состоять хотя бы из двух станций", Toast.LENGTH_SHORT).show();
            return;
        }
        newRoute.setName();
        if (isAdd) DataManager.addRoutes(newRoute);
        else DataManager.replaceRoute(route, newRoute);
        final FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_fragment_view, new ScheduleListFragment());
        ft.commit();
    }

    /**
     * Обработчик нажатия кнопки "Добавить станцию на маршрут".
     */
    private void onClickButtonAddNode() {

        final FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("route", newRoute);
        Fragment fragment = new RouteNodeFragment();
        fragment.setArguments(bundle);
        ft.replace(R.id.main_fragment_view, fragment);
        ft.commit();
    }

    /**
     * Обработчик нажатия кнопки "Отмена".
     */
    private void onClickButtonCancel() {
        final FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        Fragment fragment = new ScheduleListFragment();
        ft.replace(R.id.main_fragment_view, fragment);
        ft.commit();
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getName();
    }
}