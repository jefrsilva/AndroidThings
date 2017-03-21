package com.jefersonsilva.androidthings;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.util.ArrayList;
import java.util.List;

// Available GPIO: [BCM12, BCM13, BCM16, BCM17, BCM18, BCM19, BCM20, BCM21, BCM22, BCM23, BCM24, BCM25, BCM26, BCM27, BCM4, BCM5, BCM6]


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "FOCAFOCA";

    private List<Led> leds = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();

    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int BLUE = 2;
    private static final int YELLOW = 3;

    private static final String GPIO_PIN_YELLOW_LED = "BCM4";
    private static final String GPIO_PIN_BLUE_LED = "BCM17";
    private static final String GPIO_PIN_GREEN_LED = "BCM27";
    private static final String GPIO_PIN_RED_LED = "BCM22";

    private static final String GPIO_PIN_RED_BUTTON = "BCM26";
    private static final String GPIO_PIN_GREEN_BUTTON = "BCM13";
    private static final String GPIO_PIN_BLUE_BUTTON = "BCM6";
    private static final String GPIO_PIN_YELLOW_BUTTON = "BCM5";

    public Handler handler = new Handler();

    private GeniusRunnable geniusRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PeripheralManagerService service = new PeripheralManagerService();
        Log.d("FOCAFOCA", "Available GPIO: " + service.getGpioList());

        leds.add(new Led(service, GPIO_PIN_RED_LED));
        leds.add(new Led(service, GPIO_PIN_GREEN_LED));
        leds.add(new Led(service, GPIO_PIN_BLUE_LED));
        leds.add(new Led(service, GPIO_PIN_YELLOW_LED));

        this.geniusRunnable = new GeniusRunnable(leds);
        new Thread(geniusRunnable).start();

        buttons.add(new Button(service, GPIO_PIN_RED_BUTTON, RED));
        buttons.add(new Button(service, GPIO_PIN_GREEN_BUTTON, GREEN));
        buttons.add(new Button(service, GPIO_PIN_BLUE_BUTTON, BLUE));
        buttons.add(new Button(service, GPIO_PIN_YELLOW_BUTTON, YELLOW));

        for (Button button : buttons) {
            button.addCallback(new ButtonCallback(geniusRunnable, button.getColor()));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // handler.removeCallbacks(geniusRunnable);

        for (Button button : buttons) {
            button.close();
        }

        for (Led led : leds) {
            led.close();
        }
    }
}
