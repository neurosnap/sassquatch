package com.nysus.sassquatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RecentActivity extends ListActivity {
	
	TextView recent_view;
	ListView list;
	static BackGroundTask jso;
	JSONArray jsonArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recent);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//start intent which runs between activities
		//Intent intent = getIntent();
		//get data from previous screen
		//final String user = intent.getStringExtra(WhenActivity.USERNAME);
		final String user = User.getName();
		recent_view = (TextView) findViewById(R.id.recent_view);
		recent_view.setText(user);
		
		//list = (ListView) findViewById(R.id.list);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "get_recent"));
		params.add(new BasicNameValuePair("user", user));
		
		jso = new BackGroundTask("http://www.nysus.net/sassquatch/sassy.php", "GET", params);
		
		try {
			jsonArray = RecentActivity.jso.execute().get();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<String> row = new ArrayList<String>();
		
		for (int i = 0; i < jsonArray.length(); i++) {
			
			try {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				row.add(jsonObject.optString("customer") + " on " + jsonObject.optString("date").substring(0, 10));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.mylistview, row);
		setListAdapter(adapter);
		
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

}
