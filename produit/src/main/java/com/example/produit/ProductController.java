package com.example.produit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductGraphQLController {

    @Autowired
    private ProductService service;

    @QueryMapping
    public List<Product> products() {
        return service.getAllProducts();
    }

    @MutationMapping
    public Product addProduct(@Argument String name, @Argument int quantity, @Argument double price) {
        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        return service.addProduct(product);
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument String name, @Argument int quantity, @Argument double price) {
        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        return service.updateProduct(id, product);
    }

    @MutationMapping
    public boolean deleteProduct(@Argument Long id) {
        service.deleteProduct(id);
        return true;
    }
}
