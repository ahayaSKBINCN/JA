package com.ahaya.earthquakeviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.ahaya.earthquakeviewer.base.EarthquakeListFragment;
import com.ahaya.earthquakeviewer.base.EarthquakeModel;
import com.ahaya.earthquakeviewer.compass.CompassActivity;
import com.ahaya.earthquakeviewer.preferences.PreferencesActivity;
import com.ahaya.earthquakeviewer.starpicker.StarSignPicker;


public class MainActivity extends AppCompatActivity implements EarthquakeListFragment.OnListFragmentInteractionListener {
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    private static final int PICK_STARSIGN = 1;
    EarthquakeListFragment earthquakeListFragment;
    Button linkToCompass;
    Button linkToStarSignPicker;
    TextView textView;

    EarthquakeModel earthquakeModel;

    private static final int MENU_REFERENCES= Menu.FIRST+1;

    private static final int SHOW_PREFERENCES = 1;

    private static final String SEEN_WARNING_KEY = "SEEN_WARNING_KEY";

    private boolean SeenWarning = false;


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //此处千万不要setContentView(VIEW)；会导致组件行为异常
//        setContentView(R.layout.activity_main);
        //保存与UI相关的状态
        outState.putBoolean(SEEN_WARNING_KEY,SeenWarning);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
//通过代码添加fragment；
//        if(savedInstanceState==null){
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.fragment_container,new MainFragment());
//            fragmentTransaction.commit();
//        }
// Android 会在配置更改后，自动重新添加之间添加过的任何Fragment
// 因此，只有在自动重启时才添加它
        if (savedInstanceState == null) {
            FragmentTransaction ft = fm.beginTransaction();
            earthquakeListFragment = new EarthquakeListFragment();
            ft.add(R.id.main_activity_linear, earthquakeListFragment, TAG_LIST_FRAGMENT);
            ft.commitNow();
        } else {
            earthquakeListFragment = (EarthquakeListFragment) fm.findFragmentByTag(TAG_LIST_FRAGMENT);
        }
        //获取保存的状态；
        if(savedInstanceState!=null&&savedInstanceState.containsKey(SEEN_WARNING_KEY)){
            SeenWarning = savedInstanceState.getBoolean(SEEN_WARNING_KEY);
        }

        //TODO 暂时使用模拟数据
        linkToCompass = findViewById(R.id.button_go_to_compass);
        LinkToCompass(this, linkToCompass);
        linkToStarSignPicker = findViewById(R.id.pick_star_btn);
        LinkToStarPicker(linkToStarSignPicker);
        textView = findViewById(R.id.selected_starSign_textView);
        earthquakeModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(EarthquakeModel.class);
    }


    private void LinkToCompass(final Context context, Button btn) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CompassActivity.class);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
    }

    private void LinkToStarPicker(Button btn) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("starsigns://"));
                    startActivityForResult(intent, PICK_STARSIGN);
                    //Intent.setExtra
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_STARSIGN) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                String selectedStarSign = data.getStringExtra(StarSignPicker.EXTRA_SIGN_NAME);
               textView.setText(selectedStarSign);
            }
        }
    }

//    private void queryActivityList () {
//        PackageManager packageManager = getPackageManager();
//        Intent intent = new Intent();
//        intent.setType("vnd.android.cursor.item/vnd.com.professionalandroid.provider.moonbase");
//        intent.addCategory(Intent.CATEGORY_SELECTED_ALTERNATIVE);
//        int flags = PackageManager.MATCH_ALL;
//
//        List<ResolveInfo> actions;
//        actions = packageManager.queryIntentActivities(intent,flags);
//        ArrayList<CharSequence> labels = new ArrayList<>();
//        Resources r = getResources();
//        for(ResolveInfo action: actions)
//            labels.add(action.nonLocalizedLabel);
//
//
//    }
    /*用于创建动作菜单解析Intent 的框架代码如下
     * Intent intent = new Intent();
     * intent.setData(MyProvider.CONTENT_URI);
     * intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
     * **/

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0,MENU_REFERENCES,Menu.NONE,R.string.menu_setting);
        return true;


        /* 创建用于解析菜单中应该出现哪些操作的Intent
        Intent intent = new Intent();
        intent.setType(
                "vnd.android.cursor.item/vnd.com.professionalandroid.provider.moonbase"
        );
        intent.addCategory(Intent.CATEGORY_SELECTED_ALTERNATIVE);

        //普通菜单选项，用于为要添加的菜单项设置组和id值；
        int menuGroup = 0;
        int menuItenId = 0;
        int menuItenOrder = Menu.NONE;

        //提供调用操作的组件名--通常是当前的Activity
        ComponentName caller = getComponentName();
        //定义应首先添加的Intent；
        Intent[] specificIntentItems = null;
        //使用先前Intent中创建的菜单选项填充这个数组；
        MenuItem[] outSpecificItems = null;
        //设置任何一个可选的标记；
        int flags = Menu.FLAG_APPEND_TO_GROUP;
        //填充菜单
        menu.addIntentOptions(menuGroup,
                menuItenId,
                menuItenOrder,
                caller,
                specificIntentItems,
                intent,
                flags,
                outSpecificItems);
        return true;
         **/
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case MENU_REFERENCES:
                startActivityForResult(new Intent(this, PreferencesActivity.class), SHOW_PREFERENCES);
                return true;
        }
        return false;
    }

    @Override
    public void onListFragmentRefreshRequested() {
        updateEarthquakes();
    }

    private void updateEarthquakes(){
        earthquakeModel.loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateEarthquakes();
    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//    }
    //    public Thread createNewThread () {
//        return new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //Perform Network operations and processing;
//                final MyDataClass result = loadInBackground();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        deliverResult(result);
//                    }
//                });
//            }
//        });
//    }

    /**
     * public void createAsyncTask (){
     * String input = "redrum...redrum";
     * new LAsyncTask().execute(input);
     * }
     */
}