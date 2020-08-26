
package com.elatienda.kaytamarka.sofra.data.model.add_restaurant_review;

import com.elatienda.kaytamarka.sofra.data.model.display_all_reviews.DisplayAllReviewsData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddRestaurantReviewData {

    @SerializedName("review")
    @Expose
    private DisplayAllReviewsData review; // DisplayAllReviewsData

    public DisplayAllReviewsData getReview() {
        return review;
    } // DisplayAllReviewsData

    public void setReview(DisplayAllReviewsData review) {
        this.review = review;
    } // DisplayAllReviewsData

}
