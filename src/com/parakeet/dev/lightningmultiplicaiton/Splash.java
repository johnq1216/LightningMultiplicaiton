package com.parakeet.dev.lightningmultiplicaiton;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Splash extends Activity implements OnClickListener {

	Button bStart;
	
	Button bInstructions;
	Button bReturnToMenu;
	
	Button bRecord;
	Button bReturnFromRecord;
	
	TextView tvRecord;
	ViewFlipper flippy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		flippy = (ViewFlipper)findViewById(R.id.vf1);

		bStart = (Button) findViewById(R.id.bStart);
		bInstructions = (Button)findViewById(R.id.bInstructions);
		bReturnToMenu = (Button)findViewById(R.id.bReturn);
		bRecord = (Button)findViewById(R.id.bRecord);
		bReturnFromRecord = (Button)findViewById(R.id.bReturnFromRecord);
		
		tvRecord = (TextView) findViewById(R.id.tvRecord);

		bStart.setOnClickListener(this);
	
		bInstructions.setOnClickListener(this);
		bReturnToMenu.setOnClickListener(this);

		bReturnFromRecord.setOnClickListener(this);
		bRecord.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == R.id.bStart) {
			Intent i = new Intent("android.intent.action.GAME");
			startActivity(i);
			finish();
		}
		if (arg0.getId() == R.id.bInstructions) {
			flippy.setDisplayedChild(flippy.indexOfChild(findViewById(R.id.layout2)));
		}
		if (arg0.getId() == R.id.bReturn || arg0.getId() == R.id.bReturnFromRecord){
			flippy.setDisplayedChild(flippy.indexOfChild(findViewById(R.id.layout1)));
		}
		
		if (arg0.getId() == R.id.bRecord){
			flippy.setDisplayedChild(flippy.indexOfChild(findViewById(R.id.layout3)));
			SharedPreferences sp = getSharedPreferences("recordFiles", this.MODE_PRIVATE);
			//record is zero if no such record exists
			int intRecord = sp.getInt("intRecord", 0);
			tvRecord.setText(intRecord + " Score Streak!");
		}

	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
			flippy.setDisplayedChild(flippy.indexOfChild(findViewById(R.id.layout1)));
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
