package dev.uedercardoso.snack.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.uedercardoso.snack.domain.model.person.Person;
import dev.uedercardoso.snack.domain.repositories.PersonRepository;

@Service
public class StartupDetailsService implements UserDetailsService {

	@Autowired
	private PersonRepository personRepository;
	
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Person person = null;
    	try {
    		person = personRepository.findByUsername(username);
        } catch(NullPointerException n) {
        	n.printStackTrace();
        } catch(Exception e) {
        	e.printStackTrace();
        }
    	
        if (person == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");//Isso aqui é mostrado apenas no log
        }
        
        Set<GrantedAuthority> perfis = new HashSet<GrantedAuthority>();
        perfis.add(new SimpleGrantedAuthority(person.getProfile().toString()));
        
        return new User(person.getUsername(), person.getPassword(), perfis);
	}

	
	
}
