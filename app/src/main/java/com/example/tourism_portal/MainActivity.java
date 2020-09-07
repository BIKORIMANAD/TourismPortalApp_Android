package com.example.tourism_portal;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by 18-09-2019 End. MadeCode.
 */
public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter;
    private List<Trips> tripsList;
    ApiInterface apiInterface;
    Adapter.RecyclerViewClickListener listener;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new Adapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra("id", tripsList.get(position).getId());
                intent.putExtra("name", tripsList.get(position).getName());
                intent.putExtra("species", tripsList.get(position).getSpecies());
                intent.putExtra("breed", tripsList.get(position).getBreed());
                intent.putExtra("gender", tripsList.get(position).getGender());
                intent.putExtra("picture", tripsList.get(position).getPicture());
                intent.putExtra("birth", tripsList.get(position).getBirth());
                startActivity(intent);

            }

            @Override
            public void onLoveClick(View view, int position) {

                final int id = tripsList.get(position).getId();
                final Boolean love = tripsList.get(position).getLove();
                final ImageView mLove = view.findViewById(R.id.love);

                if (love){
                    mLove.setImageResource(R.drawable.likeof);
                    tripsList.get(position).setLove(false);
                    updateLove("update_love", id, false);
                    adapter.notifyDataSetChanged();
                } else {
                    mLove.setImageResource(R.drawable.likeon);
                    tripsList.get(position).setLove(true);
                    updateLove("update_love", id, true);
                    adapter.notifyDataSetChanged();
                }

            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EditorActivity.class));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setQueryHint("Search trip...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_site) {
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_trip) {
            Intent intent = new Intent(MainActivity.this,CreateSiteActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.View_Suggestions) {
            Intent intent = new Intent(MainActivity.this,ViewSuggestionActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_logout) {
//


            Fragment profile = new ProfileFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame,profile);
            ft.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getPets(){

        Call<List<Trips>> call = apiInterface.getPets();
        call.enqueue(new Callback<List<Trips>>() {
            @Override
            public void onResponse(Call<List<Trips>> call, Response<List<Trips>> response) {
                progressBar.setVisibility(View.GONE);
                tripsList = response.body();
                Log.i(MainActivity.class.getSimpleName(), response.body().toString());
                adapter = new Adapter(tripsList, MainActivity.this, listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Trips>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "rp :"+
                        t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();


            }
        });
    }

    public void updateLove(final String key, final int id, final Boolean love){

        Call<Trips> call = apiInterface.updateLove(key, id, love);

        call.enqueue(new Callback<Trips>() {
            @Override
            public void onResponse(Call<Trips> call, Response<Trips> response) {

                Log.i(MainActivity.class.getSimpleName(), "Response "+response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Trips> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPets();
    }

}
