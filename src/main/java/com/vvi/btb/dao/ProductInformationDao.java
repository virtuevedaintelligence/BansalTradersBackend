package com.vvi.btb.dao;

import com.vvi.btb.domain.entity.ProductInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInformationDao extends JpaRepository<ProductInformation,Long> {
}
