package com.vvi.btb.domain.response;

public record ProductInformationResponse(
        int productPrice,
        int quantity, // 10 packets 20 packets
        int weight ,// 250gm 500gm
        int productMaxRetailPrice
) {
}
