package com.nysus.sassquatch;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class WhenActivity extends FragmentActivity {
	
	TextView whenName;
	static TextView whenDatePicked;
	static Button when_next;
	Button when_recent;

	public final static String SELDATE = "com.nysus.sassquatch.SEL_DATE";
	public static String sel_date = null;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_when);
		// Show the Up button in the action bar.
		setupActionBar();
	
		//get data from previous screen
		final String user = User.getName();
		
		//display user name
		whenName = (TextView) findViewById(R.id.when_view);
		whenName.setText(user + ", please select a date to continue");
		
		//get date picked text
		whenDatePicked = (TextView) findViewById(R.id.when_date_picked);
		
		//Calendar class
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		sel_date = (month + 1) + "-" + day + "-" + year;
		whenDatePicked.setText("Date: " + (month + 1) + "-" + day + "-" + year);
		
		//get next button
		when_next = (Button) findViewById(R.id.when_go);
		when_next.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                // Perform action on click
            	//Go to new activity or page
            	Intent where_intent = new Intent(WhenActivity.this, WhereActivity.class);
         		where_intent.putExtra(SELDATE, sel_date);
         		startActivity(where_intent);
             }
         });
		
		//get recent button
		when_recent = (Button) findViewById(R.id.when_recent);
		when_recent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	//Go to new activity or page
            	Intent recent_intent = new Intent(WhenActivity.this, RecentActivity.class);
         		startActivity(recent_intent);
            }
        });
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//Calendar method
	public void showDatePickerDialog(View v) {
	    DatePickerFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	
	//Calendar method
	public static class DatePickerFragment extends android.support.v4.app.DialogFragment
						implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			sel_date = (month + 1) + "-" + day + "-" + year;
			whenDatePicked.setText("Date: " + (month + 1) + "-" + day + "-" + year);
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			sel_date = (month + 1) + "-" + day + "-" + year;
			
			whenDatePicked.setText("Date: " + (month + 1) + "-" + day + "-" + year);
		}
	}

}
