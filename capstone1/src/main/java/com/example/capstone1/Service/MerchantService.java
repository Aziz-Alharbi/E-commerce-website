package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getAllMerchants() {
        return merchants;
    }

    public void addMerchant(Merchant merchant) {
        merchants.add(merchant);
    }

    public boolean deleteMerchant(String id) {
        for (Merchant m : merchants) {
            if (m.getId().equals(id)) {
                merchants.remove(m);
                return true;
            }
        }
        return false;
    }

    public boolean updateMerchant(String id, Merchant merchant) {
        for (Merchant m : merchants) {
            if (m.getId().equals(id)) {
                int index = merchants.indexOf(m);
                merchants.set(index, merchant);
                return true;
            }
        }
        return false;
    }
}
