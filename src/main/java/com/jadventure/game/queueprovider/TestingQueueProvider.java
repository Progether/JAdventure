package com.jadventure.game.queueprovider;

import java.util.ArrayDeque;
import java.util.Deque;

public final class TestingQueueProvider extends QueueProvider {
    private Deque<String> messageHistory;
    private Deque<String> inputQueue;

    protected TestingQueueProvider() {
        // initialise testing queues
        resetTestingQueues();
    }

    public void resetTestingQueues() {
        messageHistory = new ArrayDeque<>();
        inputQueue = new ArrayDeque<>();
    }

    public String getLastMessage() {
        return messageHistory.peekLast();
    }

    public String[] getMessageHistory() {
        return messageHistory.toArray(new String[0]);
    }

    public String getMessageHistoryAsString() {
        // concatenate message history into a single string
        StringBuilder sb = new StringBuilder();

        for (String msg : messageHistory) {
            sb.append(msg);
            sb.append("\n");
        }

        return sb.toString();
    }

    public void pushToInputQueue(String input) {
        inputQueue.push(input);
    }

    @Override
    public void offer(String message) {
        super.offer(message);

        messageHistory.push(message);
    }

    @Override
    public String take() {
        return inputQueue.pop();
    }
}
