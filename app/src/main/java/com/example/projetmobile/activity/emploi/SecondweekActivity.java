package com.example.projetmobile.activity.emploi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.projetmobile.R;
import com.example.projetmobile.Utils.LetterImageView;

public class SecondweekActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    public static SharedPreferences sharedPreferences;
    public static String dayy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_second);

        setupUIViews();
        initToolbar();
        setupListView();
    }

    private void setupUIViews(){
        toolbar = (Toolbar)findViewById(R.id.ToolbarWeeksecond);
        listView = (ListView)findViewById(R.id.lvWeeksecond);
        sharedPreferences = getSharedPreferences("MY_DAY", MODE_PRIVATE);
    }
    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Semaine");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupListView(){
        String[] week = getResources().getStringArray(R.array.Week);

        WeeksecondAdapter adapter = new WeeksecondAdapter(this, R.layout.activity_secondweek_single_item, week);

        listView.setAdapter(adapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0: {
                        startActivity(new Intent(SecondweekActivity.this, DetaildayconsultActivity.class));
                        sharedPreferences.edit().putString(dayy, "Lundi").apply();
                        break;
                    }
                     case 1: {
                        startActivity(new Intent(SecondweekActivity.this, DetaildayconsultActivity.class));
                        sharedPreferences.edit().putString(dayy, "Mardi").apply();
                        break;
                    }
                    case 2: {
                        startActivity(new Intent(SecondweekActivity.this, DetaildayconsultActivity.class));
                        sharedPreferences.edit().putString(dayy, "Mercredi").apply();
                        break;
                    }
                    case 3: {
                        startActivity(new Intent(SecondweekActivity.this, DetaildayconsultActivity.class));
                        sharedPreferences.edit().putString(dayy, "Jeudi").apply();
                        break;
                    }
                    case 4: {
                        startActivity(new Intent(SecondweekActivity.this, DetaildayconsultActivity.class));
                        sharedPreferences.edit().putString(dayy, "Vendredi").apply();
                        break;
                    }
                    case 5: {
                        startActivity(new Intent(SecondweekActivity.this, DetaildayconsultActivity.class));
                        sharedPreferences.edit().putString(dayy, "Samedi").apply();
                        break;
                    }
                    default:break;
                }
            }
        });

    }

    public class WeeksecondAdapter extends ArrayAdapter{

        private int resource;
        private LayoutInflater layoutInflater;
        private String[] week = new String[]{};

        public WeeksecondAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
            this.resource = resource;
            this.week = objects;
            layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = layoutInflater.inflate(resource, null);
                holder.ivLogoo = (LetterImageView)convertView.findViewById(R.id.LVsecondweek);
                holder.tvWeekk = (TextView)convertView.findViewById(R.id.tvsecondweek);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            holder.ivLogoo.setOval(true);
            holder.ivLogoo.setLetter(week[position].charAt(0));
            holder.tvWeekk.setText(week[position]);

            return convertView;
        }

        class ViewHolder{
            private LetterImageView ivLogoo;
            private TextView tvWeekk;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home : {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
