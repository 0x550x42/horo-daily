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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ProKerala extends Fragment {

    private static final String TAG = "ProKerala";
    private static final String url = "http://www.prokerala.com/astrology/horoscope/?sign=pisces";
    TextView tv;

    public ProKerala() {
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

                Document doc = Jsoup.connect(urlString).get();
                description = doc.select("p").get(1).text() + "**************" + doc.select("p").get(2).text() + "**************" + doc.select("p").get(3).text() + "**************" + doc.select("p").get(4).text();

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
