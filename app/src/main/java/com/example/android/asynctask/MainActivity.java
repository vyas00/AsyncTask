package com.example.android.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private static final String TAG = "MainActivity";
    ExampleAsyncTask task;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        Log.d(TAG, "onCreate: MainActivity");
      task= new ExampleAsyncTask(this);
      start=findViewById(R.id.bt_start);

    }

    public void startAsyncTask(View v) {
        task.execute(15);
    }


    public static class ExampleAsyncTask extends AsyncTask<Integer, Integer, String> {

        private WeakReference<MainActivity> activityWeakReference;

        ExampleAsyncTask(MainActivity activity) {
            activityWeakReference = new WeakReference<MainActivity>(activity); }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setVisibility(View.VISIBLE);
            Log.d(TAG, "onPreExecute: async task");
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for (int i = 0; i < integers[0]; i++) {
                publishProgress((i * 100) / integers[0]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isCancelled()) return "Cancelled";
            }
            Log.d(TAG, "doInBackground: finished");
            return "Finished!";
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.progressBar.setProgress(values[0]);
            Log.d(TAG, "onProgressUpdate: async task");
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            activity.progressBar.setProgress(0);
            activity.progressBar.setVisibility(View.INVISIBLE);
            Log.d(TAG, "onPostExecute: async task");
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d(TAG, "onCancelled: async task");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: MainActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: MainActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: MainActivity");
      if(task.isCancelled()==false)  task.cancel(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: MainActivity"); }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: MainActivity"); }

}