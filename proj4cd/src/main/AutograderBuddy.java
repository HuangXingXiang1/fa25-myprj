package main;

import browser.NgordnetQueryHandler;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String synsetFile,
            String hyponymFile
    )
    {
       return new HyponymsHandler(new WordNet(synsetFile, hyponymFile));
    }
}
