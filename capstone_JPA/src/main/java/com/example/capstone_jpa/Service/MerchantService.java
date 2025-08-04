package com.example.capstone_jpa.Service;

import com.example.capstone_jpa.Model.Merchant;
import com.example.capstone_jpa.Repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    public void addMerchant(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    public boolean deleteMerchant(Integer id) {
        if (!merchantRepository.existsById(id)) return false;
        merchantRepository.deleteById(id);
        return true;
    }

    public boolean updateMerchant(Integer id, Merchant updatedMerchant) {
        Merchant oldMerchant = merchantRepository.findById(id).orElse(null);
        if (oldMerchant == null) return false;

        oldMerchant.setName(updatedMerchant.getName());
        merchantRepository.save(oldMerchant);
        return true;
    }

    public boolean existsById(Integer id) {
        return merchantRepository.existsById(id);
    }
}
