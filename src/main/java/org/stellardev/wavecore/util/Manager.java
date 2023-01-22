package org.stellardev.wavecore.util;

import java.util.HashMap;
import java.util.Map;

public abstract class Manager<K, V> {

    private final Map<K, V> map;

    public Manager(){
        this.map = new HashMap<>();
    }
    public abstract K getKey(V obj);

   public void insert(V obj){
       map.put(getKey(obj), obj);
   }

   public V get(K key){
       return map.get(key);
   }

    public void remove(K key){
         map.remove(key);
    }



}
