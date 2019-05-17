package com.rong.gt.api.unification.stack;

import com.rong.gt.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap-based implementation of map with key type {@link ItemAndMetadata.class}
 * This map automatically takes care of wildcard item stacks as keys
 * @param <V> value type
 */
public class WildcardAwareHashMap<V> extends HashMap<ItemAndMetadata, V> {

    public WildcardAwareHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public WildcardAwareHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public WildcardAwareHashMap() {
    }

    public WildcardAwareHashMap(Map<? extends ItemAndMetadata, ? extends V> m) {
        super(m);
    }

    @Override
    public V get(Object key) {
    	ItemAndMetadata itemStack = (ItemAndMetadata) key;
        V resultValue = super.get(key);
        if (resultValue == null && itemStack.itemDamage != Values.W) {
            ItemAndMetadata wildcardStack = new ItemAndMetadata(itemStack.item, Values.W);
            resultValue = super.get(wildcardStack);
        }
        return resultValue;
    }

    @Override
    public boolean containsKey(Object key) {
    	ItemAndMetadata itemStack = (ItemAndMetadata) key;
        boolean resultValue = super.containsKey(key);
        if (!resultValue && itemStack.itemDamage != Values.W) {
            ItemAndMetadata wildcardStack = new ItemAndMetadata(itemStack.item, Values.W);
            resultValue = super.containsKey(wildcardStack);
        }
        return resultValue;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        V value = get(key);
        return value == null ? defaultValue : value;
    }
}
