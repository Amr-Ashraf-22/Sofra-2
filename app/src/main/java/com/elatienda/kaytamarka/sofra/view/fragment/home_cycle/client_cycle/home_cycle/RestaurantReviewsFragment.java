package com.elatienda.kaytamarka.sofra.view.fragment.home_cycle.client_cycle.home_cycle;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.adapter.RestaurantReviewsAdapter;
import com.elatienda.kaytamarka.sofra.data.model.add_restaurant_review.AddRestaurantReview;
import com.elatienda.kaytamarka.sofra.data.model.display_all_reviews.DisplayAllReviews;
import com.elatienda.kaytamarka.sofra.data.model.display_all_reviews.DisplayAllReviewsData;
import com.elatienda.kaytamarka.sofra.databinding.FragmentRestaurantReviewsBinding;
import com.elatienda.kaytamarka.sofra.databinding.ItemCustomAddRestaurantReviewBinding;
import com.elatienda.kaytamarka.sofra.util.OnEndLess;
import com.elatienda.kaytamarka.sofra.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.elatienda.kaytamarka.sofra.data.api.ApiClient.getClient;

public class RestaurantReviewsFragment extends BaseFragment {

    private FragmentRestaurantReviewsBinding binding;
    private RestaurantsViewModel viewModel;
    private Integer maxPage = 0;
    private OnEndLess onEndLess;
    private RestaurantReviewsAdapter reviewsAdapter;
    private List<DisplayAllReviewsData> reviewsDataList = new ArrayList<>();
    private ItemCustomAddRestaurantReviewBinding dialogBinding;
    private int getRate = 0;
    private AlertDialog reviewAlertDialog;

    public RestaurantReviewsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_reviews,container,false);
        View view = binding.getRoot();
        setUpActivity();

        if (getActivity()!=null){
            //initialize viewModel
            viewModel = new ViewModelProvider(getActivity()).get(RestaurantsViewModel.class);
        }

        initRecyclerView();

        binding.fragmentRestaurantReviewsBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick(container);
            }
        });


        return view;
    }

    private void setOnClick(ViewGroup container) {
                LayoutInflater addReviewDialogInflater = LayoutInflater.from(getActivity());
                dialogBinding = DataBindingUtil.inflate(addReviewDialogInflater, R.layout.item_custom_add_restaurant_review, container , false );
                final AlertDialog.Builder addReviewAlertDialogBuiler = new AlertDialog.Builder(getActivity());
                dialogBinding.itemCustomAddRestaurantReviewImgV1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getRate = 1;

                        dialogBinding.itemCustomAddRestaurantReviewImgV1.setBorderWidth(10);
                        dialogBinding.itemCustomAddRestaurantReviewImgV1.setBorderColor(Color.GREEN);

                        dialogBinding.itemCustomAddRestaurantReviewImgV2.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV2.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV3.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV3.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV4.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV4.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV5.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV5.setBorderColor(Color.TRANSPARENT);
                    }
                });
                dialogBinding.itemCustomAddRestaurantReviewImgV2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getRate = 2;

                        dialogBinding.itemCustomAddRestaurantReviewImgV1.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV1.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV2.setBorderWidth(10);
                        dialogBinding.itemCustomAddRestaurantReviewImgV2.setBorderColor(Color.GREEN);

                        dialogBinding.itemCustomAddRestaurantReviewImgV3.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV3.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV4.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV4.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV5.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV5.setBorderColor(Color.TRANSPARENT);
                    }
                });
                dialogBinding.itemCustomAddRestaurantReviewImgV3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getRate = 3;

                        dialogBinding.itemCustomAddRestaurantReviewImgV1.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV1.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV2.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV2.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV3.setBorderWidth(10);
                        dialogBinding.itemCustomAddRestaurantReviewImgV3.setBorderColor(Color.GREEN);

                        dialogBinding.itemCustomAddRestaurantReviewImgV4.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV4.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV5.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV5.setBorderColor(Color.TRANSPARENT);
                    }
                });
                dialogBinding.itemCustomAddRestaurantReviewImgV4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getRate = 4;

                        dialogBinding.itemCustomAddRestaurantReviewImgV1.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV1.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV2.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV2.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV3.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV3.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV4.setBorderWidth(10);
                        dialogBinding.itemCustomAddRestaurantReviewImgV4.setBorderColor(Color.GREEN);

                        dialogBinding.itemCustomAddRestaurantReviewImgV5.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV5.setBorderColor(Color.TRANSPARENT);
                    }
                });
                dialogBinding.itemCustomAddRestaurantReviewImgV5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getRate = 5;

                        dialogBinding.itemCustomAddRestaurantReviewImgV1.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV1.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV2.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV2.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV3.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV3.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV4.setBorderWidth(0);
                        dialogBinding.itemCustomAddRestaurantReviewImgV4.setBorderColor(Color.TRANSPARENT);

                        dialogBinding.itemCustomAddRestaurantReviewImgV5.setBorderWidth(10);
                        dialogBinding.itemCustomAddRestaurantReviewImgV5.setBorderColor(Color.GREEN);
                    }
                });
                dialogBinding.itemCustomAddRestaurantReviewBtnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(getRate == 0 || dialogBinding.itemCustomAddRestaurantReviewEtComment.getText().toString().isEmpty()){

                            if (getRate == 0){
                                dialogBinding.itemCustomAddRestaurantReviewImgV1.setBorderWidth(10);
                                dialogBinding.itemCustomAddRestaurantReviewImgV1.setBorderColor(Color.RED);

                                dialogBinding.itemCustomAddRestaurantReviewImgV2.setBorderWidth(10);
                                dialogBinding.itemCustomAddRestaurantReviewImgV2.setBorderColor(Color.RED);

                                dialogBinding.itemCustomAddRestaurantReviewImgV3.setBorderWidth(10);
                                dialogBinding.itemCustomAddRestaurantReviewImgV3.setBorderColor(Color.RED);

                                dialogBinding.itemCustomAddRestaurantReviewImgV4.setBorderWidth(10);
                                dialogBinding.itemCustomAddRestaurantReviewImgV4.setBorderColor(Color.RED);

                                dialogBinding.itemCustomAddRestaurantReviewImgV5.setBorderWidth(10);
                                dialogBinding.itemCustomAddRestaurantReviewImgV5.setBorderColor(Color.RED);
                            }

                            if (dialogBinding.itemCustomAddRestaurantReviewEtComment.getText().toString().isEmpty()){
                                dialogBinding.itemCustomAddRestaurantReviewEtComment.setError(getString(R.string.add_restaurant_review_et_comment));
                            }

                        }else {
                            addReview();
                        }
                    }
                });
        addReviewAlertDialogBuiler.setCancelable(false);
        addReviewAlertDialogBuiler.setView(dialogBinding.getRoot());
        reviewAlertDialog = addReviewAlertDialogBuiler.create();
        Objects.requireNonNull(reviewAlertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        reviewAlertDialog.show();
    }

    private void addReview() {
        if (getRate != 0){
            viewModel.addReview(getClient().addReview(getRate,dialogBinding.itemCustomAddRestaurantReviewEtComment.getText().toString(),43,"HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB"));
            startAddReviewCall();
        }
    }

    private void startAddReviewCall() {
        if (getActivity() != null){
            viewModel.addReviewsMutableLiveData.observe(getActivity(), new Observer<AddRestaurantReview>() {
                @Override
                public void onChanged(AddRestaurantReview addRestaurantReview) {
                    try {
                        if (addRestaurantReview.getStatus() == 1) {
                            reviewAlertDialog.dismiss();
                            reviewsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "onResponse status-0: \n" + addRestaurantReview.getMsg(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.fragmentRestaurantReviewsRvItems.setHasFixedSize(true);
        binding.fragmentRestaurantReviewsRvItems.setLayoutManager(layoutManager);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        getReviews(current_page);

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        binding.fragmentRestaurantReviewsRvItems.addOnScrollListener(onEndLess);

        if(getActivity()!=null){
            reviewsAdapter = new RestaurantReviewsAdapter(getActivity(), reviewsDataList, getActivity().getSupportFragmentManager(), getActivity());
            binding.fragmentRestaurantReviewsRvItems.setAdapter(reviewsAdapter);
        }
        getReviews(1);
    }

    private void getReviews(int page) {
        viewModel.getReviews(getClient().getAllReviews(43, page));
        startReviewsCall(page);
    }

    private void startReviewsCall(int page) {
        if (getActivity() != null){
            viewModel.reviewsMutableLiveData.observe(getActivity(), new Observer<DisplayAllReviews>() {
                @Override
                public void onChanged(DisplayAllReviews displayAllReviews) {
                    try {
                        if (displayAllReviews.getStatus() == 1) {
                            maxPage = displayAllReviews.getData().getLastPage();
                            //restaurantsAdapter.clear();
                            reviewsDataList.addAll(displayAllReviews.getData().getData());
                            reviewsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "onResponse status-0: \n" + displayAllReviews.getMsg(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}