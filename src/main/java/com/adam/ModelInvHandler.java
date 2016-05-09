package com.adam;

import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.log4j.Logger;

import com.adam.model.ColumnM;
import com.adam.model.DomainM;
import com.adam.model.KeyM;
import com.adam.model.KeyType;
import com.adam.model.TableM;

public class ModelInvHandler implements InvocationHandler {
    static Logger logger = Logger.getLogger(ModelInvHandler.class);

    String ref = null;

    KeyType keyType = null;

    public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("setType")) {
            this.keyType = ((KeyType) args[0]);
            return null;
        }
        if (method.getName().equals("setRef")) {
            this.ref = ((String) args[0]);
            return null;
        }
        RefAble refObj = null;
        if (Proxy.isProxyClass(obj.getClass())) {
            refObj = (RefAble) load(obj, this.ref);
        }
        obj = refObj;
        try {
            return method.invoke(refObj, args);
        } catch (InvocationTargetException e) {
            System.out.println(e.getCause());
        }
        return null;
    }

    public Object load(Object obj, String ref) {
        if ((obj instanceof KeyM)) {
            obj = ParseHandler.getInstance().getKey(ref);
            if (this.keyType != null) {
                KeyM key = (KeyM) obj;
                key.setType(this.keyType);
            }
        } else if ((obj instanceof TableM)) {
            obj = ParseHandler.getInstance().getTable(ref);
        } else if ((obj instanceof ColumnM)) {
            obj = ParseHandler.getInstance().getColumn(ref);
        } else if ((obj instanceof DomainM)) {
            obj = ParseHandler.getInstance().getDomain(ref);
        } else {
            throw new RuntimeException("unknow proxy type : " + obj);
        }
        if (obj == null) {
            logger.error("can not load obj : " + ref);
        }
        return obj;
    }
}
