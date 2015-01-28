package com.mechanitis.demo.sense.user

import spock.lang.Specification

class UserLocatorSpecification extends Specification {
    def 'should return the user location'() {
        when:
        def location = UserLocator.getLocation(EXAMPLE_INPUT)

        then:
        location == 'ATL, GA'
    }

    private static final String EXAMPLE_INPUT =
            "\"user\":{\"id\":36075321,\"id_str\":\"36075321\",\"name\":\"Toni Alexander\",\"screen_name\":\"cupcakecutie07\"," +
            "\"location\":\"ATL, GA\",\"url\":null,\"description\":\"I'm a laid back chick who loves to have fun\",\"protected\":false,\"verified\":false," +
            "\"followers_count\":41,\"friends_count\":118,\"listed_count\":1,\"favourites_count\":5,\"statuses_count\":2555,\"created_at\":\"Tue Apr 28 14:17:20 +0000 2009\"," +
            "\"utc_offset\":-18000,\"time_zone\":\"Eastern Time (US & Canada)\",\"geo_enabled\":false,\"lang\":\"en\",\"contributors_enabled\":false,\"is_translator\":false," +
            "\"profile_background_color\":\"FF6699\",\"profile_background_image_url\":\"http:\\/\\/abs.twimg.com\\/images\\/themes\\/theme11\\/bg.gif\"," +
            "\"profile_background_image_url_https\":\"https:\\/\\/abs.twimg.com\\/images\\/themes\\/theme11\\/bg.gif\",\"profile_background_tile\":true," +
            "\"profile_link_color\":\"B40B43\",\"profile_sidebar_border_color\":\"CC3366\",\"profile_sidebar_fill_color\":\"E5507E\",\"profile_text_color\":\"362720\"," +
            "\"profile_use_background_image\":true,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/3720790185\\/d65b1fe7b8245a389901dab26c7ec77d_normal.jpeg\"," +
            "\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/3720790185\\/d65b1fe7b8245a389901dab26c7ec77d_normal.jpeg\",\"default_profile\":false," +
            "\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null";

}
