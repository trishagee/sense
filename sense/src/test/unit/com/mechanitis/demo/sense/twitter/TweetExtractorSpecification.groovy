package com.mechanitis.demo.sense.twitter

import spock.lang.Specification

class TweetExtractorSpecification extends Specification {
    private static final String EXAMPLE_INPUT = "tweet = {\"created_at\":\"Tue Jan 27 12:37:11 +0000 2015\"," +
            "\"id\":560053908144275456,\"id_str\":\"560053908144275456\"," +
            "\"text\":\"A simplistic approach to your life is a healthy antidote to yo... More for Sagittarius http:\\/\\/t.co\\/cRS9M0bneh\",\"source\":\"\\u003ca href=\\\"http:\\/\\/www.twittascope.com\\\" rel=\\\"nofollow\\\"\\u003eTwittascope\\u003c\\/a\\u003e\"," +
            "\"truncated\":false,\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":null,\"in_reply_to_user_id_str\":null," +
            "\"in_reply_to_screen_name\":null," +
            "\"user\":{\"id\":36075321,\"id_str\":\"36075321\",\"name\":\"Toni Alexander\",\"screen_name\":\"cupcakecutie07\"," +
            "\"location\":\"ATL, GA\",\"url\":null,\"description\":\"I'm a laid back chick who loves to have fun\",\"protected\":false,\"verified\":false," +
            "\"followers_count\":41,\"friends_count\":118,\"listed_count\":1,\"favourites_count\":5,\"statuses_count\":2555,\"created_at\":\"Tue Apr 28 14:17:20 +0000 2009\"," +
            "\"utc_offset\":-18000,\"time_zone\":\"Eastern Time (US & Canada)\",\"geo_enabled\":false,\"lang\":\"en\",\"contributors_enabled\":false,\"is_translator\":false," +
            "\"profile_background_color\":\"FF6699\",\"profile_background_image_url\":\"http:\\/\\/abs.twimg.com\\/images\\/themes\\/theme11\\/bg.gif\"," +
            "\"profile_background_image_url_https\":\"https:\\/\\/abs.twimg.com\\/images\\/themes\\/theme11\\/bg.gif\",\"profile_background_tile\":true," +
            "\"profile_link_color\":\"B40B43\",\"profile_sidebar_border_color\":\"CC3366\",\"profile_sidebar_fill_color\":\"E5507E\",\"profile_text_color\":\"362720\"," +
            "\"profile_use_background_image\":true,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/3720790185\\/d65b1fe7b8245a389901dab26c7ec77d_normal.jpeg\"," +
            "\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/3720790185\\/d65b1fe7b8245a389901dab26c7ec77d_normal.jpeg\",\"default_profile\":false," +
            "\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null}," +
            "\"geo\":null,\"coordinates\":null,\"place\":null," +
            "\"contributors\":null,\"retweet_count\":0,\"favorite_count\":0,\"entities\":{\"hashtags\":[],\"trends\":[],\"urls\":[{\"url\":\"http:\\/\\/t.co\\/cRS9M0bneh\"," +
            "\"expanded_url\":\"http:\\/\\/bit.ly\\/yibOac\",\"display_url\":\"bit.ly\\/yibOac\",\"indices\":[87,109]}],\"user_mentions\":[],\"symbols\":[]},\"favorited\":false," +
            "\"retweeted\":false,\"possibly_sensitive\":false,\"filter_level\":\"low\",\"lang\":\"en\",\"timestamp_ms\":\"1422362231657\"}";

    def 'should return the tweet itself from the full Twitter content'() {
        when:
        def tweetContent = TweetParser.getTweetMessageFrom(EXAMPLE_INPUT)

        then:
        tweetContent == "A simplistic approach to your life is a healthy antidote to yo... More for Sagittarius http:\\/\\/t.co\\/cRS9M0bneh"
    }

    def 'should return the user twitter handle'() {
        when:
        def location = TweetParser.getTwitterHandle(EXAMPLE_INPUT)

        then:
        location == 'cupcakecutie07'
    }

}
