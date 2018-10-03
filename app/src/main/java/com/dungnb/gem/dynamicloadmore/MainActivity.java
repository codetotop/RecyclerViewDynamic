package com.dungnb.gem.dynamicloadmore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
  List<Item> items;
  MyAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    addData();
  }

  private void addData() {
    for (int i = 0; i < 10; i++) {
      String name = UUID.randomUUID().toString();
      Item item = new Item(name, name.length());
      items.add(item);
    }
  }
}
