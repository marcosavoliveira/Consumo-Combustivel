package User;

import EncryptClasses.EncryptMethods;

public class Owner extends Person {
    String driverLicense;
    String login;
    String password;
    Integer id;

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

    public Integer getId() {
        return id;
    }

    public void setPassword(String password, EncryptMethods encrypt) {
        this.password = encrypt.encrypt(password);
    }
}
