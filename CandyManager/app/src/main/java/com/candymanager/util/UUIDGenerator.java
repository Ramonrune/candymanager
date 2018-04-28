package com.candymanager.util;

import java.util.UUID;

/**
 * Created by Usuario on 19/04/2018.
 */

public class UUIDGenerator {

    public static String uuid(){
        return UUID.randomUUID().toString();
    }
}
