package com.inteagle.common.util;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Lock锁工具类
 * 
 * @author IVAn
 * @CreateDate 2018年8月15日 下午4:00:34
 */
public class LockUtil {

	// 读写锁
	private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	// 采用弱引用保存锁对象，使GC可以自动回收锁
	private static Map<String, Lock> LockMap = new HashMap<>();

	private LockUtil() {
		throw new RuntimeException("new LockUtil instance error");
	}

	/**
	 * 获取lock锁对象
	 * 
	 * @param key
	 * @return Lock
	 * @author IVAn
	 * @createDate 2018年8月15日 下午4:06:21
	 */
	public static Lock getLock(String key) {
		readWriteLock.readLock().lock();
		try {
			Lock lock = LockMap.get(key);
			if (lock != null) {
				readWriteLock.readLock().unlock();
				return lock;
			} else {
				// 在使用写锁之前要释放读锁
				readWriteLock.readLock().unlock();
				return putLock(key);
			}
		} catch (Exception e) {
			readWriteLock.readLock().unlock();
			throw e;
		}
	}

	/**
	 * 创建并保存lock锁
	 * 
	 * @param key
	 * @return Lock
	 * @author IVAn
	 * @createDate 2018年8月15日 下午4:09:25
	 */
	public static Lock putLock(String key) {
		readWriteLock.writeLock().lock();
		try {
			Lock lock = new ReentrantLock();
			LockMap.put(key, lock);
			return lock;
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	/**
	 * 删除lock锁
	 * 
	 * @param key
	 * @return Lock
	 * @author IVAn
	 * @createDate 2018年8月15日 下午4:19:07
	 */
	public static Lock removeLock(String key) {
		readWriteLock.writeLock().lock();
		try {
			return LockMap.remove(key);
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}
}

