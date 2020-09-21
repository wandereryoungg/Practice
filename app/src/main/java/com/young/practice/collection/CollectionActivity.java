package com.young.practice.collection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.young.practice.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        List<String> arrayList = new ArrayList<>();
        arrayList.add("hello");

        List<String> linkedList = new LinkedList<>();
        linkedList.add("hello");

        //map储存数据，去重了的
        Set<String> hashSet = new HashSet<>();
        hashSet.add("hello");

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("hello", "bro");
        //https://www.cnblogs.com/williamjie/p/9099141.html

    }
}