package com.example.myactivity;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button button;
	private area ar;
	private ArrayList<area> list;
	public static final String ENCODING = "UTF-8";
    private ListView listview,listview1;
    private MyAdapter myAdapter,myAdapter2;
    private ArrayList<area> firstname;
    private ArrayList<area> secondname;
    private PopupWindow citypopupWindow;
    private String aa,bb;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		firstname = new ArrayList<area>();
		list = new ArrayList<area>();
		secondname = new ArrayList<area>();
		button = (Button) findViewById(R.id.button);
		initPopWindowForCitys();
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * ComponentName componetName = new ComponentName(
				 * //这个是另外一个应用程序的包名 "com.thestore.main", //这个参数是要启动的Activity
				 * "com.thestore.main.LoadingActivity"); Intent intent= new
				 * Intent(); //我们给他添加一个参数表示从apk1传过去的 Bundle bundle = new
				 * Bundle(); bundle.putString("arge1", "这是跳转过来的！来自apk1");
				 * intent.putExtras(bundle); intent.setComponent(componetName);
				 * MainActivity.this.startActivity(intent);
				 */
				// Intent intent= new Intent();
				/*Intent intent = new Intent(Intent.ACTION_SEND);
				// intent.putExtra(Intent.EXTRA_EMAIL, "hello haozi");
				MainActivity.this.startActivity(intent);*/
				
				
				try {
					
					citypopupWindow.setAnimationStyle(R.style.PopupAnimation);
					citypopupWindow.showAtLocation(v, Gravity.NO_GRAVITY,0, 0);
					citypopupWindow.update();
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
/*		
*/	}

	// 从assets 文件夹中获取文件并读取数据
	public String getFromAssets(String fileName) {
		String result = "";
		try {
			InputStream in = getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, ENCODING);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
	public void initPopWindowForCitys() {
		LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		View layout = inflater.inflate(R.layout.wheelpopupforcity, null);
		 listview = (ListView)layout.findViewById(R.id.list);
	     listview1  = (ListView)layout.findViewById(R.id.list1);
		layout.getBackground().setAlpha(130);
		layout.invalidate();
		citypopupWindow = new PopupWindow(layout, LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		Button btn_city_canle = (Button) layout.findViewById(R.id.pickcitycancle);
		Button pickcityconfirm = (Button) layout.findViewById(R.id.pickcityconfirm);
		btn_city_canle.getPaint().setFakeBoldText(true);
		btn_city_canle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				citypopupWindow.dismiss();
				citypopupWindow.setFocusable(false);
			}
		});
		pickcityconfirm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String cc = aa+bb;
				citypopupWindow.dismiss();
				citypopupWindow.setFocusable(false);
				Toast.makeText(MainActivity.this, cc, Toast.LENGTH_LONG).show();
			}
		});
		String str1 = getFromAssets("leibie.json");
		JSONObject js;
		try {
			js = new JSONObject(str1);
			String areaBeans = js.getString("areaBeans");
			JSONArray areaBean = new JSONArray(areaBeans);
			for (int i = 0; i < areaBean.length(); i++) {
				ar = new area();
				JSONObject json = (JSONObject)areaBean.get(i);
				ar.setAreaid(json.optString("areaid"));
				ar.setName(json.optString("name"));
				ar.setPinyin(json.optString("pinyin"));
				ar.setShortpinyin(json.optString("shortpinyin"));
				ar.setType(json.optString("type"));
				ar.setParentId(json.optString("parentId"));
				list.add(i, ar);
			}
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getType().equals("s")) {
					firstname.add(list.get(i));
				}
			}
		
			citypopupWindow.setFocusable(true);
			myAdapter = new MyAdapter(MainActivity.this,firstname);
			myAdapter2 = new MyAdapter(MainActivity.this, secondname);
			listview.setAdapter(myAdapter);
			listview1.setAdapter(myAdapter2);
			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					secondname.clear();
					for (int i = 0; i < list.size(); i++) {
						String id = firstname.get(arg2).getAreaid();
						aa = firstname.get(arg2).getName();
						if (list.get(i).getType().equals("c")) {
							if (list.get(i).getParentId().equals(id)) {
								secondname.add(list.get(i));
								
							}
						}
					}
					
					myAdapter2.notifyDataSetChanged();
				}
			});
			
			listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					bb = secondname.get(arg2).getName();
				}
			});
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
