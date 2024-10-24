package com.pedestriamc.fonts.text;

import java.util.HashMap;

public class TestFont extends UnicodeFont {

    public TestFont() {
        super("TEST_FONT", new HashMap<Character, String>() {{
            put('a', "\uD835\uDCB6");  // 𝒶
            put('b', "\uD835\uDCB7");  // 𝒷
            put('c', "\uD835\uDCB8");  // 𝒸
            put('d', "\uD835\uDCB9");  // 𝒹
            put('e', "\u212F");        // ℯ
            put('f', "\uD835\uDCBB");  // 𝒻
            put('g', "\u210A");        // ℊ
            put('h', "\uD835\uDCBD");  // 𝒽
            put('i', "\uD835\uDCBE");  // 𝒾
            put('j', "\uD835\uDCBF");  // 𝒿
            put('k', "\uD835\uDCC0");  // 𝓀
            put('l', "\uD835\uDCC1");  // 𝓁
            put('m', "\uD835\uDCC2");  // 𝓂
            put('n', "\uD835\uDCC3");  // 𝓃
            put('o', "\u2134");        // ℴ
            put('p', "\uD835\uDCC5");  // 𝓅
            put('q', "\uD835\uDCC6");  // 𝓆
            put('r', "\uD835\uDCC7");  // 𝓇
            put('s', "\uD835\uDCC8");  // 𝓈
            put('t', "\uD835\uDCC9");  // 𝓉
            put('u', "\uD835\uDCCA");  // 𝓊
            put('v', "\uD835\uDCCB");  // 𝓋
            put('w', "\uD835\uDCCC");  // 𝓌
            put('x', "\uD835\uDCCD");  // 𝓍
            put('y', "\uD835\uDCCE");  // 𝓎
            put('z', "\uD835\uDCCF");  // 𝓏
        }});
    }
}
