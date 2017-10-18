package com.github.ledoyen.ice.parser;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.CharStream;

import com.google.common.collect.ImmutableList;

// TODO cleanup
class LexerLookup {

    private static final Comparator<String> LONGEST_FIRST = (s1, s2) -> s2.length() - s1.length();

    private final Map<Integer, Set<String>> tokenIdTermsMap = new HashMap<>();

    public void put(final int tokenId, Iterable<String> tokenList) {
        this.tokenIdTermsMap.put(tokenId, new LinkedHashSet(ImmutableList.sortedCopyOf(LONGEST_FIRST, tokenList)));
    }

    public boolean contains(final int tokenId, final CharStream input) {

        boolean contains = false;

        if (!tokenIdTermsMap.containsKey(tokenId)) {
            return contains;
        }

        Set<String> terms = tokenIdTermsMap.get(tokenId);

        for (String term : terms) {

            contains = ahead(term, input);

            if (contains) {
                break;
            }
        }

        return contains;
    }

    private boolean ahead(final String word, final CharStream input) {

        for (int i = 0; i < word.length(); i++) {

            char wordChar = word.charAt(i);
            int inputChar = input.LA(i + 1);

            if (inputChar != wordChar) {
                return false;
            }
        }

        input.seek(input.index() + word.length() - 1);

        return true;
    }
}
