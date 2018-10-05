package com.dungnb.gem.dynamicloadmore;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
  List<Item> items;
  MyAdapter adapter;
  RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    addData();
    InitView();
  }

  private void InitView() {
    recyclerView = findViewById(R.id.rcvData);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new MyAdapter(recyclerView, this, items);
    recyclerView.setAdapter(adapter);

    adapter.setLoadMore(new ILoadMore() {
      @Override
      public void onLoadMore() {
        if (items.size() <= 50) {
          items.add(null);
          adapter.notifyItemInserted(items.size() - 1);
          new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              items.remove(items.size() - 1);
              adapter.notifyItemRemoved(items.size());

              //Random more data

              int index = items.size();
              int end = index + 10;
              for (int i = index; i < end; i++) {
                String name = UUID.randomUUID().toString();
                Item item = new Item(name, name.length());
                items.add(item);
              }
              adapter.notifyDataSetChanged();
              adapter.setLoaded();
            }
          }, 2000);
        } else {
          Toast.makeText(MainActivity.this, "Load data compeleted !", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private void addData() {
    items = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      String name = UUID.randomUUID().toString();
      Item item = new Item(name, name.length());
      items.add(item);
    }
  }
}
