package com.ktws.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import com.ktws.Entity.MyUser;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserPrincipal implements UserDetails{

	private static final long serialVersionUID = 9128185410645444701L;
	private MyUser user;

    public MyUserPrincipal(MyUser user1) {
        this.user = user1;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
