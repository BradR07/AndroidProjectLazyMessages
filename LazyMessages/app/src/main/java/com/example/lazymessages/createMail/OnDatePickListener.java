package com.example.lazymessages.createMail;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

public class OnDatePickListener {

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
           /* myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Date datePicked = new Date(year,monthOfYear,dayOfMonth);

            String myFormat = "dd/MM/yy"; //In which you need put here
            SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.FRANCE);
            mBirthDate.setText(dateFormat.format(datePicked));*/
        }
    };
}