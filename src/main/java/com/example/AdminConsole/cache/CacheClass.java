package com.example.AdminConsole.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class CacheClass {

    private LoadingCache<String,Integer> loadingCache;

    public CacheClass() {
        super();
        loadingCache = CacheBuilder.newBuilder().expireAfterWrite(25 ,TimeUnit.SECONDS)
                .maximumSize(100).build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    public void adduserValue(String name){
        int attempt =0;
        try {
            attempt= loadingCache.get(name) +1;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        loadingCache.put(name,attempt);
    }

    public Integer getValue(String username){
        try {
            return loadingCache.get(username);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
