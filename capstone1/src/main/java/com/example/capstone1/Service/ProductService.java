package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    //6- Create endpoint for getting and adding and deleting updating a Product.




    ArrayList<Product> products = new ArrayList<>();


    public ArrayList<Product> getAllProducts(){
        return products;
    }

    public Product getProduct(String id){

        for(Product product1 : products){

            if (product1.getId().equals(id)){
                return product1;
            }
        }
        return null;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public boolean deleteProduct(String id){

        for(Product product1 : products){
            if (product1.getId().equals(id)){
                int index = products.indexOf(product1);
                products.remove(index);
                return true;
            }
        }
        return false;
    }

    public boolean updateProduct(String id, Product product){

        for(Product product1 : products){
            if (product1.getId().equals(id)){
                int index = products.indexOf(product1);
                products.set(index, product);
                return true;
            }
        }
        return false;
    }

    public boolean setProductDiscount(String productId, double discount) {
        for (Product p : products) {
            if (p.getId().equals(productId)) {
                p.setDiscountPercentage(discount);
                return true;
            }
        }
        return false;
    }







}
