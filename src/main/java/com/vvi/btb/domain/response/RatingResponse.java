package com.vvi.btb.domain.response;

import java.util.Date;

public record RatingResponse(
         String reviewBy,
         int starRating,
         String reviewDescription,
         Date reviewDate,
         String location
) {
}
