package helperclasses;

import models.DBInterface;

/**
 * Created by Håkon Ødegård Løvdal on 14/03/14.
 */
public class Login implements DBInterface {

	private final String user;
	private final String pw;


	public Login(String user, String pw) {
		this.user = user;
		this.pw = pw;
	}

	public String getUser() {
		return user;
	}

	public boolean checkLogin(String user, String pw) {
		if ((user.equals(this.user)) && (pw.equals(this.pw))) {
			return true;
		}
		return false;
	}

	@Override
	public void initialize() {

	}

	@Override
	public void refresh() {

	}

	@Override
	public void save() {

	}

	@Override
	public void delete() {

	}
}
