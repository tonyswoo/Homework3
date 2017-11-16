package core.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import core.api.IAdmin;
import core.api.impl.Admin;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestAdminMocked {

	@Mock
    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = Mockito.mock(Admin.class);
    }

    @Test
    public void testMakeClassCorrect() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        Mockito.verify(this.admin, Mockito.calls(1)).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
    }

    @Test
    public void testMakeClassWrongYear() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        Mockito.verify(this.admin, Mockito.never()).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
    }
}
