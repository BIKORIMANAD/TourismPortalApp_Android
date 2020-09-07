package com.example.tourism_portal;

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
public class CreateSiteActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter_site adapterSite;
    private List<Site> siteList;
    ApiInterface_site apiInterfaceSite;
    Adapter_site.RecyclerViewClickListener listener;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_site);

        apiInterfaceSite = ApiClient_site.getApiClient().create(ApiInterface_site.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new Adapter_site.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(CreateSiteActivity.this, EditorActivity_site.class);
                intent.putExtra("id", siteList.get(position).getId());
                intent.putExtra("name", siteList.get(position).getName());
                intent.putExtra("species", siteList.get(position).getSpecies());
                intent.putExtra("breed", siteList.get(position).getBreed());
                intent.putExtra("gender", siteList.get(position).getGender());
                intent.putExtra("picture", siteList.get(position).getPicture());
                intent.putExtra("birth", siteList.get(position).getBirth());
                startActivity(intent);

            }

            @Override
            public void onLoveClick(View view, int position) {

                final int id = siteList.get(position).getId();
                final Boolean love = siteList.get(position).getLove();
                final ImageView mLove = view.findViewById(R.id.love);

                if (love){
                    mLove.setImageResource(R.drawable.likeof);
                    siteList.get(position).setLove(false);
                    updateLove("update_love", id, false);
                    adapterSite.notifyDataSetChanged();
                } else {
                    mLove.setImageResource(R.drawable.likeon);
                    siteList.get(position).setLove(true);
                    updateLove("update_love", id, true);
                    adapterSite.notifyDataSetChanged();
                }

            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateSiteActivity.this, EditorActivity_site.class));
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
        searchView.setQueryHint("Search site...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapterSite.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterSite.getFilter().filter(newText);
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
            Intent intent = new Intent(CreateSiteActivity.this,CreateSiteActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_trip) {
            Intent intent = new Intent(CreateSiteActivity.this,MainActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.View_Suggestions) {
            Intent intent = new Intent(CreateSiteActivity.this,SuggestionActivity.class);
            startActivity(intent);
            return true;
        }


        if (id == R.id.action_logout) {
            Intent intent = new Intent(CreateSiteActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void getPets(){

        Call<List<Site>> call = apiInterfaceSite.getPets();
        call.enqueue(new Callback<List<Site>>() {
            @Override
            public void onResponse(Call<List<Site>> call, Response<List<Site>> response) {
                progressBar.setVisibility(View.GONE);
                siteList = response.body();
                Log.i(CreateSiteActivity.class.getSimpleName(), response.body().toString());
                adapterSite = new Adapter_site(siteList, CreateSiteActivity.this, listener);
                recyclerView.setAdapter(adapterSite);
                adapterSite.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Site>> call, Throwable t) {
                Toast.makeText(CreateSiteActivity.this, "rp :"+
                        t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateLove(final String key, final int id, final Boolean love){

        Call<Site> call = apiInterfaceSite.updateLove(key, id, love);

        call.enqueue(new Callback<Site>() {
            @Override
            public void onResponse(Call<Site> call, Response<Site> response) {

                Log.i(CreateSiteActivity.class.getSimpleName(), "Response "+response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(CreateSiteActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateSiteActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Site> call, Throwable t) {
                Toast.makeText(CreateSiteActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPets();
    }

}
