package com.dukan.dukkan.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dukan.dukkan.APIClient;
import com.dukan.dukkan.APIInterface;
import com.dukan.dukkan.R;
import com.dukan.dukkan.adapter.NewProductAdapter;
import com.dukan.dukkan.adapter.RecyclerNewProductAdapter;
import com.dukan.dukkan.fragment.ReviewSheetFragment;
import com.dukan.dukkan.model.SliderItem;
import com.dukan.dukkan.pojo.CartMain;
import com.dukan.dukkan.pojo.CartMain2;
import com.dukan.dukkan.pojo.CartParamenter;
import com.dukan.dukkan.pojo.CartRemoveParamenter;
import com.dukan.dukkan.pojo.FavoriteMain;
import com.dukan.dukkan.pojo.Image;
import com.dukan.dukkan.pojo.MostWanted;
import com.dukan.dukkan.pojo.NewProduct;
import com.dukan.dukkan.pojo.ShowProduct;
import com.dukan.dukkan.pojo.ShowProduct;
import com.dukan.dukkan.pojo.Slider;
import com.dukan.dukkan.util.HorizontalListView;
import com.dukan.dukkan.util.SharedPreferenceManager;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;
import com.yihsian.slider.library.SliderItemView;
import com.yihsian.slider.library.SliderLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowProductActivity extends AppCompatActivity {
    APIInterface apiInterface;
    private Toolbar toolbar;
    int productID;
    private SliderLayout sliderLayout;
    private TextView tv_sala, product_name, product_desc, product_price, tv_rating_num, product_detail;
    private TextView tv_ref, desc_info, product_count, tv_heart;
    RatingBar ratingBar2;
    HorizontalListView HorizontalListView;
    LinearLayout liner_description, rel_add_card_num, rel_add_to_card;
    ImageView img_roow, img_heart;
    private SliderAdapterExample adapter;
    SliderView sliderView;
    ProgressBar progressBar2;
    RecyclerView recyclerViewNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_review);
        Bundle extras = getIntent().getExtras();
        productID = extras.getInt("productID");
        toolbar = findViewById(R.id.home_toolbar);
        sliderLayout = findViewById(R.id.sliderLayout);
        product_name = findViewById(R.id.product_name);
        product_desc = findViewById(R.id.product_desc);
        product_price = findViewById(R.id.product_price);
        ratingBar2 = findViewById(R.id.ratingBar2);
        tv_rating_num = findViewById(R.id.tv_rating_num);
        recyclerViewNewProduct = findViewById(R.id.recyclerViewNewProduct);
        product_detail = findViewById(R.id.product_detail);
        tv_heart = findViewById(R.id.tv_heart);
        img_heart = findViewById(R.id.img_heart);
        progressBar2 = findViewById(R.id.progressBar);
        ImageView iconBack = toolbar.findViewById(R.id.icon_back);
        ImageView ic_share = toolbar.findViewById(R.id.icon_share);
        ImageView icon_buy = toolbar.findViewById(R.id.icon_buy);
        tv_sala = toolbar.findViewById(R.id.tv_sala);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageView icon_notification = toolbar.findViewById(R.id.icon_notification);
        icon_buy.setVisibility(View.VISIBLE);
        icon_notification.setVisibility(View.GONE);
        icon_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowProductActivity.this, NotificationsActivity.class));

            }
        });
        icon_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowProductActivity.this, CartActivity.class));
                finish();
            }
        });
        ic_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getString(R.string.url) + "/products/" + productID;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        rel_add_to_card = findViewById(R.id.rel_add_to_card);
        CardView card_favorite = findViewById(R.id.card_favorite);
        CardView card_desc = findViewById(R.id.card_desc);
        CardView card_rating = findViewById(R.id.card_rating);
        CardView card_customer_reviews = findViewById(R.id.card_customer_reviews);
        img_roow = findViewById(R.id.img_roows);
        HorizontalListView = findViewById(R.id.HorizontalListView);
        desc_info = findViewById(R.id.desc_info);
        tv_ref = findViewById(R.id.tv_ref);
        liner_description = findViewById(R.id.liner_description);

        rel_add_card_num = findViewById(R.id.rel_add_card_num);
        RelativeLayout relative_plus = findViewById(R.id.relative_plus);
        RelativeLayout relative_minus = findViewById(R.id.relative_minus);
        product_count = findViewById(R.id.product_count);
        card_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SharedPreferenceManager.getInstance(getBaseContext()).get_api_token().equals("")) {
                    Bundle bundle = new Bundle();
                    ReviewSheetFragment reviewSheetFragment = new ReviewSheetFragment();
                    bundle.putInt("productID", productID);
                    reviewSheetFragment.setArguments(bundle);
                    reviewSheetFragment.show(getSupportFragmentManager()
                            , reviewSheetFragment.getTag());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowProductActivity.this);

                    builder.setTitle(getString(R.string.sign_in));
                    builder.setMessage(getString(R.string.login_to_rate));

                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(ShowProductActivity.this, LoginActivity.class));
                            finish();
                        }
                    });

                    builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
        card_customer_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShowProductActivity.this, CustomerReviews.class);
                i.putExtra("productID", productID);
                startActivity(i);
            }
        });
        card_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (liner_description.getVisibility() == View.VISIBLE) {
                    liner_description.setVisibility(View.GONE);
                    img_roow.setRotation(0);
                } else {
                    liner_description.setVisibility(View.VISIBLE);
                    img_roow.setRotation(90);
                }
            }
        });
        sliderView = findViewById(R.id.imageSlider);


        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        getProductDetails();

        rel_add_to_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar2.setVisibility(View.VISIBLE);
                @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                CartParamenter cartParamenter = new CartParamenter(productID, ID);
                Call<CartMain> call1 = apiInterface.cart(ID, cartParamenter);
                call1.enqueue(new Callback<CartMain>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                        CartMain cart = response.body();
                        if (cart.status) {
                            rel_add_card_num.setVisibility(View.VISIBLE);
                            rel_add_to_card.setVisibility(View.GONE);
                            tv_sala.setVisibility(View.VISIBLE);
                            SharedPreferenceManager.getInstance(getApplicationContext()).setCartCount(SharedPreferenceManager.getInstance(getApplicationContext()).getCartCount() + 1);
                            tv_sala.setText("" + SharedPreferenceManager.getInstance(getApplicationContext()).getCartCount());

                        } else
                            Toast.makeText(ShowProductActivity.this, cart.message, Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<CartMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.GONE);
                        call.cancel();
                    }
                });
            }
        });
        relative_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                CartParamenter cartParamenter = new CartParamenter(productID, ID);
                Call<CartMain> call1 = apiInterface.cart(ID, cartParamenter);
                progressBar2.setVisibility(View.VISIBLE);
                relative_plus.setEnabled(false);
                relative_minus.setEnabled(false);
                call1.enqueue(new Callback<CartMain>() {
                    @Override
                    public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                        CartMain cart = response.body();
                        System.out.println("7878788888888888 " + cart.status);
                        if (cart.status) {
                            int pCount = Integer.parseInt(product_count.getText().toString());
                            pCount++;
                            product_count.setText("" + pCount);
                            if (call1.isExecuted()) {
                                progressBar2.setVisibility(View.GONE);
                                relative_plus.setEnabled(true);
                                relative_minus.setEnabled(true);
                            }
                        } else {
                            Toast.makeText(ShowProductActivity.this, "" + cart.message, Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<CartMain> call, Throwable t) {
                        System.out.println("7878788888888888 " + t.getMessage());
                        call.cancel();
                    }
                });


            }

        });
        relative_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(product_count.getText().toString()) > 1) {
                    @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(productID, ID, 1, "delete");
                    Call<CartMain> call1 = apiInterface.cartRemove(cartRemoveParamenter);
                    progressBar2.setVisibility(View.VISIBLE);
                    relative_plus.setEnabled(false);
                    relative_minus.setEnabled(false);
                    call1.enqueue(new Callback<CartMain>() {
                        @Override
                        public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                            CartMain cart = response.body();
                            if (cart.status) {
                                int pCount = Integer.parseInt(product_count.getText().toString());
                                pCount--;
                                product_count.setText("" + pCount);
                                if (call1.isExecuted()) {
                                    progressBar2.setVisibility(View.GONE);
                                    relative_plus.setEnabled(true);
                                    relative_minus.setEnabled(true);
                                }
                            } else
                                Toast.makeText(ShowProductActivity.this, "" + cart.message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<CartMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                            call.cancel();
                        }
                    });
                }
            }

        });

        card_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar2.setVisibility(View.VISIBLE);
                if (tv_heart.getText().toString().equals("false")) {
                    @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    CartParamenter cartParamenter = new CartParamenter(productID, ID);
                    Call<FavoriteMain> call1 = apiInterface.favorite(cartParamenter);
                    call1.enqueue(new Callback<FavoriteMain>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(Call<FavoriteMain> call, Response<FavoriteMain> response) {
                            FavoriteMain favorite = response.body();
                            if (favorite.status) {
                                img_heart.setImageResource(R.drawable.ic_heart_on);
                                tv_heart.setText("true");
                            } else
                                Toast.makeText(ShowProductActivity.this, favorite.message, Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<FavoriteMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.GONE);
                            call.cancel();
                        }
                    });
                } else {
                    @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(productID, ID, 1, "delete");
                    Call<FavoriteMain> call1 = apiInterface.favoriteRemove(cartRemoveParamenter);
                    call1.enqueue(new Callback<FavoriteMain>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(Call<FavoriteMain> call, Response<FavoriteMain> response) {
                            FavoriteMain favorite = response.body();
                            if (favorite.status) {
                                img_heart.setImageResource(R.drawable.ic_heart);
                                tv_heart.setText("false");
                            } else
                                Toast.makeText(ShowProductActivity.this, favorite.message, Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<FavoriteMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.GONE);
                            call.cancel();
                        }
                    });
                }
            }
        });
    }

    private void getProductDetails() {
        progressBar2.setVisibility(View.VISIBLE);
        Call<ShowProduct> callNew = apiInterface.productDetails(productID);
        callNew.enqueue(new Callback<ShowProduct>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ShowProduct> callNew, Response<ShowProduct> response) {
                Log.d("TAG111111", response.code() + "");
                ShowProduct resource = response.body();
                if (resource.status) {
                    productID = resource.data.id;
                    if (resource.data.category != null)
                        product_name.setText("" + resource.data.category.name);
                    product_desc.setText(resource.data.name);
                    desc_info.setText(resource.data.name);
                    product_detail.setText(resource.data.description);
                    product_price.setText(String.valueOf(resource.data.price));
                    tv_rating_num.setText(String.valueOf(resource.data.rate));
                    ratingBar2.setRating(resource.data.rate);
//                    List<NewProduct> newProduct = resource.data.similarProducts;
//
//                    NewProductAdapter NewProductAdapter = new NewProductAdapter(getApplicationContext(),newProduct);
//                    HorizontalListView.setAdapter(NewProductAdapter);
//                    NewProductAdapter.notifyDataSetChanged();
//                    HorizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            onClikMostwanted(view,newProduct,i);
//                        }
//                    });
                    LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewNewProduct.setLayoutManager(layoutManager2);
                    List<NewProduct> datumListNew = resource.data.similarProducts;
                    RecyclerNewProductAdapter adapterNew = new RecyclerNewProductAdapter(getApplicationContext(), datumListNew);
                    recyclerViewNewProduct.setAdapter(adapterNew);

                    List<Image> slid = resource.data.images;
                    List<SliderItem> sliderItemList = new ArrayList<>();
                    for (Image datum : slid) {
                        SliderItem sliderItem = new SliderItem();
                        sliderItem.setImageUrl(datum.image);
                        sliderItemList.add(sliderItem);
                    }
                    adapter.renewItems(sliderItemList);
                    System.out.println("VVVDDDDDDDDDD " + resource.data.isCart);
                    if (resource.data.isFavorite) {
                        img_heart.setImageResource(R.drawable.ic_heart_on);
                        tv_heart.setText("true");
                    } else {
                        img_heart.setImageResource(R.drawable.ic_heart);
                        tv_heart.setText("false");
                    }
                    if (resource.data.isCart != null) {
                        rel_add_card_num.setVisibility(View.VISIBLE);
                        rel_add_to_card.setVisibility(View.GONE);
                        product_count.setText(String.valueOf(resource.data.isCart.qty));
                    } else {
                        rel_add_card_num.setVisibility(View.GONE);
                        rel_add_to_card.setVisibility(View.VISIBLE);
                    }
                    List<ShowProduct.Data.ProductDetail> productDetails = resource.data.productDetails;
                    String details = "";
                    for (ShowProduct.Data.ProductDetail datum : productDetails) {
                        details = details + "key:" + datum.key + " value:" + datum.value + " unit:" + datum.unit + " price:" + datum.price + " \n";
                    }
                    tv_ref.setText(details);
                } else
                    Toast.makeText(ShowProductActivity.this, resource.message, Toast.LENGTH_SHORT).show();

                progressBar2.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ShowProduct> call, Throwable t) {
                Log.d("TAG111111", "  e " + t.getMessage());
                progressBar2.setVisibility(View.GONE);

            }

        });
    }

    void onClikMostwanted(View view, List<NewProduct> mosted, int i) {
        TextView text_add = (TextView) view.findViewById(R.id.text_add);
        TextView tv_heart = (TextView) view.findViewById(R.id.tv_heart);
        ImageView img_heart = (ImageView) view.findViewById(R.id.img_heart);
        LinearLayout linear_main = (LinearLayout) view.findViewById(R.id.linear_main);
        RelativeLayout rel_add_to_card = (RelativeLayout) view.findViewById(R.id.rel_add_to_card);
        RelativeLayout rel_heart = (RelativeLayout) view.findViewById(R.id.rel_heart);
        ProgressBar progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar);
        linear_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i2 = new Intent(ShowProductActivity.this, ShowProductActivity.class);
                i2.putExtra("productID", mosted.get(i).id);
                overridePendingTransition(0, 0);
                startActivity(i2);
                overridePendingTransition(0, 0);
            }
        });
        //////////////////
        rel_add_to_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar2.setVisibility(View.VISIBLE);
                if (text_add.getText().equals(getString(R.string.add_to_cart))) {
                    @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    CartParamenter cartParamenter = new CartParamenter(mosted.get(i).id, ID);
                    Call<CartMain> call1 = apiInterface.cart(ID, cartParamenter);
                    call1.enqueue(new Callback<CartMain>() {
                        @Override
                        public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                            CartMain cart = response.body();
                            if (cart.status)
                                text_add.setText(getString(R.string.remove_to_cart));
                            else
                                Toast.makeText(ShowProductActivity.this, cart.message, Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(Call<CartMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.VISIBLE);
                            call.cancel();
                        }
                    });
                } else {
                    @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(mosted.get(i).id, ID, 1, "delete");
                    Call<CartMain> call1 = apiInterface.cartRemove(cartRemoveParamenter);
                    call1.enqueue(new Callback<CartMain>() {
                        @Override
                        public void onResponse(Call<CartMain> call, Response<CartMain> response) {
                            CartMain cart = response.body();
                            if (cart.status)
                                text_add.setText(getString(R.string.add_to_cart));
                            else
                                Toast.makeText(ShowProductActivity.this, cart.message, Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<CartMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.GONE);
                            call.cancel();
                        }
                    });
                }
            }
        });
        //////////////////
        rel_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar2.setVisibility(View.VISIBLE);
                if (tv_heart.getText().toString().equals("false")) {
                    @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    CartParamenter cartParamenter = new CartParamenter(mosted.get(i).id, ID);
                    Call<FavoriteMain> call1 = apiInterface.favorite(cartParamenter);
                    call1.enqueue(new Callback<FavoriteMain>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(Call<FavoriteMain> call, Response<FavoriteMain> response) {
                            FavoriteMain favorite = response.body();
                            if (favorite.status) {
                                img_heart.setImageResource(R.drawable.ic_heart_on);
                                tv_heart.setText("true");
                            } else
                                Toast.makeText(ShowProductActivity.this, favorite.message, Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(Call<FavoriteMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.VISIBLE);
                            call.cancel();
                        }
                    });
                } else {
                    @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    CartRemoveParamenter cartRemoveParamenter = new CartRemoveParamenter(mosted.get(i).id, ID, 1, "delete");
                    Call<FavoriteMain> call1 = apiInterface.favoriteRemove(cartRemoveParamenter);
                    call1.enqueue(new Callback<FavoriteMain>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(Call<FavoriteMain> call, Response<FavoriteMain> response) {
                            FavoriteMain favorite = response.body();
                            if (favorite.status) {
                                img_heart.setImageResource(R.drawable.ic_heart);
                                tv_heart.setText("false");
                            } else
                                Toast.makeText(ShowProductActivity.this, favorite.message, Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<FavoriteMain> call, Throwable t) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.GONE);
                            call.cancel();
                        }
                    });
                }
            }
        });
        getCartsCount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProductDetails();
        getCartsCount();
    }

    private void getCartsCount() {
        @SuppressLint("HardwareIds") String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        System.out.println("KKKKKKKKKKKKK12223 " + ID);
        Call<CartMain2> callNew = apiInterface.doGetListCart(ID, "android");
        callNew.enqueue(new Callback<CartMain2>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CartMain2> callNew, Response<CartMain2> response) {
                CartMain2 cart = response.body();
                if (cart.status) {
                    SharedPreferenceManager.getInstance(getApplicationContext()).setCartCount(cart.data.carts.size());
                    tv_sala.setVisibility(View.VISIBLE);
                    tv_sala.setText("" + SharedPreferenceManager.getInstance(getApplicationContext()).getCartCount());

                }

            }

            @Override
            public void onFailure(Call<CartMain2> call, Throwable t) {
            }

        });
    }
}