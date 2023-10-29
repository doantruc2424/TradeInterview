package com.example.TradeInterview.util;

import java.util.HashMap;
import java.util.Map;

public class WalletLock {
    public static Map<String, WalletLock> currentLocks = new HashMap<>();

    public static synchronized WalletLock getLockObjectForBusinessId(String businessId){

        WalletLock currentLock = currentLocks.get(businessId);
        if(currentLock == null){
            WalletLock lock = new WalletLock();
            currentLocks.put(businessId,lock);
            return lock;
        }
        else{
            return currentLock;
        }
    }
    public static synchronized  void releaseLockForBusinessId(String businessId){
        currentLocks.remove(businessId);
    }
}
