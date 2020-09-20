package com.example.matalaandroid1;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import java.time.LocalDateTime;


public class MainActivity extends AppCompatActivity {
    LocalDateTime now = LocalDateTime.now();
    int totalPrice;
    int progress1 = 0;
    final int triaki_price = 2;
    final int soya_price = 1;
    final int ginger_price = 3;
    LinearLayout linearLayout;
    String[] Alergy;
    TextView priceTv;
    RadioGroup radiogroup;
    TextView dateTv;
    TextView endOrderTv;
    ProgressBar progressBar;
    boolean isSushiChoosed = false;
    boolean isDateOK = true;
    boolean isTimeOK = true;
    boolean isToday = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimePicker timePicker = findViewById(R.id.time_picker);
        availableOrderButton();


        totalPrice = 0;
        priceTv = findViewById(R.id.price_textview);


        // set a change listener on the SeekBar
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        int progress = seekBar.getProgress();
        TextView tvProgressLabel = findViewById(R.id.text_view_seekbar);
        //tvProgressLabel.setText(progress);


        /*Changes the  by the selected radio button*/
        radiogroup = findViewById(R.id.rg);
        final ImageView Sushi_image = findViewById(R.id.Sushi_image);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Sushi_image.setImageBitmap(null);
                RadioButton Maki = findViewById(R.id.rb1),
                        Nigiri_Tuna = findViewById(R.id.rb2),
                        Latini_roll = findViewById(R.id.rb3),
                        vegeterian_combination = findViewById(R.id.rb4),
                        Tokyo_combination = findViewById(R.id.rb5);

                if (checkedId == Maki.getId())
                    Sushi_image.setImageResource(R.drawable.maki);
                else if (checkedId == Nigiri_Tuna.getId())
                    Sushi_image.setImageResource(R.drawable.nigirituna);
                else if (checkedId == Latini_roll.getId())
                    Sushi_image.setImageResource(R.drawable.latiniroll);
                else if (checkedId == vegeterian_combination.getId())
                    Sushi_image.setImageResource(R.drawable.vegeteriancomb);
                else if (checkedId == Tokyo_combination.getId())
                    Sushi_image.setImageResource(R.drawable.tokyocomb);

            }
        });




        Alergy = new String[]{getResources().getString(R.string.No_alergies),getResources().getString(R.string.soya_spinner),
                getResources().getString(R.string.nuts), getResources().getString(R.string.milk)};
        Spinner alergy_spinner = findViewById(R.id.alergy_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Alergy);
        alergy_spinner.setAdapter(adapter);
        final ImageView chef_imageView = findViewById(R.id.chef_image_view);
        //chef_imageView.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
        // /*
        /*Changes the  images by the selected name from the spinner*/

        alergy_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chef_imageView.setImageBitmap(null);
                if (parent.getSelectedItem().toString().equals(getResources().getString(R.string.soya_spinner)))
                    chef_imageView.setImageResource(R.drawable.soya);
                else if (parent.getSelectedItem().toString().equals(getResources().getString(R.string.milk)))
                    chef_imageView.setImageResource(R.drawable.milk);
                else if (parent.getSelectedItem().toString().equals(getResources().getString(R.string.nuts)))
                    chef_imageView.setImageResource(R.drawable.nuts);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        /*Handles the radio button for sushi and  raises the price accordingly*/
        RadioButton maki = findViewById(R.id.rb1);
        maki.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotalPrice();
            }
        });
        RadioButton nigiri_tuna = findViewById(R.id.rb2);
        nigiri_tuna.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotalPrice();
            }
        });
        RadioButton latini_roll = findViewById(R.id.rb3);
        latini_roll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotalPrice();
            }
        });
        RadioButton vegeterian_comb = findViewById(R.id.rb4);
        vegeterian_comb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotalPrice();
            }
        });
        RadioButton tokyo_comb = findViewById(R.id.rb5);
        tokyo_comb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotalPrice();
            }
        });





        /*Handles the check boxes in case of sauces and  raises the price accordingly*/
        CheckBox triaki_sauces = findViewById(R.id.cb1);
        triaki_sauces.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotalPrice();
            }
        });

        CheckBox soya = findViewById(R.id.cb2);
        soya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotalPrice();
            }
        });

        final CheckBox ginger = findViewById(R.id.cb3);
        ginger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotalPrice();
            }
        });





        /*Handles date choosing and show it below the calender view*/
        CalendarView date = findViewById(R.id.calendar);
        dateTv = findViewById(R.id.chosen_date_tv);
        date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String s = getResources().getString(R.string.chosen_date);
                int currentYear = now.getYear();
                int currentMonth = now.getMonthValue();
                int currentDayOfMonth = now.getDayOfMonth();
                if (year == currentYear && month + 1 == currentMonth && dayOfMonth == currentDayOfMonth) {
                    isToday = true;
                } else {
                    isToday = false;
                    isTimeOK = true;
                }
                if (year < currentYear) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT).show();
                    isDateOK = false;
                    availableOrderButton();
                    return;
                } else if (year == currentYear) {
                    if (month + 1 < currentMonth) {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT).show();
                        isDateOK = false;
                        availableOrderButton();
                        return;
                    } else if (month + 1 == currentMonth) {
                        if (dayOfMonth < currentDayOfMonth) {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT).show();
                            isDateOK = false;
                            availableOrderButton();
                            return;
                        }
                    }
                }
                isDateOK = true;
                availableOrderButton();
                String date = (dayOfMonth) + "/" + (month + 1) + "/" + (year);
                dateTv.setText(s + " " + date);
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                CalendarView date = findViewById(R.id.calendar);
                int currentHour = now.getHour();
                int currentMinute = now.getMinute();
                if (isToday == true) {
                    if (hourOfDay < currentHour) {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.invalid_time), Toast.LENGTH_SHORT).show();
                        isTimeOK = false;
                        availableOrderButton();
                        return;
                    } else if (hourOfDay == currentHour) {
                        if (minute < currentMinute) {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.invalid_time), Toast.LENGTH_SHORT).show();
                            isTimeOK = false;
                            availableOrderButton();
                            return;
                        }
                    }
                }
                isTimeOK = true;
                availableOrderButton();
            }
        });



        /*Handles end of order, missing fields and final message*/
        progressBar = findViewById(R.id.progress_bar);
        endOrderTv = findViewById(R.id.end_order_tv);
        Button orderBtn = findViewById(R.id.order_btn);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                for(int i=0;i<linearLayout.getChildCount();i++){
                    View view=linearLayout.getChildAt(i);
                    if(view instanceof EditText)
                        if(((EditText)view).getText().toString().equals("")){
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.missing_input), Toast.LENGTH_SHORT).show();
                            return;
                        }
                }

                 */
                boolean flag = false;
                for (int i = 0; i < radiogroup.getChildCount(); i++) {
                    View view = radiogroup.getChildAt(i);
                    if (view instanceof RadioButton)
                        if (((RadioButton) view).isChecked()) {
                            flag = true;
                            break;
                        }
                }
                if (!flag) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.missing_input), Toast.LENGTH_SHORT).show();
                    return;
                }
                String s = dateTv.getText().toString();
                if (dateTv.getText().toString().equals(getResources().getString(R.string.chosen_date))) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.missing_input), Toast.LENGTH_SHORT).show();
                    return;
                }
                /*Handles progress bar*/
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.currentThread().sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                        endOrderTv.setText(getResources().getString(R.string.end_order_txt));
                    }

                }, 1000);
            }
        });
    }

    private void updateTotalPrice() {
        RadioButton maki = findViewById(R.id.rb1);
        RadioButton nigiri_tuna = findViewById(R.id.rb2);
        RadioButton latini_roll = findViewById(R.id.rb3);
        RadioButton vegeterian_comb = findViewById(R.id.rb4);
        RadioButton tokyo_comb = findViewById(R.id.rb5);
        final CheckBox triaki = findViewById(R.id.cb1);
        final CheckBox soya = findViewById(R.id.cb2);
        final CheckBox ginger = findViewById(R.id.cb3);
        final Button orderbutton = findViewById(R.id.order_btn);
        totalPrice = 0;
        if (maki.isChecked()) {
            isSushiChoosed = true;
            totalPrice += 3;
            Toast.makeText(MainActivity.this, getResources().getString(R.string.price_updated), Toast.LENGTH_SHORT).show();
        }
        if (nigiri_tuna.isChecked()) {
            isSushiChoosed = true;
            totalPrice += 4;
            Toast.makeText(MainActivity.this, getResources().getString(R.string.price_updated), Toast.LENGTH_SHORT).show();
        }
        if (latini_roll.isChecked()) {
            isSushiChoosed = true;
            totalPrice += 6;
            Toast.makeText(MainActivity.this, getResources().getString(R.string.price_updated), Toast.LENGTH_SHORT).show();
        }
        if (vegeterian_comb.isChecked()) {
            isSushiChoosed = true;
            totalPrice += 7;
            Toast.makeText(MainActivity.this, getResources().getString(R.string.price_updated), Toast.LENGTH_SHORT).show();
        }
        if (tokyo_comb.isChecked()) {
            isSushiChoosed = true;
            totalPrice += 10;
            Toast.makeText(MainActivity.this, getResources().getString(R.string.price_updated), Toast.LENGTH_SHORT).show();
        }
        if (triaki.isChecked()) {
            totalPrice += 2;
            Toast.makeText(MainActivity.this, getResources().getString(R.string.price_updated), Toast.LENGTH_SHORT).show();
        }
        if (soya.isChecked()) {
            totalPrice += 1;
            Toast.makeText(MainActivity.this, getResources().getString(R.string.price_updated), Toast.LENGTH_SHORT).show();
        }

        if (ginger.isChecked()) {
            totalPrice += 3;
            Toast.makeText(MainActivity.this, getResources().getString(R.string.price_updated), Toast.LENGTH_SHORT).show();
        }

        priceTv.setText(totalPrice + "$");
        availableOrderButton();
    }

    private void availableOrderButton() {
        final Button orderbutton = findViewById(R.id.order_btn);
        if (isDateOK == true && isTimeOK == true && isSushiChoosed == true)
            orderbutton.setEnabled(true);
        else
            orderbutton.setEnabled(false);
    }


    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            TextView tvProgressLabel = findViewById(R.id.text_view_seekbar);
            tvProgressLabel.setText(getResources().getString(R.string.numChopSticks) + " " + progress);
            progress1 = progress;

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

            // called after the user finishes moving the SeekBar
            //Add Imageview
            SeekBar seek_bar = findViewById(R.id.seekBar);
            LinearLayout ChopSticsLayoutt = findViewById(R.id.imgchopstick);
            for (int j = 0; j < 5; j++) {
                ImageView img = ChopSticsLayoutt.findViewById(j);
                if (img != null) {
                    ChopSticsLayoutt.removeView(img);
                }
            }
            for (int i = 0; i < progress1; i++) {

                ImageView chopStick_img = new ImageView(MainActivity.this);
                chopStick_img.setImageResource(R.drawable.chopsticks);
                chopStick_img.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
                chopStick_img.setImageResource(R.drawable.chopsticks);
                chopStick_img.setId(i);
                ChopSticsLayoutt.addView(chopStick_img);

            }
        }

    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}






