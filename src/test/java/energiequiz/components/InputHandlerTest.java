package energiequiz.components;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.plugin.mock.platform.MockPlatform;
import com.pi4j.plugin.mock.provider.gpio.analog.MockAnalogInputProvider;
import com.pi4j.plugin.mock.provider.gpio.analog.MockAnalogOutputProvider;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalInput;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalInputProvider;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalOutputProvider;
import com.pi4j.plugin.mock.provider.i2c.MockI2CProvider;
import com.pi4j.plugin.mock.provider.pwm.MockPwmProvider;
import com.pi4j.plugin.mock.provider.serial.MockSerialProvider;
import com.pi4j.plugin.mock.provider.spi.MockSpiProvider;
//import energiequiz.components.Joystick;
//import energiequiz.components.base.PIN;
import energiequiz.controller.Game;
import energiequiz.io.InputHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 *tests for the InputHandlerTest class.
 */

public class InputHandlerTest {

//    protected Context pi4j;
//    private InputHandler inputHandler;
//    private Game game;
//
//    private Joystick joystick;
//
//    private MockDigitalInput diNorth;
//    private MockDigitalInput diEast;
//    private MockDigitalInput diSouth;
//    private MockDigitalInput diWest;
//
//    private final PIN pinNorth = PIN.D21;
//    private final PIN pinEast  = PIN.D22;
//    private final PIN pinSouth = PIN.D23;
//    private final PIN pinWest  = PIN.D24;
//
//    @BeforeEach
//    public final void setUpPi4J() {
//        pi4j = Pi4J.newContextBuilder()
//                .add(new MockPlatform())
//                .add(
//                        MockAnalogInputProvider.newInstance(),
//                        MockAnalogOutputProvider.newInstance(),
//                        MockSpiProvider.newInstance(),
//                        MockPwmProvider.newInstance(),
//                        MockSerialProvider.newInstance(),
//                        MockI2CProvider.newInstance(),
//                        MockDigitalInputProvider.newInstance(),
//                        MockDigitalOutputProvider.newInstance()
//                )
//                .build();
//        game = new Game();
//        joystick = new Joystick(pi4j, pinNorth, pinEast, pinSouth, pinWest);
//        diNorth  = joystick.mockNorth();
//        diEast   = joystick.mockEast();
//        diSouth  = joystick.mockSouth();
//        diWest   = joystick.mockWest();
//        //inputHandler = new InputHandler(game, pi4j);
//    }

    /*@Test
    private void testMoveUp() {

    }*/

//    @AfterEach
//    public void tearDownPi4J() {
//        pi4j.shutdown();
//    }
}
