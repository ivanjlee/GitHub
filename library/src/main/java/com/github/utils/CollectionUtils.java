package com.github.utils;

import java.util.Collection;

/**
 * collection tools
 *
 * @author  Ivan on 2018-11-29 00:11.
 * @version v0.1
 * @since   v0.1.0
 */
public final class CollectionUtils {

    /**
     * whether a collection is empty
     *
     * @param collection collection
     * @return 0 if the collection is empty
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * get the size of a collection
     * @param collection collection
     * @return 0 if collection is empty
     */
    public static int size(Collection collection) {
        return isEmpty(collection) ? 0 : collection.size();
    }

}
