package com.example.midigiwordapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.UiModeManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    List<Model> list;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.my_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swapview);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        list = new ArrayList<>();
        recyclerviewInit();
        retrofitInit();
        swapDelete();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                retrofitInit();
            }
        });
    }

    private void swapDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // this method is called
                // when the item is moved.
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                Model deletedCourse = list.get(viewHolder.getAdapterPosition());

                // below line is to get the position
                // of the item at that position.
                int position = viewHolder.getAdapterPosition();

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                list.remove(viewHolder.getAdapterPosition());

                // below line is to notify our item is removed from adapter.
                recyclerAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                // below line is to display our snackbar with action.
                Snackbar.make(recyclerView,deletedCourse.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // adding on click listener to our action of snack bar.
                        // below line is to add our item to array list with a position.
                        list.add(position, deletedCourse);

                        // below line is to notify item is
                        // added to our adapter class.
                        recyclerAdapter.notifyItemInserted(position);
                    }
                }).show();
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(recyclerView);
    }

    public void retrofitInit(){
        Api api=RetrofitClient.getClient().create(Api.class);
        Call<List<Model>> call = api.getGetails();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                list=response.body();
                Log.e("Api Error", String.valueOf(response.body()));
                recyclerAdapter.setList(list);

            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Log.e("Api Error",t.getMessage());


            }
        });
    }
     public void recyclerviewInit(){
         LinearLayoutManager layoutManager = new LinearLayoutManager(this);
         recyclerView.setLayoutManager(layoutManager);
         recyclerAdapter = new RecyclerAdapter(getApplicationContext(),list);
         recyclerView.setAdapter(recyclerAdapter);
     }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Back two times to close apps", Toast.LENGTH_SHORT).show(); }
        mBackPressed = System.currentTimeMillis();
    }
}