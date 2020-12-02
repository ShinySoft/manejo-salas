package com.example.manejosalas.controlador;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.manejosalas.DAO.UsuarioDAO;
import com.example.manejosalas.entidad.Usuario;

//import com.cristianruizblog.loginSecurity.entity.Authority;
//import com.cristianruizblog.loginSecurity.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioDAO userRepository;
	
    @Override
     public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
		
     //Buscar el usuario con el repositorio y si no existe lanzar una exepcion
     Usuario appUser = 
                 userRepository.findByCorreo(correo);//.orElseThrow(() -> new UsernameNotFoundException("No existe usuario"));
		
     
    //Mapear nuestra lista de Authority con la de spring security 
    List grantList = new ArrayList();
            
    
    if(appUser.getPerfil().equals("S")){
    	GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_SUPER");
    	grantList.add(grantedAuthority);
    }else if(appUser.getPerfil().equals("A")) {
    	GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
    	grantList.add(grantedAuthority);
    }else if(appUser.getPerfil().equals("U")) {
    	GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
    	grantList.add(grantedAuthority);
    }/*
    for (Authority authority: appUser.getAuthority()) {
        // ROLE_USER, ROLE_ADMIN,..
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
            grantList.add(grantedAuthority);
    }*/
		
    //Crear El objeto UserDetails que va a ir en sesion y retornarlo.
    UserDetails user = (UserDetails) new User(appUser.getCorreo(), appUser.getPassword(), appUser.getEstado(), true, true, true, grantList);    	 
         return user;
    }
}