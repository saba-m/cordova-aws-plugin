package com.babyscripts.awsplugin;

/**
 * Created by dkopel on 7/13/17.
 */
public enum DefinedEnvironment {
    Prod("https://internal-api.getbabyscripts.com"),
    Demo("https://demo-internal-api.getbabyscripts.com"),
    Staging("https://staging-internal-api.getbabyscripts.com");

    DefinedEnvironment(String url) {
        this.url = url;
    }

    final private String url;

    public String getUrl() {
        return url;
    }
}
