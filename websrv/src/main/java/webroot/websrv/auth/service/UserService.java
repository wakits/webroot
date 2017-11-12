package webroot.websrv.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import webroot.websrv.auth.entity.User;

@Service
public interface UserService {
  User save(User user);
  void delete(Long id);
  List<User> findAll();
  User findById(Long id);
  User findByEmail(String email);
  User findByUsername(String username);
  User findUserByToken(String token);
  List<User> findByAnyName(String name);
  void updateToken(String username, String token);
}