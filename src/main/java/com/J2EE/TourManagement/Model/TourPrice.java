package com.J2EE.TourManagement.Model;

import lombok.Data;
import java.math.BigDecimal;

@Data

public class TourPrice {
    private Long id;
    private Long idTourDetail;
    private String priceType;
    private double price;

}
