package xyz.udit.horodaily;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class GaneshaSpeaks extends Fragment {

    private static final String TAG = "GaneshaSpeaks";
    private static final String url = "http://www.ganeshaspeaks.com/pisces/pisces-daily-horoscope.action";
    TextView tv;

    public GaneshaSpeaks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_common, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        tv = (TextView)getView().findViewById(R.id.text_view);
        new FetchData(url, getContext()).execute();
    }

    class FetchData extends AsyncTask<Void, String, String> {

        private static final String TAG = "FetchData";

        Context context;
        URL url;
        String urlString;
        ProgressDialog progressDialog;
        String text, description;

        public FetchData(String url, Context ctx) {
            this.context = ctx;
            this.urlString = url;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Fetching your horoscope...");
            progressDialog.setCancelable(false);
            //progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                Document document = Jsoup.connect(urlString).get();
                Elements elements = document.select("span.nrmltxt");
                description = elements.get(2).text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return description;
        }

        @Override
        protected void onPostExecute(String description) {
            progressDialog.dismiss();
            tv.setText(description);
        }
    }

}
