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
public class Today extends Fragment {

    private static final String TAG = "Today";
    private static final String url = "http://www.tarot.com/daily-horoscope/pisces";
    TextView tv;

    public Today() {
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
        protected String doInBackground(Void... params) {
            try {

                Document document;
                Elements elements;

                document = Jsoup.connect("http://www.tarot.com/daily-horoscope/pisces").get();
                elements = document.select("p.js-today_interp_copy");
                description = elements.text();

                description += "\n\n-------------------------------------------------------------------------\n\n";

                document = Jsoup.connect("http://www.astrolis.com/horoscopes/pisces").get();
                elements = document.select("span[itemprop='articleBody']");
                description += elements.text();

                description += "\n\n-------------------------------------------------------------------------\n\n";

                document = Jsoup.connect("http://www.prokerala.com/astrology/horoscope/?sign=pisces").get();
                description += document.select("p").get(1).text() + "**************" + document.select("p").get(2).text() + "**************" + document.select("p").get(3).text() + "**************" + document.select("p").get(4).text();

                description += "\n\n-------------------------------------------------------------------------\n\n";

                document = Jsoup.connect("http://www.ganeshaspeaks.com/pisces/pisces-daily-horoscope.action").get();
                elements = document.select("div.block100 span.nrmltxt");
                description += elements.text();

                description += "\n\n-------------------------------------------------------------------------\n\n";

                document = Jsoup.connect("http://www.cainer.com/today/piscest.html").get();
                elements = document.select("p");
                description += elements.text();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return description;
        }

        @Override
        protected void onPostExecute(String description) {
            tv.setText(description);
        }
    }

}
