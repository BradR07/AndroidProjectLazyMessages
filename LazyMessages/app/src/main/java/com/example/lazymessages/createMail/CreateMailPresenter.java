package com.example.lazymessages.createMail;

import android.content.Context;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CreateMailPresenter {

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    protected void setRecurringAlarm2(Context context, String day, String month, String year, String hour, String minute) {
        Calendar currentDate = Calendar.getInstance();
        Calendar dueDate = Calendar.getInstance();

        dueDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        dueDate.set(Calendar.MONTH, Integer.parseInt(month));
        dueDate.set(Calendar.YEAR, Integer.parseInt(year));
        dueDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        dueDate.set(Calendar.MINUTE, Integer.parseInt(minute));
        dueDate.set(Calendar.SECOND, 0);

        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24);
        }
        long timeDiff = dueDate.getTimeInMillis() - currentDate.getTimeInMillis();
        WorkRequest dailyRemindCollectRequest = new OneTimeWorkRequest.Builder(RemindCollecteWorker.class)
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                .build();
        WorkManager.getInstance(context).enqueue(dailyRemindCollectRequest);
    }
}