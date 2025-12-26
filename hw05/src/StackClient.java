public class StackClient {
    public static Stack flipped(Stack stack){
        // 容器
        Stack temp = new Stack();
        Stack result = new Stack();

        while (stack.size() > 0){
            int value = stack.pop();
            temp.push(value);
            result.push(value);
        }
        while (temp.size() > 0){
            int value = temp.pop();
            stack.push(value);
        }
        return result;
    }
}
