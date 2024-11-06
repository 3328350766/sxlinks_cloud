//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.common.util.jsonschema;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class b {
    private static final Logger a = LoggerFactory.getLogger(b.class);

    public b() {
    }

    public static JSONObject a(JsonSchemaDescrip var0, List<CommonProperty> var1) {
        JSONObject var2 = new JSONObject();
        var2.put("$schema", var0.get$schema());
        var2.put("type", var0.getType());
        var2.put("title", var0.getTitle());
        List var3 = var0.getRequired();
        var2.put("required", var3);
        JSONObject var4 = new JSONObject();
        Iterator var5 = var1.iterator();

        while(var5.hasNext()) {
            CommonProperty var6 = (CommonProperty)var5.next();
            Map var7 = var6.getPropertyJson();
            var4.put(var7.get("key").toString(), var7.get("prop"));
        }

        var2.put("properties", var4);
        return var2;
    }

    public static JSONObject a(String var0, List<String> var1, List<CommonProperty> var2) {
        JSONObject var3 = new JSONObject();
        var3.put("type", "object");
        var3.put("view", "tab");
        var3.put("title", var0);
        if (var1 == null) {
            var1 = new ArrayList();
        }

        var3.put("required", var1);
        JSONObject var4 = new JSONObject();
        Iterator var5 = var2.iterator();

        while(var5.hasNext()) {
            CommonProperty var6 = (CommonProperty)var5.next();
            Map var7 = var6.getPropertyJson();
            var4.put(var7.get("key").toString(), var7.get("prop"));
        }

        var3.put("properties", var4);
        return var3;
    }
}
