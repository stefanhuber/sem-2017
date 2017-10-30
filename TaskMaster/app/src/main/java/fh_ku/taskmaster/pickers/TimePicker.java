package fh_ku.taskmaster.pickers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static String TAG = TimePicker.class.getName();
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        if (getDate() == null) {
            setDate(new Date());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        Log.i(TAG, "Time picked: " + calendar.getTime());
        setDate(calendar.getTime());
    }
}
