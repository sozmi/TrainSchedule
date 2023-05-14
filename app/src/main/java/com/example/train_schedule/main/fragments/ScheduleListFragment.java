package com.example.train_schedule.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.train_schedule.R;
import com.example.train_schedule.main.adapters.ScheduleViewAdapter;
import com.example.train_schedule.model.server.DataManager;


/**
 * Фрагмент для добавления или редактирования маршрутов.
 */
public class ScheduleListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_list, container, false);

        final RecyclerView mScheduleView = view.findViewById(R.id.schedule_list_station);
        final Button btnAdd = view.findViewById(R.id.schedule_btn_add);

        ScheduleViewAdapter childItemAdapter = new ScheduleViewAdapter(DataManager.getRoutes());
        mScheduleView.setAdapter(childItemAdapter);
        btnAdd.setOnClickListener(v -> onClickButtonAdd());

        return view;
    }

    /**
     * Обработчик нажатия кнопки "Сохранить".
     */
    private void onClickButtonAdd() {
        final FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_fragment_view, new RouteFragment());
        ft.commit();
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getName();
    }
}