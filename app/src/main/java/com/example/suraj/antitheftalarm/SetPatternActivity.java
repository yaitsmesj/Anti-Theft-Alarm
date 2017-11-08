package com.example.suraj.antitheftalarm;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class SetPatternActivity extends AppCompatActivity {

    String final_pattern ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pattern);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final PatternLockView patternLockView = (PatternLockView) findViewById(R.id.set_pattern_lock_view);
        Button button = (Button) findViewById(R.id.set_pattern_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(MainActivity.TAG, "onClick: "+final_pattern);
                editor.putString(Utils.save_pattern_key,final_pattern);
                editor.commit();
                Toast.makeText(SetPatternActivity.this, "Pattern Saved", Toast.LENGTH_SHORT).show();
               // finishActivity(Utils.setPatternRequestCode);
                finish();
            }
        });
        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {       }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {  }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                final_pattern = PatternLockUtils.patternToString(patternLockView,pattern);
            }

            @Override
            public void onCleared() {  }
        });


    }
}
