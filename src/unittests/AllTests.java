package unittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import unittests.Elements.LightTest;
import unittests.Elements.RecursiveTest;
import unittests.Elements.ShadowTest;
import unittests.Geometries.SquareTest;
import unittests.Renderer.RenderTest;

@RunWith(Suite.class)
@SuiteClasses({RenderTest.class,ShadowTest.class,LightTest.class,SquareTest.class,RecursiveTest.class})

public class AllTests {

}
