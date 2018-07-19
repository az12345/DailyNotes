package com.carpediemsolution.dailynotes.utils;

import android.content.Context;

import java.net.URI;

public class UriValidator {

 private final Context context;

    public UriValidator(Context context) {
       this.context = context;
    }

    public boolean isFile(String uri) {
        return "file".equals(URI.create(uri).getScheme());
    }
}

