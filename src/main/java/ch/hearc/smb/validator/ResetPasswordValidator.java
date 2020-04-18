package ch.hearc.smb.validator;

import ch.hearc.smb.dto.PasswordDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ResetPasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return PasswordDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PasswordDto passwordDto = (PasswordDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "NotEmpty");
        if (passwordDto.getNewPassword().length() < 8 || passwordDto.getNewPassword().length() > 32) {
            errors.rejectValue("newPassword", "Size.userForm.password");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPasswordConfirm", "NotEmpty");
        if (!passwordDto.getnewPasswordConfirm().equals(passwordDto.getNewPassword())) {
            errors.rejectValue("newPasswordConfirm", "Diff.userForm.passwordConfirm");
        }
    }

}
