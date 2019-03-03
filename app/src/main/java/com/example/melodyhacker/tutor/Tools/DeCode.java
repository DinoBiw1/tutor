package com.example.melodyhacker.tutor.Tools;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

/**
 * Created by melodyhacker on 2/3/19.
 */

public class DeCode {
    public String getHash(String encode) {

        byte[] data = Base64.decode(encode, Base64.DEFAULT);
        String hash = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            hash = new String(data, StandardCharsets.UTF_8);
        }
        return hash;
    }
}
