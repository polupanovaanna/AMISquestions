package ru.fmcs.hse.database;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PrewiewAdapterWrapper<T extends RecyclerView.ViewHolder> {
    DatabaseReference databaseRef;
    final Map<String, Integer> currentPosts = new HashMap<>();
    private Class<?> clz;
    private ValueEventListener updater;
    private RecyclerView.Adapter<T> adapter;
    private ArrayList dataHolder;
    private DatabaseOrdering currentOrder = Ordering.DEFAULT;
    private DatabaseFiltering currentFilter = Ordering.DEFAULT_FILTER;
    private DatabaseGlobalOrdering globalOrder;
    private String currentFilterString = "";
    private boolean reversed = false;
    private int limit = 3;

    public PrewiewAdapterWrapper(Class<?> componentType) {
        clz = componentType;
        updateOrder();
    }

    public void init(RecyclerView.Adapter<T> adapter, ArrayList holder) {

        try {
            databaseRef = FirebaseDatabase.getInstance().getReference((String)
                    clz.getField("GROUP_ID").get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        this.adapter = adapter;
        dataHolder = holder;
        updater = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DataSnapshot> tmp = new ArrayList<>();
                for (DataSnapshot post : snapshot.getChildren()) {
                    tmp.add(post);
                }
                if (reversed) Collections.reverse(tmp);
                for (DataSnapshot post : tmp) {
                    if (post.hasChildren() && post.getKey() != null) {
                        if (currentPosts.containsKey(post.getKey())) {
                            holder.set(currentPosts.get(post.getKey()), post.getValue(clz));
                        } else {
                            currentPosts.put(post.getKey(), holder.size());
                            holder.add(post.getValue(clz));
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        startUpdating();
    }

    public void changeOrdering(DatabaseOrdering nOrder) {
        stopUpdating();
        globalOrder = Ordering.map(currentFilter, nOrder, currentFilterString);
        adapter.notifyDataSetChanged();
        startUpdating();
    }

    public void getMore(String value) {
        limit++;
        databaseRef.removeEventListener(updater);
        if (!reversed) {
            globalOrder.getQuery(databaseRef).limitToFirst(limit).startAt(value).addValueEventListener(updater);
        } else {
            globalOrder.getQuery(databaseRef).limitToFirst(limit).endAt(value).addValueEventListener(updater);
        }
    }

    private void updateOrder() {
        globalOrder = Ordering.map(currentFilter, currentOrder, currentFilterString);
    }

    public void addFiltering(DatabaseFiltering nFilter, String child) {
        stopUpdating();
        currentFilter = nFilter;
        currentFilterString = child;
        updateOrder();
        adapter.notifyDataSetChanged();
        startUpdating();
    }

    public void removeFilter() {
        stopUpdating();
        currentFilter = Ordering.DEFAULT_FILTER;
        currentFilterString = null;
        updateOrder();
        startUpdating();
    }

    public void removeOrder() {
        stopUpdating();
        currentOrder = Ordering.DEFAULT;
        updateOrder();
        startUpdating();
    }

    public void getMore(int value) {
        limit += 2;
        databaseRef.removeEventListener(updater);
        if (currentFilter.equals(Ordering.DEFAULT_FILTER)) {
            if (!reversed) {
                globalOrder.getQuery(databaseRef).limitToFirst(limit).startAt(value).addValueEventListener(updater);
            } else {
                globalOrder.getQuery(databaseRef).limitToLast(limit).endAt(value).addValueEventListener(updater);
            }
        }
    }

    public void reverse() {
        reversed = !reversed;
        stopUpdating();
        startUpdating();
    }

    private void startUpdating() {
        if (currentFilter.equals(Ordering.DEFAULT_FILTER)) {

            if (!reversed) {
            globalOrder.getQuery(databaseRef).limitToFirst(limit).addValueEventListener(updater);
        } else {
            globalOrder.getQuery(databaseRef).limitToLast(limit).addValueEventListener(updater);
        }
        }else{
            globalOrder.getQuery(databaseRef).addValueEventListener(updater);
        }
    }

    private void stopUpdating() {
        databaseRef.removeEventListener(updater);
        dataHolder.clear();
        currentPosts.clear();
    }
}
