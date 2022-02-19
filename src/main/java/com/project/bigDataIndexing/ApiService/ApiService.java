package com.project.bigDataIndexing.ApiService;

import org.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.json.Json;

public class ApiService {
    private JedisPool jedisPool;

    private JedisPool getJedisConnection(){
        if(this.jedisPool == null){
            this.jedisPool = new JedisPool();
        }
        return jedisPool;
    }

    public String createPlan(JSONObject obj){
        String planKey = obj.get("objectId").toString();
        Jedis jedis = this.getJedisConnection().getResource();
        jedis.set(planKey,obj.toString());
        jedis.close();
        return planKey;
    }

    public JSONObject getPlan(String planKey){
        Jedis jedis = this.getJedisConnection().getResource();
        String jsonkey = jedis.get(planKey);
        jedis.close();
        JSONObject obj = new JSONObject(jsonkey);
        return  obj;
    }

    public void deletePlan(String planKey) {
        Jedis jedis = this.getJedisConnection().getResource();
        jedis.del(planKey);
        jedis.close();
    }

    public boolean ifObjectExists(String planObject){
        Jedis jedis = this.getJedisConnection().getResource();
        String presentKey = jedis.get(planObject);
        jedis.close();
        if(presentKey == null || presentKey.isEmpty()){
            System.out.println("in fun ifobjectexists");
            return false;
        }else return true;
    }
}
