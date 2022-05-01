package com.dukan.dukkan.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.activity.ProductsActivity;
import com.dukan.dukkan.activity.ShowProductActivity;
import com.dukan.dukkan.activity.ShowStoresActivity;
import com.dukan.dukkan.adapter.BrandAdapter;
import com.dukan.dukkan.adapter.DeliveryAdapter;
import com.dukan.dukkan.adapter.ListProductAdapter;
import com.dukan.dukkan.adapter.MostWantedAdapter;
import com.dukan.dukkan.adapter.NewProductAdapter;
import com.dukan.dukkan.adapter.RecyclerMostwantedAdapter;
import com.dukan.dukkan.adapter.RecyclerNewProductAdapter;
import com.dukan.dukkan.adapter.RecyclerProductAdapter;
import com.dukan.dukkan.adapter.StoreAdapter;
import com.dukan.dukkan.pojo.Advertisement;
import com.dukan.dukkan.pojo.Brand;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.Delivery;
import com.dukan.dukkan.pojo.FavoriteMain;
import com.dukan.dukkan.pojo.MostWanted;
import com.dukan.dukkan.pojo.MultipleProducts;
import com.dukan.dukkan.pojo.NewProduct;
import com.dukan.dukkan.pojo.Product;
import com.dukan.dukkan.pojo.ShowStore;
import com.dukan.dukkan.pojo.Slider;
import com.dukan.dukkan.pojo.Store;
import com.dukan.dukkan.util.HorizontalListView;
import com.squareup.picasso.Picasso;
import com.yihsian.slider.library.SliderItemView;
import com.yihsian.slider.library.SliderItemView2;
import com.yihsian.slider.library.SliderLayout;
import com.yihsian.slider.library.SliderLayout2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    APIInterface apiInterface;
    private HorizontalListView HorizontalListViewLastOffers,HorizontalListViewMost,HorizontalListViewNewProduct;
    private SliderLayout2 sliderLayout;
    ImageView img_filter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    TextView store_name;
    String ID,StoreName="";
    int StoreId=0;
    RecyclerView recyclerViewViewMost,recyclerViewNewProduct,recyclerViewLastOffers;

    @SuppressLint("HardwareIds")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.product_list, container, false);
        apiInterface = APIClient.getClient(getContext()).create(APIInterface.class);
        sliderLayout = root.findViewById(R.id.sliderLayout);
       ID = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            StoreId = bundle.getInt("StoreId", 0);
            StoreName = bundle.getString("StoreName", "");
        }
        TextView view_all_product = root.findViewById(R.id.view_all_product);
        TextView view_most_product = root.findViewById(R.id.view_most_product);
        TextView view_last_offers = root.findViewById(R.id.view_last_offers);
        store_name = root.findViewById(R.id.store_name);
        store_name.setText(StoreName);

        recyclerViewViewMost = root.findViewById(R.id.recyclerViewViewMost);
        recyclerViewNewProduct = root.findViewById(R.id.recyclerViewNewProduct);
        recyclerViewLastOffers = root.findViewById(R.id.recyclerViewLastOffers);

        HorizontalListViewMost = root.findViewById(R.id.HorizontalListViewMost);
        HorizontalListViewNewProduct = root.findViewById(R.id.HorizontalListViewNewProduct);
        HorizontalListViewLastOffers = root.findViewById(R.id.HorizontalListViewLastOffers);
        mSwipeRefreshLayout = (SwipeRefreshLayout)  root.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        view_all_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProductsActivity.class);
                i.putExtra("title", getString(R.string.new_products));
                i.putExtra("store", StoreId);
                i.putExtra("new", 1);
                i.putExtra("most", 0);
                i.putExtra("category", 0);
                startActivity(i);
            }
        });
        view_most_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProductsActivity.class);
                i.putExtra("title", getString(R.string.most_wanted));
                i.putExtra("store", StoreId);
                i.putExtra("new", 0);
                i.putExtra("most", 1);
                i.putExtra("category", 0);
                startActivity(i);
            }
        });
        view_last_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), ProductsActivity.class);
//                i.putExtra("title", getString(R.string.latest_offers));
//                i.putExtra("store", StoreId);
//                i.putExtra("new", 0);
//                i.putExtra("most", 1);
//                startActivity(i);
            }
        });
        getStores();

        return root;
    }
    private void getStores() {
        Toast.makeText(getContext(), ""+StoreId, Toast.LENGTH_SHORT).show();
        mSwipeRefreshLayout.setRefreshing(true);
        Call<ShowStore> callNew = apiInterface.StoreDetails(StoreId);
        callNew.enqueue(new Callback<ShowStore>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ShowStore> callNew, Response<ShowStore> response) {
                Log.d("TAG111111",response.code()+"");
                ShowStore resource = response.body();
                if(resource.status){
//                    List<NewProduct> newProduct = resource.data.newProducts;
//                    NewProductAdapter NewProductAdapter = new NewProductAdapter(getContext(),newProduct);
//                    HorizontalListViewNewProduct.setAdapter(NewProductAdapter);
//                    NewProductAdapter.notifyDataSetChanged();
//                    HorizontalListViewNewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            onClikMostwanted(view,newProduct,i);
//                        }
//                    });

                    LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewNewProduct.setLayoutManager(layoutManager);
                    List<NewProduct> datumListNew = resource.data.newProducts;
                    RecyclerNewProductAdapter adapter = new RecyclerNewProductAdapter(getContext(), datumListNew);
                    recyclerViewNewProduct.setAdapter(adapter);

                    LinearLayoutManager layoutManager2= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewViewMost.setLayoutManager(layoutManager2);
                    List<NewProduct> datumListMost = resource.data.mostWanted;
                    RecyclerNewProductAdapter adapterMost = new RecyclerNewProductAdapter(getContext(), datumListMost);
                    recyclerViewViewMost.setAdapter(adapterMost);

                    LinearLayoutManager layoutManager3= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewViewMost.setLayoutManager(layoutManager3);
                    List<NewProduct> datumListLast = resource.data.latestOffers;
                    RecyclerNewProductAdapter adapterLast = new RecyclerNewProductAdapter(getContext(), datumListLast);
                    recyclerViewViewMost.setAdapter(adapterLast);

                    List<Advertisement> slid = resource.data.advertisements;
                    SliderItemView2 view01 = new SliderItemView2(getContext());
                    sliderLayout.removeAllSliders();
                    for (Advertisement datum : slid) {
                        view01 = new SliderItemView2(getContext());
                        System.out.println("SWQQAAAAdddd "+datum.image);
                        view01.setItem2(datum.image,datum.title,datum.description);
                        sliderLayout.addSlider(view01);
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);

            }
            @Override
            public void onFailure(Call<ShowStore> call, Throwable t) {
                Log.d("TAG111111","  e "+t.getMessage());

            }

        });
    }

    @Override
    public void onRefresh() {
        getStores();
    }
}