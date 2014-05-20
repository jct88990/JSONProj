package com.feva.jsonproj;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class JSONProjActivity extends Activity implements OnClickListener {
	Button button1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		HttpURLConnection urlConnection = null;
		final EditText et = (EditText) findViewById(R.id.editText1);
		String source = et.getText().toString();
				
		try {
			/*URL url = new URL(
					"http://ajax.googleapis.com/ajax/services/language/translate?v=1.0&q="
							+ source + "&langpair=en|zh-TW");
			*/
			/*if(source.equals("")){
				source = "Cocacola";
			}*/
			
			URL url = new URL("https://graph.facebook.com/" + source);
			
			//URL url = new URL("http://rate-exchange.appspot.com/currency?from=CNY&to=HKD");
			
			
			urlConnection = (HttpURLConnection) url.openConnection();
			
			InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String json_result = br.readLine();

			final TextView tv = (TextView) findViewById(R.id.textView1);
			JSONObject jsonObject = new JSONObject(json_result);
			if (json_result != null) {
				//String answer = jsonObject.getJSONObject("responseData").getString("translatedText");
				//String answer = jsonObject.getString("responseDetails");
				//tv.setText(answer);
				
				String about = jsonObject.getString("about");
				tv.setText(about);

				String imgLink = jsonObject.getJSONObject("cover").getString("source");
				ImageView iv = (ImageView) findViewById(R.id.imageView1);
				iv.setImageBitmap(GetURLBitmap(new URL(imgLink)));	
							
			}

			br.close();
			in.close();
		} catch (Exception E) {
			Log.e("and", "Err:" + E);
		} finally {
			urlConnection.disconnect();
		}
	}
	public static Bitmap GetURLBitmap(URL url) {
		try {
			URLConnection conn = url.openConnection();
			conn.connect();
			InputStream isCover = conn.getInputStream();
			Bitmap bmpCover = BitmapFactory.decodeStream(isCover);
			isCover.close();
			return bmpCover;
		} catch (Exception e) {
			return null;
		}
	}	
	
}