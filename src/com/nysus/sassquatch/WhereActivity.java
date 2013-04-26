package com.nysus.sassquatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class WhereActivity extends Activity implements OnItemSelectedListener {
	
	TextView where_date;
	Button where_go;
	String customer_value;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_where);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//start intent which runs between activities
		Intent intent = getIntent();
		//get data from previous screen
		final String user = intent.getStringExtra(WhenActivity.USERNAME);
		final String date = intent.getStringExtra(WhenActivity.SELDATE);
		
		where_date = (TextView) findViewById(R.id.where_date);
		where_date.setText(user + " on " + date);
		
		where_go = (Button) findViewById(R.id.where_go);
		where_go.setEnabled(false);
		where_go.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            	Spinner spin = (Spinner) findViewById(R.id.customer_spinner);
                
            	customer_value = spin.getSelectedItem().toString();
                
            	String rr = "http://www.nysus.net/sassquatch/sassy.php";
            	String queryString = "?operator=" + user + "&customer=" + customer_value + "&date=" + date;
            	
            	rr = rr + queryString.replaceAll(" ", "%20");
            	
            	URL u = null;
				
				try {
					u = new URL(rr);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					u.openStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					
					new AlertDialog.Builder(WhereActivity.this)
				    .setTitle("Success!")
				    .setMessage("I cannot believe you found the Sassquatch!")
				    .setPositiveButton("I'm that great", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				        	Intent intent = new Intent(WhereActivity.this, MainActivity.class);
			         		startActivity(intent);
				        }
				     })
				     .show();
				}
            }
        });
		
		String readFeed = readFeed();

	    // you can use this array to find the school ID based on name
	    ArrayList<Customer> customers = new ArrayList<Customer>();
	    // you can use this array to populate your spinner
	    ArrayList<String> customer_name = new ArrayList<String>();

	    try {
	      JSONArray jsonArray = new JSONArray(readFeed);
	      
	      for (int i = 0; i < jsonArray.length(); i++) {
	    	  
	        JSONObject jsonObject = jsonArray.getJSONObject(i);

	        Customer customer = new Customer(jsonObject.optString("ID"), jsonObject.optString("client_name"));
	        customers.add(customer);
	        customer_name.add(jsonObject.optString("client_name"));
	       
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		
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
	
	public String readFeed() {
	    StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();

	    // domain intentionally obfuscated for security reasons
	    HttpGet httpGet = new HttpGet("http://www.nysus.net/sassquatch/sassy.php?action=get_customers");
	    try {
	      HttpResponse response = client.execute(httpGet);
	      StatusLine statusLine = response.getStatusLine();
	      int statusCode = statusLine.getStatusCode();
	      if (statusCode == 200) {
	        HttpEntity entity = response.getEntity();
	        InputStream content = entity.getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	        String line;
	        while ((line = reader.readLine()) != null) {
	          builder.append(line);
	        }
	      } else {
	        Log.e(MainActivity.class.toString(), "Failed to download file");
	      }
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return builder.toString();
	  }

}
