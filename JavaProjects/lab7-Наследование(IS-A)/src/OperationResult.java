public record OperationResult<T>(boolean isSuccess, String message, T data) {
    public static <T> OperationResult<T> success(String msg, T data) {
        return new OperationResult<>(true, msg, data);
    }
    public static <T> OperationResult<T> fail(String msg) {
        return new OperationResult<>(false, msg, null);
    }
}