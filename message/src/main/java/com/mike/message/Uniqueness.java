package com.mike.message;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Mike
 */
public interface Uniqueness {
    public default String generateUniqueID() {
        String request_ID;
        Random random = new Random();
        int x = random.nextInt();
        request_ID = "" + x;
        Date currentDate = new Date();
        Timestamp t = new Timestamp(currentDate.getTime());
        request_ID = request_ID + t;
        return request_ID;
    }
}
