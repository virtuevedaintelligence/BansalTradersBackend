package com.vvi.btb.domain.response;

import java.util.Date;

public record RatingResponse(
         long id,
         String reviewBy,
         int starRating,
         String reviewDescription,
         Date reviewDate,
         String location
) {
}
