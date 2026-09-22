public record CreateRobotResult(boolean isSuccess, String message, RobotVacuum robot) {

    public static CreateRobotResult success(RobotVacuum robot) {
        return new CreateRobotResult(true, "Робот успешно создан и готов к работе.", robot);
    }

    public static CreateRobotResult failure(String message) {
        return new CreateRobotResult(false, message, null);
    }
}