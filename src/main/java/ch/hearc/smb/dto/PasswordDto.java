package ch.hearc.smb.dto;

public class PasswordDto {

    private String newPassword;

    private String newPasswordConfirm;

    private String token;

    private long id;

    public PasswordDto(long id, String token) {
        this.id = id;
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String oldPassword) {
        this.newPassword = oldPassword;
    }

    public String getnewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setnewPasswordConfirm(String newPassword) {
        this.newPasswordConfirm = newPassword;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
