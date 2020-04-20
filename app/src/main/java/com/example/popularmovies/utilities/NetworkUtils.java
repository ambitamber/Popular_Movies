package com.example.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String STATIC_MOIVES_URL = "https://api.themoviedb.org/3/search/movie?";
    final static String QUERY_PARAM = "query";
    final static String API_KEY_path = "api_key";
    final static String API_KEY = "b640aea96524ead852f99db7104962de";


    public static URL buildUrl(String movieQuery) {
        Uri builtUri = Uri.parse(STATIC_MOIVES_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, movieQuery)
                .appendQueryParameter(API_KEY_path, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
