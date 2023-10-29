package com.example.TradeInterview.util;

import java.util.HashMap;
import java.util.Map;

public class LockBusiness {
    public static Map<String, LockBusiness> currentLocks = new HashMap<>();

    public static synchronized LockBusiness getLockObjectForBusinessId(String businessId){

        LockBusiness currentLock = currentLocks.get(businessId);
        if(currentLock == null){
            LockBusiness lock = new LockBusiness();
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
