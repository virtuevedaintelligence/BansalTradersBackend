package com.vvi.btb.domain.request;

import lombok.Data;
import java.io.Serializable;
@Data
public class RatingRequest implements Serializable {

    private String reviewBy;
    private int starRating;
    private String reviewDescription;
    private Long productId;
    private Long userId;
    private String location;
}
