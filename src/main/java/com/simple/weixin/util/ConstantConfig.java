package com.simple.weixin.util;

import com.typesafe.config.*;

/**
 * Created by lining on 15-2-15.
 */
public class ConstantConfig {

    private static Config config = ConfigFactory.load("env.properties",
            ConfigParseOptions.defaults().setSyntax(ConfigSyntax.PROPERTIES).setAllowMissing(false),
            ConfigResolveOptions.defaults()
                    .setUseSystemEnvironment(false));

    public static String getStringValue(String key) {
        return config.getString(key);
    }
    public static int getIntValue(String key) {
        return config.getInt(key);
    }

    //
    public static final String oauthURL_callback = getStringValue("oauthURL.callback");
    public static final String secertKey = getStringValue("secretKey");

    //jiayitang
    public static final String jiayitang_callback = getStringValue("jiayitang.callback");
    public static final String jiayitang_chos = getStringValue("jiayitang.chos");
    public static final String jiayitang_key = getStringValue("jiayitang.key");

    //cerp
    public static final String cerp_callback = getStringValue("cerp.callback");
    public static final String cerp_chos = getStringValue("cerp.chos");
    public static final String cerp_key = getStringValue("cerp.key");

    public static final String yyGame_callback = getStringValue("yy.game.callback");
    public static final String yyGame_key = getStringValue("yy.game.key");
}