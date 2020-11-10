package com.jadventure.game.queueprovider;

import java.util.Scanner;

public final class StandAloneQueueProvider extends QueueProvider {
    private Scanner console;

    protected StandAloneQueueProvider() {
        console = new Scanner(System.in);
    }

    @Override
    public void offer(String message) {
        super.offer(message);

        System.out.println(message);
    }

    @Override
    public String take() {
        return console.nextLine();
    }
}
