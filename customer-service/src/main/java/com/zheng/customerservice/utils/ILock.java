package com.zheng.customerservice.utils;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/23/2024 - 22:35
 */
public interface ILock {
  
  /**
   * try to get the lock.
   *
   * @return
   */
  boolean tryLock();
  
  /**
   * release lock.
   */
  void unlock();
}
