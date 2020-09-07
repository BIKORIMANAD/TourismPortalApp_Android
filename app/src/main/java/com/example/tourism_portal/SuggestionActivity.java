package com.example.tourism_portal;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;


public class SuggestionActivity extends AppCompatActivity {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextHeroId, editTextName, editTextRealname,ratingBar,spinnerTeam;

    ProgressBar progressBar;
    ListView listView;
    Button buttonAddUpdate;
//
    List<Hero> heroList;

    boolean isUpdating = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        buttonAddUpdate = findViewById(R.id.buttonAddUpdate);
        progressBar =  findViewById(R.id.progressBar);
        listView =  findViewById(R.id.listViewHeroes);
        editTextHeroId = findViewById(R.id.editTextHeroId);
        editTextName =  findViewById(R.id.editTextName);
        editTextRealname =  findViewById(R.id.editTextRealname);
        ratingBar =  findViewById(R.id.ratingBar);
        spinnerTeam =  findViewById(R.id.spinnerTeamAffiliation);
        heroList = new ArrayList<>();
        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdating) {
//                    updateHero();
                } else {
                    createHero();
                }
            }
        });
//        readHeroes();
    }


    private void createHero() {

        String name = editTextName.getText().toString().trim();
        String realname = editTextRealname.getText().toString().trim();
        String ratingBara = ratingBar.getText().toString().trim();
        String spinnerTeama = spinnerTeam.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Please enter name");
            editTextName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            ratingBar.setError("Please enter Address");
            editTextName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            spinnerTeam.setError("Please enter Message");
            editTextName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(realname)) {
            editTextRealname.setError("Please enter Phone Number");
            editTextRealname.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("realname", realname);
        params.put("rating", ratingBara);
        params.put("teamaffiliation", spinnerTeama);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_HERO, params, CODE_POST_REQUEST);
        request.execute();
    }





    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
//                    refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    class HeroAdapter extends ArrayAdapter<Hero> {
        List<Hero> heroList;

        public HeroAdapter(List<Hero> heroList) {
            super(SuggestionActivity.this, R.layout.layout_hero_list_admin, heroList);
            this.heroList = heroList;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_hero_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);

            final Hero hero = heroList.get(position);

            textViewName.setText(hero.getName());

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SuggestionActivity.this);

                    builder.setTitle("From " + hero.getName() + "|" + hero.getPhoneNumber())
                            .setMessage("Message : " + hero.getMessage())
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setIcon(android.R.drawable.ic_dialog_info)
                            .show();

                }
            });return listViewItem;
        }
    }
}
