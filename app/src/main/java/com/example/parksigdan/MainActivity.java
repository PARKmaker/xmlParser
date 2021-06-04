package com.example.parksigdan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_Register).setOnClickListener(onClickListener);
        findViewById(R.id.btn_Login).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_Login:
                    startActivity(LoginActivity.class);
                    break;
                case R.id.btn_Register:
                    startActivity(RegisterActivity.class);
                    break;
            }
        }
    };

    private void startActivity(Class c){
        Intent intent = new Intent(this, c);
        //mainintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //뒤로가기 누르면 전 액티비티 나오는거 방지
        MainActivity.this.startActivity(intent);
    }
}