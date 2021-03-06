package com.example.parksigdan;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class Main2Activity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView nameTextView;
    private TextView emailTextView;
    private FirebaseAuth auth;
    private EditText edit;
    private TextView text;
    private XmlPullParser xpp;
    private String key="k8x4%2Fbz7DOk4tBpVg0VV9hbv9O8g0Qh44Hnp7kHl6AMn8jn6xV%2FfEDgjxlY1ntEKxT32VLFjuJk5IU5fElzYqg%3D%3D";
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);

        auth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View view = navigationView.getHeaderView(0);

        nameTextView = (TextView)view.findViewById(R.id.header_name_tv);
        emailTextView = (TextView)view.findViewById(R.id.header_name_tv);

        nameTextView.setText(auth.getCurrentUser().getDisplayName());
        emailTextView.setText(auth.getCurrentUser().getEmail());

        edit= (EditText)findViewById(R.id.edit);
        text= (TextView)findViewById(R.id.result);
    }

    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data=getXmlData();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(data);
                            }
                        });
                    }
                }).start();
                break;
        }
    }

    private String getXmlData() {
        StringBuffer buffer=new StringBuffer();
        String str = edit.getText().toString();//EditText??? ????????? Text????????????
        String location = null;
        try {
            location = URLEncoder.encode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String query="%EC%A0%84%EB%A0%A5%EB%A1%9C";

        String queryUrl="http://apis.data.go.kr/1470000/FoodNtrIrdntInfoService/getFoodNtrItdntList?ServiceKey="
                +key
                + "&desc_kor=" + location;

        try{
            URL url= new URL(queryUrl);//???????????? ??? ?????? url??? URL ????????? ??????.
            InputStream is= url.openStream(); //url????????? ??????????????? ??????

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml????????? ??????
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream ???????????? xml ????????????

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("?????? ??????...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//?????? ?????? ????????????

                        if(tag.equals("item")) ;// ????????? ????????????
                        else if(tag.equals("DESC_KOR")){
                            buffer.append("?????? : ");
                            xpp.next();
                            buffer.append(xpp.getText());//title ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append("\n"); //????????? ?????? ??????
                        }
                        else if(tag.equals("SERVING_WT")){
                            buffer.append("1???????????? : ");
                            xpp.next();
                            buffer.append(xpp.getText());//category ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append("\n");//????????? ?????? ??????
                        }
                        else if(tag.equals("NUTR_CONT1")){
                            buffer.append("?????? :");
                            xpp.next();
                            buffer.append(xpp.getText());//description ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append("\n");//????????? ?????? ??????
                        }
                        else if(tag.equals("NUTR_CONT2")){
                            buffer.append("???????????? :");
                            xpp.next();
                            buffer.append(xpp.getText());//telephone ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append("\n");//????????? ?????? ??????
                        }
                        else if(tag.equals("NUTR_CONT3")){
                            buffer.append("????????? :");
                            xpp.next();
                            buffer.append(xpp.getText());//address ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append("\n");//????????? ?????? ??????
                        }
                        else if(tag.equals("NUTR_CONT4")){
                            buffer.append("?????? :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapx ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append("\n"); //????????? ?????? ??????
                        }
                        else if(tag.equals("NUTR_CONT5")){
                            buffer.append("?????? :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append("\n"); //????????? ?????? ??????
                        }
                        else if(tag.equals("NUTR_CONT6")){
                            buffer.append("????????? :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append("\n"); //????????? ?????? ??????
                        }
                        else if(tag.equals("NUTR_CONT7")){
                            buffer.append("???????????????  :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append("\n"); //????????? ?????? ??????
                        }
                        else if(tag.equals("NUTR_CONT8")){
                            buffer.append("???????????????  :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append("\n"); //????????? ?????? ??????
                        }
                        else if(tag.equals("NUTR_CONT9")){
                            buffer.append("??????????????????  :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append("\n"); //????????? ?????? ??????
                        }
                        else if(tag.equals("BGN_YEAR")) {
                            buffer.append("???????????? :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append("\n"); //????????? ?????? ??????
                        }
                        else if(tag.equals("ANIMAL_PLANT")) {
                            buffer.append("???????????? :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append("\n"); //????????? ?????? ??????
                        }

                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //?????? ?????? ????????????

                        if(tag.equals("item")) buffer.append("\n");// ????????? ??????????????????..?????????
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        buffer.append("?????? ???\n");
        return buffer.toString();//StringBuffer ????????? ?????? ??????

    }//getXmlData method....

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}