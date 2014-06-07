package com.parakeet.dev.lightningmultiplicaiton;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.TextView;

public class FinalScore extends Activity implements OnClickListener {

	private TextView tvScore, tvRecord;
	private LinearLayout linearLayout;
	private Button bPlayAgain, bMainMenu;
	private int intScore, intRecord;
	private SharedPreferences sp;
	private SoundPool soundPool;
	private int soundFinish = 0;
	private android.media.MediaPlayer ourSong;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finalscore);
		
		
		ourSong = android.media.MediaPlayer.create(FinalScore.this, R.raw.victory);

	
		ourSong.start();
		

		sp = getSharedPreferences("recordFiles", this.MODE_PRIVATE);
		// record is zero if no such record exists
		intRecord = sp.getInt("intRecord", 0);

		tvScore = (TextView) findViewById(R.id.tvScore);
		tvRecord = (TextView) findViewById(R.id.tvRecord);
		bPlayAgain = (Button) findViewById(R.id.bPlayAgain);
		bMainMenu = (Button) findViewById(R.id.bMainMenu);
		
		linearLayout = (LinearLayout)findViewById(R.id.finalScore);

		bPlayAgain.setOnClickListener(this);
		bMainMenu.setOnClickListener(this);

		// the negative one is a default value if no such extra is found
		intScore = getIntent().getIntExtra("score", -1);
		tvScore.setText(intScore + " problems solved!");

		// if new record
		if (intScore > intRecord) {
			tvRecord.setText("New Record!!");
			SharedPreferences.Editor editor = sp.edit();
			editor.putInt("intRecord", intScore);
			editor.commit();
			linearLayout.setBackgroundResource(R.drawable.newrecord);
			tvRecord.setText("NEW RECORD!!!");
		} else {
			tvRecord.setText("Record: " + intRecord + " problems solved");
		}
		soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		soundFinish = soundPool.load(this, R.raw.victory, 1);

		soundPool.play(soundFinish, 1, 1, 0, 0, 1);

		
	}

	@Override
	public void onClick(View arg0) {
		
		if( arg0.getId()==R.id.bPlayAgain){
			// TODO Auto-generated method stub
			Intent i = new Intent();
			i.setClass(this, MainActivity.class);
			startActivity(i);
			finish();
		}
	
		if (arg0.getId()==R.id.bMainMenu){
			// TODO Auto-generated method stub
			Intent i = new Intent();
			i.setClass(this, Splash.class);
			startActivity(i);
			finish();
		}
	}

}
