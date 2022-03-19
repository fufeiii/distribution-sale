package cn.fufeii.ds.common.util;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 基于hibernate的bean验证工具类。
 *
 * @author FuFei
 * @date 2022/3/19
 */
public final class ValidationUtil {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private ValidationUtil() {
    }

    /**
     * 验证实体对象是否符合通过验证规则
     *
     * @return ValidationUtil.Result对象，封装了验证结果和错误提示
     */
    public static <T> Result validate(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> cvSet = VALIDATOR.validate(object, groups);
        if (cvSet.isEmpty()) {
            return Result.success();
        }
        // 未通过验证，则获取第一个不符合规范的约束的信息。
        ConstraintViolation<T> next = cvSet.iterator().next();
        String path = next.getPropertyPath().toString();
        return Result.fail(path + next.getMessage());
    }

    /**
     * 该对象是否通过验证规则
     *
     * @return 通过true，不通过false
     */
    public static <T> boolean isValidate(T object, Class<?>... groups) {
        return VALIDATOR.validate(object, groups).isEmpty();
    }


    public static class Result {
        private final boolean ok;
        private final String msg;

        private Result(boolean ok, String msg) {
            this.ok = ok;
            this.msg = msg;
        }

        private static Result success() {
            return new Result(true, null);
        }

        private static Result fail(String message) {
            return new Result(false, message);
        }

        public boolean getOk() {
            return ok;
        }

        public String getMsg() {
            return msg;
        }

    }

}
