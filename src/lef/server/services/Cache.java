package lef.server.services;

import java.util.HashMap;
import java.util.Map;

public class Cache {

	private Map<String, Object> memCached;
	
	private static Cache cache;
	
	static { 
		cache = new Cache();
	}
	
	public static Cache instance(){
		return cache;
	}
	
	private Cache(){
		memCached = new HashMap<String, Object>();
	}
	
	public Object get(String key){
		return memCached.get(key);
	}
	
	public void set(String key, Object value){
		this.memCached.put(key, value);
	}
	
	public boolean has(String key){
		return this.memCached.containsKey(key);
	}
	
	public boolean delete(String key){
		if (this.memCached.remove(key) != null){
			return true;
		}else{
			return false;
		}
	}
	
}
