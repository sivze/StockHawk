package com.sam_chordas.android.stockhawk.service;

import com.google.android.gms.gcm.TaskParams;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.sam_chordas.android.stockhawk.R;

/**
 * Created by sam_chordas on 10/1/15.
 */
public class StockIntentService extends IntentService {

  public StockIntentService(){
    super(StockIntentService.class.getName());
  }

  public StockIntentService(String name) {
    super(name);
  }

  @Override protected void onHandleIntent(Intent intent) {
    Log.d(StockIntentService.class.getSimpleName(), "Stock Intent Service");
    StockTaskService stockTaskService = new StockTaskService(this);
    Bundle args = new Bundle();
    if (intent.getStringExtra(getString(R.string.key_tag)).equals(getString(R.string.value_add))){
      args.putString(getString(R.string.key_symbol), intent.getStringExtra(getString(R.string.key_symbol)));
    }
    // We can call OnRunTask from the intent service to force it to run immediately instead of
    // scheduling a task.
    try {
      stockTaskService.onRunTask(new TaskParams(intent.getStringExtra(getString(R.string.key_tag)), args));
    } catch (Exception e){
      //show invalid ticker error on adding invalid ticker
      Handler handler = new Handler(getMainLooper());
      handler.post(new Runnable() {
        @Override
        public void run() {
          Toast.makeText(getApplicationContext(), getString(R.string.invalid_stock_ticker), Toast.LENGTH_SHORT)
          .show();
        }
      });
    }

  }
}
