/*
 * Copyright 2015-2016 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.converter.DoNotConvert;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a domain-qualified resource name; for example the identifier for a text index or user store.
 * <p>
 * This class implements {@link Serializable} to facilitate easier caching
 */
@Data
@DoNotConvert
public class ResourceName implements ResourceIdentifier {
    public static final String PUBLIC_INDEXES_DOMAIN = "PUBLIC_INDEXES";

    public static final ResourceName WIKI_CHI = new ResourceName(PUBLIC_INDEXES_DOMAIN, "wiki_chi");
    public static final ResourceName WIKI_ENG = new ResourceName(PUBLIC_INDEXES_DOMAIN, "wiki_eng");
    public static final ResourceName WIKI_FRA = new ResourceName(PUBLIC_INDEXES_DOMAIN, "wiki_fra");
    public static final ResourceName WIKI_GER = new ResourceName(PUBLIC_INDEXES_DOMAIN, "wiki_ger");
    public static final ResourceName WIKI_ITA = new ResourceName(PUBLIC_INDEXES_DOMAIN, "wiki_ita");
    public static final ResourceName WIKI_SPA = new ResourceName(PUBLIC_INDEXES_DOMAIN, "wiki_spa");
    public static final ResourceName WORLD_FACTBOOK = new ResourceName(PUBLIC_INDEXES_DOMAIN, "world_factbook");
    public static final ResourceName NEWS_ENG = new ResourceName(PUBLIC_INDEXES_DOMAIN, "news_eng");
    public static final ResourceName NEWS_FRA = new ResourceName(PUBLIC_INDEXES_DOMAIN, "news_fra");
    public static final ResourceName NEWS_GER = new ResourceName(PUBLIC_INDEXES_DOMAIN, "news_ger");
    public static final ResourceName NEWS_ITA = new ResourceName(PUBLIC_INDEXES_DOMAIN, "news_ita");
    public static final ResourceName ARXIV = new ResourceName(PUBLIC_INDEXES_DOMAIN, "arxiv");
    public static final ResourceName PATENTS = new ResourceName(PUBLIC_INDEXES_DOMAIN, "patents");

    private static final Pattern ESCAPE_PATTERN = Pattern.compile("([\\\\:])");

    private static final Pattern UNESCAPE_BACKSLASH_PATTERN = Pattern.compile("\\\\");
    private static final Pattern UNESCAPE_COLON_PATTERN = Pattern.compile("\\\\:");

    // Pattern for breaking a domain:name string into a domain and a name
    private static final Pattern SPLIT_PATTERN = Pattern.compile("((?:[^\\\\:]|\\\\[\\\\:])+):(.+)");

    private static final long serialVersionUID = -3170086101527611633L;

    /**
     * The domain of the resource. Must be non-null
     *
     * @serial
     */
    private final String domain;

    /**
     * The name of the resource. Must be non-null
     *
     * @serial
     */
    private final String name;

    /**
     * Construct a ResourceName from a colon-separated and escaped domain and name.
     *
     * @param identifier Colon-separated domain and name
     */
    public ResourceName(final String identifier) {
        if (StringUtils.isEmpty(identifier)) {
            throw new IllegalArgumentException("Identifier must not be empty");
        }

        final Matcher matcher = SPLIT_PATTERN.matcher(identifier);

        // The matches method attempts to match the entire string against the pattern
        if (matcher.matches()) {
            domain = unescapeComponent(matcher.group(1));
            name = unescapeComponent(matcher.group(2));
        } else {
            throw new IllegalArgumentException("Identifier was invalid");
        }
    }

    /**
     * Construct a ResourceName from a domain and a name.
     *
     * @param domain The resource domain
     * @param name   The resource name
     */
    @JsonCreator
    public ResourceName(
            @JsonProperty("domain") final String domain,
            @JsonProperty("name") final String name
    ) {
        validate(domain, name);

        this.domain = domain;
        this.name = name;
    }

    /**
     * Escapes the domain and name and joins them with a colon.
     *
     * @return The HOD resource name string
     */
    @Override
    public String toString() {
        return escapeComponent(domain) + SEPARATOR + escapeComponent(name);
    }

    // HOD resource names (text index names, domain names etc) must have : escaped to \: and \ escaped to \\ when
    // combined into a resource name.
    private static String escapeComponent(final String input) {
        return ESCAPE_PATTERN.matcher(input).replaceAll("\\\\$1");
    }

    private static String unescapeComponent(final String input) {
        return input.replace("\\:", ":").replace("\\\\", "\\");
    }

    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();

        validate(domain, name);
    }

    private void validate(final String domain, final String name) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null");
        }

        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
    }
}
