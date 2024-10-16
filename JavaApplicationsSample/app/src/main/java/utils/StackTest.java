package utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Stack;

@Slf4j
public class StackTest {

    // Pushing element on the top of the stack
    static void stack_push(Stack<Integer> stack) {
        for (int i = 0; i < 5; i++) {
            stack.push(i);
        }
    }

    // Popping element from the top of the stack
    static void stack_pop(Stack<Integer> stack) {
        log.info("Pop Operation:");

        while (!stack.isEmpty()) {
            Integer y = stack.pop();
            log.info(String.valueOf(y));
        }
    }

    // Displaying element on the top of the stack
    static void stack_peek(Stack<Integer> stack) {
        Integer element = stack.peek();
        log.info("Element on stack top: " + element);
    }

    // Searching element in the stack
    static void stack_search(Stack<Integer> stack, Integer element) {
        Integer pos = stack.search(element);

        if (pos == -1)
            log.info("Element not found");
        else
            log.info("Element is found at position: " + pos);
    }


    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<Integer>();

        stack_push(stack);
        stack_pop(stack);
        stack_push(stack);
        stack_peek(stack);
        stack_search(stack, 2);
        stack_search(stack, 6);
    }

}
