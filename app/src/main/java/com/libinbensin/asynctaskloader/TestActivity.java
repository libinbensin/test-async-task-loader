package com.libinbensin.asynctaskloader;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    private Snackbar mSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading(view);
                loadData();
            }
        });
    }

    private void showLoading(View view){
        mSnackbar = Snackbar.make(view, "Loading..", Snackbar.LENGTH_INDEFINITE);
        mSnackbar.show();
        TextView textView = (TextView) findViewById(R.id.hello_text);
        textView.setText("Loading..");
    }

    private void hideLoading(){
        if(mSnackbar != null) {
            mSnackbar.dismiss();
        }
    }

    private void loadData(){
        new LoaderClient<Book>(this)
                .load(new LoaderClient.LoaderClientCallback<Book>() {
                    @Override
                    public Book onLoadInBackground() {
                        return new NetworkAPI().getBooks();
                    }

                    @Override
                    public void onResult(Book result) {
                        TextView textView = (TextView) findViewById(R.id.hello_text);
                        if(result != null) {
                            textView.setText(result.getName());
                        }else {
                            textView.setText("Request Failed.");
                        }
                        hideLoading();
                    }

                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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
}
