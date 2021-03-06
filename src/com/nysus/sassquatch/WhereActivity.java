package com.nysus.sassquatch;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class WhereActivity extends Activity implements OnItemSelectedListener, asyncOnComplete<JSONArray> {
	
	TextView where_date;
	Button where_go;
	String customer_value;
	EditText inp_customer;
	EditText inp_mileage;
	static BackGroundTask jso;
	static BackGroundTask jso_submit;
	asyncOnComplete<JSONArray> that = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_where);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//start intent which runs between activities
		Intent intent = getIntent();
		//get data from previous screen
		final String user = User.getName();
		final String date = intent.getStringExtra(WhenActivity.SELDATE);
		
		//input text for customer and mileage
		inp_customer = (EditText) findViewById(R.id.where_edit_customer);
		inp_mileage = (EditText) findViewById(R.id.where_edit_mileage);
		
		//showing some text
		where_date = (TextView) findViewById(R.id.where_date);
		where_date.setText(user + " on " + date);
		
		//Save button
		where_go = (Button) findViewById(R.id.where_go);
		where_go.setEnabled(false);
		where_go.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            	Spinner spin = (Spinner) findViewById(R.id.customer_spinner);
                
            	//Get value of drop down menu selections
            	customer_value = spin.getSelectedItem().toString();
            	
            	//querystring parameters to be sent with the URL
            	List<NameValuePair> params = new ArrayList<NameValuePair>();
        		params.add(new BasicNameValuePair("action", "submit_entry"));
        		params.add(new BasicNameValuePair("operator", user));
        		params.add(new BasicNameValuePair("date", date));
        		
        		//logic for figuring out if mileage has any input
        		if (inp_mileage.getText().toString().equals("")) {
        			params.add(new BasicNameValuePair("mileage", "0"));
        		} else {
        			params.add(new BasicNameValuePair("mileage", inp_mileage.getText().toString()));
        		}
        		
        		//logic for figuring out if a customer has been selected or inputed manually
        		if (inp_customer.getText().toString().equals("")) {
        			params.add(new BasicNameValuePair("customer", customer_value));
        		} else {
        			params.add(new BasicNameValuePair("customer", inp_customer.getText().toString()));
        		}
        		
        		//Async class
        		jso_submit = new BackGroundTask(WhereActivity.this, that, "http://www.nysus.net/sassquatch/sassy.php", "GET", params);
        		//Executes the async thread
        		WhereActivity.jso_submit.execute();
        		
        		//Dialog when the async server call is finished
				new AlertDialog.Builder(WhereActivity.this)
			    .setTitle("Success!")
			    .setMessage("I cannot believe you found the Sassquatch!")
			    .setPositiveButton("I'm that great", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	Intent intent = new Intent(WhereActivity.this, WhenActivity.class);
		         		startActivity(intent);
			        }
			     })
			     .show();

            }
        });
		
		//---
		//ASYNC SERVER CALL
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "get_customers"));
		
		//async class
		jso = new BackGroundTask(WhereActivity.this, that, "http://www.nysus.net/sassquatch/sassy.php", "GET", params);
		//activates doInBackground for BackGroundTask
		//then after the async task is done, call onComplete()
		WhereActivity.jso.execute();
		
	}
	
	//callback for BackGroundTask async call
	public void onComplete(JSONArray jsonArray) {

	    ArrayList<Customer> customers = new ArrayList<Customer>();
	    // you can use this array to populate your spinner
	    ArrayList<String> customer_name = new ArrayList<String>();

	    try {
	      
	      for (int i = 0; i < jsonArray.length(); i++) {
	    	  
	        JSONObject jsonObject = jsonArray.getJSONObject(i);

	        Customer customer = new Customer(jsonObject.optString("ID"), jsonObject.optString("client_name"));
	        customers.add(customer);
	        customer_name.add(jsonObject.optString("client_name"));
	       
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		
	    //drop down menu
		Spinner spinner = (Spinner) findViewById(R.id.customer_spinner);
		spinner.setOnItemSelectedListener(this);
		spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, customer_name));
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
	
	//drop down menu event handler
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		// An item was selected. You can retrieve the selected item using
		// parent.getItemAtPosition(pos)
		String item = parent.getItemAtPosition(pos).toString();
		
		if (item.equals("None")) {
			where_go.setEnabled(false);
		} else {
			where_go.setEnabled(true);
		}
	}
	
	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}

}
