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
import com.example.train_schedule.main.fragments.RouteNodeFragment;
import com.example.train_schedule.model.objects.Route;
import com.example.train_schedule.model.objects.RouteNode;

import java.util.List;

/**
 Адаптер для отображения списка узлов маршрута в виде RecyclerView.
 */
public class RouteViewAdapter extends RecyclerView.Adapter<RouteViewAdapter.ViewHolder> {
    private final List<RouteNode> mNodesRoute;// Список узлов маршрута
    private final Route mRoute;// Маршрут

    /**
     Конструктор адаптера.
     @param route Маршрут
     */
    public RouteViewAdapter(Route route) {
        mRoute = route;
        mNodesRoute = route.getNodes();
    }

    /**
     Создание нового ViewHolder'а для элемента списка.
     @param parent Родительский View, к которому привязывается элемент списка
     @param viewType Тип View для элемента списка
     @return ViewHolder для элемента списка
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_route_item, parent, false);
        return new ViewHolder(view);
    }


    /**
     Привязка данных к ViewHolder'у элемента списка.
     @param holder ViewHolder элемента списка
     @param position Позиция элемента в списке
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RouteNode routeNode = mNodesRoute.get(position);
        holder.mNameView.setText(routeNode.getName());
        holder.mTimeInView.setText(routeNode.getArrivalTimeS());
        holder.mTimeOutView.setText(routeNode.getDepartureTimeS());
        holder.mTimeView.setText(routeNode.getTimeParking());

        holder.mItem.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("route", mRoute);
            bundle.putSerializable("routeNode", routeNode);
            Fragment fragment = new RouteNodeFragment();
            fragment.setArguments(bundle);
            final FragmentTransaction ft = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment_view, fragment);
            ft.commit();
        });

        holder.mDeleteButton.setOnClickListener(v -> {
            mNodesRoute.remove(routeNode);
            notifyItemRemoved(position);
        });
    }

    /**
     Получение количества элементов в списке.
     @return Количество элементов в списке
     */
    @Override
    public int getItemCount() {
        return mNodesRoute.size();
    }

    /**
     * ViewHolder для элемента списка.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mNameView, mTimeInView, mTimeOutView, mTimeView;
        public final ImageButton mDeleteButton;
        public final ConstraintLayout mItem;

        public ViewHolder(View view) {
            super(view);
            mNameView = view.findViewById(R.id.route_item_txt_name);
            mTimeInView = view.findViewById(R.id.route_item_txt_time_in);
            mTimeOutView = view.findViewById(R.id.route_item_txt_time_out);
            mTimeView = view.findViewById(R.id.route_item_txt_time);
            mItem = view.findViewById(R.id.route_item);
            mDeleteButton = view.findViewById(R.id.route_item_btn_delete);
        }
    }
}