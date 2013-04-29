package com.nysus.sassquatch;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends Activity {
	
	static BackGroundTask jso;
	ArrayList<Operators> operators;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//sets the content view
		setContentView(R.layout.activity_main);
		
		//----
		//ASYNC SERVER CALL
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "get_operators"));
		
		jso = new BackGroundTask("http://www.nysus.net/sassquatch/sassy.php", "GET", params);
	    // you can use this array to find the school ID based on name
		operators = new ArrayList<Operators>();
	    // you can use this array to populate your spinner
	    ArrayList<String> username = new ArrayList<String>();

	    try {
	    	
	    	JSONArray jsonArray = MainActivity.jso.execute().get();
	      
	      for (int i = 0; i < jsonArray.length(); i++) {
	    	  
	        JSONObject jsonObject = jsonArray.getJSONObject(i);

	        Operators operator = new Operators(Integer.parseInt(jsonObject.optString("ID")), jsonObject.optString("name"), jsonObject.optString("email"));
	        operators.add(operator);
	        username.add(jsonObject.optString("name"));
	       
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		
	    //drop down menu
		Spinner spinner = (Spinner) findViewById(R.id.who_operator);
		spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, username));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//go to when page after button has been clicked for user
	public void gotoWhen(View view) {
		
		Spinner spin = (Spinner) findViewById(R.id.who_operator);
		String username = null;
		
		//get selected value as username
    	username = spin.getSelectedItem().toString();
		
    	//find username in arraylist from server and set user information
    	for (Operators o : operators) {
    		if (username.equals(o.getUser())) {
    			User.setName(o.getUser());
    			User.setID(o.getID());
    			User.setEmail(o.getEmail());
    		}
    	}
    	
    	//go to next activity or page
		Intent intent = new Intent(this, WhenActivity.class);
		startActivity(intent);
		
	}

}
