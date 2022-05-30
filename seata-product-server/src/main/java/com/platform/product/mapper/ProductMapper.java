package com.platform.product.mapper;



import com.platform.product.domain.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper
{
    public Product selectById(Long productId);

    public void updateById(Product product);
}
