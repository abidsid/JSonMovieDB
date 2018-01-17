package com.apps.abjs.jsonmoviedb.models;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.apps.abjs.jsonmoviedb.MainActivity;
import com.apps.abjs.jsonmoviedb.R;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajuna on 7/3/2017.
 */

public class Second_Activity extends AppCompatActivity {

    private ListView list;
    private ProgressDialog dialog;
    JSONTask.movieadapter adapter;
    private String abc;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create default options which will be used for every
//  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())

                .defaultDisplayImageOptions(defaultOptions)

                .build();

        ImageLoader.getInstance().init(config); // Do it on Application start
        setContentView(R.layout.nextpage);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading");
        list = (ListView)findViewById(R.id.list);





//        Parcelable[] mylist=getIntent().getParcelableArrayExtra("list");
        //ArrayList<MovieModel> mylist=new ArrayList<>();
        //Bundle bundle = getIntent().getExtras();
//        Log.d("Activity B", mylist.toString());
//        if(bundle!=null){
//            mylist = (List<MovieModel>) bundle.getParcelableArrayList("Key");
//            Log.d("List", mylist.toString());
//        }

//        MainActivity.JSONTask.movieadapter adapter = new MainActivity.JSONTask.movieadapter(getApplicationContext(),R.layout.second_activity,result);
//        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Search", query);
                abc=query;
                Log.d("ABC", abc);
                new JSONTask().execute("https://api.themoviedb.org/3/search/movie?query="+abc+"&api_key=75d6a21a24cc88a4b60fcd0246f02ad1");
//                Log.d("API string","https://api.themoviedb.org/3/search/movie?query="+query+"&api_key=75d6a21a24cc88a4b60fcd0246f02ad1");

//                new JSONTask().execute("https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesData.txt");
//                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                final LinearLayout layout = (LinearLayout)findViewById(R.id.layout);

                layout.setBackgroundColor(Color.WHITE);

//                if(newText == ""){
//                    Intent intent = new Intent();
//                    intent.setClass(getApplicationContext(), Second_Activity.class);
//
//                    startActivity(intent);
//                    return false;
//                }
//                else {
//                    new JSONTask().execute("https://api.themoviedb.org/3/search/movie?query=" + newText + "&api_key=75d6a21a24cc88a4b60fcd0246f02ad1");
//                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    public class JSONTask extends AsyncTask<String, String, List<MovieModel>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<MovieModel> doInBackground(String... urls) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urls[0]);

                try {

                    connection = (HttpURLConnection) url.openConnection();
//                    Log.d("Hello", connection.toString());
                    connection.connect();


                    InputStream stream = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));


                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = reader.readLine()) != null) {

                        buffer.append(line);

                    }

                    String finalJson = buffer.toString();

                    JSONObject parentjson = new JSONObject(finalJson);
                    JSONArray parentarray = parentjson.getJSONArray("results");
                    Log.d("parentjson", parentjson.toString());


//                    StringBuffer finalbuffereddata = new StringBuffer();
                    ArrayList<MovieModel> moviemodelList = new ArrayList<>();
                    Gson gson = new Gson();

                    for (int i = 0; i < parentarray.length(); i++) {


                        JSONObject finalobject = parentarray.getJSONObject(i);
                        Log.d("Json", finalobject.toString());
                        MovieModel movieModel = gson.fromJson(finalobject.toString(), MovieModel.class);


//                        MovieModel movieModel = new MovieModel();
//
//                        movieModel.setMovie(finalobject.getString("movie"));
//                        movieModel.setYear(finalobject.getInt("year"));
//                        movieModel.setDirector(finalobject.getString("director"));
//                        movieModel.setDuration(finalobject.getString("duration"));
//                        movieModel.setImage(finalobject.getString("image"));
//                        movieModel.setRating((float) finalobject.getDouble("rating"));
//                        movieModel.setStory(finalobject.getString("story"));
//                        List<MovieModel.cast> castlist = new ArrayList<>();
//                        for (int j = 0; j < finalobject.getJSONArray("cast").length(); j++) {
//                            JSONObject castobject = finalobject.getJSONArray("cast").getJSONObject(j);
//                            MovieModel.cast cast = new MovieModel.cast();
//                            cast.setName(castobject.getString("name"));
//
//                            castlist.add(cast);
//                        }
//                        movieModel.setCastlist(castlist);
////                        Log.d("Movie", movieModel.getDirector());


                        moviemodelList.add(movieModel);
                    }


                    return moviemodelList;


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(List<MovieModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();


//            ArrayList<MovieModel> listnew = new ArrayList<MovieModel>();
//            listnew = result;
//            Log.d("MSg", result.toString());
            adapter = new movieadapter(getApplicationContext(), R.layout.second_activity, result);
            Log.d("adapter", adapter.toString());
            list.setAdapter(adapter);


//                Intent intent = new Intent(getApplicationContext(), Second_Activity.class);
//            intent.setClass(MainActivity.this, Second_Activity.class);
            //Bundle bundle = new Bundle();
            //bundle.putSerializable("Key", listnew);
            //Log.d("Activity A", bundle.toString());
//            bundle.putParcelableArrayList("Key", listnew);
//            bundle.putParcelableArrayList("my list", (ArrayList<? extends Parcelable>) listnew);
//                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) listnew);
//                Log.d("Intent", intent.toString());
//                startActivity(intent);

//            intent.putExtras("List", listnew );

//            startActivityForResult(intent, 0);


//            txt1.setText(result);
        }

        public class movieadapter extends ArrayAdapter {
            private LayoutInflater inflater;
            private List<MovieModel> movielist;
            private int resourse;
            Context context;

            public movieadapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<MovieModel> objects) {
                super(context, resource, objects);
                movielist = objects;
                this.context = context;
                this.resourse = resource;
                inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = inflater.inflate(resourse, null);
                    holder.poster_path = (ImageView) convertView.findViewById(R.id.poster_path);
                    holder.title = (TextView) convertView.findViewById(R.id.title);
                    holder.original_language = (TextView) convertView.findViewById(R.id.original_language);
                    holder.release_date = (TextView) convertView.findViewById(R.id.release_date);
                    holder.adult = (TextView) convertView.findViewById(R.id.adult);
//                    holder.director = (TextView) convertView.findViewById(R.id.director);
//                    holder.rating = (RatingBar) convertView.findViewById(R.id.ratingBar);
//                    holder.cast = (TextView) convertView.findViewById(R.id.cast);
                    holder.overview = (TextView) convertView.findViewById(R.id.overview);
                    holder.overview.setMovementMethod(new ScrollingMovementMethod());
                    convertView.setTag(holder);
                }
                else{
                    holder = (ViewHolder) convertView.getTag();
                }



                // Then later, when you want to display image
//                Log.d("Poster Path",movielist.get(position).getPoster_path());
//                ImageLoader.getInstance().displayImage(movielist.get(position).getPoster_path(), holder.poster_path); // Default options will be used
                String url = movielist.get(position).getPoster_path();
                Picasso.with(context).load(url).into(holder.poster_path);
                holder.title.setText(movielist.get(position).getTitle());
                Log.d("title",movielist.get(position).getTitle());
                holder.original_language.setText(movielist.get(position).getOriginal_language());
                holder.release_date.setText("Release Date: "+movielist.get(position).getRelease_date());
                holder.adult.setText("Adult: "+ movielist.get(position).isAdult());
//                holder.director.setText("Director: " + movielist.get(position).getDirector());
//                holder.rating.setRating(movielist.get(position).getRating() / 4);
//                StringBuffer stringbuffer = new StringBuffer();
//                for (MovieModel.cast cast1 : movielist.get(position).getCastlist()) {
//                    stringbuffer.append(cast1.getName() + ", ");
//
//                }
//                holder.cast.setText("Cast: " + stringbuffer);
                holder.overview.setText("Story: " + movielist.get(position).getOverview());


                return convertView;
            }
            class ViewHolder{
//                private ScrollView scrollview;
                private ImageView poster_path;
                private TextView title;
                private TextView original_language;
                private TextView release_date;
                private TextView adult;
                private TextView overview;

            }
        }
    }
}
