package com.example.train_schedule.model.server;

import android.text.Editable;
import android.text.TextWatcher;

/**

 Класс CustomTextWatcher реализует интерфейс TextWatcher для отслеживания изменений текста.
 */
public class CustomTextWatcher implements TextWatcher {
    private final UpdateTimeListener updateTimeListener;
    private final SetTimeListener setTimeListener;
    /**
     Конструктор класса CustomTextWatcher.
     @param setTimeListener объект, реализующий интерфейс SetTimeListener для установки времени.
     @param updateTimeListener объект, реализующий интерфейс UpdateTimeListener для обновления времени.
     */
    public CustomTextWatcher(SetTimeListener setTimeListener, UpdateTimeListener updateTimeListener) {

        this.updateTimeListener = updateTimeListener;
        this.setTimeListener = setTimeListener;
    }

    @Override
    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
        String s = cs.toString().replace("_", "");
        if (s.length() == 16)
        {
            setTimeListener.setTime(s);
            updateTimeListener.onUpdate(); // вызов метода onUpdate()
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
    }

    @Override
    public void afterTextChanged(Editable arg0) {
    }
}