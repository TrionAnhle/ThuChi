package com.example.thuchi;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class CaiDatActivity extends AppCompatActivity {
    private TextView tvCDGioThongBao;
    private Button btnDatThoiGian;
    private EditText edtCDThongBao;
    private TimePicker timePicker;
    private int notificationId = 1;
    public static String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caidat);
        setControll();
        setEvent();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuBack){
            Intent intent = new Intent(CaiDatActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void setControll(){
//        tvCDGioThongBao = findViewById(R.id.tvCDGioThongBao);
        btnDatThoiGian = findViewById(R.id.btnCDHien);
        edtCDThongBao = findViewById(R.id.edtCDThongBao);
        timePicker = findViewById(R.id.timePicker);
    }
    public void setEvent(){
        btnDatThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaiDatActivity.this,AlarmReceiver.class);
                intent.putExtra("notificationId",notificationId);
                intent.putExtra("todo",edtCDThongBao.getText().toString());
                final PendingIntent alarmIntent = PendingIntent.getBroadcast(CaiDatActivity.this,0,
                        intent,PendingIntent.FLAG_CANCEL_CURRENT);
                final AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);


                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY,hour);
                startTime.set(Calendar.MINUTE,minute);
                startTime.set(Calendar.SECOND,0);

                long alarmStartTime = startTime.getTimeInMillis();
                alarm.set(AlarmManager.RTC_WAKEUP,alarmStartTime,alarmIntent);
                Toast.makeText(getApplicationContext(),"Đặt thời gian thành công",Toast.LENGTH_LONG).show();
            }
        });
    }
}
