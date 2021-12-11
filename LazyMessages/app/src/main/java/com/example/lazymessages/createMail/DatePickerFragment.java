package com.example.lazymessages.createMail;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {
    private Context context ;
    private TimePickerDialog TP;
    private String mDay = "";
    private String mMonth = "";
    private String mYear = "";
    private String mHour = "";
    private String mMinute = "";
    private OnReturnDateInformation mCallback ;

    @Override
    public void onAttach(Context context ) {
        super.onAttach(context);
        this.context = context;
    }

    public DatePickerFragment(OnReturnDateInformation mCallback) {
        this.mCallback = mCallback;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);
        // Créer une nouvelle instance de DatePickerDialog et l'a renvoi
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        TimePickerDialog.OnTimeSetListener onTimeSetListener;
        TP = new TimePickerDialog(this.context, this ,hour , min, true);
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.mDay = String.valueOf(dayOfMonth);
        this.mMonth = String.valueOf(month);
        this.mYear = String.valueOf(year);
        TP.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.mHour = String.valueOf(hourOfDay);
        this.mMinute = String.valueOf(minute);
        this.mCallback.onDateChosen(mDay,mMonth,mYear,mHour,mMinute);
    }
}