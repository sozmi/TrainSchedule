package com.example.train_schedule.main.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.train_schedule.R;
import com.example.train_schedule.main.fragments.RouteFragment;
import com.example.train_schedule.model.objects.Route;

import java.util.List;


/**
 * Адаптер для отображения списка маршрутов в виде RecyclerView.
 */
public class ScheduleViewAdapter extends RecyclerView.Adapter<ScheduleViewAdapter.ViewHolder> {
    private final List<Route> mRoutes; //список маршрутов

    /**
     * Конструктор адаптера.
     *
     * @param items Список маршрутов
     */
    public ScheduleViewAdapter(List<Route> items) {
        mRoutes = items;
    }

    /**
     * Создание нового ViewHolder'а для элемента списка.
     *
     * @param parent   Родительский View, к которому привязывается элемент списка
     * @param viewType Тип View для элемента списка
     * @return ViewHolder для элемента списка
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_schedule_item, parent, false);
        return new ScheduleViewAdapter.ViewHolder(view);
    }

    /**
     * Привязка данных к ViewHolder'у элемента списка.
     *
     * @param holder   ViewHolder элемента списка
     * @param position Позиция элемента в списке
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Route route = mRoutes.get(position);
        holder.mNameView.setText(route.getName());
        holder.mTimeArrivalView.setText(route.getArrivalTimeS());
        holder.mTimeDepartureView.setText(route.getDepartureTimeS());
        holder.mTimeView.setText(route.getAllTime());

        holder.mItem.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("route", route);
            Fragment fragment = new RouteFragment();
            fragment.setArguments(bundle);
            final FragmentTransaction ft = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment_view, fragment);
            ft.commit();
        });
        holder.mDeleteButton.setOnClickListener(v -> {
            mRoutes.remove(route);
            notifyItemRemoved(position);
        });
    }

    /**
     * Получение количества элементов в списке.
     *
     * @return Количество элементов в списке
     */
    @Override
    public int getItemCount() {
        return mRoutes.size();
    }

    /**
     * ViewHolder для элемента списка.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mNameView, mTimeArrivalView, mTimeDepartureView, mTimeView;
        public final ImageButton mDeleteButton;
        public final ConstraintLayout mItem;

        public ViewHolder(View view) {
            super(view);
            mNameView = view.findViewById(R.id.schedule_item_txt_name);
            mTimeArrivalView = view.findViewById(R.id.schedule_item_txt_time_arrival);
            mTimeDepartureView = view.findViewById(R.id.schedule_item_txt_time_departure);
            mTimeView = view.findViewById(R.id.schedule_item_txt_time);
            mItem = view.findViewById(R.id.schedule_item);
            mDeleteButton = view.findViewById(R.id.schedule_item_btn_delete);
        }
    }
}