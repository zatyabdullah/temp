package com.fly.firefly;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidquery.util.AQUtility;
import com.fly.firefly.base.AQuery;
import com.fly.firefly.base.BaseFragmentActivity;
import com.fly.firefly.drawer.DrawerItem;
import com.fly.firefly.drawer.NavigationDrawerFragment;

import butterknife.ButterKnife;

//import com.actionbarsherlock.view.Menu;
//import com.actionbarsherlock.view.MenuItem;


public class MainFragmentActivity extends BaseFragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks
{

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private static MainFragmentActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aq = new AQuery(this);
        setMenuButton();
        ButterKnife.inject(this);
        instance = this;

        hideTitle();

        // Set up the drawer.
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

       //FragmentManager fragmentManager = getSupportFragmentManager();
       //fragmentManager.beginTransaction().replace(R.id.tab_container, TabButtomFragment.newInstance()).commit();


        // Inflate the "decor.xml"
       /* LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DrawerLayout drawer = (DrawerLayout) inflater.inflate(R.layout.decor, null); // "null" is important.

        // HACK: "steal" the first child of decor view
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
       //
        decor.removeView(child);
        FrameLayout container = (FrameLayout) drawer.findViewById(R.id.main_activity_fragment_container); // This is the container we defined just now.
        container.addView(child);

        // Make the drawer replace the first child
        decor.addView(drawer);
*/
        // Do what you want to do.......


       // FragmentManager fragmentManager = getSupportFragmentManager();
       // fragmentManager.beginTransaction().replace(R.id.main_activity_fragment_container, MainFragment.newInstance()).commit();

        //if (savedInstanceState == null) {
        //    goToSearchFragment();
        //}


    }

    public static Activity getContext() {
        //return instance.getApplicationContext();
        //return ActivityName.this;
        return instance;

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (isTaskRoot())
        {
            // clean the file cache with advance option
            long triggerSize = 3000000; // starts cleaning when cache size is
            // larger than 3M
            long targetSize = 2000000; // remove the least recently used files
            // until cache size is less than 2M
            AQUtility.cleanCacheAsync(this, triggerSize, targetSize);
        }
    }

    public void setMenuButton()
    {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.menubutton).visible();
        aq.id(R.id.menubutton).clicked(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mNavigationDrawerFragment.openDrawer();
            }
        });
    }

    @Override
    public void onNavigationDrawerItemSelected(int position, DrawerItem item)
    {
        // update the main content by replacing fragments
        /*try
        {
            if (item.getTag().equals("Gallery"))
            {
                Intent gallery = new Intent(this, GalleryView.class);
                gallery.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gallery);
            }
            else if (item.getTag().equals("PROFILE"))
            {
                Intent sbb = new Intent(this, MyProfileView.class);
                sbb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(sbb);
            }
            else if (item.getTag().equals("Home"))
            {
                Intent sbb = new Intent(this, PromotionsLandingView.class);
                sbb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(sbb);
            }
            else if (item.getTag().equals("SBB"))
            {
                Intent sbb = new Intent(this, ShopByBrandView.class);
                sbb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(sbb);
            }
            else if (item.getTag().equals("Newsletter"))
            {
                Intent newsletter = new Intent(this, NewsletterView.class);
                newsletter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newsletter.putExtra("mainlist", true);
                startActivity(newsletter);
            }
            else if (item.getTag().equals("Augmented"))
            {
                Intent intent = new Intent(this, AugmentedStart.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else if (item.getTag().equals("Merchant"))
            {
                Intent sbb = new Intent(this, MerchantListView.class);
                sbb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(sbb);
            }
            else if (item.getTag().equals("Badges"))
            {
                Intent sbb = new Intent(this, BadgesList.class);
                sbb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(sbb);
            }
            else if (item.getTag().equals("Contact Us"))
            {
                Intent sbb = new Intent(this, ContactusDetailView.class);
                sbb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(sbb);
            }
            else if (item.getTag().equals("Term & Condition"))
            {
                Intent sbb = new Intent(this, TncView.class);
                sbb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(sbb);

            }
            else if(item.getTag().equals("htmlName"))
            {
                Intent dynamicTnc = new Intent(this, DynamicTnC.class);
                dynamicTnc.putExtra("htmlId", item.getTag().toString());
                dynamicTnc.putExtra("htmlName", item.getTitle().toString());
                dynamicTnc.putExtra("htmlPath", item.getPath().toString());
                dynamicTnc.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dynamicTnc);
            }

            else if (item.getTag().equals("HEADER"))
            {
            }
            else
            {
                Intent department = new Intent(this, DepartmentView.class);
                department.putExtra("id", item.getTag().toString());
                department.putExtra("departmentLvl", Integer.toString(item.getLvl()));
                department.putExtra("name", item.getTitle().toString());
                Log.e("departmentName", item.getTitle().toString());
                department.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(department);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

    }

    public void restoreActionBar()
    {
        ActionBar actionBar = getActionBar();
        // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        // actionBar.setDisplayShowTitleEnabled(true);
        // actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();
        switch (id)
        {
            case R.id.action_settings:
                break;

            default:
                break;
        }*/
        return super.onOptionsItemSelected(item);
    }


}
