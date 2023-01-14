package org.stellardev.wavecore.configuration;

import lombok.SneakyThrows;
import org.stellardev.wavecore.file.YamlFile;

import java.lang.reflect.*;
import java.util.*;


public class Serialization {


    private static final List<Class<? extends Serializable>> CONFIG_MAP = new ArrayList<>();

    public static void register(Class<? extends Serializable> clazz) {
        if (CONFIG_MAP.contains(clazz)) {
            return;
        }
        CONFIG_MAP.add(clazz);
    }

    public static Class<? extends Serializable> getSerializableClass(Serializable configItem) {
        for (Class<? extends Serializable> configItems : CONFIG_MAP) {
            if (configItems.equals(configItem.getClass())) {
                return configItems;
            }
        }
        return null;
    }

    public static Object deserialize(Class<? extends Serializable> clazz, YamlFile yamlFile, String path) {
        Field field;
        Method method;
        try {
            method = clazz.getMethod("deserialize", YamlFile.class, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }

        if (!Modifier.isStatic(method.getModifiers())) {
            return null;
        }

        try {
            return method.invoke(clazz, yamlFile, path);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;

    }

    @SneakyThrows
    public static Map<String, Object> serialize(Object obj) {
        Map<String, Object> map = new HashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object classObject = field.get(obj);
            if (classObject instanceof Serializable) {
                Map<String, Object> serialized = serialize(classObject);
                for (String key : serialized.keySet()) {
                    map.put(field.getName() + "." + key, serialized.get(key));
                }
            } else if (classObject instanceof List) {

                List<?> list = (List<?>) classObject;

                if (list.isEmpty()) {
                    continue;
                }

                if (list.get(0) instanceof Serializable) {

                    for (int i = 0; i < list.size(); i++) {
                        Map<String, Object> serialized = serialize(list.get(i));
                        for (String key : serialized.keySet()) {
                            Object object = serialized.get(key);
                            if (object instanceof Serializable) {
                                Map<String, Object> subObjectSerialized = serialize(object);
                                for (String subObjectKey : subObjectSerialized.keySet()) {
                                    map.put(field.getName() + "." + i + "." + key + "." + subObjectKey, subObjectSerialized.get(subObjectKey));
                                }
                                continue;
                            }
                            map.put(field.getName() + "." + i + "." + key, object);
                        }
                    }
                    continue;
                }
                map.put(field.getName(), list);
            } else {
                map.put(field.getName(), classObject);
            }

        }
        return map;
    }


}
