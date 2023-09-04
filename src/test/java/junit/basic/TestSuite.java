package junit.basic;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({HelloJUnitTest.class,HelloJUnitTest2.class})
public class TestSuite {

}
