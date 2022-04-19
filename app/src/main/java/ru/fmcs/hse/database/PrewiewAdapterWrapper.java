package ru.fmcs.hse.database;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrewiewAdapterWrapper<T extends RecyclerView.ViewHolder> {
    DatabaseReference databaseRef;
    final Map<String, Integer> currentPosts = new HashMap<>();
    Class<?> clz;

    public PrewiewAdapterWrapper(Class<?> componentType) {
        clz = componentType;
    }

    public void init(RecyclerView.Adapter<T> adapter, ArrayList holder , String order) {

        try {
            System.out.println((String) clz.getField("GROUP_ID").get(null));
            databaseRef = FirebaseDatabase.getInstance().getReference((String)
                    clz.getField("GROUP_ID").get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        databaseRef.orderByChild(order).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot post : snapshot.getChildren()) {
                    if (post.hasChildren() && post.getKey() != null) {
                        if (currentPosts.containsKey(post.getKey())) {
                            holder.set(currentPosts.get(post.getKey()), post.getValue(clz));
                            adapter.notifyItemChanged(currentPosts.get(post.getKey()));
                        } else {
                            currentPosts.put(post.getKey(), holder.size());
                            holder.add(post.getValue(clz));
                            adapter.notifyDataSetChanged();
                            System.out.println(holder.size());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
