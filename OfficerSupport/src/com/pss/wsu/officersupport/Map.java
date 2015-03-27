package com.pss.wsu.officersupport;

import java.net.UnknownHostException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class Map extends FragmentActivity {
	static final LatLng TutorialsPoint = new LatLng(21, 57);
	public LatLng newPoint = null;
	private GoogleMap googleMap;
	public String latitude;
	public String longitude = "It fucked up";
	double longi = 0.0;
	double lat = 0.0;
	String firstName;
	String lastName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		

		Thread thread = new Thread() {
			@Override
			public void run() {
				try {

					Mongo mc = null;
					try {
						mc = new Mongo("ds053607.mongolab.com", 53607);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (MongoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					DB db = mc.getDB("pdsdwsu");
					boolean auth = db.authenticate("dansdWSU",
							"wrightstatesd1".toCharArray());
					System.out.println("Authorization is " + auth);
					DBCollection table = db.getCollection("location");

					BasicDBObject searchQuery = (BasicDBObject) JSON
							.parse("{'locations.firstName':'Jane'}");

					DBCursor cursor = table.find(searchQuery);
					String jsonFile = "";
					while (cursor.hasNext()) {
						jsonFile += cursor.next();
					}

					JSONObject obj = new JSONObject(jsonFile);
					JSONArray arr = obj.getJSONArray("locations");
					for (int i = 0; i < arr.length(); i++) {
						latitude = arr.getJSONObject(i).getString("lat");
						longitude = arr.getJSONObject(i).getString("long");
						firstName = arr.getJSONObject(i).getString("firstName");
						lastName = arr.getJSONObject(i).getString("lastName");

					}

					System.out.println(latitude);
					System.out.println(longitude);
					
					cursor.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		thread.start();

		
		try {

			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
			}
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			 
			boolean check = true;
			while(check){
				try{
					lat = Double.parseDouble(latitude);
					longi = Double.parseDouble(longitude);
					check = false;
				}catch(Exception e){
					
				}
			}
			
			
			newPoint = new LatLng(lat, longi);
			Bitmap pic = BitmapFactory.decodeResource(this.getResources(),  R.drawable.badge);
			pic = Bitmap.createScaledBitmap(pic, 50, 50, true);
			Marker TP = googleMap.addMarker(new MarkerOptions().position(
					newPoint).title(firstName + " " + lastName).icon(BitmapDescriptorFactory.fromBitmap(pic)));
			
			googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(newPoint, 9, 0, 0)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}