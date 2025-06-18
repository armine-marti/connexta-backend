package org.example.phonebook.repository;

import org.example.phonebook.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
/**
 * Repository interface for {@link User} entity operations.
 *
 * <p>Extends {@link JpaRepository} to provide basic CRUD operations and defines
 * custom queries for finding users by email, soft deletion, and paginated search
 * with optional filtering by name or email.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.userStatus = 'DELETED_USER' WHERE u.id = :userId")
    void softDeleteById(@Param("userId") int userId);

    Page<User> findByEmailContainingIgnoreCase(String search, Pageable pageable);

    @Query("SELECT u FROM User u WHERE (:search IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<User> searchUsers(@Param("search") String search, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.email <> :email")
    Page<User> findAllExcludingEmail(@Param("email") String email, Pageable pageable);

    @Query("""
    SELECT u FROM User u 
    WHERE u.email <> :email AND (
        LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR
        LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%'))
    )
""")
    Page<User> searchUsersExcludingEmail(@Param("search") String search,
                                         @Param("email") String email,
                                         Pageable pageable);
}