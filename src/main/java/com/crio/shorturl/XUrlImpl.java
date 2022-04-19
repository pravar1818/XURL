package com.crio.shorturl;

import java.util.HashMap;

class XUrlImpl implements XUrl {

    private HashMap<String,String>lToS = new HashMap<>();;
    private HashMap<String,String>sToL = new HashMap<>();;
    private HashMap<String,Integer>lookUp = new HashMap<>();;

    private String generateUrl(String longUrl){
        String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcsefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<9;i++){
            int index = (int)(alphaNumeric.length()*Math.random());
            sb.append(alphaNumeric.charAt(index));
        }
        return sb.toString();
    }

    @Override
    public String registerNewUrl(String longUrl) {
       if(lToS.containsKey(longUrl)){
            return lToS.get(longUrl);
        }else{
            String shortUrl = "http://short.url/" + generateUrl(longUrl);
            lToS.put(longUrl, shortUrl);
            sToL.put(shortUrl, longUrl);
            return shortUrl;
        }
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if(sToL.containsKey(shortUrl)){
            return null;
        }else{
            lToS.put(longUrl, shortUrl);
            sToL.put(shortUrl, longUrl);
            return shortUrl;
        }
    }

    @Override
    public String getUrl(String shortUrl) {
        String longUrl = sToL.get(shortUrl);
        if(longUrl!=null){
            if(lookUp.containsKey(longUrl)){
                Integer cnt = lookUp.get(longUrl);
                cnt = cnt + 1;
                lookUp.put(longUrl, cnt);
            }else{
                lookUp.put(longUrl, 1);
            }
        }
        return longUrl;
    }

    @Override
    public Integer getHitCount(String longUrl) {
        if(lookUp.containsKey(longUrl)){
            return lookUp.get(longUrl);
        }else{
            return 0;
        }
    }

    @Override
    public String delete(String longUrl) {
        if(!lToS.containsKey(longUrl)){
            return null;
        }else{
            String shortUrl = lToS.get(longUrl);
            lToS.remove(longUrl);
            sToL.remove(shortUrl);
            return shortUrl;
        }
    }

}