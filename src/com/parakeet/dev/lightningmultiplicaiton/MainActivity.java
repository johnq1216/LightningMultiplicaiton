package com.parakeet.dev.lightningmultiplicaiton;

import java.util.ArrayList;
import java.util.Random;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

//the main class handles inputs.  the turbine handles most of teh game

//use imageview instead of view.  Can change background....make it random.  The image itself will be a happy facecfs



/**
 * log:
 * - create a splash screen
 * - create a new record image
 * - create a great! victory image when player scores 30 problems
 */

public class MainActivity extends Activity implements OnClickListener, Runnable {

	private Button bv1, bv2, bv3;
	private ArrayList<Button> buttonArray = new ArrayList<Button>();
	private TextView tvq1, tvq2;
	private Random rand = new Random();

	private int factor1, factor2;
	private int product1, product2, product3;
	// variant mixes up the correct number on the other 2
	private int correctAnswer, buttonWithCorrectAnswer;

	private ImageView imageView;
	private Bitmap bmSmily1, bmSmily2, bmSmily3, bmSmilySad;
	private ProgressBar progressBar;
	private int intProgressStatus = 0;
	private int intProgressDificultyRate = 1;
	private int intProgressLevelCycle = 0;

	private int intScore = 0;

	private Handler handler = new Handler();

	private Thread thread = null;

	private boolean isRunning = false;
	
	private SoundPool sp;
	private int soundDing = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bv1 = (Button) findViewById(R.id.bv1);
//		bv1.getBackground().setColorFilter(new LightingColorFilter(0XFFFFFFF, Color.RED));
		bv2 = (Button) findViewById(R.id.bv2);
//		bv2.getBackground().setColorFilter(new LightingColorFilter(0XFFFFFFF, Color.BLUE));
		bv3 = (Button) findViewById(R.id.bv3);
//		bv3.getBackground().setColorFilter(new LightingColorFilter(0XFFFFFFF, Color.YELLOW));
		bv1.setOnClickListener(this);
		bv2.setOnClickListener(this);
		bv3.setOnClickListener(this);
		buttonArray.add(bv1);
		buttonArray.add(bv2);
		buttonArray.add(bv3);

		bmSmily1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.smily1);
		bmSmily2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.smily2);
		bmSmily3 = BitmapFactory.decodeResource(getResources(),
				R.drawable.smily3);
		bmSmilySad = BitmapFactory.decodeResource(getResources(),
				R.drawable.smilysad);

		tvq1 = (TextView) findViewById(R.id.tvq1);
		tvq2 = (TextView) findViewById(R.id.tvq2);
		
		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		soundDing = sp.load(this, R.raw.ding, 1);

		imageView = (ImageView) findViewById(R.id.imageView1);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		setNumbers();
		
	}

	private void setNumbers() {
		// TODO Auto-generated method stub

		if (rand.nextInt(4)+1 == 1){
			factor1 = rand.nextInt(8)+2;
		}
		
		if (factor1 == 0){
			factor1 = 2;
		}
		
		factor2 = rand.nextInt(10);
		correctAnswer = factor1 * factor2;

		buttonWithCorrectAnswer = rand.nextInt(buttonArray.size()) + 1;

		if (buttonWithCorrectAnswer == 1) {
			product1 = correctAnswer;
		} else {
			product1 = rand.nextInt(82);
		}

		if (buttonWithCorrectAnswer == 2) {
			product2 = correctAnswer;
		} else {
			product2 = rand.nextInt(82);
		}

		if (buttonWithCorrectAnswer == 3) {
			product3 = correctAnswer;
		} else {
			product3 = rand.nextInt(82);
		}

		bv1.setText(product1 + "");
		bv2.setText(product2 + "");
		bv3.setText(product3 + "");
		tvq1.setText(factor1 + "");
		tvq2.setText(factor2 + "");
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.bv1) {
			if (product1 == correctAnswer) {
				choseCorrectAnswer();
			} else {
				intProgressStatus = progressBar.getMax();
			}
		}
		if (view.getId() == R.id.bv2) {
			if (product2 == correctAnswer) {
				choseCorrectAnswer();
			} else {
				intProgressStatus = progressBar.getMax();
			}

		}
		if (view.getId() == R.id.bv3) {
			if (product3 == correctAnswer) {
				choseCorrectAnswer();
			} else {
				intProgressStatus = progressBar.getMax();
			}
		}

	}

	private void choseCorrectAnswer() {

		if(soundDing != 0)
			sp.play(soundDing, .5f, .5f, 0, 0, 2);
		setNumbers();
		intScore++;
		intProgressStatus = 0;
	    intProgressLevelCycle++;
	    if (intProgressLevelCycle > 3){
	    	intProgressLevelCycle = 0;
	    	intProgressDificultyRate++;
	    }
		//probaly already difficult enough 
		// int modValue = progressDificultyRate % 2;
		// progressDificultyRate = progressDificultyRate + modValue;
//		intProgressDificultyRate = intProgressDificultyRate + (intProgressDificultyRate % 2);
		int intRandom = new Random().nextInt(3) + 1;
		switch (intRandom) {
		case 1:
			imageView.setImageBitmap(bmSmily1);
			break;
		case 2:
			imageView.setImageBitmap(bmSmily2);
			break;
		case 3:
			imageView.setImageBitmap(bmSmily3);
			break;

		}

	}

	private void gameOver() {
		Intent i = new Intent("android.intent.action.FINALSCORE");
		// name of data is score. intent will carry int value
		i.putExtra("score", intScore);
		// paramter 1 will return code when the activity exits
//		i.setClass(this, FinalScore.class);
		startActivity(i);
		finish();

	}

	public void onPause() {
		super.onPause();
		isRunning = false;
		// ourSong.release();
		while (true) {
			// stops thread and allows it to die
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		thread = null;

	}

	public void onResume() {
		super.onResume();
		isRunning = true;
		thread = new Thread(this);
		thread.start();
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isRunning) {
			if (intProgressStatus < 100) {
				intProgressStatus = intProgressStatus
						+ intProgressDificultyRate;
				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						progressBar.setProgress(intProgressStatus);
					}

				});
		
			} else{
				isRunning = false;
				gameOver();
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
