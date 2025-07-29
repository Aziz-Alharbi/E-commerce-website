package com.example.capstone1.Service;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {



    private final ProductService productService;
    private final MerchantStockService merchantStockService;


    ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean deleteUser(String id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                users.remove(u);
                return true;
            }
        }
        return false;
    }

    public boolean updateUser(String id, User user) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                int index = users.indexOf(u);
                users.set(index, user);
                return true;
            }
        }
        return false;
    }

    public ApiResponse buyProduct(String userId, String productId, String merchantId){

        User user = null;
        Product product = null;
        MerchantStock stock = null;


        for(User user1 : users){
            if (user1.getId().equals(userId)) {
                user = user1;
            }
        }

        for(Product product1 : productService.getAllProducts()){
            if (product1.getId().equals(productId)) {
                product = product1;
            }
        }

        for(MerchantStock ms : merchantStockService.getAllMerchantStocks()){
            if (ms.getProductId().equals(productId) && ms.getMerchantId().equals(merchantId)) {
                stock = ms;
            }
        }

        if (user == null || product == null || stock == null) {
            return new ApiResponse("Invalid IDs");
        }
        if (stock.getStock() <= 0) {
            return new ApiResponse("Product out of stock");
        }


        double price = product.getPrice();
        double discountAmount = price * product.getDiscountPercentage() / 100;
        price -= discountAmount;

        double deliveryFee = (price >= 200) ? 0 : product.getDeliveryFee();
        double finalPrice = price + deliveryFee;

        if (user.getBalance() < finalPrice) {
            return new ApiResponse("Insufficient balance");
        }

        stock.setStock(stock.getStock() - 1);
        user.setBalance(user.getBalance() - price);


        // Set refund info
        user.setCanRefund(true);
        user.setLastProductId(productId);
        user.setLastMerchantId(merchantId);
        return new ApiResponse( "Purchase successful");
    }

    public ApiResponse refundProduct(String userId, String productId, String merchantId) {
        User user = null;
        Product product = null;
        MerchantStock stock = null;

        for (User u : users) {
            if (u.getId().equals(userId)) {
                user = u;
            }
        }
        for (Product p : productService.getAllProducts()) {
            if (p.getId().equals(productId)) {
                product = p;
            }
        }
        for (MerchantStock s : merchantStockService.getAllMerchantStocks()) {
            if (s.getProductId().equals(productId) && s.getMerchantId().equals(merchantId)) {
                stock = s;
            }
        }


        if (user == null || product == null || stock == null) {
            return new ApiResponse("Invalid IDs");
        }

        if (!user.isCanRefund() ||
                !user.getLastProductId().equals(productId) ||
                !user.getLastMerchantId().equals(merchantId)) {
            return new ApiResponse("Refund not allowed");
        }

        // Store request instead of processing immediately
        user.setRefundRequested(true);
        user.setRequestType("refund");
        user.setRequestApproved(false); // wait for admin

        // Process refund
        user.setBalance(user.getBalance() + product.getPrice());
        stock.setStock(stock.getStock() + 1);
        user.setCanRefund(false);

//        return new ApiResponse("Refund successful");
        return new ApiResponse("Refund request submitted, awaiting admin approval");

    }


    public ApiResponse checkProductDiscount(String productId) {
        Product product = null;

        for (Product p : productService.getAllProducts()) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }

        if (product == null) {
            return new ApiResponse("Invalid product ID");
        }


        double discount = product.getDiscountPercentage();
        double price = product.getPrice();
        if (product.getDiscountPercentage() > 0) {
            price -= price * (product.getDiscountPercentage() / 100);
        }

        boolean freeDelivery = product.getPrice() >= 200;

        return new ApiResponse("Discount: " + discount + "%, " +
                (freeDelivery ? "Eligible for free delivery" : "Delivery fee applies"));
    }


    public ApiResponse replaceProduct(String userId, String oldProductId, String merchantId, String newProductId) {
        User user = null;
        Product oldProduct = null;
        Product newProduct = null;
        MerchantStock oldStock = null;
        MerchantStock newStock = null;

        for (User u : users) {
            if (u.getId().equals(userId)) user = u;
        }

        for (Product p : productService.getAllProducts()) {
            if (p.getId().equals(oldProductId)) oldProduct = p;
            if (p.getId().equals(newProductId)) newProduct = p;
        }

        for (MerchantStock ms : merchantStockService.getAllMerchantStocks()) {
            if (ms.getProductId().equals(oldProductId) && ms.getMerchantId().equals(merchantId)) oldStock = ms;
            if (ms.getProductId().equals(newProductId) && ms.getMerchantId().equals(merchantId)) newStock = ms;
        }

        if (user == null || oldProduct == null || newProduct == null || oldStock == null || newStock == null) {
            return new ApiResponse("Invalid IDs");
        }

        user.setReplaceRequested(true);
        user.setRequestType("replace");
        user.setRequestApproved(false);
        user.setNewProductId(newProductId); // save new product


        if (!user.isCanRefund() || !user.getLastProductId().equals(oldProductId) || !user.getLastMerchantId().equals(merchantId)) {
            return new ApiResponse("Replacement not allowed");
        }

        if (newStock.getStock() <= 0) {
            return new ApiResponse("New product is out of stock");
        }

        double oldPrice = oldProduct.getPrice();
        double newPrice = newProduct.getPrice();

        if (newPrice > oldPrice) {
            double diff = newPrice - oldPrice;
            if (user.getBalance() < diff) return new ApiResponse("Insufficient balance to exchange");
            user.setBalance(user.getBalance() - diff);
        } else {
            double refund = oldPrice - newPrice;
            user.setBalance(user.getBalance() + refund);
        }

        oldStock.setStock(oldStock.getStock() + 1);
        newStock.setStock(newStock.getStock() - 1);
        user.setCanRefund(false);
        user.setLastProductId(newProductId);

      //  return new ApiResponse("Replace successful");
        return new ApiResponse("Replace request submitted, awaiting admin approval");

    }


    public ApiResponse processApproval(String userId, boolean approve) {
        for (User u : users) {
            if (u.getId().equals(userId)) {
                if (!u.isRefundRequested() && !u.isReplaceRequested())
                    return new ApiResponse("No request to approve");

                if (approve) {
                    if (u.getRequestType().equals("refund")) {
                        // do refund logic
                    } else if (u.getRequestType().equals("replace")) {
                        // do replace logic
                    }
                    u.setRequestApproved(true);
                    u.setRefundRequested(false);
                    u.setReplaceRequested(false);
                    return new ApiResponse("Request approved and processed");
                } else {
                    u.setRefundRequested(false);
                    u.setReplaceRequested(false);
                    return new ApiResponse("Request denied");
                }
            }
        }
        return new ApiResponse("User not found");
    }







}
