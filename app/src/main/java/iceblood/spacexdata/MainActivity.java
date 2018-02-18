package iceblood.spacexdata;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import iceblood.spacexdata.loaders.GsonLoader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<RocketData>>{

    private Bundle mBundle;
    public static final int LOADER_ID = 1;
    private Loader<List<RocketData>> mLoader;

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RocketDataAdapter rocketDataAdapter;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = getWindow().getDecorView();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        rocketDataAdapter = new RocketDataAdapter(this);
        recyclerView.setAdapter(rocketDataAdapter);

        rocketDataAdapter.listener = new RocketDataAdapter.Listener() {
            @Override
            public void onItemClick(String url) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        };

        mBundle = new Bundle();
        mBundle.putInt(GsonLoader.ARG_WORD, 2017);
        mLoader = getSupportLoaderManager().initLoader(LOADER_ID, mBundle, this);
        setProgressBarVisible();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<RocketData>> onCreateLoader(int id, Bundle args) {
        Loader<List<RocketData>> mLoader = null;
        if (id == LOADER_ID) {
            mLoader = new GsonLoader(this, args);
        }
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<RocketData>> loader, List<RocketData> data) {
        setProgressBarGone();
        if(data != null){
        setRocketDataList(data);
        }else {
            showErrorConnection();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<RocketData>> loader) {

    }

    public void setRocketDataList(List<RocketData> rocketDataList) {
        rocketDataAdapter.setRocketDataList(rocketDataList);
        rocketDataAdapter.notifyDataSetChanged();
    }

    public void setProgressBarVisible(){
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }
    public void setProgressBarGone(){
        progressBar.setVisibility(ProgressBar.GONE);
    }
    public void showErrorConnection(){
        Snackbar.make(view, view.getContext().getString(R.string.connection_fail), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
