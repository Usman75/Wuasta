package com.walarm.wuasta;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.Time;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by AnushaB05 & GokulVSD on 18-09-2017.
 */

public class modifyFragment extends Fragment implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{

    public View v;
    Toast toast;
    int daycounter;
    int totaldelay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //Storing a reference to the views in this fragment, and setting listener for clock widget set button

        v = inflater.inflate(R.layout.modifylayout, container , false);

        Button timeset = (Button)v.findViewById(R.id.timesetbutton);
        timeset.setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Called everytime this fragment is loaded up

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);

        //daycounter is a variable used to make sure that atleast one day is enabled for calculation of predicted time
        daycounter=0;

        //Restoring the previous time selected in the clock widget
        ((TimePicker)view.findViewById(R.id.timePicker)).setHour(sharedPref.getInt("sethour",9));
        ((TimePicker)view.findViewById(R.id.timePicker)).setMinute(sharedPref.getInt("setminute",0));

        //Incase Work was renamed from Settings fragment
        TextView timetobeat = (TextView)view.findViewById(R.id.timetobeattext);
        timetobeat.setText("TIME TO BE AT "+sharedPref.getString("workname","WORK").toUpperCase());



        //Restoring previously selected repeat days and adding them to the listener

        ToggleButton sun = (ToggleButton)view.findViewById(R.id.suntoggle);
        sun.setChecked(sharedPref.getBoolean("sun",false));
        if(sun.isChecked()) daycounter++;
        sun.setOnCheckedChangeListener(this);

        ToggleButton mon = (ToggleButton)view.findViewById(R.id.montoggle);
        mon.setChecked(sharedPref.getBoolean("mon",true));
        if(mon.isChecked()) daycounter++;
        mon.setOnCheckedChangeListener(this);

        ToggleButton tue = (ToggleButton)view.findViewById(R.id.tuetoggle);
        tue.setChecked(sharedPref.getBoolean("tue",true));
        if(tue.isChecked()) daycounter++;
        tue.setOnCheckedChangeListener(this);

        ToggleButton wed = (ToggleButton)view.findViewById(R.id.wedtoggle);
        wed.setChecked(sharedPref.getBoolean("wed",true));
        if(wed.isChecked()) daycounter++;
        wed.setOnCheckedChangeListener(this);

        ToggleButton thu = (ToggleButton)view.findViewById(R.id.thutoggle);
        thu.setChecked(sharedPref.getBoolean("thu",true));
        if(thu.isChecked()) daycounter++;
        thu.setOnCheckedChangeListener(this);

        ToggleButton fri = (ToggleButton)view.findViewById(R.id.fritoggle);
        fri.setChecked(sharedPref.getBoolean("fri",true));
        if(fri.isChecked()) daycounter++;
        fri.setOnCheckedChangeListener(this);

        ToggleButton sat = (ToggleButton)view.findViewById(R.id.sattoggle);
        sat.setChecked(sharedPref.getBoolean("sat",false));
        if(sat.isChecked()) daycounter++;
        sat.setOnCheckedChangeListener(this);


        //Restoring previously selected Delay.
        //It's restored in this way since delay is in the form of discreet buttons, totalling to upto 1 hour.

        totaldelay = sharedPref.getInt("delay",0);

        ToggleButton plusfive = (ToggleButton)view.findViewById(R.id.fiveone);
        ToggleButton plusten = (ToggleButton)view.findViewById(R.id.tenone);
        ToggleButton plusfifteen = (ToggleButton)view.findViewById(R.id.fifteenone);
        ToggleButton plusthirty = (ToggleButton)view.findViewById(R.id.thirtyone);

        switch (sharedPref.getInt("delay",0)){
            case 0:
                break;
            case 5:
                plusfive.setChecked(true);
                break;
            case 10:
                plusten.setChecked(true);
                break;
            case 15:
                plusfifteen.setChecked(true);
                break;
            case 20:
                plusfive.setChecked(true);
                plusfifteen.setChecked(true);
                break;
            case 25:
                plusten.setChecked(true);
                plusfifteen.setChecked(true);
                break;
            case 30:
                plusthirty.setChecked(true);
                break;
            case 35:
                plusfive.setChecked(true);
                plusthirty.setChecked(true);
                break;
            case 40:
                plusten.setChecked(true);
                plusthirty.setChecked(true);
                break;
            case 45:
                plusfifteen.setChecked(true);
                plusthirty.setChecked(true);
                break;
            case 50:
                plusfive.setChecked(true);
                plusfifteen.setChecked(true);
                plusthirty.setChecked(true);
                break;
            case 55:
                plusten.setChecked(true);
                plusfifteen.setChecked(true);
                plusthirty.setChecked(true);
                break;
            case 60:
                plusfive.setChecked(true);
                plusten.setChecked(true);
                plusfifteen.setChecked(true);
                plusthirty.setChecked(true);
                break;
        }
        plusfive.setOnCheckedChangeListener(this);
        plusten.setOnCheckedChangeListener(this);
        plusfifteen.setOnCheckedChangeListener(this);
        plusthirty.setOnCheckedChangeListener(this);


        //Choosing the location for which you wish to recieve weather information, and restoring from previous settings

        ToggleButton weatherhome = (ToggleButton)v.findViewById(R.id.weatherhome);
        weatherhome.setOnCheckedChangeListener(this);

        ToggleButton weatherwork = (ToggleButton)v.findViewById(R.id.weatherwork);
        weatherwork.setTextOn(sharedPref.getString("workname","WORK").toUpperCase());
        weatherwork.setTextOff(sharedPref.getString("workname","WORK").toUpperCase());
        weatherwork.setOnCheckedChangeListener(this);

        ToggleButton weatherenroute = (ToggleButton)v.findViewById(R.id.weatherenroute);
        weatherenroute.setOnCheckedChangeListener(this);

        String weatherloc = sharedPref.getString("weatherloc","Work");

        if(weatherloc.equals("Work"))
            weatherwork.setChecked(true);
        else if (weatherloc.equals("Home"))
            weatherhome.setChecked(true);
        else
            weatherenroute.setChecked(true);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        //Called when a toggle button is clicked

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        //Checking which button was clicked, and modifying settings accordingly.
        //daycounter has to be atleast 1, never 0, since atleast one day must be selected for predicting wake time.
        switch (buttonView.getId()){

            case R.id.suntoggle: if(isChecked){
                edit.putBoolean("sun",true);
                edit.putBoolean("recheck",true);
                daycounter++;
            }
                else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("sun",false);
                    edit.putBoolean("recheck",true);
                }
            }
            break;
            case R.id.montoggle: if(isChecked){
                edit.putBoolean("mon",true);
                edit.putBoolean("recheck",true);
                daycounter++;
            }
            else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("mon",false);
                    edit.putBoolean("recheck",true);
                }
            }
            break;
            case R.id.tuetoggle: if(isChecked){
                edit.putBoolean("tue",true);
                edit.putBoolean("recheck",true);
                daycounter++;
            }
            else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("tue",false);
                    edit.putBoolean("recheck",true);
                }
            }
                break;
            case R.id.wedtoggle: if(isChecked){
                edit.putBoolean("wed",true);
                edit.putBoolean("recheck",true);
                daycounter++;
            }
            else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("wed",false);
                    edit.putBoolean("recheck",true);
                }
            }
                break;
            case R.id.thutoggle: if(isChecked){
                edit.putBoolean("thu",true);
                edit.putBoolean("recheck",true);
                daycounter++;
            }
            else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("thu",false);
                    edit.putBoolean("recheck",true);
                }
            }
                break;
            case R.id.fritoggle: if(isChecked){
                edit.putBoolean("fri",true);
                edit.putBoolean("recheck",true);
                daycounter++;
            }
            else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("fri",false);
                    edit.putBoolean("recheck",true);
                }
            }
                break;
            case R.id.sattoggle: if(isChecked){
                edit.putBoolean("sat",true);
                edit.putBoolean("recheck",true);
                daycounter++;
            }
            else{
                if(daycounter==1){
                    buttonView.setChecked(true);
                }
                else{
                    daycounter--;
                    edit.putBoolean("sat",false);
                    edit.putBoolean("recheck",true);
                }
            }
                break;
            case R.id.fiveone: if(isChecked){
                totaldelay+=5;
                edit.putInt("delay",totaldelay);
                edit.putBoolean("recheck",true);
            }
            else{
                totaldelay-=5;
                edit.putInt("delay",totaldelay);
                edit.putBoolean("recheck",true);
            }
                break;
            case R.id.tenone: if(isChecked){
                totaldelay+=10;
                edit.putInt("delay",totaldelay);
                edit.putBoolean("recheck",true);
            }
            else{
                totaldelay-=10;
                edit.putInt("delay",totaldelay);
                edit.putBoolean("recheck",true);
            }
                break;
            case R.id.fifteenone: if(isChecked){
                totaldelay+=15;
                edit.putInt("delay",totaldelay);
                edit.putBoolean("recheck",true);
            }
            else{
                totaldelay-=15;
                edit.putInt("delay",totaldelay);
                edit.putBoolean("recheck",true);
            }
                break;
            case R.id.thirtyone: if(isChecked){
                totaldelay+=30;
                edit.putInt("delay",totaldelay);
                edit.putBoolean("recheck",true);
            }
            else{
                totaldelay-=30;
                edit.putInt("delay",totaldelay);
                edit.putBoolean("recheck",true);
            }
                break;
            case R.id.weatherhome: if(isChecked){

                //This shenanigans is done since changing the state of the weather buttons calls the listener again,
                //which we don't want.

                ((ToggleButton)v.findViewById(R.id.weatherwork)).setOnCheckedChangeListener(null);
                ((ToggleButton)v.findViewById(R.id.weatherenroute)).setOnCheckedChangeListener(null);
                ((ToggleButton)v.findViewById(R.id.weatherwork)).setChecked(false);
                ((ToggleButton)v.findViewById(R.id.weatherenroute)).setChecked(false);
                ((ToggleButton)v.findViewById(R.id.weatherwork)).setOnCheckedChangeListener(this);
                ((ToggleButton)v.findViewById(R.id.weatherenroute)).setOnCheckedChangeListener(this);

                edit.putString("weatherloc","Home");
            }
            else{
                buttonView.setChecked(true);
            }
                break;
            case R.id.weatherwork: if(isChecked){

                //Same thing here.

                ((ToggleButton)v.findViewById(R.id.weatherhome)).setOnCheckedChangeListener(null);
                ((ToggleButton)v.findViewById(R.id.weatherenroute)).setOnCheckedChangeListener(null);
                ((ToggleButton)v.findViewById(R.id.weatherhome)).setChecked(false);
                ((ToggleButton)v.findViewById(R.id.weatherenroute)).setChecked(false);
                ((ToggleButton)v.findViewById(R.id.weatherhome)).setOnCheckedChangeListener(this);
                ((ToggleButton)v.findViewById(R.id.weatherenroute)).setOnCheckedChangeListener(this);

                edit.putString("weatherloc","Work");
            }
            else{
                buttonView.setChecked(true);
            }
                break;
            case R.id.weatherenroute: if(isChecked){

                //Same thing here.

                ((ToggleButton)v.findViewById(R.id.weatherhome)).setOnCheckedChangeListener(null);
                ((ToggleButton)v.findViewById(R.id.weatherwork)).setOnCheckedChangeListener(null);
                ((ToggleButton)v.findViewById(R.id.weatherhome)).setChecked(false);
                ((ToggleButton)v.findViewById(R.id.weatherwork)).setChecked(false);
                ((ToggleButton)v.findViewById(R.id.weatherhome)).setOnCheckedChangeListener(this);
                ((ToggleButton)v.findViewById(R.id.weatherwork)).setOnCheckedChangeListener(this);

                edit.putString("weatherloc","Enroute");
            }
            else{
                buttonView.setChecked(true);
            }
                break;
        }
        edit.commit();

    }

    @Override
    public void onClick(View view) {

        //Called when a new "time to be at" is set from the clock widget. It is stored in sharedpreferences.

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("wuastafile",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        TimePicker timePicker = (TimePicker)v.findViewById(R.id.timePicker);
        edit.putInt("sethour",timePicker.getHour());
        edit.putInt("setminute",timePicker.getMinute());
        edit.putBoolean("recheck",true);

        if(toast != null) toast.cancel();
        toast = Toast.makeText(getActivity(), "Time Set", Toast.LENGTH_SHORT);
        toast.show();

        edit.commit();
    }
}
