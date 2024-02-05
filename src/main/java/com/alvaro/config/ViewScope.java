package com.alvaro.config;

import jakarta.faces.context.FacesContext;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Map;

public class ViewScope implements Scope {
    @Override
    public Object get(final String name, ObjectFactory<?> objectFactory) {
        if(FacesContext.getCurrentInstance().getViewRoot() != null){
            final Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            if(viewMap.containsKey(name)) {
                return viewMap.get(name);
            }else{
                final Object object = objectFactory.getObject();
                viewMap.put(name, object);
                return object;
            }
        }else{
            return null;
        }
    }

    @Override
    public Object remove(final String name) {
        if(FacesContext.getCurrentInstance().getViewRoot() != null){
            return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
        }else{
            return null;
        }
    }

    @Override
    public void registerDestructionCallback(final String name, final Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(final String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
