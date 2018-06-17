package com.e.multiplelocationmarker.BottomSheet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.multiplelocationmarker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created on 17-06-2018.
 */
public class MapBottomSheet extends BottomSheetDialogFragment {

    private Context mContext;
    private TextView mAddressTv, mTimeTv;
    private static final String mAddressKey = "ADDRESS_KEY";
    private String mAddress;
    private static final String TAG = "myTag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mAddress = bundle.getString(mAddressKey);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.location_track_bottom_sheet_layout, container, false);
        if (view != null) {
            mAddressTv = view.findViewById(R.id.address_tv);
            mTimeTv = view.findViewById(R.id.time_tv);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAddressTv.setText(mAddress);
        String time = showDateAsStr(new Date().getTime(), "IST");
        mTimeTv.setText(time);
    }

    /**
     * Get Date with time from timestamp
     *
     * @param time     -> timestamp
     * @param timeZone -> timeZone
     * @return -> returns String in format of "dd/MM/yyy HH:mm:ss (AM/PM)"
     */

    private String showDateAsStr(long time, String timeZone) {

        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);

        if (cal.get(Calendar.AM_PM) == Calendar.AM) {
            // AM
            if (hour != 0)
                return hour + ":" + minute + " AM";
            else
                return 12 + ":" + minute + " AM";
        } else {
            // PM
            if (hour != 0)
                return hour + ":" + minute + " PM";
            else
                return 12 + ":" + minute + " PM";
        }

    }
}
