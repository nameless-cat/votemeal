package com.agromov.votemeal.matchers;

import com.agromov.votemeal.web.json.JsonUtil;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Created by A.Gromov on 13.06.2017.
 */
abstract public class TestMatcher<T> extends BaseMatcher<String>
{
    protected T expected;

    public TestMatcher(T expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Object actual) {
        return compare(expected, (String) actual);
    }

    protected abstract boolean compare(T expected, String actual);

    @Override
    public void describeTo(Description description) {
        description.appendText(JsonUtil.writeValue(expected));
    }
}
