package com.example.tourism_portal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.tourism_portal.models.Site;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;



public class SiteListActivity extends AppCompatActivity {

    private List<Site> siteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StudentListAdapter mAdapter;
    private String URL;
    public String SERVER;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);

        URL = getString(R.string.server) + "fetch_data.php?data=all";
        SERVER = getString(R.string.server);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new StudentListAdapter(siteList);

        recyclerView.setHasFixedSize(true);
//
        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Site site = siteList.get(position);
                //Toast.makeText(getApplicationContext(), site.getName() + " is selected!", Toast.LENGTH_SHORT).show();

                Intent studentDetailsIntent = new Intent(SiteListActivity.this, StudentDetailsActivity.class);
                studentDetailsIntent.putExtra("studentId", site.getId());

                startActivity(studentDetailsIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        requestStudentList();

        FloatingActionButton fab = findViewById(R.id.add_new_student);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SiteListActivity.this, SuggestionActivity.class);

                startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 10) {
                requestStudentList();
            }
        }
    }

    /*
     * Send Request to the Server to ask the Site List
     * */

    private void requestStudentList(){
        JsonArrayRequest request = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(SiteListActivity.this, "Couldn't get tourist Site list! Please try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<Site> sites = new Gson().fromJson(response.toString(), new TypeToken<List<Site>>() {
                        }.getType());

                        siteList.clear();
                        siteList.addAll(sites);

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(SiteListActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MyApplication.getInstance().addToRequestQueue(request);
    }

}
