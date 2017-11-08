package com.example.suraj.antitheftalarm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.suraj.antitheftalarm.services.MyIntentService;

import java.util.List;

public class PatternConfirmActivity extends AppCompatActivity {


    String final_pattern="";
    PatternLockView patternLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.TAG, "onCreate: Pattern Confirm Activity");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String saved_pattern = sharedPreferences.getString(Utils.save_pattern_key,"");
        Log.d(MainActivity.TAG, "onCreate: "+ saved_pattern);
        if(saved_pattern==null || saved_pattern.equals("")){
            // start pattern set activity
            //finish Activity

            Intent intent = new Intent( PatternConfirmActivity.this,SetPatternActivity.class);
            startActivityForResult(intent,Utils.setPatternRequestCode);
            finish();
        }

        setContentView(R.layout.activity_pattern_confirm);

        Intent intent = getIntent();
        final Boolean setPassword = intent.getBooleanExtra("set_password",true);
        patternLockView = (PatternLockView)findViewById(R.id.pattern_lock_view);

            patternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {  }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {  }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(patternLockView,pattern);
                    if(final_pattern.equals(saved_pattern)){

                        if(setPassword){
                            //start pattern set Activity
                            Intent intent = new Intent( PatternConfirmActivity.this,SetPatternActivity.class);
                            startActivityForResult(intent,Utils.setPatternRequestCode);
                            finish();
                            //finish Activity
                        }else{
                            //stop Handler
                            Utils.patternEntered = true;
                            //stop Service
                            MyIntentService.motionServiceStatus = false;
                            //stop music
                            Utils.stopMusic();
                            //finish Activity
                            finish();
                        }

                    }else
                        Toast.makeText(PatternConfirmActivity.this, "Incorrect Pattern", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCleared() {    }
            });

    }

}
