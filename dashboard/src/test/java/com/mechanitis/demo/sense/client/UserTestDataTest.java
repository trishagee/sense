package com.mechanitis.demo.sense.client;

import com.mechanitis.demo.sense.client.user.UserTestData;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@SuppressWarnings("deprecation")
public class UserTestDataTest {
    @Test
    public void shouldUSeDeprecatedProperly() {
        UserTestData userTestData = new UserTestData();
        assertThat(userTestData, is(notNullValue()));
    }

}