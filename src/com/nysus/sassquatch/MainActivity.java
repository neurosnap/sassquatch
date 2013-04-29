package com.nysus.sassquatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.nysus.sassquatch.User;

public class MainActivity extends Activity {
	
	Button buttSteve, buttKevin, buttMichelle, buttBrian, buttEric;
	public final static String USERNAME = "com.nysus.sassquatch.USER";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//sets the content view
		setContentView(R.layout.activity_main);
		
		//get layout buttons
		buttSteve = (Button) findViewById(R.id.who_steve);
		buttKevin = (Button) findViewById(R.id.who_kevin);
		buttMichelle = (Button) findViewById(R.id.who_michelle);
		buttBrian = (Button) findViewById(R.id.who_brian);
		buttEric = (Button) findViewById(R.id.who_eric);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//go to when page after button has been clicked for user
	public void gotoWhen(View view) {
		
		String username = null;
		//User user = User.getInstance();
		
		if (buttSteve.getId() == ((Button) view).getId()) {
			username = "Steve Sass";
		}
		
		if (buttKevin.getId() == ((Button) view).getId()) {
			username = "Kevin Wickenheiser";
		}
		
		if (buttMichelle.getId() == ((Button) view).getId()) {
			username = "Michelle Stewart";
		}
		
		if (buttBrian.getId() == ((Button) view).getId()) {
			username = "Brian Williamson";
		}
		
		if (buttEric.getId() == ((Button) view).getId()) {
			username = "Eric Bower";
		}
		
		Intent intent = new Intent(this, WhenActivity.class);
		//intent.putExtra(USERNAME, user);
		User.setName(username);
		startActivity(intent);
		
	}

}
