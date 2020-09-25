package com.example.myappnew.Activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myappnew.BottomNavigationViewBehavior;
import com.example.myappnew.Callback.LatestMoviesCallback;
import com.example.myappnew.Callback.MoviesListCallback;
import com.example.myappnew.Callback.PopularMoviesCallback;
import com.example.myappnew.Callback.Result;
import com.example.myappnew.Callback.TopRatedCallback;
import com.example.myappnew.Callback.UpcomingCallback;
import com.example.myappnew.EndlessRecyclerViewScrollListener;
import com.example.myappnew.Fragment.MovieDetailFragment;
import com.example.myappnew.Fragment.NotificationsFragment;
import com.example.myappnew.Fragment.ProfileFragment;
import com.example.myappnew.Interfaces.LatestMoviesInterface;
import com.example.myappnew.Interfaces.MoviesInterface;
import com.example.myappnew.Interfaces.PopularMoviesInterface;
import com.example.myappnew.Interfaces.ResultsInterface;
import com.example.myappnew.Interfaces.TopRatedInterface;
import com.example.myappnew.Interfaces.UpcomingsInterface;
import com.example.myappnew.ListingPaginationOnScrollListner;
import com.example.myappnew.MyBroadCastReceiver;
import com.example.myappnew.PaginationListener;
import com.example.myappnew.R;
import com.example.myappnew.adapter.LatestMoviesAdapter;
import com.example.myappnew.adapter.MoviesListAdapter;
import com.example.myappnew.adapter.PopularMoviesAdapter;
import com.example.myappnew.adapter.SearchViewAdapter;
import com.example.myappnew.adapter.TopRatedAdapter;
import com.example.myappnew.adapter.UpcomingsAdapter;
import com.example.myappnew.adapter.ViewPagerAdapter;

import com.example.myappnew.pojo.Results_Popular;
import com.example.myappnew.pojo.Results_TopRated;
import com.example.myappnew.pojo.Results_Upcomings;
import com.example.myappnew.presenter.LatestMoviesPresenter;
import com.example.myappnew.presenter.MoviesListPresenter;
import com.example.myappnew.presenter.PopularMoviesPresenter;
import com.example.myappnew.presenter.TopRatedPresenter;
import com.example.myappnew.presenter.UpcomingsPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.journaldev.recyclerviewgridlayoutmanager.AutoFitGridLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;
import static com.example.myappnew.PaginationListener.PAGE_START;

public class LogedInActivity extends AppCompatActivity implements MoviesInterface, ResultsInterface , AdapterView.OnItemSelectedListener, LatestMoviesInterface , PopularMoviesInterface, TopRatedInterface, UpcomingsInterface {


    int autoFitLayoutSize = 350;
    RecyclerView np_rv;
    MoviesListPresenter moviesListPresenter;
    MoviesListAdapter moviesListAdapter;
    String query;
    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    View view;
    List<Result> moviesListData = new ArrayList<>();
    ArrayList<Result> resultData = new ArrayList<>();
    ProgressBar progressBar;
    SearchView searchView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    Spinner spinner;
    EndlessRecyclerViewScrollListener scrollListener;
    Context LogedInActivitycontext;
    FirebaseAuth firebaseAuth;
    RecyclerView p_rv;
    RecyclerView tr_rv;
    LinearLayoutManager linearLayoutManager_popular;
    LinearLayoutManager linearLayoutManager_toprated;
    LinearLayoutManager linearLayoutManager_nowplaying;
    LinearLayoutManager linearLayoutManager_upcoming;
    LinearLayoutManager linearLayoutManager_latest;
    RecyclerView uc_rv;
    Activity activity;
    LatestMoviesAdapter latestMoviesAdapter;
    LatestMoviesPresenter latestMoviesPresenter;
    List<LatestMoviesCallback> mLatestMoviesData =new ArrayList<>();
    RecyclerView lm_rv;
    List<PopularMoviesCallback> mPmovies = new ArrayList<>();
    PopularMoviesAdapter popularMoviesAdapter;
    PopularMoviesPresenter popularMoviesPresenter;
    List<Result> mPopularMoviesResults= new ArrayList<>() ;
    List<TopRatedCallback> mTopRatedData = new ArrayList<>();
    List<Results_TopRated> results_topRateds =new ArrayList<>();
    List<UpcomingCallback> upcomingCallbacks =new ArrayList<>();
    List<Result> results_upcomings = new ArrayList<>();
    TopRatedAdapter topRatedAdapter;
    TopRatedPresenter topRatedPresenter;
    UpcomingsAdapter upcomingsAdpater;
    UpcomingsPresenter upcomingsPresenter;
    ListView listView;
    RecyclerView searchRV;
    SearchViewAdapter  searchViewAdapter;
    SwipeRefreshLayout swipeRefreshLayout_nowplaying;
    SwipeRefreshLayout swipeRefreshLayout_latestMovies;
    SwipeRefreshLayout swipeRefreshLayout_popular;
    SwipeRefreshLayout swipeRefreshLayout_topRated;
    SwipeRefreshLayout swipeRefreshLayout_upcomin;
    AutoFitGridLayoutManager layoutManager;

    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
     int popular_page=1;
    int itemCount = 0;
    private int TOTALPAGES = 10;

   // broadcastrecciver
   Context _mContext;
    BroadcastReceiver br = null;
    IntentFilter filter;


    @Override
    protected void onStart() {
        super.onStart();

   //     IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
   //     registerReceiver(broadcastReceiver,intentFilter);

        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(LogedInActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged_in);

        searchView=findViewById(R.id.searchView);
        firebaseAuth = FirebaseAuth.getInstance();
        np_rv = findViewById(R.id.recyclerview_nowplaying);
        progressBar = findViewById(R.id.pb_login);
        spinner=findViewById(R.id.Spinner01);
        searchView =findViewById(R.id.searchView);
        lm_rv=findViewById(R.id.recyclerview_latestmovies);
        p_rv=findViewById(R.id.recyclerview_popular);
        tr_rv=findViewById(R.id.recyclerview_toprated);
        uc_rv=findViewById(R.id.recyclerview_upcoming);
        searchRV=findViewById(R.id.searchrv);
        swipeRefreshLayout_nowplaying=findViewById(R.id.su_np);
        swipeRefreshLayout_latestMovies=findViewById(R.id.su_lm);
        swipeRefreshLayout_popular=findViewById(R.id.su_p);
        swipeRefreshLayout_topRated=findViewById(R.id.su_tr);
        swipeRefreshLayout_upcomin=findViewById(R.id.su_uc);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());

        p_rv.setVisibility(View.VISIBLE);
        swipeRefreshLayout_nowplaying.setVisibility(View.GONE);
        swipeRefreshLayout_latestMovies.setVisibility(View.GONE);
        swipeRefreshLayout_upcomin.setVisibility(View.GONE);
        swipeRefreshLayout_topRated.setVisibility(View.GONE);
        swipeRefreshLayout_popular.setVisibility(View.VISIBLE);


        searchbar();
        spinner();
        swipelayout();
        nowplaying();
        latestmovies();
        popularmovies();
        toprated();
        upcomings();
        //pagination
        popular_pagination();

    }






    private void swipelayout() {
        swipeRefreshLayout_nowplaying.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout_nowplaying.setRefreshing(false);
                moviesListPresenter.getNowPlayingmoviesforlist(1);
            }
        });
        swipeRefreshLayout_upcomin.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout_upcomin.setRefreshing(false);
                upcomingsPresenter.getUpcomingsPresenter(1);
            }
        });
        swipeRefreshLayout_topRated.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout_topRated.setRefreshing(false);
                topRatedPresenter.getoprated(1);
            }
        });
        swipeRefreshLayout_popular.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout_popular.setRefreshing(false);
                popularMoviesPresenter.getPopularMoviesData(1);
            }
        });
        swipeRefreshLayout_latestMovies.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout_latestMovies.setRefreshing(false);
                latestMoviesPresenter.getlatestmovies();
            }
        });
    }

    public void nowplaying() {

        moviesListAdapter = new MoviesListAdapter(resultData, moviesListData,this, fragmentManager);
        moviesListPresenter = new MoviesListPresenter(this, this);
        moviesListPresenter.getNowPlayingmoviesforlist(1);
        layoutManager = new AutoFitGridLayoutManager(this, autoFitLayoutSize);
        np_rv.setLayoutManager(layoutManager);
        np_rv.setAdapter(moviesListAdapter);
    }

    private void upcomings() {

        upcomingsAdpater= new UpcomingsAdapter(results_upcomings,this);
        upcomingsPresenter= new UpcomingsPresenter(this);
        upcomingsPresenter.getUpcomingsPresenter(1);
        linearLayoutManager_upcoming = new AutoFitGridLayoutManager(this,autoFitLayoutSize);
        uc_rv.setAdapter(upcomingsAdpater);
        uc_rv.setLayoutManager(linearLayoutManager_upcoming);
    }

    private void toprated() {

        topRatedAdapter= new TopRatedAdapter(results_topRateds,this);
        topRatedPresenter= new TopRatedPresenter(this);
        topRatedPresenter.getoprated(1);
        linearLayoutManager_toprated = new AutoFitGridLayoutManager(this,autoFitLayoutSize);
        tr_rv.setAdapter(topRatedAdapter);
        tr_rv.setLayoutManager(linearLayoutManager_toprated);
    }

    private void popularmovies() {

        popularMoviesAdapter= new PopularMoviesAdapter(mPopularMoviesResults,this);
        popularMoviesPresenter= new PopularMoviesPresenter(this);
        popularMoviesPresenter.getPopularMoviesData(popular_page);
        linearLayoutManager_popular = new AutoFitGridLayoutManager(this,autoFitLayoutSize);
        p_rv.setAdapter(popularMoviesAdapter);
        p_rv.setLayoutManager(linearLayoutManager_popular);

    }

    private void latestmovies() {

        latestMoviesAdapter = new LatestMoviesAdapter(mLatestMoviesData,this);
        latestMoviesPresenter = new LatestMoviesPresenter(this);
        linearLayoutManager_latest = new AutoFitGridLayoutManager(this,autoFitLayoutSize);
        lm_rv.setLayoutManager(linearLayoutManager_latest);
        latestMoviesPresenter.getlatestmovies();
        lm_rv.setAdapter(latestMoviesAdapter);


    }

    private void spinner() {

        spinner.setOnItemSelectedListener(this);
        List<String> mLists=new ArrayList<>();
        mLists.add("Popular");
        mLists.add("Now playing");
        mLists.add("Latest");
        mLists.add("Top Rated");
        mLists.add("Upcoming");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, mLists);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }


    private void searchbar() {


          //final ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,moviesListData);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRV.setAdapter(searchViewAdapter);

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                progressBar.setVisibility(View.VISIBLE);
                query = newText;

                if(moviesListData==null) {
                   Toast.makeText(LogedInActivity.this,"moviesListData is null",Toast.LENGTH_LONG).show();
                }
                else
                {
                    searchRV.setVisibility(View.VISIBLE);
                    searchViewAdapter = new SearchViewAdapter(moviesListData,getApplicationContext());

                    searchViewAdapter.updateData(moviesListData);
                    moviesListPresenter.getmoviesforQueryList(query);
                    p_rv.setAdapter(searchViewAdapter);
                }
                progressBar.setVisibility(View.GONE);
                //arrayAdapter.getFilter().filter(query);

                return false;
            }
        });

    }

    @Override
    public void onmovies(MoviesListCallback moviesListCallback,List<Result> results) {
        //moviesListData = Collections.singletonList(moviesListCallback);
        moviesListData = results;
        moviesListAdapter.updateData(moviesListData);
    }

    @Override
    public void onFetchStart() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFetchFailure(String messege) {
        Toast.makeText(this, messege, Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFetchComplete() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void resultinterface(List<Result> result) {
        resultData = (ArrayList<Result>) result;
        moviesListAdapter.updateResultData(resultData);
    }

    private void viewpager() {

      //  tabLayout = findViewById  (R.id.tabs);
        //viewPager = findViewById(R.id.viewpager);
        String NP="Now Playing";
        String LM="Latest Movies";
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MovieDetailFragment(), NP);
        adapter.addFragment(new ProfileFragment(), LM);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void latestmovies(LatestMoviesCallback latestMoviesCallback) {
        this.mLatestMoviesData= Collections.singletonList(latestMoviesCallback);
        latestMoviesAdapter.updateData(mLatestMoviesData);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String s= parent.getItemAtPosition(position).toString();
        if(s.equals("Now playing"))
        {

            lm_rv.setVisibility(View.GONE);
            np_rv.setVisibility(View.VISIBLE);
            p_rv.setVisibility(View.GONE);
            tr_rv.setVisibility(View.GONE);
            uc_rv.setVisibility(View.GONE);

            swipeRefreshLayout_nowplaying.setVisibility(View.VISIBLE);
            swipeRefreshLayout_latestMovies.setVisibility(View.GONE);
            swipeRefreshLayout_upcomin.setVisibility(View.GONE);
            swipeRefreshLayout_topRated.setVisibility(View.GONE);
            swipeRefreshLayout_popular.setVisibility(View.GONE);



        }
        else if(s.equals("Latest"))
        {

            lm_rv.setVisibility(View.VISIBLE);
            np_rv.setVisibility(View.GONE);
            p_rv.setVisibility(View.GONE);
            tr_rv.setVisibility(View.GONE);
            uc_rv.setVisibility(View.GONE);

            swipeRefreshLayout_nowplaying.setVisibility(View.GONE);
            swipeRefreshLayout_latestMovies.setVisibility(View.VISIBLE);
            swipeRefreshLayout_upcomin.setVisibility(View.GONE);
            swipeRefreshLayout_topRated.setVisibility(View.GONE);
            swipeRefreshLayout_popular.setVisibility(View.GONE);




        }
        else if(s.equals("Popular"))
        {
            lm_rv.setVisibility(View.GONE);
            np_rv.setVisibility(View.GONE);
            p_rv.setVisibility(View.VISIBLE);
            tr_rv.setVisibility(View.GONE);
            uc_rv.setVisibility(View.GONE);

            swipeRefreshLayout_nowplaying.setVisibility(View.GONE);
            swipeRefreshLayout_latestMovies.setVisibility(View.GONE);
            swipeRefreshLayout_upcomin.setVisibility(View.GONE);
            swipeRefreshLayout_topRated.setVisibility(View.GONE);
            swipeRefreshLayout_popular.setVisibility(View.VISIBLE);



        }
        else if(s.equals("Top Rated"))
        {
            lm_rv.setVisibility(View.GONE);
            np_rv.setVisibility(View.GONE);
            p_rv.setVisibility(View.GONE);
            tr_rv.setVisibility(View.VISIBLE);
            uc_rv.setVisibility(View.GONE);

            swipeRefreshLayout_nowplaying.setVisibility(View.GONE);
            swipeRefreshLayout_latestMovies.setVisibility(View.GONE);
            swipeRefreshLayout_upcomin.setVisibility(View.GONE);
            swipeRefreshLayout_topRated.setVisibility(View.VISIBLE);
            swipeRefreshLayout_popular.setVisibility(View.GONE);



        }
        else if(s.equals("Upcoming"))
        {
            lm_rv.setVisibility(View.GONE);
            np_rv.setVisibility(View.GONE);
            p_rv.setVisibility(View.GONE);
            tr_rv.setVisibility(View.GONE);
            tr_rv.setVisibility(View.GONE);
            uc_rv.setVisibility(View.VISIBLE);

            swipeRefreshLayout_nowplaying.setVisibility(View.VISIBLE);
            swipeRefreshLayout_latestMovies.setVisibility(View.GONE);
            swipeRefreshLayout_upcomin.setVisibility(View.VISIBLE);
            swipeRefreshLayout_topRated.setVisibility(View.GONE);
            swipeRefreshLayout_popular.setVisibility(View.GONE);



        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void popularmovies(PopularMoviesCallback popularMovies,List<Result> results) {
        mPmovies= Collections.singletonList(popularMovies);
        mPopularMoviesResults= results;
        popularMoviesAdapter.updateData(mPopularMoviesResults);

    }

    @Override
    public void toprated(TopRatedCallback topRatedCallback, List<Results_TopRated> results_topRated) {
         mTopRatedData = Collections.singletonList(topRatedCallback);
        results_topRateds = results_topRated;
        topRatedAdapter.updateData(results_topRated);




    }

    @Override
    public void upcomingsdata(UpcomingCallback upcomingCallback, List<Result> results) {
        results_upcomings = results;
        upcomingsAdpater.updateData(results_upcomings);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //toolbar.setTitle("Shop");
                    loadActivity();
                    return true;
                case R.id.navigation_sms:
                    //toolbar.setTitle("My Gifts");
                    return true;
                case R.id.navigation_notifications:
                    //toolbar.setTitle("Cart");
                    NotificationsFragment notificationsFragment = new NotificationsFragment();
                    loadFragment(notificationsFragment);
                    return true;
                case R.id.navigation_profile:
                    //toolbar.setTitle("Profile");
                    ProfileFragment profileFragment =new ProfileFragment();
                    loadFragment(profileFragment);
                    return true;
            }
            return false;
        }

        private void loadActivity() {
            startActivity(new Intent(LogedInActivity.this,LogedInActivity.class));
        }

        private void loadFragment(Fragment fragment) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_loggedin,fragment).addToBackStack(null).commit();
        }
    };

    @Override
    protected void onPause() {



        super.onPause();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    @Override
    protected void onResume() {


        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }



    private void popular_pagination() {

        p_rv.addOnScrollListener(new PaginationListener(linearLayoutManager_popular) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                doApiCall();

            }

            @Override
            public int getTotalPageCount() {
                return TOTALPAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }

    private void doApiCall() {
        popularMoviesPresenter.getPopularMoviesData(currentPage);
        if(currentPage!=TOTALPAGES)
        {
            //popularMoviesAdapter.addLoadingFooter();
        }
        final ArrayList<Result> items = new ArrayList<>();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    itemCount++;
                    Result postItem = new Result();
                    //   postItem.setTitle(getString(R.string.text_title) + itemCount);
                    //   postItem.setDescription(getString(R.string.text_description));
                    items.add(postItem);
                }

                /**
                 * manage progress view
                 */
                if (currentPage != PAGE_START)
                    popularMoviesAdapter.removeLoading();
                popularMoviesAdapter.addItems(items);
                //swipeRefreshLayout_popular.setRefreshing(false);
                // check weather is last page or not
                if (currentPage < TOTALPAGES) {
                    popularMoviesAdapter.addLoading();
                } else {
                    isLastPage = true;
                }
                isLoading = false;
            }
        }, 1500);


    }


}
