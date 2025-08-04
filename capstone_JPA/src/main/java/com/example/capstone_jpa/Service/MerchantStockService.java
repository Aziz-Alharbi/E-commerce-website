package com.example.capstone_jpa.Service;

import com.example.capstone_jpa.Model.MerchantStock;
import com.example.capstone_jpa.Repository.MerchantStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    private final MerchantStockRepository merchantStockRepository;
    private final ProductService productService;
    private final MerchantService merchantService;

    public List<MerchantStock> getAllMerchantStocks() {
        return merchantStockRepository.findAll();
    }

    public void addMerchantStock(MerchantStock stock) {
        if (!productService.existsById(stock.getProductId()) ||
                !merchantService.existsById(stock.getMerchantId())) {
            throw new RuntimeException("Invalid product or merchant ID");
        }
        merchantStockRepository.save(stock);
    }

    public boolean deleteMerchantStock(Integer id) {
        if (!merchantStockRepository.existsById(id)) return false;
        merchantStockRepository.deleteById(id);
        return true;
    }

    public boolean updateMerchantStock(Integer id, MerchantStock updated) {
        MerchantStock old = merchantStockRepository.findById(id).orElse(null);
        if (old == null) return false;

        old.setProductId(updated.getProductId());
        old.setMerchantId(updated.getMerchantId());
        old.setStock(updated.getStock());

        merchantStockRepository.save(old);
        return true;
    }

    public boolean addMerch(Integer merchantId, Integer productId, int stock) {
        List<MerchantStock> stocks = merchantStockRepository.findAll();
        for (MerchantStock ms : stocks) {
            if (ms.getMerchantId().equals(merchantId) && ms.getProductId().equals(productId)) {
                ms.setStock(ms.getStock() + stock);
                merchantStockRepository.save(ms);
                return true;
            }
        }
        return false;
    }

    public MerchantStock findStock(Integer merchantId, Integer productId) {
        List<MerchantStock> stocks = merchantStockRepository.findAll();
        for (MerchantStock ms : stocks) {
            if (ms.getMerchantId().equals(merchantId) && ms.getProductId().equals(productId)) {
                return ms;
            }
        }
        return null;
    }

}
