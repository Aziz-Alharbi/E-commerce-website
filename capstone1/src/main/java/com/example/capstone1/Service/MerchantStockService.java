package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantStockService {

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public ArrayList<MerchantStock> getAllMerchantStocks() {
        return merchantStocks;
    }

    public void addMerchantStock(MerchantStock merchantStock) {
        merchantStocks.add(merchantStock);
    }

    public boolean deleteMerchantStock(String id) {
        for (MerchantStock ms : merchantStocks) {
            if (ms.getId().equals(id)) {
                merchantStocks.remove(ms);
                return true;
            }
        }
        return false;
    }

    public boolean updateMerchantStock(String id, MerchantStock merchantStock) {
        for (MerchantStock ms : merchantStocks) {
            if (ms.getId().equals(id)) {
                int index = merchantStocks.indexOf(ms);
                merchantStocks.set(index, merchantStock);
                return true;
            }
        }
        return false;
    }


    public boolean addMerch(String merchantId, String productId, int stock) {
        for (MerchantStock ms : merchantStocks) {
            if (ms.getProductId().equals(productId) && ms.getMerchantId().equals(merchantId)) {
                ms.setStock(ms.getStock() + stock);
                return true;
            }
        }
        return false;
    }





}
