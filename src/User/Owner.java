package User;

import EncryptClasses.EncryptMethods;
import EncryptClasses.SHA256;

public class Owner extends Person{
    String driverLicense;
    String login;
    String password;

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password, EncryptMethods encrypt) {
        this.password = encrypt.encrypt(password);
    }
}
