package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;


/**
 * TODO: 
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some example test cases.
 * Write your own unit tests to test against IntQueue interface with specification testing method 
 * using mQueue = new LinkedIntQueue();
 * 
 * 2. 
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
        // mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        for (int i : testList) {
            mQueue.enqueue(i);
        }
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(i), mQueue.peek());
            mQueue.dequeue();
        }
    }

    @Test
    public void testEnqueue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(mQueue.dequeue(), testList.get(i));
        }
    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }
    
    @Test
    public void testClear() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testEmptyDequeue() {
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testOrderAfterResizing() {
        // Exceed the capacity and dequeue some elements to test the order
        for (int i = 0; i < 9; i++) {
            assertTrue(mQueue.enqueue(i));
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
        for (int i = 9; i < 20; i++) {
            assertTrue(mQueue.enqueue(i));
        }

        assertEquals(15, mQueue.size());


        // Check if elements are in correct order after resizing
        for (int i = 5; i < 20; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
        assertTrue(mQueue.isEmpty());
    }


}
