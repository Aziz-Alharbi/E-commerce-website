package com.example.capstone_jpa.Service;

import com.example.capstone_jpa.Api.ApiResponse;
import com.example.capstone_jpa.Model.MerchantStock;
import com.example.capstone_jpa.Model.Product;
import com.example.capstone_jpa.Model.User;
import com.example.capstone_jpa.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProductService productService;
    private final MerchantStockService merchantStockService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public boolean deleteUser(Integer id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }

    public boolean updateUser(Integer id, User updatedUser) {
        User old = userRepository.findById(id).orElse(null);
        if (old == null) return false;

        updatedUser.setId(id);
        userRepository.save(updatedUser);
        return true;
    }

    public ApiResponse buyProduct(Integer userId, Integer productId, Integer merchantId) {
        User user = userRepository.findById(userId).orElse(null);
        Product product = productService.getProduct(productId);
        MerchantStock stock = merchantStockService.findStock(merchantId, productId);

        if (user == null || product == null || stock == null) return new ApiResponse("Invalid IDs");
        if (stock.getStock() <= 0) return new ApiResponse("Product out of stock");

        double price = product.getPrice();
        double discountAmount = price * product.getDiscountPercentage() / 100;
        price -= discountAmount;

        double deliveryFee = (price >= 200) ? 0 : product.getDeliveryFee();
        double finalPrice = price + deliveryFee;

        if (user.getBalance() < finalPrice) return new ApiResponse("Insufficient balance");

        stock.setStock(stock.getStock() - 1);
        user.setBalance(user.getBalance() - finalPrice);
        user.setCanRefund(true);

        user.setLastProductId(productId);
        user.setLastMerchantId(merchantId);

        userRepository.save(user);
        return new ApiResponse("Purchase successful");
    }

    public ApiResponse refundProduct(Integer userId, Integer productId, Integer merchantId) {
        User user = userRepository.findById(userId).orElse(null);
        Product product = productService.getProduct(productId);
        MerchantStock stock = merchantStockService.findStock(merchantId, productId);

        if (user == null || product == null || stock == null) return new ApiResponse("Invalid IDs");

        if (!user.getCanRefund() ||
                !user.getLastProductId().equals(productId.toString()) ||
                !user.getLastMerchantId().equals(merchantId.toString())) {
            return new ApiResponse("Refund not allowed");
        }

        user.setRefundRequested(true);
        user.setRequestType("refund");
        user.setRequestApproved(false);

        user.setBalance(user.getBalance() + product.getPrice());
        stock.setStock(stock.getStock() + 1);
        user.setCanRefund(false);

        userRepository.save(user);
        return new ApiResponse("Refund request submitted, awaiting admin approval");
    }

    public ApiResponse replaceProduct(Integer userId, Integer oldProductId, Integer merchantId, Integer newProductId) {
        User user = userRepository.findById(userId).orElse(null);
        Product oldProduct = productService.getProduct(oldProductId);
        Product newProduct = productService.getProduct(newProductId);
        MerchantStock oldStock = merchantStockService.findStock(merchantId, oldProductId);
        MerchantStock newStock = merchantStockService.findStock(merchantId, newProductId);

        if (user == null || oldProduct == null || newProduct == null || oldStock == null || newStock == null) {
            return new ApiResponse("Invalid IDs");
        }

        if (!user.getCanRefund() ||
                !user.getLastProductId().equals(oldProductId.toString()) ||
                !user.getLastMerchantId().equals(merchantId.toString())) {
            return new ApiResponse("Replacement not allowed");
        }

        if (newStock.getStock() <= 0) return new ApiResponse("New product is out of stock");

        double priceDiff = newProduct.getPrice() - oldProduct.getPrice();
        if (priceDiff > 0 && user.getBalance() < priceDiff) {
            return new ApiResponse("Insufficient balance to exchange");
        }

        if (priceDiff > 0) {
            user.setBalance(user.getBalance() - priceDiff);
        } else {
            user.setBalance(user.getBalance() + Math.abs(priceDiff));
        }

        oldStock.setStock(oldStock.getStock() + 1);
        newStock.setStock(newStock.getStock() - 1);

        user.setReplaceRequested(true);
        user.setRequestType("replace");
        user.setNewProductId(newProductId);
        user.setRequestApproved(false);
        user.setCanRefund(false);
        user.setLastProductId(newProductId);
        userRepository.save(user);
        return new ApiResponse("Replacement request submitted, awaiting admin approval");
    }

    public ApiResponse processApproval(Integer userId, boolean approve) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return new ApiResponse("User not found");

        if (!user.getRefundRequested() && !user.getReplaceRequested()){
            return new ApiResponse("No request to approve");
        }

        if (approve) {
            user.setRequestApproved(true);
            user.setRefundRequested(false);
            user.setReplaceRequested(false);
            userRepository.save(user);
            return new ApiResponse("Request approved and processed");
        } else {
            user.setRefundRequested(false);
            user.setReplaceRequested(false);
            user.setRequestApproved(false);
            userRepository.save(user);
            return new ApiResponse("Request denied");
        }
    }

    public ApiResponse checkProductDiscount(Integer productId) {
        Product product = productService.getProduct(productId);
        if (product == null) return new ApiResponse("Invalid product ID");

        double discount = product.getDiscountPercentage();
        double price = product.getPrice();
        double discountedPrice = price - (price * (discount / 100));
        boolean freeDelivery = price >= 200;

        return new ApiResponse("Discount: " + discount + "%, Final Price: " + discountedPrice +
                ", " + (freeDelivery ? "Free delivery" : "Delivery fee applies"));
    }

}
