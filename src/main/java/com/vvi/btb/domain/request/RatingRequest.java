package com.vvi.btb.domain.request;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
@Data
public class RatingRequest implements Serializable {

    private String reviewBy;
    private int starRating;
    private String reviewDescription;
    private Date reviewDate;
    private int productId;
    private int userId;
}
